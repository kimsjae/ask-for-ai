package org.askforai.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

public class UserRequest {

	@Data
	public static class SignupDTO {
		@NotBlank(message = "username을 입력하세요.")
		@Size(min = 4, max = 12, message = "username은 4자 이상 12자 이하입니다.")
		private String username;
		
		@NotBlank(message = "password을 입력하세요.")
		@Size(min = 4, max = 12, message = "password는 4자 이상 12자 이하입니다.")
		private String password;
		
		@NotBlank(message = "name을 입력하세요.")
		@Size(min = 4, max = 12, message = "name은 4자 이상 12자 이하입니다.")
		private String name;
		
		@NotBlank(message = "email을 입력하세요.")
		private String email;
	}
	
	@Data
	public static class SigninDTO {
		@NotBlank(message = "username을 입력하세요.")
		@Size(min = 4, max = 12, message = "username은 4자 이상 12자 이하입니다.")
		private String username;
		
		@NotBlank(message = "password을 입력하세요.")
		@Size(min = 4, max = 12, message = "password는 4자 이상 12자 이하입니다.")
		private String password;
	}
	
	@Data
	public static class WithdrawDTO {
		@NotBlank(message = "password을 입력하세요.")
		private String password;
	}
	
}
