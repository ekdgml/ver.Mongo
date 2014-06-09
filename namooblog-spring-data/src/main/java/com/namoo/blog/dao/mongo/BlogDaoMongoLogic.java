package com.namoo.blog.dao.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.namoo.blog.dao.BlogDao;
import com.namoo.blog.dao.mongo.document.BlogDoc;
import com.namoo.blog.dao.mongo.repository.BlogRepository;
import com.namoo.blog.dao.mongo.shared.IdGenerator;
import com.namoo.blog.domain.Blog;

@Repository
public class BlogDaoMongoLogic implements BlogDao {
	
	@Autowired
	private BlogRepository repository;
	
	@Autowired
	private IdGenerator idGenerator;
	
	@Override
	public String createBlog(Blog blog) {
		// 
		blog.setId(idGenerator.next("blog"));
		BlogDoc doc = new BlogDoc(blog);
		
		repository.save(doc);
		return blog.getId();
	}

	@Override
	public Blog readBlog(String blogId) {
		// 
		BlogDoc doc = repository.findOne(blogId);
		if (doc != null) {
			return doc.createDomain();
		}
		return null;
	}

	@Override
	public void updateBlog(Blog blog) {
		// 
		BlogDoc doc = new BlogDoc(blog);
		repository.save(doc);
	}

	@Override
	public void deleteBlog(String blogId) {
		// 
		BlogDoc doc = repository.findOne(blogId);
		repository.delete(doc);
	}
}
