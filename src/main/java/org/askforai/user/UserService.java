package org.askforai.user;

import java.util.Optional;

import org.askforai._core.exception.custom.Exception400;
import org.askforai._core.exception.custom.Exception403;
import org.askforai._core.exception.custom.Exception404;
import org.askforai._core.exception.custom.Exception409;
import org.askforai._core.util.BCryptUtil;
import org.askforai._core.util.JwtUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserService {
	
	private final UserRepository userRepository;
	private final BCryptUtil bCryptUtil;
	private final JwtUtil jwtUtil;
	
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

	public String signin(UserRequest.SigninDTO reqDTO, String existingToken) {
        log.info("로그인 시도: {}", reqDTO);
        
        // 사용자 조회
        User user = userRepository.findByUsername(reqDTO.getUsername())
                .orElseThrow(() -> new Exception404("username 혹은 password가 일치하지 않습니다."));
        
        // 비밀번호 검증
        if (bCryptUtil.matches(reqDTO.getPassword(), user.getPassword())) {        	
        	if (existingToken != null && jwtUtil.validateToken(existingToken, user.getUsername())) {
                return existingToken; // 유효한 토큰 사용
            } else {
                return jwtUtil.generateAccessToken(user.getUsername()); // 새로운 토큰 발급
            }
        } else {
            throw new Exception404("username 혹은 password가 일치하지 않습니다.");
        }
    }
	
	public void withdraw(UserRequest.WithdrawDTO reqDTO, String username) {
		log.info("회원탈퇴 시도: {}", reqDTO);
		
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new Exception403("권한없음."));
		
		if (bCryptUtil.matches(reqDTO.getPassword(), user.getPassword())) {
			userRepository.deleteById(user.getId());
        } else {
        	log.warn("탈퇴 실패: {}", user);
            throw new Exception400("탈퇴 실패: 비밀번호가 일치하지 않습니다.");
        }
	}
	
	
}
