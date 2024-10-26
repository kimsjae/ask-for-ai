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
            
            // 기존 토큰 검증
            if (existingToken != null) {
                // 토큰 유효성 검증
                if (jwtUtil.validateToken(existingToken)) {
                    // 인증 성공: 유효한 토큰을 사용
                    return existingToken; 
                } else {
                    // 유효하지 않은 토큰 처리 (예: 로그아웃 등)
                    log.warn("유효하지 않은 토큰: {}", existingToken);
                }
            }

            // 새로운 액세스 토큰 발급
            return jwtUtil.generateAccessToken(user.getUsername());
        } else {
            throw new Exception404("username 혹은 password가 일치하지 않습니다.");
        }
    }
	
	public void withdraw(UserRequest.WithdrawDTO reqDTO, String token) {
		log.info("회원탈퇴 시도: {}", reqDTO);
		
		String username = jwtUtil.extractUsername(token);
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new Exception404("사용자를 찾을 수 없습니다."));
		
		if (bCryptUtil.matches(reqDTO.getPassword(), user.getPassword())) {
			userRepository.deleteById(user.getId());
			
        } else {
        	log.warn("탈퇴 실패: {}", user);
            throw new Exception400("탈퇴 실패: 비밀번호가 일치하지 않습니다.");
        }
	}
	
	@Transactional(readOnly = true)
	public User getUserInfo(String token) {
		if (!jwtUtil.validateToken(token)) {
			throw new Exception403("유효하지 않은 토큰입니다.");
		}
		
		String username = jwtUtil.extractUsername(token);
		User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new Exception404("사용자를 찾을 수 없습니다."));

        return user;
	}
	
}
