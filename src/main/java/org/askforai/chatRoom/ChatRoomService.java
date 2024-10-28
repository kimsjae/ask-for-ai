package org.askforai.chatRoom;

import org.askforai._core.exception.custom.Exception403;
import org.askforai.user.User;
import org.askforai.user.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
	
	private final ChatRoomRepository chatRoomRepository;
	private final UserRepository userRepository;
	
	public ChatRoom createChatRoom() {
	    String username = SecurityContextHolder.getContext().getAuthentication().getName();

	    // 사용자 정보 조회
	    User user = userRepository.findByUsername(username)
	            .orElseThrow(() -> new Exception403("유효하지 않은 사용자입니다."));

	    // 새 채팅방 생성
	    ChatRoom chatRoom = ChatRoom.builder()
	            .user(user)
	            .build();

	    return chatRoomRepository.save(chatRoom);
	}

}
