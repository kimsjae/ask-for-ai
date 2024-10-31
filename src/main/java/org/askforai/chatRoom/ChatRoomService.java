package org.askforai.chatRoom;

import java.util.List;

import org.askforai._core.exception.custom.Exception403;
import org.askforai._core.exception.custom.Exception404;
import org.askforai.message.Message;
import org.askforai.message.MessageRepository;
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
	private final MessageRepository messageRepository;
	
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
	
	// 해당 채팅방 조회
	@Transactional(readOnly = true)
	public ChatRoom getChatRoomById(Long chatRoomId) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
					.orElseThrow(() -> new Exception404("존재하지 않는 채팅방입니다."));
			
			return chatRoom;
			
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
	
	// 즐겨찾는 채팅방 목록보기
	@Transactional(readOnly = true)
	public List<ChatRoom> getFavoriteChatRoomByUserId() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			Long userId = ((CustomUserDetails) auth.getPrincipal()).getId();
			
			List<ChatRoom> favoriteChatRoomList = chatRoomRepository.findFavoriteChatRoomByUserId(userId);
			
			return favoriteChatRoomList;
		} else {
			throw new Exception403("권한 없음.");
		}
	}
	
	// 채팅방 즐겨찾기 on/off
	public void toggleFavoriteStatus(ChatRoomRequest.ChatRoomIdDTO reqDTO) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			ChatRoom chatRoom = chatRoomRepository.findById(reqDTO.getChatRoomId())
					.orElseThrow(() -> new Exception404("존재하지 않는 채팅방입니다."));
			
			chatRoom.setFavorite(!chatRoom.isFavorite());
			
			chatRoomRepository.save(chatRoom);
			
		} else {
			throw new Exception403("권한 없음.");
		}
	}
	
	// 채팅방 이름 수정하기
	public void updateChatRoomTitle(ChatRoomRequest.RenameDTO reqDTO) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			ChatRoom chatRoom = chatRoomRepository.findById(reqDTO.getChatRoomId())
					.orElseThrow(() -> new Exception404("존재하지 않는 채팅방입니다."));
			
			chatRoom.setTitle(reqDTO.getTitle());
			
			chatRoomRepository.save(chatRoom);
			
		} else {
			throw new Exception403("권한 없음.");
		}
	}
	
	// 채팅방 삭제하기
	public void deleteChatRoom(ChatRoomRequest.ChatRoomIdDTO reqDTO) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			List<Message> messages = messageRepository.findByChatRoomId(reqDTO.getChatRoomId());
			if (!messages.isEmpty()) {
				messageRepository.deleteAll(messages);
			}
			
			chatRoomRepository.deleteById(reqDTO.getChatRoomId());
			
		} else {
			throw new Exception403("권한 없음.");
		}
	}

}
