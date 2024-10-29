package org.askforai.message;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.askforai.chatRoom.ChatRoom;
import org.askforai.chatRoom.ChatRoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/messages")
public class MessageController {
	private final MessageService messageService;
	private final ChatRoomService chatRoomService;
	private final OpenAIService openAIService;
	
	
	@PostMapping
	public ResponseEntity<?> saveMessage(@RequestBody MessageRequest.MessageDTO reqDTO) {
		ChatRoom chatRoom = chatRoomService.getChatRoomById(reqDTO.getChatRoomId());
		messageService.saveMessage(chatRoom, reqDTO.getSender(), reqDTO.getContent());
		
		String aiResponse = openAIService.generateResponse(reqDTO.getContent());
		messageService.saveMessage(chatRoom, Sender.AI, aiResponse);
		
		MessageResponse.RespDTO respDTO = new MessageResponse.RespDTO();
		respDTO.setUserMessage(reqDTO.getContent());
		respDTO.setAiResponse(aiResponse);
		
		return ResponseEntity.status(HttpStatus.OK).body(respDTO);
	}
	
	// 채팅방 별 메시지 조회
	@GetMapping
	public ResponseEntity<List<MessageResponse.MessagesDTO>> getMessage(@RequestBody MessageRequest.ChatRoomIdDTO reqDTO) {
	    List<Message> messageList = messageService.getMessage(reqDTO);
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
	    
	    List<MessageResponse.MessagesDTO> messagesDTOList = messageList.stream().map(message -> {
	        MessageResponse.MessagesDTO resDTO = new MessageResponse.MessagesDTO();
	        resDTO.setChatRoomId(message.getChatRoom().getId());
	        resDTO.setTitle(message.getChatRoom().getTitle());
	        resDTO.setSender(message.getSender());
	        resDTO.setContent(message.getContent());
	        resDTO.setTimestamp(message.getTimestamp().format(formatter));
	        return resDTO;
	    }).collect(Collectors.toList());

	    return ResponseEntity.status(HttpStatus.OK).body(messagesDTOList);
	}
	

}
