package org.askforai.message;

import lombok.Data;

public class MessageResponse {

	@Data
	public static class RespDTO {
		private String userMessage;
		private String aiResponse;
	}
	
}
