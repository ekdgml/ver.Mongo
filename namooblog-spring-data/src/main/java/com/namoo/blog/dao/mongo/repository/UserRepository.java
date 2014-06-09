package com.namoo.blog.dao.mongo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.namoo.blog.dao.mongo.document.UserDoc;

public interface UserRepository extends CrudRepository<UserDoc, String>{

	List<UserDoc> findAll();

	List<UserDoc> findByName(String name);
}
