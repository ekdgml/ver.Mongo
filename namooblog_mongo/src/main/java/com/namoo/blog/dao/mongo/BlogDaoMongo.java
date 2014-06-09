package com.namoo.blog.dao.mongo;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.namoo.blog.dao.BlogDao;
import com.namoo.blog.dao.shared.MongoClientFactory;
import com.namoo.blog.dao.shared.SequenceUtil;
import com.namoo.blog.domain.Blog;

public class BlogDaoMongo implements BlogDao {
	//
	private static final String COLLECTION = "blogs";
	
	public BlogDaoMongo() {
		//
		DB db = MongoClientFactory.getDB();
		DBCollection coll = db.getCollectionFromString(COLLECTION);
		
		coll.createIndex(new BasicDBObject("blogId", 1));
	}
	
	@Override
	public int createBlog(Blog blog) {
		//
		DB db = MongoClientFactory.getDB();
		
		int blogId = SequenceUtil.next(db, "blog");
		blog.setBlogId(blogId);
		
		DBObject doc = createDBObject(blog);
		DBCollection coll = db.getCollection(COLLECTION);
		coll.insert(doc);
		
		return blogId;
	}

	@Override
	public Blog readBlog(int blogId) {
		//
		DB db = MongoClientFactory.getDB();
		DBCollection coll = db.getCollection(COLLECTION);
		
		DBObject doc = coll.findOne(new BasicDBObject("blogId", blogId));
		if (doc != null) {
			return mapToBlog(doc);
		}
		return null;
	}

	@Override
	public List<Blog> readBlogsByName(String name) {
		//
		DB db = MongoClientFactory.getDB();
		DBCollection coll = db.getCollection(COLLECTION);
		
		DBCursor cursor = coll.find(new BasicDBObject("name", name));
		
		List<Blog> blogs = new ArrayList<Blog>();
		while (cursor.hasNext()) {
			//
			Blog blog = mapToBlog(cursor.next());
			blogs.add(blog);
		}
		return blogs;
	}

	@Override
	public void updateBlog(Blog blog) {
		//
		DB db = MongoClientFactory.getDB();
		DBCollection coll = db.getCollection(COLLECTION);
		
		DBObject query = new BasicDBObject("blogId", blog.getBlogId());
		DBObject doc = createDBObject(blog);
		
		coll.update(query, doc);
	}

	@Override
	public void deleteBlog(int blogId) {
		//
		DB db = MongoClientFactory.getDB();
		DBCollection coll = db.getCollection(COLLECTION);
		
		coll.remove(new BasicDBObject("blogId", blogId));
	}
	
	//-------------------------------------------------------------------------
	//private method
		
		/**
		 * DBObjec -> blog
		 * select
		 * 
		 * @param DBObject
		 * @return blog
		 */
		private Blog mapToBlog(DBObject doc) {
			//
			Blog blog = new Blog();
			blog.setBlogId((int) doc.get("blogId"));
			blog.setName((String) doc.get("name"));
			blog.setOwnerId((String) doc.get("ownerId"));
			
			return blog;
		}
		
		/**
		 * Blog -> DBObject
		 * insert
		 * 
		 * @param blog
		 * @return DBObject
		 */
		private DBObject createDBObject(Blog blog) {
			DBObject doc = new BasicDBObject();
			doc.put("blogId", blog.getBlogId());
			doc.put("name", blog.getName());
			doc.put("ownerId", blog.getOwnerId());
			
			return doc;
		}

}
