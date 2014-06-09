package com.namoo.blog.dao.mongo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.namoo.blog.dao.mongo.document.CommentDoc;

public interface CommentRepository extends CrudRepository<CommentDoc, String>{

	List<CommentDoc> findByPostId(String postId); 
	//
	List<CommentDoc> findByCommenterId(String commenterId);
	
}
