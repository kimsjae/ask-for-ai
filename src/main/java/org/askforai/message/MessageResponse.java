package org.askforai.message;

import lombok.Data;

public class MessageResponse {

	@Data
	public static class RespDTO {
		private String userMessage;
		private String aiResponse;
	}
	
	@Data
	public static class MessagesDTO {
		private Long chatRoomId;
		private String title;
		private Sender sender;
		private String content;
		private String timestamp;
	}
	
}
