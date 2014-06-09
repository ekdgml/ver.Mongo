package com.namoo.blog.dao;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;
import com.namoo.blog.dao.mongo.BlogDaoMongo;
import com.namoo.blog.domain.Blog;
import com.namoo.blog.shared.BaseMongoTestCase;

public class BlogDaoTest extends BaseMongoTestCase{
	//
	private static final String DATASET_JSON  = "/blogs.json";
	
	private BlogDao dao;
	
	@Before
	public void setUp() {
		//
		this.dao = new BlogDaoMongo();
	}
	
	@Test
	public void testCreateBlog() {
		//
		Blog blog = new Blog();
		blog.setName("test");
		blog.setOwnerId("ekdgml");
		
		int blogId = dao.createBlog(blog);
		
		//assert
		blog = dao.readBlog(blogId);
		assertThat(blog.getName(), is("test"));
		assertThat(blog.getOwnerId(), is("ekdgml"));
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testReadBlog() {
		//
		Blog blog = dao.readBlog(1);
		
		//assert
		assertThat(blog.getName(), is("blog1"));
		assertThat(blog.getOwnerId(), is("ekdgml"));
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testReadBlogsByName() {
		//
		List<Blog> blogs = dao.readBlogsByName("blog1");
		
		//assert
		assertThat(blogs.get(0).getOwnerId(), is("ekdgml"));
		assertThat(blogs.get(1).getOwnerId(), is("wntjd"));
		assertThat(blogs.get(2).getOwnerId(), is("hong"));
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testUpdateBlog() {
		//
		Blog blog = dao.readBlog(1);
		blog.setName("blogTest");
		dao.updateBlog(blog);
		
		//assert
		blog = dao.readBlog(1);
		assertThat(blog.getName(), is("blogTest"));
		assertThat(blog.getOwnerId(), is("ekdgml"));
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testDeleteBlog() {
		//
		dao.deleteBlog(1);
		
		//assert
		assertNull(dao.readBlog(1));
	}

}
