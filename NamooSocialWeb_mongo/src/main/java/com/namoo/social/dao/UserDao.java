package com.namoo.social.dao;

import java.util.List;

import com.namoo.social.domain.User;

public interface UserDao {
	//
	List<User> readAllUsers();
	User readUser(String userId);
	void insertUser(User user);
	void updateUser(User user);
	void deleteUser(String userId);
	
	// relationship
	List<User> readFollowings(String userId);
	List<User> readFollowers(String userId);
	void insertRelationship(String fromId, String toId);
	void deleteRelationship(String fromId, String toId);
	void deleteAllRelationship(String fromId);
}
