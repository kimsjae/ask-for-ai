package org.askforai.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
	
	private final UserService userService;
	
	@PostMapping
	public ResponseEntity<?> signup(@Valid @RequestBody UserRequest.SignupDTO reqDTO) {
		User user = userService.signup(reqDTO);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(user);
	}

}