package com.namoo.blog.dao.mongo.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.namoo.blog.domain.User;

@Document(collection="users")
public class UserDoc {
	//
	@Id
	private String id;
	private String name;
	private String email;
	
	//-----------------------------------------------------------------
	
	public UserDoc() {
		//
	}
	
	public UserDoc(User user) {
		//
		this.id = user.getId();
		this.name = user.getName();
		this.email = user.getEmail();
	}
	//-----------------------------------------------------------------
	
	public User createDomain() {
		//
		User user = new User();
		user.setEmail(email);
		user.setId(id);
		user.setName(name);
		
		return user;
	}
	
	//-----------------------------------------------------------------
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
}
