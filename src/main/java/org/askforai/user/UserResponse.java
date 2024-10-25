package org.askforai.user;

import lombok.AllArgsConstructor;
import lombok.Data;

public class UserResponse {
	
	@Data
	@AllArgsConstructor
	public static class SigninDTO {
		private String accessToken;
		private String refreshToken;
	}

}
