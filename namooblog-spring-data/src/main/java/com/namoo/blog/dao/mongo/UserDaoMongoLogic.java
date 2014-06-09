package com.namoo.blog.dao.mongo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.namoo.blog.dao.UserDao;
import com.namoo.blog.dao.mongo.document.UserDoc;
import com.namoo.blog.dao.mongo.repository.UserRepository;
import com.namoo.blog.domain.User;

@Repository
public class UserDaoMongoLogic implements UserDao {
	//
	@Autowired
	private UserRepository repository;
	
	@Override
	public void createUser(User user) {
		//
		UserDoc doc = new UserDoc(user);
		repository.save(doc);
	}

	@Override
	public User readUser(String userId) {
		//
		UserDoc doc = repository.findOne(userId);
		if (doc != null) {
			return doc.createDomain();
		}
		return null;
	}

	@Override
	public List<User> readAllUsers() {
		//
		List<UserDoc> docs = repository.findAll();
		if (docs != null) {
			//
			List<User> users = new ArrayList<User>();
			for (UserDoc doc : docs) {
				//
				users.add(doc.createDomain());
			}
			
			// Sorting - 이름으로 오름차순 정렬
			Comparator<User> comparator = new Comparator<User>() {
				//
				@Override
				public int compare(User o1, User o2) {
					//
					return o1.getName().compareTo(o2.getName());
				}
			};
			Collections.sort(users, comparator);
			
			return users;
		}
		return null;
	}
	
	@Override
	public List<User> readUsersByName(String name) {
		//
		List<UserDoc> docs = repository.findByName(name);
		if (docs != null) {
			//
			List<User> users = new ArrayList<User>();
			for (UserDoc doc : docs) {
				//
				users.add(doc.createDomain());
			}
			return users;
		}
		return null;
	}

	@Override
	public void updateUser(User user) {
		//
		UserDoc doc = new UserDoc(user);
		repository.save(doc);
	}

	@Override
	public void deleteUser(String userId) {
		//
		UserDoc doc = repository.findOne(userId);
		repository.delete(doc);
	}

}
