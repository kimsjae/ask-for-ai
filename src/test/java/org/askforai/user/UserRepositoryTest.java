package org.askforai.user;

import java.util.Optional;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class UserRepositoryTest {
	
	@Autowired
	UserRepository userRepository;

	@Test
	void testFindByUsername() {
		// given
		User user = User.builder()
				.username("asdasd1")
				.password("asdasd2")
				.name("asdasd3")
				.email("asd@asd")
				.build();
		
		// when
		userRepository.save(user);
		Optional<User> userOp = userRepository.findByUsername(user.getUsername());
		
		// then
		// 실패 결과 한번에 다 출력.
		SoftAssertions softly = new SoftAssertions();
		softly.assertThat(userOp).isPresent();
		softly.assertThat(userOp.get().getUsername()).isEqualTo("asdasd1123ew");
		softly.assertThat(userOp.get().getPassword()).isEqualTo("asdasd22asda21");
		softly.assertThat(userOp.get().getName()).isEqualTo("asdasd3311asd");
		softly.assertThat(userOp.get().getEmail()).isEqualTo("asd@asd3");
		softly.assertAll();
		
//		assertThat(userOp).isPresent();
//		assertThat(userOp.get().getUsername()).isEqualTo("asdasd1");
//		assertThat(userOp.get().getPassword()).isEqualTo("asdasd2");
//		assertThat(userOp.get().getName()).isEqualTo("asdasd3");
//		assertThat(userOp.get().getEmail()).isEqualTo("asd@asd");
	}

}
