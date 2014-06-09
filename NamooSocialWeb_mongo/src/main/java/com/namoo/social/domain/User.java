package com.namoo.social.domain;

import java.util.List;

public class User {
	//
	private String userId;
	private String name;
	private String email;
	private String password;
	
	// 메시지
	private List<Message> messages;
	
	// 관계
	private List<User> followings;
	private List<User> followers;
	
	//--------------------------------------------------------------------------
	
	public User() {
		//
	}
	
	public User(String userId) {
		//
		this.userId = userId;
	}
	
	//--------------------------------------------------------------------------
	
	public User findFollowing(String userId) {
		//
		if (followings != null) {
			for (User user : followings) {
				if (user.getUserId().equals(userId)) {
					return user;
				}
			}
		}
		return null;
	}
	
	public User findFollower(String userId) {
		//
		if (followers != null) {
			for (User user : followers) {
				if (user.getUserId().equals(userId)) {
					return user;
				}
			}
		}
		return null;
	}
	
	//--------------------------------------------------------------------------
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<User> getFollowings() {
		return followings;
	}
	public void setFollowings(List<User> followings) {
		this.followings = followings;
	}
	public List<User> getFollowers() {
		return followers;
	}
	public void setFollowers(List<User> followers) {
		this.followers = followers;
	}
	public List<Message> getMessages() {
		return messages;
	}
	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
}
