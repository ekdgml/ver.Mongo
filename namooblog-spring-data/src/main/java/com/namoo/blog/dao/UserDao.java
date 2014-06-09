package com.namoo.blog.dao;

import java.util.List;

import com.namoo.blog.domain.User;

public interface UserDao {
	//
	void createUser(User user);
	User readUser(String userId);
	List<User> readAllUsers();
	List<User> readUsersByName(String name);
	void updateUser(User user);
	void deleteUser(String userId);
}
