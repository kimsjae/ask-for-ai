package org.askforai.chatRoom;

import lombok.Data;

public class ChatRoomRequest {
	
	@Data
	public static class ChatRoomIdDTO {
		private Long chatRoomId;
	}
	
	@Data
	public static class RenameDTO {
		private Long chatRoomId;
		private String title;
	}
	
}
