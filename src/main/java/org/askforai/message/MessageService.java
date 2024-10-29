package org.askforai.message;

import java.util.List;

import org.askforai._core.exception.custom.Exception403;
import org.askforai.chatRoom.ChatRoom;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MessageService {
	private final MessageRepository messageRepository;
	
	public void saveMessage(ChatRoom chatRoom, Sender sender, String content) {
		Message message = Message.builder()
				.chatRoom(chatRoom)
				.sender(sender)
				.content(content)
				.build();
		messageRepository.save(message);
	}
	
	// 채팅방 별 메시지 조회
	@Transactional(readOnly = true)
	public List<Message> getMessage(MessageRequest.ChatRoomIdDTO reqDTO) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			List<Message> messageList = messageRepository.findByChatRoomId(reqDTO.getChatRoomId());
			
			return messageList;
		} else {
			throw new Exception403("권한 없음.");
		}
	}
	
}
