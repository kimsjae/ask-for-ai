package org.askforai.chatRoom;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	// 즐겨찾는 채팅방 목록보기
	@GetMapping("/me/favorite")
	public ResponseEntity<?> getFavoriteChatRoomByUserId() {
		List<ChatRoom> favoriteChatRoomList = chatRoomService.getFavoriteChatRoomByUserId();
		
		return ResponseEntity.status(HttpStatus.OK).body(favoriteChatRoomList);
	}
	
	// 채팅방 즐겨찾기 on/off
	@PatchMapping("/me/favorite")
	public ResponseEntity<?> toggleFavoriteStatus(@RequestBody ChatRoomRequest.FavoriteDTO reqDTO) {
		chatRoomService.toggleFavoriteStatus(reqDTO);
		
		return ResponseEntity.ok("즐겨찾기 변경 완료");
	}
	
	// 채팅방 이름 수정하기
	@PatchMapping("/me/title")
	public ResponseEntity<?> updateTitle(@RequestBody ChatRoomRequest.RenameDTO reqDTO) {
		chatRoomService.updateChatRoomTitle(reqDTO);
		
		return ResponseEntity.ok("채팅방 이름 변경 완료");
	}
	
}
