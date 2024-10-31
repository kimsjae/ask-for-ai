package org.askforai.message;

import java.time.LocalDateTime;

import org.askforai.chatRoom.ChatRoom;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "messages")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private ChatRoom chatRoom;
	
	@Enumerated(EnumType.ORDINAL)
	private Sender sender;
	
	@Column(nullable = false, columnDefinition = "TEXT")
	private String content;
	
	@Column(updatable = false)
    @CreationTimestamp
	private LocalDateTime timestamp;
	
	public Message(Sender sender, String content) {
        this.sender = sender;
        this.content = content;
    }
	
}
