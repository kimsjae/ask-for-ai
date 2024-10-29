package org.askforai.message;

import java.time.LocalDateTime;

import lombok.Data;

public class MessageRequest {

	@Data
	public static class MessageDTO {
		private Long chatRoomId;
		private Sender sender;
		private String content;
		private LocalDateTime timestamp;
	}
	
}
