package org.askforai.message;

import org.askforai.chatRoom.ChatRoom;
import org.askforai.chatRoom.ChatRoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MessageController {
	private final MessageService messageService;
	private final ChatRoomService chatRoomService;
	private final OpenAIService openAIService;
	
	
	@PostMapping("/messages")
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

}
