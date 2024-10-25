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
		Optional<User> username = userRepository.findByUsername(reqDTO.getUsername());
		if (username.isPresent()) {
			throw new Exception409("이미 존재하는 username 입니다.");
		}
		Optional<User> email = userRepository.findByEmail(reqDTO.getEmail());
		if (email.isPresent()) {
			throw new Exception409("이미 존재하는 email 입니다.");
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
