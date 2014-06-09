package com.namoo.social.dao.mongo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.namoo.social.dao.UserDao;
import com.namoo.social.dao.mongo.document.UserDoc;
import com.namoo.social.dao.mongo.repository.UserRepository;
import com.namoo.social.domain.User;

@Repository
public class UserDaoMongoLogic implements UserDao {
	//
	@Autowired
	private UserRepository repository; 
	
	@Override
	public List<User> readAllUsers() {
		//
		List<UserDoc> docs = repository.findAll();
		if (docs != null) {
			//
			List<User> users = new ArrayList<User>();
			for (UserDoc doc : docs) {
				users.add(doc.createDomain());
			}
			return users;
		}
		return null;
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
	public void insertUser(User user) {
		//
		UserDoc doc = new UserDoc(user);
		repository.save(doc);
	}

	@Override
	public void updateUser(User user) {
		//
		UserDoc doc = repository.findOne(user.getUserId());
		doc = new UserDoc(user);
		repository.save(doc);
	}

	@Override
	public void deleteUser(String userId) {
		//
		repository.delete(userId);
	}

	@Override
	public List<User> readFollowings(String userId) {
		//
		UserDoc doc = repository.findOne(userId);
		List<UserDoc> followingDocs = doc.getFollowings();
		if (followingDocs != null) {
			//
			List<User> followings = new ArrayList<User>();
			for (UserDoc followingDoc : followingDocs) {
				followings.add(followingDoc.createDomain());
			}
			return followings;
		}
		return null;
	}

	@Override
	public List<User> readFollowers(String userId) {
		//
		UserDoc doc = repository.findOne(userId);
		List<UserDoc> followerDocs = doc.getFollowers();
		if (followerDocs != null) {
			//
			List<User> followers = new ArrayList<User>();
			for (UserDoc followerDoc : followerDocs) {
				//
				followers.add(followerDoc.createDomain());
			}
			return followers;
		}
		return null;
	}

	@Override
	public void insertRelationship(String fromId, String toId) {
		//
		repository.insertRelationship(fromId, toId);
	}

	@Override
	public void deleteRelationship(String fromId, String toId) {
		//
		repository.deleteRelationship(fromId, toId);
	}

	@Override
	public void deleteAllRelationship(String fromId) {
		//
		repository.deleteAllRelationship(fromId);
	}

}
