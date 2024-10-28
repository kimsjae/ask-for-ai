package org.askforai.chatRoom;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "chatRooms")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoom {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ColumnDefault("'새로운 채팅방'")
	@Builder.Default
	private String title = "새로운 채팅방";
	
	private boolean isFavorite;
	
	private Long userId;
	
//	@ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "user_id", nullable = false)
//	private User user;
	
}
