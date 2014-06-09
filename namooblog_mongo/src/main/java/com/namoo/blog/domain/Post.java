package com.namoo.blog.domain;

import java.util.ArrayList;
import java.util.List;

public class Post {
	//
	private int blogId;
	private int postId;
	private String subject;
	private String contents;
	
	private List<Comment> comments;
	
	//--------------------------------------------------------------------------
	
	public void addComments(Comment comment) {
		//
		if (comments == null) {
			//
			comments = new ArrayList<Comment>();
		}
		comments.add(comment);
	}
	//--------------------------------------------------------------------------
	// getter, setter
	
	public int getBlogId() {
		return blogId;
	}

	public void setBlogId(int blogId) {
		this.blogId = blogId;
	}
	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
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
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
}
