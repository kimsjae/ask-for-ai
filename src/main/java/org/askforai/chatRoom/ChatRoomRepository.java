package org.askforai.chatRoom;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
	
	@Query("SELECT c from ChatRoom c where c.userId = :userId")
	List<ChatRoom> findChatRoomsByUserId(@Param("userId") Long userId);
	
	@Query("SELECT c from ChatRoom c where c.userId = :userId AND c.isFavorite = true")
	List<ChatRoom> findFavoriteChatRoomByUserId(@Param("userId") Long userId);
	
}
