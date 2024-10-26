package org.askforai.user;

import lombok.AllArgsConstructor;
import lombok.Data;

public class UserResponse {
	
	@Data
	@AllArgsConstructor
	public static class InfoDTO {
		private Long id;
        private String username;
        private String name;
        private String email;
	}

}
