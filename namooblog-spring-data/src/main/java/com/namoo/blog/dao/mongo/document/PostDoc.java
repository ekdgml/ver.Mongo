package com.namoo.blog.dao.mongo.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.namoo.blog.domain.Post;

@Document(collection = "posts")
public class PostDoc {
	//
	@Id
	private String id;
	@Indexed
	private String blogId;
	private String subject;
	private String contents;
	
	//--------------------------------------------------------------------------
	
	public PostDoc() {
		//
	}

	public PostDoc(String blogId, Post post) {
		//
		this.id = post.getId();
		this.blogId = blogId;
		this.subject = post.getSubject();
		this.contents = post.getContents();
	}
	
	//--------------------------------------------------------------------------

	public Post createDomain() {
		//
		Post post = new Post();
		post.setId(id);
		post.setSubject(subject);
		post.setContents(contents);
		
		return post;	
	}
	
	//--------------------------------------------------------------------------
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
}
