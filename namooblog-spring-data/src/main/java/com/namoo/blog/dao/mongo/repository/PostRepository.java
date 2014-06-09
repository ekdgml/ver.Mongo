package com.namoo.blog.dao.mongo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.namoo.blog.dao.mongo.document.PostDoc;

public interface PostRepository extends CrudRepository<PostDoc, String>, PostRepositoryCustom{
	//
	List<PostDoc> findByBlogId(String blogId);
}
