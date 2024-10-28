package org.askforai.chatRoom;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.askforai.user.User;
import org.askforai.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ChatRoomRepositoryTest {
	
	@Autowired
	private ChatRoomRepository chatRoomRepository;
	
	@Autowired
	private UserRepository userRepository;
	

	@Test
	void testFindByUserId() {
		// given
		User user = User.builder()
				.username("asdasd")
				.password("asdasd")
				.name("asdasd")
				.email("asd@asd")
				.build();
		
		userRepository.save(user);
		
		
		ChatRoom chatRoom = ChatRoom.builder()
				.userId(user.getId())
				.build();
		
		chatRoomRepository.save(chatRoom);
		
		// when
		List<ChatRoom> chatRooms = chatRoomRepository.findChatRoomsByUserId(user.getId());
				
		// then
		assertThat(chatRooms).isNotEmpty();
		
	}

}
