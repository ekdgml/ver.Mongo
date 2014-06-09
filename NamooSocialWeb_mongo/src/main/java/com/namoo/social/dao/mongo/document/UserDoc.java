package com.namoo.social.dao.mongo.document;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.namoo.social.domain.User;

@Document(collection="users")
public class UserDoc {
	//
	@Id
	private String userId;
	private String name;
	private String email;
	private String password;
	
	private List<UserDoc> followings;
	private List<UserDoc> followers;
	
	//-----------------------------------------------------------------------------------
	
	public UserDoc() {
		//
	}
	
	public UserDoc(User user) {
		//
		this.userId = user.getUserId();
		this.name = user.getName();
		this.email = user.getEmail();
		this.password = user.getPassword();
	}
	
	//-----------------------------------------------------------------------------------
	
	public User createDomain() {
		//
		User user = new User();
		user.setEmail(email);
		user.setName(name);
		user.setPassword(password);
		user.setUserId(userId);
		
		return user;
	}
	//-----------------------------------------------------------------------------------
	// getter, setter
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
	public List<UserDoc> getFollowings() {
		return followings;
	}
	public void setFollowings(List<UserDoc> followings) {
		this.followings = followings;
	}
	public List<UserDoc> getFollowers() {
		return followers;
	}
	public void setFollowers(List<UserDoc> followers) {
		this.followers = followers;
	}
}
