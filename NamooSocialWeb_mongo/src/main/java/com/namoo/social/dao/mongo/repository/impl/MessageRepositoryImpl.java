package com.namoo.social.dao.mongo.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;

import com.namoo.social.dao.mongo.document.MessageDoc;
import com.namoo.social.dao.mongo.document.TimeLineDoc;
import com.namoo.social.dao.mongo.document.UserDoc;
import com.namoo.social.dao.mongo.repository.MessageRepositoryCustom;
import com.namoo.social.domain.Message;

public class MessageRepositoryImpl extends SimpleMongoRepository<MessageDoc, String> implements MessageRepositoryCustom {
	//
	@Autowired
	public MessageRepositoryImpl(MongoRepositoryFactory factory, MongoTemplate template) {
		super(factory.<MessageDoc, String>getEntityInformation(MessageDoc.class), template);
	}

	@Override
	public void insertMessageInTimeLine(Message message) {
		//
		// 1.내 팔로워 목록조회 -> timeline 의 ownerId
		MongoOperations operations = getMongoOperations();
		
		Query query = new Query(Criteria.where("followers._id").is(message.getWriter().getUserId()));
		List<UserDoc> followers = operations.find(query, UserDoc.class);
		TimeLineDoc doc = new TimeLineDoc(message.getWriter().getUserId(), message);
		operations.save(doc);
		
		// timeLine에 put!
		for (UserDoc follower : followers) {
			doc = new TimeLineDoc(follower.getUserId(), message);
			operations.save(doc);
		}
	}
	
	@Override
	public void deleteAllMessagesByUserId(String userId) {
		//
		// 내글을 지우고
		MongoOperations operations = getMongoOperations();
		
		Query query = new Query(Criteria.where("writer.userId").is(userId));
		operations.remove(query, MessageDoc.class);
		
		// 남의 타임라인에서 내 글 지우기
		Query query2 = new Query(Criteria.where("message.writer.userId").is(userId));
		operations.remove(query2, TimeLineDoc.class);
	}
}
