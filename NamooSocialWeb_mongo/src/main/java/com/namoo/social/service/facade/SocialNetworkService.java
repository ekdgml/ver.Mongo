package com.namoo.social.service.facade;

import java.util.List;

import com.namoo.social.domain.Message;
import com.namoo.social.domain.User;

public interface SocialNetworkService {
	//
	List<User> getUsers();
	User getUserInfo(String userId);
	User getUserDetail(String userId);
	void modifyUser(User user);
	void withdrawalUser(String userId);
	void registerUser(User user);
	boolean login(String userId, String password);
	
	// relationship
	void follow(String followerId, String followingId);
	void unfollow(String follwerId, String followingId);
	
	// message
	List<Message> getTimelineMessages(String userId);
	List<Message> getMyMessages(String userId);
	Message addMessage(Message message);
	void modifyMessage(Message message);
	void removeMessage(String messageId);
	Message getMessage(String messageId);
}
