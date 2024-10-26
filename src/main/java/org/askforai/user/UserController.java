package org.askforai.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;
	
	@PostMapping("/users")
	public ResponseEntity<?> signup(@Valid @RequestBody UserRequest.SignupDTO reqDTO) {
		User user = userService.signup(reqDTO);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(user);
	}
	
	@PostMapping("/signin")
	public ResponseEntity<String> signin(@Valid @RequestBody UserRequest.SigninDTO reqDTO, HttpServletRequest request) {
        String token = userService.signin(reqDTO, request);
        
        return ResponseEntity.ok(token);
    }
	
	@DeleteMapping("/withdraw")
	public ResponseEntity<?> withdraw(@Valid @RequestBody UserRequest.WithdrawDTO reqDTO, HttpServletRequest request) {
		userService.withdraw(reqDTO, request);
		
		return ResponseEntity.noContent().build();
	}

}
