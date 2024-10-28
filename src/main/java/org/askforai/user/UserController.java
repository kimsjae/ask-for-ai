package org.askforai.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
		String authorizationHeader = request.getHeader("Authorization");
        String existingToken = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            existingToken = authorizationHeader.substring(7);
        }
        
        String token = userService.signin(reqDTO, existingToken);
        
        return ResponseEntity.ok(token);
    }
	
	@DeleteMapping("/withdraw")
	public ResponseEntity<?> withdraw(@Valid @RequestBody UserRequest.WithdrawDTO reqDTO) {
	    userService.withdraw(reqDTO);
	    
	    return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/users/me")
	public ResponseEntity<?> getUserInfo() {
	    User userInfo = userService.getUserInfo();
	    
	    return ResponseEntity.ok(userInfo);
	}

}
