package com.namoo.blog.dao.mongo.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;

import com.mongodb.BasicDBObject;
import com.namoo.blog.dao.mongo.document.CommentDoc;
import com.namoo.blog.dao.mongo.document.PostDoc;
import com.namoo.blog.dao.mongo.repository.PostRepositoryCustom;


/**
 * 사용하지 않음. 참고용
 * 
 * @author kosta-18
 *
 */
public class PostRepositoryImpl extends SimpleMongoRepository<PostDoc, String> implements PostRepositoryCustom {

	@Autowired
	public PostRepositoryImpl(MongoRepositoryFactory factory, MongoTemplate template) {
		super(factory.<PostDoc, String>getEntityInformation(PostDoc.class), template);
	}

	@Override
	public void insertCommentToPost(String postId, CommentDoc comment) {
		// 
		MongoOperations operations = getMongoOperations();
		
		Query query = new Query(Criteria.where("id").is(postId));
		Update update = new Update();
		update.push("comments", comment);
		
		operations.updateFirst(query, update, PostDoc.class);
	}

	@Override
	public void deleteComment(String commentId) {
		//
		MongoOperations operations = getMongoOperations();
		
		Query query = new Query(Criteria.where("comments.id").is(commentId));
		Update update = new Update();
		update.pull("comments", new BasicDBObject("id", commentId));
		
		operations.updateFirst(query, update, PostDoc.class);
	}
}
