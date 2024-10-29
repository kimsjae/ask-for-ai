package org.askforai.message;

import java.util.List;

import org.askforai.chatRoom.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MessageRepository extends JpaRepository<Message, Long> {

	List<Message> findByChatRoom(ChatRoom chatRoom);
	
	// 채팅방 별 메시지 조회
	@Query("SELECT m FROM Message m WHERE m.chatRoom.id = :chatRoomId")
	List<Message> findByChatRoomId(@Param("chatRoomId") Long chatRoomId);
	
}
