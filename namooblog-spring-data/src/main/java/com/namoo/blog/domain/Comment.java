package com.namoo.blog.domain;

public class Comment {
	//
	private String id;
	private String comment;
	private User commenter;
	
	//--------------------------------------------------------------------------
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public User getCommenter() {
		return commenter;
	}
	public void setCommenter(User commenter) {
		this.commenter = commenter;
	}
}
