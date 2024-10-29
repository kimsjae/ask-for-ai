package org.askforai.message;

import org.askforai.chatRoom.ChatRoom;
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
	
}
