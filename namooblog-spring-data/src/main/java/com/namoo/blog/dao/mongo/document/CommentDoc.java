package com.namoo.blog.dao.mongo.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.namoo.blog.domain.Comment;
import com.namoo.blog.domain.User;

@Document(collection="comments")
public class CommentDoc {
	//
	@Id
	private String id;
	@Indexed
	private String postId;
	private String comment;
	private String commenterId;
	
	//--------------------------------------------------------------------------

	public CommentDoc() {
		//
	}
	
	public CommentDoc(String postId, Comment comment) {
		//
		this.id = comment.getId();
		this.postId = postId;
		this.comment = comment.getComment();
		this.commenterId = comment.getCommenter().getId();
	}
	
	//--------------------------------------------------------------------------
	
	public Comment createDomain() {
		// 
		Comment comment = new Comment();
		comment.setId(this.id);
		comment.setComment(this.comment);
		comment.setCommenter(new User(this.commenterId));
		
		return comment;
	}
	
	//--------------------------------------------------------------------------
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPostId() {
		return postId;
	}
	public void setPostId(String postId) {
		this.postId = postId;
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
