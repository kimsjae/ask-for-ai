package org.askforai.chatRoom;

import java.util.List;

import org.askforai._core.exception.custom.Exception403;
import org.askforai.user.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatRoomService {
	
	private final ChatRoomRepository chatRoomRepository;
	
	public ChatRoom createChatRoom() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			Long userId = ((CustomUserDetails) auth.getPrincipal()).getId();
			
			ChatRoom chatRoom = ChatRoom.builder()
					.userId(userId)
					.build();
			
			return chatRoomRepository.save(chatRoom);
		} else {
			throw new Exception403("권한 없음.");
		}
	}
	
	// 채팅방 목록
	@Transactional(readOnly = true)
	public List<ChatRoom> getChatRoomsByUserId() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			Long userId = ((CustomUserDetails) auth.getPrincipal()).getId();
			
			List<ChatRoom> chatRoomList = chatRoomRepository.findChatRoomsByUserId(userId);
			
			return chatRoomList;
		} else {
			throw new Exception403("권한 없음.");
		}
	}

}
