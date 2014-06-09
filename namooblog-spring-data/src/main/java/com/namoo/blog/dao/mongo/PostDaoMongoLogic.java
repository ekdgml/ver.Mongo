package com.namoo.blog.dao.mongo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.namoo.blog.dao.PostDao;
import com.namoo.blog.dao.mongo.document.PostDoc;
import com.namoo.blog.dao.mongo.repository.PostRepository;
import com.namoo.blog.dao.mongo.shared.IdGenerator;
import com.namoo.blog.domain.Post;

@Repository
public class PostDaoMongoLogic implements PostDao {
	//
	@Autowired
	private PostRepository repository;
	
	@Autowired
	private IdGenerator idGenerator;
	
	@Override
	public String createPost(String blogId, Post post) {
		// 
		post.setId(idGenerator.next("blog"));
		PostDoc doc = new PostDoc(blogId, post);
		
		repository.save(doc);
		return post.getId();
	}

	@Override
	public Post readPost(String postId) {
		// 
		PostDoc doc = repository.findOne(postId);
		if (doc != null) {
			return doc.createDomain();
		}
		return null;
	}

	@Override
	public List<Post> readPostsByBlogId(String blogId) {
		// 
		List<PostDoc> postDocs = repository.findByBlogId(blogId);
		if (postDocs != null) {
			List<Post> posts = new ArrayList<Post>();
			for (PostDoc doc : postDocs) {
				posts.add(doc.createDomain());
			}
			return posts;
		}
		return null;
	}

	@Override
	public void updatePost(Post post) {
		// 
		PostDoc doc = repository.findOne(post.getId());
		doc.setSubject(post.getSubject());
		doc.setContents(post.getContents());
		
		repository.save(doc);
	}

	@Override
	public void deletePost(String postId) {
		// 
		PostDoc doc = repository.findOne(postId);
		repository.delete(doc);
	}
}
