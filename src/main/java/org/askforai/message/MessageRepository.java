package org.askforai.message;

import java.util.List;

import org.askforai.chatRoom.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {

	List<Message> findByChatRoom(ChatRoom chatRoom);
	
}
