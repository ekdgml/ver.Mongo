package com.namoo.blog.domain;

public class Comment {
	//
	private int commentId;
	private String comment;
	private String commenterId;
	
	//--------------------------------------------------------------------------
	
	public int getCommentId() {
		return commentId;
	}
	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getCommenterId() {
		return commenterId;
	}
	public void setCommenterId(String commenterId) {
		this.commenterId = commenterId;
	}
}
