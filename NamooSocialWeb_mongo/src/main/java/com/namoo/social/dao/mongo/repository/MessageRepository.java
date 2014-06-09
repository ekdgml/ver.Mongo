package com.namoo.social.dao.mongo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.namoo.social.dao.mongo.document.MessageDoc;

public interface MessageRepository extends CrudRepository<MessageDoc, String>, MessageRepositoryCustom{
	//
	List<MessageDoc> findAllByWriterUserId(String userId);
}
