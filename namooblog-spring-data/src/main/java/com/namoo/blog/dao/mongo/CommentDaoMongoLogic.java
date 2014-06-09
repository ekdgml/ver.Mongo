package com.namoo.blog.dao.mongo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.namoo.blog.dao.CommentDao;
import com.namoo.blog.dao.mongo.document.CommentDoc;
import com.namoo.blog.dao.mongo.repository.CommentRepository;
import com.namoo.blog.dao.mongo.shared.IdGenerator;
import com.namoo.blog.domain.Comment;

@Repository
public class CommentDaoMongoLogic implements CommentDao {
	//
	@Autowired
	private CommentRepository repository;
	
	@Autowired
	private IdGenerator idGenerator;
	
	@Override
	public String createComment(String postId, Comment comment) {
		// 
		String commentId = idGenerator.next("comment");
		comment.setId(commentId);
		
		CommentDoc doc = new CommentDoc(postId, comment);
		repository.save(doc);
		
		return commentId;
	}

	@Override
	public Comment readComment(String commentId) {
		//
		CommentDoc doc = repository.findOne(commentId);
		if (doc != null) {
			//
			return doc.createDomain();
		}
		return null;
	}

	@Override
	public List<Comment> readCommentsByPostId(String postId) {
		//
		List<CommentDoc> docs = repository.findByPostId(postId);
		if (docs != null) {
			//
			List<Comment> comments = new ArrayList<Comment>();
			for (CommentDoc doc : docs) {
				//
				comments.add(doc.createDomain());
			}
			
			// Sorting - id로 내림차순 정렬
			Collections.sort(comments, new Comparator<Comment>() {

				@Override
				public int compare(Comment o1, Comment o2) {
					// 
					return new Integer(o2.getId()).compareTo(new Integer(o1.getId()));
				}
			});
			return comments;
		}
		return null;
	}

	@Override
	public List<Comment> readCommentsByCommenterId(String commenterId) {
		//
		List<CommentDoc> docs = repository.findByCommenterId(commenterId);
		if (docs != null) {
			//
			List<Comment> comments = new ArrayList<Comment>();
			for (CommentDoc doc : docs) {
				//
				comments.add(doc.createDomain());
			}
			return comments;
		}
		return null;
	}

	@Override
	public void updateComment(Comment comment) {
		//
		CommentDoc doc = repository.findOne(comment.getId());
		doc = new CommentDoc(doc.getPostId(), comment);
		
		repository.save(doc);
	}

	@Override
	public void deleteComment(String commentId) {
		//
		CommentDoc doc = repository.findOne(commentId);
		repository.delete(doc);
	}
}
