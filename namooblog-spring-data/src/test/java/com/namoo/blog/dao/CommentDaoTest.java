package com.namoo.blog.dao;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;
import com.namoo.blog.domain.Comment;
import com.namoo.blog.domain.User;
import com.namoo.blog.shared.BaseMongoTestCase;

public class CommentDaoTest extends BaseMongoTestCase{
	//
	private static final String DATASET_JSON = "/com/namoo/blog/dao/comments.json";
	
	@Autowired
	private CommentDao dao;
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testCreateComment() {
		//
		Comment comment = new Comment();
		comment.setComment("코멘트!!");
		comment.setCommenter(new User("hyunohkim"));
		
		String commentId = dao.createComment("1", comment);
		
		//assert
		comment = dao.readComment(commentId);
		assertThat(comment.getComment(), is("코멘트!!"));
		assertThat(comment.getCommenter().getId(), is("hyunohkim"));
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testReadComment() {
		//
		Comment comment = dao.readComment("1");
		
		//assert
		assertEquals("코멘트!", comment.getComment());
		assertEquals("wntjd", comment.getCommenter().getId());
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testReadCommentsByPostId() {
		//
		List<Comment> comments = dao.readCommentsByPostId("1");
		
		//assert
		assertEquals(1, comments.size());
		assertEquals("코멘트!", comments.get(0).getComment());
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testReadCommentsByCommenterId() {
		//
		List<Comment> comments = dao.readCommentsByCommenterId("wntjd");
		
		//assert
		assertEquals(1, comments.size());
		assertEquals("코멘트!", comments.get(0).getComment());
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testUpdateComment() {
		//
		Comment comment = dao.readComment("1");
		comment.setComment("testComment");
		dao.updateComment(comment);
		
		//assert
		comment = dao.readComment("1");
		assertEquals("testComment", comment.getComment());
		assertEquals("wntjd", comment.getCommenter().getId());
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testDeleteComment() {
		//
		assertNotNull(dao.readComment("1"));
		dao.deleteComment("1");
		assertNull(dao.readComment("1"));
	}
}
