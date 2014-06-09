package com.namoo.social.service.facade;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;
import com.namoo.social.domain.Message;
import com.namoo.social.domain.User;
import com.namoo.social.shared.BaseMongoTestCase;

public class SocialNetworkServiceTest extends BaseMongoTestCase{
	//
	private static final String DATASET_JSON = "/com/namoo/social/service/facade/messages.json";
	
	@Autowired
	private SocialNetworkService socialNetworkService;
	
	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testGetUsers() {
		//
		List<User> users = socialNetworkService.getUsers();
		
		// assertion
		assertThat(users.size(), is(3));
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testGetUserDetail() {
		//
		User user = socialNetworkService.getUserDetail("hyunohkim");
		
		// assertion
		assertThat(user.getName(), is("김현오"));
		assertThat(user.getEmail(), is("hyunohkim@nextree.co.kr"));
		assertThat(user.getFollowers().size(), is(1));
		assertThat(user.getFollowings().size(), is(1));
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testModifyUser() {
		//
		User user = socialNetworkService.getUserInfo("hyunohkim");
		user.setEmail("haroldkim@nextree.co.kr");
		user.setName("Harold Kim");
		user.setPassword("abcd");
		
		socialNetworkService.modifyUser(user);
		
		// assertion
		user = socialNetworkService.getUserDetail("hyunohkim");
		assertThat(user.getName(), is("Harold Kim"));
		assertThat(user.getEmail(), is("haroldkim@nextree.co.kr"));
		assertThat(user.getPassword(), is("abcd"));
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testWithdrawalUser() {
		//
		socialNetworkService.withdrawalUser("hyunohkim");
		
		// assertion
		assertNull(socialNetworkService.getUserDetail("hyunohkim"));
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testRegisterUser() {
		//
		User user = new User();
		user.setUserId("testuser");
		user.setName("테스트");
		user.setEmail("test@testcase.com");
		user.setPassword("1111");
		
		socialNetworkService.registerUser(user);
		
		// assertion
		user = socialNetworkService.getUserInfo("testuser");
		assertThat(user.getUserId(), is("testuser"));
		assertThat(user.getName(), is("테스트"));
		assertThat(user.getEmail(), is("test@testcase.com"));
		assertThat(user.getPassword(), is("1111"));
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testFollow() {
		//
		socialNetworkService.follow("hyunohkim", "yunakim");
		
		// assertion
		User user = socialNetworkService.getUserDetail("hyunohkim");
		assertThat(user.getFollowings().size(), is(2));
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testUnfollow() {
		//
		// assert precondition
		User user = socialNetworkService.getUserDetail("hyunohkim");
		assertThat(user.getFollowings().size(), is(1));

		socialNetworkService.unfollow("hyunohkim", "gdhong");
		
		// assertion
		user = socialNetworkService.getUserDetail("hyunohkim");
		assertThat(user.getFollowings().size(), is(0));
	}
	
	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testGetTimelineMessages() {
		//
		List<Message> messages = socialNetworkService.getTimelineMessages("hyunohkim");
		
		// assertion
		assertThat(messages.size(), is(2));
		Message lastMessage = messages.get(0);
		assertThat(lastMessage.getContents(), is("길동이의 첫번째 트윗."));
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testGetMyMessages() {
		//
		List<Message> messages = socialNetworkService.getMyMessages("hyunohkim");
		
		// assertion
		assertThat(messages.size(), is(1));
		Message lastMessage = messages.get(0);
		assertThat(lastMessage.getContents(), is("두번째 글입니다."));
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testAddMessage() {
		//
		Message message = new Message();
		message.setContents("메시지를 남깁니다.");
		message.setWriter(new User("hyunohkim"));
		
		message = socialNetworkService.addMessage(message);
		
		// assertion
		List<Message> messages = socialNetworkService.getMyMessages("hyunohkim");
		assertThat(messages.size(), is(2));
		Message newMessage = messages.get(1);
		
		assertThat(newMessage.getContents(), is("메시지를 남깁니다."));
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testModifyMessage() {
		//
		Message message = socialNetworkService.getMessage("1");
		message.setContents("내용을 수정합니다.");
		
		socialNetworkService.modifyMessage(message);
		
		// assertion
		message = socialNetworkService.getMessage("1");
		assertThat(message.getContents(), is("내용을 수정합니다."));
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testRemoveMessage() {
		// assert precondition
		assertNotNull(socialNetworkService.getMessage("1"));
		
		socialNetworkService.removeMessage("1");
		
		// assertion
		assertNull(socialNetworkService.getMessage("1"));
	}

}
