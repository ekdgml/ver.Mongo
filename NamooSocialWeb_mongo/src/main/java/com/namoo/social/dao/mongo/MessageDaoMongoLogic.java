package com.namoo.social.dao.mongo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.namoo.social.dao.MessageDao;
import com.namoo.social.dao.mongo.document.MessageDoc;
import com.namoo.social.dao.mongo.document.TimeLineDoc;
import com.namoo.social.dao.mongo.repository.MessageRepository;
import com.namoo.social.dao.mongo.repository.TimeLineRepository;
import com.namoo.social.domain.Message;

@Repository
public class MessageDaoMongoLogic implements MessageDao {
	//
	@Autowired
	private MessageRepository msgRepository;
	@Autowired
	private TimeLineRepository tlRepository;
	
	@Override
	public List<Message> readMessages(String userId) {
		//
		List<MessageDoc> docs = msgRepository.findAllByWriterUserId(userId);
		if (docs != null) {
			//
			List<Message> messages = new ArrayList<Message>();
			for (MessageDoc doc : docs) {
				messages.add(doc.createDomain());
			}
			return messages;
		}
		return null;
	}

	@Override
	public List<Message> readRelatedMessages(String userId) {
		//
		List<TimeLineDoc> tlDocs = tlRepository.findByOwnerId(userId);
		if (tlDocs != null) {
			List<Message> messages = new ArrayList<Message>();
			for (TimeLineDoc tlDoc : tlDocs) {
				messages.add(tlDoc.createDomain());
			}
			return messages;
		}
		return null;
	}

	@Override
	public Message readMessage(String messageId) {
		//
		MessageDoc doc = msgRepository.findOne(messageId);
		if (doc != null) {
			return doc.createDomain();
		}
		return null;
	}

	@Override
	public String insertMessage(Message message) {
		//
		MessageDoc doc = new MessageDoc(message);
		msgRepository.save(doc);
		msgRepository.insertMessageInTimeLine(message);
		return doc.getId();
		
	}

	@Override
	public void updateMessage(Message message) {
		//
		MessageDoc doc = msgRepository.findOne(message.getId());
		doc = new MessageDoc(message);
		msgRepository.save(doc);
	}

	@Override
	public void deleteMessage(String messageId) {
		//
		msgRepository.delete(messageId);
	}

	@Override
	public void deleteAllMessagesByUserId(String userId) {
		//
		msgRepository.deleteAllMessagesByUserId(userId);
	}

}
