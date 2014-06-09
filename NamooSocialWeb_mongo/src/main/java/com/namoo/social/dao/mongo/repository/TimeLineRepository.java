package com.namoo.social.dao.mongo.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.namoo.social.dao.mongo.document.TimeLineDoc;

public interface TimeLineRepository extends CrudRepository<TimeLineDoc, Serializable>{
	//
	List<TimeLineDoc> findByOwnerId(String ownerId);
}
