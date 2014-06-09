package com.namoo.social.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;
import com.namoo.social.domain.Message;
import com.namoo.social.domain.User;
import com.namoo.social.shared.BaseMongoTestCase;

public class MessageDaoTest extends BaseMongoTestCase {
	//
	private static final String DATASET_JSON = "/com/namoo/social/dao/messages.json";
	
	@Autowired
	private MessageDao messageDao;
	
	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testReadMessages() {
		//
		List<Message> messages = messageDao.readMessages("hyunohkim");
		
		// assertion
		assertThat(messages.size(), is(2));
		Message firstMessage = messages.get(1);
		assertThat(firstMessage.getContents(), is("두번째 글입니다."));
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)  
	public void testReadRelatedMessages() {
		//
		List<Message> messages = messageDao.readRelatedMessages("hyunohkim");
		
		// assertion
		assertThat(messages.size(), is(2));
		Message firstMessage = messages.get(0);
		assertThat(firstMessage.getContents(), is("길동이의 첫번째 트윗."));
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)  
	public void testInsertMessage() {
		//
		Message message = new Message();
		message.setContents("메시지를 남깁니다.");
		message.setWriter(new User("hyunohkim"));
		
		messageDao.insertMessage(message);
		// assertion
		List<Message> messages = messageDao.readMessages("hyunohkim");
		assertThat(messages.size(), is(3));
		Message newMessage = messages.get(0);
		
		assertThat(newMessage.getContents(), is("길동이의 첫번째 트윗."));
		
		// Timeline assertion
		List<Message> timelines = messageDao.readRelatedMessages("hyunohkim");
		Message firstMessage = timelines.get(2);
		assertThat(firstMessage.getContents(), is("메시지를 남깁니다."));
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT) 
	public void testUpdateMessage() {
		//
		Message message = messageDao.readMessage("1");
		message.setContents("내용을 수정합니다.");
		
		messageDao.updateMessage(message);
		
		// assertion
		message = messageDao.readMessage("1");
		assertThat(message.getContents(), is("내용을 수정합니다."));
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)  
	public void testDeleteMessage() {
		//
		// assert precondition
		assertNotNull(messageDao.readMessage("1"));
		
		messageDao.deleteMessage("1");
		
		// assertion
		assertNull(messageDao.readMessage("1"));
	}

}
