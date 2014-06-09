package com.namoo.social.dao.mongo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.namoo.social.dao.mongo.document.UserDoc;

public interface UserRepository extends CrudRepository<UserDoc, String>, UserRepositoryCustom{
	//
	List<UserDoc> findAll();
}
