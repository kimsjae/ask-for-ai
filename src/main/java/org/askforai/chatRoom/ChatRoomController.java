package org.askforai.chatRoom;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	
}
