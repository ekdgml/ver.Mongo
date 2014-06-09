package com.namoo.blog.dao.mongo.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.namoo.blog.domain.Blog;
import com.namoo.blog.domain.User;

@Document(collection="blogs")
public class BlogDoc {
	//
	@Id
	private String id;
	private String name;
	private String ownerId;
	
	//--------------------------------------------------------------------------
	
	public BlogDoc() {
		//
	}
	
	public BlogDoc(Blog blog) {
		//
		this.id = blog.getId();
		this.name = blog.getName();
		this.ownerId = blog.getOwner().getId();
	}
	
	//--------------------------------------------------------------------------
	
	public Blog createDomain() {
		//
		Blog blog = new Blog();
		blog.setId(id);
		blog.setName(name);
		blog.setOwner(new User(ownerId));

		return blog;
	}
	
	//--------------------------------------------------------------------------
	
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
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
}
