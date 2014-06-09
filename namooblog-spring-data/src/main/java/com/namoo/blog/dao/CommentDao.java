package com.namoo.blog.dao;

import java.util.List;

import com.namoo.blog.domain.Comment;

public interface CommentDao {
	//
	String createComment(String postId, Comment comment);
	Comment readComment(String commentId);
	List<Comment> readCommentsByPostId(String postId);
	List<Comment> readCommentsByCommenterId(String commenterId);
	void updateComment(Comment comment);
	void deleteComment(String commentId);
}
