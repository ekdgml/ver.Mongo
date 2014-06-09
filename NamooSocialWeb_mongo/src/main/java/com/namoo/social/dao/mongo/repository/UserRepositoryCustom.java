package com.namoo.social.dao.mongo.repository;


public interface UserRepositoryCustom{
	//
	void insertRelationship(String fromId, String toId);
	void deleteRelationship(String fromId, String toId);
	void deleteAllRelationship(String fromId);
}
