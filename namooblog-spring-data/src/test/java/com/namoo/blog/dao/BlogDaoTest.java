package com.namoo.blog.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;
import com.namoo.blog.domain.Blog;
import com.namoo.blog.domain.User;
import com.namoo.blog.shared.BaseMongoTestCase;

public class BlogDaoTest extends BaseMongoTestCase {

	private static final String DATASET_JSON = "/com/namoo/blog/dao/blogs.json";
	
	@Autowired
	private BlogDao blogDao;
	
	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testReadBlog() {
		//
		Blog blog = blogDao.readBlog("1");
		
		assertEquals("NamooBlog", blog.getName());
		assertEquals("ekdgml", blog.getOwner().getId());
	}
	
	@Test
	public void testCreateBlog() {
		//
		Blog blog = new Blog();
		blog.setName("테스트 블로그");
		blog.setOwner(new User("testuser"));
		
		String blogId = blogDao.createBlog(blog);
		
		blog = blogDao.readBlog(blogId);
		
		assertEquals("테스트 블로그", blog.getName());
		assertEquals("testuser", blog.getOwner().getId());
	}
	
	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testUpdateBlog() {
		//
		Blog blog = blogDao.readBlog("1");
		
		blog.setOwner(new User("testuser"));
		blog.setName("수정된 블로그");
		
		blogDao.updateBlog(blog);
		
		blog = blogDao.readBlog(blog.getId());
		
		assertEquals("수정된 블로그", blog.getName());
		assertEquals("testuser", blog.getOwner().getId());
	}
	
	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testDeleteBlog() {
		//
		Blog blog = blogDao.readBlog("1");
		blogDao.deleteBlog(blog.getId());
		
		assertNull(blogDao.readBlog("1"));
	}

}
