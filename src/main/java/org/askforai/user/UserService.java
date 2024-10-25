package org.askforai.user;

import java.util.Optional;

import org.askforai._core.exception.custom.Exception409;
import org.askforai._core.util.BCryptUtil;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
	
	private final UserRepository userRepository;
	private final BCryptUtil bCryptUtil;
	
	public User signup(UserRequest.SignupDTO reqDTO) {
		log.info("회원가입 시도: {}", reqDTO);
		Optional<User> userOp = userRepository.findByUsername(reqDTO.getUsername());
		if (userOp.isPresent()) {
			throw new Exception409("중복된 username 입니다.");
		}
		
		String encryptedPassword = bCryptUtil.encryptPassword(reqDTO.getPassword());
		
		User user = User.builder()
				.username(reqDTO.getUsername())
				.password(encryptedPassword)
				.name(reqDTO.getName())
				.email(reqDTO.getEmail())
				.build();
		
		return userRepository.save(user);
	}

}
