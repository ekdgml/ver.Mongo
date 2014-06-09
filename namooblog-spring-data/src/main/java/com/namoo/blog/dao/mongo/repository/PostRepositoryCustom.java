package com.namoo.blog.dao.mongo.repository;

import com.namoo.blog.dao.mongo.document.CommentDoc;

/**
 * 사용하지 않음. 참고용
 * 
 * @author kosta-18
 *
 */
public interface PostRepositoryCustom {
	//
	void insertCommentToPost(String postId, CommentDoc comment);
	void deleteComment(String commentId);
}
