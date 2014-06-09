package com.namoo.blog.dao;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;
import com.namoo.blog.dao.mongo.PostDaoMongo;
import com.namoo.blog.domain.Comment;
import com.namoo.blog.domain.Post;
import com.namoo.blog.shared.BaseMongoTestCase;

public class PostDaoTest extends BaseMongoTestCase{
	//
	private static final String DATASET_JSON  = "/posts.json";
	
	private PostDao dao;
	
	@Before
	public void setUp() throws Exception {
		//
		this.dao = new PostDaoMongo();
	}

	@Test
	public void testCreatePost() {
		//
		Post post = new Post();
		post.setContents("testContents");
		post.setSubject("testSubject");
		int postId = dao.createPost(post);
		
		//assert
		post = dao.readPost(postId);
		assertThat(post.getContents(), is("testContents"));
		assertThat(post.getSubject(), is("testSubject"));
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testReadPost() {
		//
		Post post = dao.readPost(1);
		
		//assert
		assertThat(post.getContents(), is("post1-Contents"));
		assertThat(post.getSubject(), is("post1"));
		assertThat(post.getComments().get(0).getComment(), is("comment1"));
		assertThat(post.getComments().get(0).getCommenterId(), is("ekdgml"));
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testReadPostsBySubject() {
		//
		List<Post> posts = dao.readPostsBySubject("post1");
		
		//assert
		assertThat(posts.get(0).getContents(), is("post1-Contents"));
		assertThat(posts.get(1).getContents(), is("post2-Contents"));
		assertThat(posts.get(2).getContents(), is("post3-Contents"));
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testReadPostsByBlogId() {
		//
		List<Post> posts = dao.readPostsByBlogId(1);
		
		//assert
		assertThat(posts.get(0).getContents(), is("post1-Contents"));
		assertThat(posts.get(1).getContents(), is("post2-Contents"));
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testUpdatePost() {
		//
		Post post = dao.readPost(1);
		post.setContents("testcontents");
		dao.updatePost(post);
		
		//assert
		post = dao.readPost(1);
		assertThat(post.getContents(), is("testcontents"));
		assertThat(post.getSubject(), is("post1"));
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testDeletePost() {
		//
		dao.deletePost(1);
		
		//assert
		assertNull(dao.readPost(1));
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testCreateComment() {
		//
		Comment comment = new Comment();
		comment.setComment("testComment");
		comment.setCommenterId("ekdgml");
		dao.createComment(1, comment);
		
		//assert
		Post post = dao.readPost(1);
		assertThat(post.getComments().get(1).getComment(), is("testComment"));
		assertThat(post.getComments().get(1).getCommenterId(), is("ekdgml"));
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testDeleteComment() {
		//
		dao.deleteComment(1);
		
		//assert
		Post post = dao.readPost(1);
		assertNull(post.getComments());
	}

}
