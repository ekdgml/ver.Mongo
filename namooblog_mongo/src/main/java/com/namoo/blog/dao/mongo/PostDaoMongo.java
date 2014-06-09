package com.namoo.blog.dao.mongo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.namoo.blog.dao.PostDao;
import com.namoo.blog.dao.shared.MongoClientFactory;
import com.namoo.blog.dao.shared.SequenceUtil;
import com.namoo.blog.domain.Comment;
import com.namoo.blog.domain.Post;

public class PostDaoMongo implements PostDao {
	//
	private static final String COLLECTION = "posts";
	
	public PostDaoMongo() {
		// 
		//index 걸기!
		DB db = MongoClientFactory.getDB();
		DBCollection coll = db.getCollection(COLLECTION);
		coll.createIndex(new BasicDBObject("postId", 1));
		coll.createIndex(new BasicDBObject("comments.commentId", 1));
	}
	
	@Override
	public int createPost(Post post) {
		//
		DB db = MongoClientFactory.getDB();
		
		int postId = SequenceUtil.next(db, "post");
		post.setPostId(postId);
		
		DBObject doc = createDBObject(post);
		DBCollection coll = db.getCollection(COLLECTION);
		coll.insert(doc);
		
		return postId;
	}

	@Override
	public Post readPost(int postId) {
		//
		DB db = MongoClientFactory.getDB();
		DBCollection coll = db.getCollection(COLLECTION);
		
		DBObject doc = coll.findOne(new BasicDBObject("postId", postId));
		if (doc != null) {
			return mapToPost(doc);
		}
		return null;
	}

	@Override
	public List<Post> readPostsBySubject(String subject) {
		//
		DB db = MongoClientFactory.getDB();
		DBCollection coll = db.getCollection(COLLECTION);
		
		DBCursor cursor = coll.find(new BasicDBObject("subject", subject));
		
		List<Post> posts = new ArrayList<Post>();
		while (cursor.hasNext()) {
			//
			Post post = mapToPost(cursor.next());
			posts.add(post);
		}
		return posts;
	}
	
	@Override
	public List<Post> readPostsByBlogId(int blogId) {
		//
		DB db = MongoClientFactory.getDB();
		DBCollection coll = db.getCollection(COLLECTION);
		
		DBCursor cursor = coll.find(new BasicDBObject("blogId", blogId));
		
		List<Post> posts = new ArrayList<Post>();
		while (cursor.hasNext()) {
			//
			Post post = mapToPost(cursor.next());
			posts.add(post);
		}
		return posts;
	}

	@Override
	public void updatePost(Post post) {
		//
		DB db = MongoClientFactory.getDB();
		DBCollection coll = db.getCollection(COLLECTION);
		
		DBObject query = new BasicDBObject("postId", post.getPostId());
		DBObject doc = createDBObject(post);
		
		coll.update(query, doc);
	}

	@Override
	public void deletePost(int postId) {
		//
		DB db = MongoClientFactory.getDB();
		DBCollection coll = db.getCollection(COLLECTION);
		
		coll.remove(new BasicDBObject("postId", postId));
	}
	//---------------------------------------------------------------------------------
	// Comment

	@Override
	public int createComment(int postId, Comment comment) {
		//
		DB db = MongoClientFactory.getDB();
		DBCollection coll = db.getCollection(COLLECTION);
		
		int commentId = SequenceUtil.next(db, "post");
		comment.setCommentId(commentId);
		
		DBObject doc = createComment2DBObject(comment);
		
		DBObject post = coll.findOne(new BasicDBObject("postId", postId));
		
//		방법1. push 사용하기
//		DBObject query = new BasicDBObject("postId", postId);
//		DBObject update = new BasicDBObject();
//		update.put("$push", new BasicDBObject("comments", doc));
//		
//		coll.update(query, update);
		
//      방법2. post를 꺼내서 comment를 넣고 post업데이트!
		BasicDBList commentDocs = (BasicDBList) post.get("comments");
		if (commentDocs == null) {
			commentDocs = new BasicDBList();
		}
		commentDocs.add(doc);
		
		coll.update(new BasicDBObject("postId", postId), post);
		
		return commentId;
	}

	@Override
	public void deleteComment(int commentId) {
		//
		DB db = MongoClientFactory.getDB();
		DBCollection coll = db.getCollection(COLLECTION);
		
		DBObject query = new BasicDBObject("comments.commentId", commentId);
		
//      방법1. pull 사용하기
		DBObject update = new BasicDBObject("$pull", new BasicDBObject("comments" , new BasicDBObject("commentId", commentId)));

		coll.update(query, update);
		
//      방법2. post를 찾아서 commentDoc을 지우고 update!
		// iterator!
	}
	
	//---------------------------------------------------------------------------------

	/**
	 * DBObjec -> post
	 * select
	 * 
	 * @param DBObject
	 * @return post
	 */
	private Post mapToPost(DBObject doc) {
		//
		Post post = new Post();
		post.setContents((String) doc.get("contents"));
		post.setPostId((int) doc.get("postId"));
		post.setSubject((String) doc.get("subject"));
		
		if (doc.get("comments") != null) {
			BasicDBList commentDocs = (BasicDBList) doc.get("comments");
			Iterator<Object> iter = commentDocs.iterator();
			while (iter.hasNext()) {
				//
				DBObject commentDoc = (DBObject) iter.next();
				
				Comment comment = new Comment();
				comment.setComment((String) commentDoc.get("comment"));
				comment.setCommenterId((String) commentDoc.get("commenterId"));
				comment.setCommentId((int) commentDoc.get("commentId"));
				
				post.addComments(comment);
			}
		}
		return post;
	}
	
	/**
	 * Post -> DBObject
	 * insert
	 * 
	 * @param post
	 * @return DBObject
	 */
	private DBObject createDBObject(Post post) {
		//
		DBObject doc = new BasicDBObject();
		doc.put("contents", post.getContents());
		doc.put("postId", post.getPostId());
		doc.put("subject", post.getSubject());
		
		if (post.getComments() != null) {
			//
			BasicDBList commentDocs = new BasicDBList();
			
			for (Comment comment : post.getComments()) {
				DBObject commentDoc = new BasicDBObject();
				commentDoc.put("comment", comment.getComment());
				commentDoc.put("commentId", comment.getCommentId());
				commentDoc.put("commenterId", comment.getCommenterId());
				
				commentDocs.add(commentDoc);
			}
			doc.put("comments", commentDocs);
		}
		return doc;
	}
	
	private DBObject createComment2DBObject(Comment comment) {
		//
		DBObject doc = new BasicDBObject();
		doc.put("comment", comment.getComment());
		doc.put("commenterId", comment.getCommenterId());
		doc.put("commentId", comment.getCommentId());
		
		return doc;
	}
}
