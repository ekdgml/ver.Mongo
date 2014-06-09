package com.namoo.social.service.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.namoo.social.dao.MessageDao;
import com.namoo.social.dao.UserDao;
import com.namoo.social.domain.Message;
import com.namoo.social.domain.User;
import com.namoo.social.service.facade.SocialNetworkService;

@Service
public class SocialNetworkServiceLogic implements SocialNetworkService {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private MessageDao messageDao;
	
	@Override
	public List<User> getUsers() {
		// 
		return userDao.readAllUsers();
	}

	@Override
	public User getUserInfo(String userId) {
		// 
		return userDao.readUser(userId);
	}
	
	@Override
	public User getUserDetail(String userId) {
		// 
		User user = userDao.readUser(userId);
		if (user != null) {
			user.setFollowers(userDao.readFollowers(userId));
			user.setFollowings(userDao.readFollowings(userId));
			user.setMessages(messageDao.readMessages(userId));
		}
		return user;
	}

	@Override
	public void modifyUser(User user) {
		// 
		userDao.updateUser(user);
	}

	@Override
	public void withdrawalUser(String userId) {
		// 
		// 팔로우하고 있는 관계를 모두 끊는다.
		userDao.deleteAllRelationship(userId);
		
		// 사용자의 모든 메시지를 삭제한다.
		messageDao.deleteAllMessagesByUserId(userId);
		
		// 사용자를 삭제한다.
		userDao.deleteUser(userId);
	}

	@Override
	public void registerUser(User user) {
		// 
		userDao.insertUser(user);
	}
	
	@Override
	public boolean login(String userId, String password) {
		// 
		User user = userDao.readUser(userId);
		if (user != null && user.getUserId().equals(userId) && user.getPassword().equals(password)) {
			return true;
		}
		return false;
	}

	@Override
	public void follow(String followerId, String followingId) {
		// 
		userDao.insertRelationship(followerId, followingId);
	}

	@Override
	public void unfollow(String followerId, String followingId) {
		// 
		userDao.deleteRelationship(followerId, followingId);
	}
	
	@Override
	public List<Message> getTimelineMessages(String userId) {
		// 
		return messageDao.readRelatedMessages(userId);
	}

	@Override
	public List<Message> getMyMessages(String userId) {
		// 
		return messageDao.readMessages(userId);
	}

	@Override
	public Message addMessage(Message message) {
		// 
		String id = messageDao.insertMessage(message);
		message.setId(id);
		
		return message;
	}

	@Override
	public void modifyMessage(Message message) {
		// 
		messageDao.updateMessage(message);
	}

	@Override
	public void removeMessage(String messageId) {
		// 
		messageDao.deleteMessage(messageId);
	}

	@Override
	public Message getMessage(String messageId) {
		// 
		return messageDao.readMessage(messageId);
	}
}
