package com.namoo.social.dao.mongo.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;

import com.mongodb.BasicDBObject;
import com.namoo.social.dao.mongo.document.UserDoc;
import com.namoo.social.dao.mongo.repository.UserRepositoryCustom;

public class UserRepositoryImpl extends SimpleMongoRepository<UserDoc, String> implements UserRepositoryCustom {
	//
	
	@Autowired
	public UserRepositoryImpl(MongoRepositoryFactory factory, MongoTemplate template) {
		super(factory.<UserDoc, String>getEntityInformation(UserDoc.class), template);
	}

	@Override
	public void insertRelationship(String fromId, String toId) {
		//
		MongoOperations operations = getMongoOperations();
		
		Query fromQuery = new Query(Criteria.where("_id").is(fromId));
		Query toQuery = new Query(Criteria.where("_id").is(toId));
		
		// from사용자
		UserDoc doc = operations.findOne(fromQuery, UserDoc.class);
 		UserDoc embeddedDoc = new UserDoc();
		embeddedDoc.setEmail(doc.getEmail());
		embeddedDoc.setUserId(doc.getUserId());
		embeddedDoc.setName(doc.getName());
		
		// to사용자 조회
		UserDoc userDoc = operations.findOne(toQuery, UserDoc.class);
		
		UserDoc embeddedUser = new UserDoc();
		embeddedUser.setUserId(userDoc.getUserId());
		embeddedUser.setEmail(userDoc.getEmail());
		embeddedUser.setName(userDoc.getName());
		
		// update1 - 나의 팔로윙 목록에 push
		Update update = new Update();
		update.push("followings", embeddedUser);
		operations.updateFirst(fromQuery, update, UserDoc.class);
		
		// update2 - to사용자의 팔로워 목록에 push
		update = new Update();
		update.push("followers", embeddedDoc);
		operations.updateFirst(toQuery, update, UserDoc.class);
	}

	@Override
	public void deleteRelationship(String fromId, String toId) {
		//
		MongoOperations operations = getMongoOperations();
		
		Query fromQuery = new Query(Criteria.where("_id").is(fromId));
		Query toQuery = new Query(Criteria.where("_id").is(toId));
		
		// update1 - 나의 팔로잉 목록에서 pull
		Update update = new Update();
		update.pull("followings", new BasicDBObject("_id", toId));
		operations.updateFirst(fromQuery, update, UserDoc.class);
		
		// update2 - to사용자의 팔로워 목록에서 pull
		update = new Update();
		update.pull("followers", new BasicDBObject("_id", fromId));
		operations.updateFirst(toQuery, update, UserDoc.class);
	}

	@Override
	public void deleteAllRelationship(String fromId) {
		//
		MongoOperations operations = getMongoOperations();
		
		Query query1 = new Query(Criteria.where("followings.id").is(fromId));
		Query query2 = new Query(Criteria.where("followers.id").is(fromId));
		
		// 팔로윙 제거
		Update update = new Update();
		update.pull("followings", new BasicDBObject("_id", fromId));
		
		operations.updateMulti(query1, update, UserDoc.class);
		
		// 팔로워 제거
		update = new Update();
		update.pull("followers", new BasicDBObject("_id", fromId));
		
		operations.updateMulti(query2, update, UserDoc.class);
	}

}
