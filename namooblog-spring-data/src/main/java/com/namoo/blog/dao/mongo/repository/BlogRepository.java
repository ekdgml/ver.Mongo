package com.namoo.blog.dao.mongo.repository;

import org.springframework.data.repository.CrudRepository;

import com.namoo.blog.dao.mongo.document.BlogDoc;


public interface BlogRepository extends CrudRepository<BlogDoc, String>{
	//
	//CrudRepository -> create, update : insert
	//               -> read, delete 제공기능
}
