package org.askforai.chatRoom;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat-rooms")
public class ChatRoomController {

	private final ChatRoomService chatRoomService;
	
	// 새 채팅방 만들기
	@PostMapping
	public ResponseEntity<?> createChatRoom() {
		ChatRoom chatRoom = chatRoomService.createChatRoom();
		
		return ResponseEntity.status(HttpStatus.CREATED).body(chatRoom);
	}
	
	// 채팅방 목록
	@GetMapping("/me")
	public ResponseEntity<?> getChatRoomsByUserId() {
		List<ChatRoom> chatRoomList = chatRoomService.getChatRoomsByUserId();
		
		return ResponseEntity.status(HttpStatus.OK).body(chatRoomList);
	}
	
}
