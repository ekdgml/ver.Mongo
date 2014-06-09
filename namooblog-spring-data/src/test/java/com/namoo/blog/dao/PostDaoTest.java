package com.namoo.blog.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;
import com.namoo.blog.domain.Post;
import com.namoo.blog.shared.BaseMongoTestCase;

public class PostDaoTest extends BaseMongoTestCase {

	private static final String DATASET_JSON = "/com/namoo/blog/dao/posts.json";
	
	@Autowired
	private PostDao postDao;
	
	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testCreatePost() {
		//
		Post post = new Post();
		post.setSubject("new post");
		post.setContents("bla bla bla...");
		
		String postId = postDao.createPost("1", post);
		
		//
		post = postDao.readPost(postId);
		assertEquals("new post", post.getSubject());
		assertEquals("bla bla bla...", post.getContents());
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testReadPost() {
		//
		Post post = postDao.readPost("1");
		assertEquals("첫번째 포스트", post.getSubject());
		assertEquals("내용입니다.", post.getContents());
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testReadPostsByBlogId() {
		//
		List<Post> posts = postDao.readPostsByBlogId("1");
		
		assertEquals(1, posts.size());
		Post post = posts.get(0);
		
		assertEquals("첫번째 포스트", post.getSubject());
		assertEquals("내용입니다.", post.getContents());
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testUpdatePost() {
		//
		Post post = postDao.readPost("1");
		
		post.setSubject("수정된 제목");
		post.setContents("수정된 내용");
		
		postDao.updatePost(post);
		
		post = postDao.readPost("1");
		assertEquals("수정된 제목", post.getSubject());
		assertEquals("수정된 내용", post.getContents());
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testDeletePost() {
		//
		postDao.deletePost("1");
		assertNull(postDao.readPost("1"));
	}
}
