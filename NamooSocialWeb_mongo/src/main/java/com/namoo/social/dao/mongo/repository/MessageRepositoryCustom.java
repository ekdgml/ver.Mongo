package com.namoo.social.dao.mongo.repository;

import com.namoo.social.domain.Message;

public interface MessageRepositoryCustom {
	//
	void insertMessageInTimeLine(Message message);
	void deleteAllMessagesByUserId(String userId);
}
