package com.namoo.blog.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;
import com.namoo.blog.domain.User;
import com.namoo.blog.shared.BaseMongoTestCase;

public class UserDaoTest extends BaseMongoTestCase{
	//
	private static final String DATASET_JSON = "/com/namoo/blog/dao/users.json";
	
	@Autowired
	private UserDao dao;
	
	@Test
	public void testCreateUser() {
		//
		User user = new User();
		user.setEmail("test");
		user.setId("test");
		user.setName("test");
		dao.createUser(user);
		
		//assert
		user = dao.readUser("test");
		assertEquals("test", user.getEmail());
		assertEquals("test", user.getId());
		assertEquals("test", user.getName());
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testReadUser() {
		//
		User user = dao.readUser("ekdgml");
		
		//assert
		assertEquals("박상희", user.getName());
		assertEquals("ekdgml@naver.com", user.getEmail());
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testReadAllUsers() {
		//
		List<User> users = dao.readAllUsers();
		
		//assert
		assertEquals(3, users.size());
		assertEquals("ekdgml", users.get(0).getId());
		assertEquals("박상희", users.get(0).getName());
		assertEquals("ekdgml2", users.get(1).getId());
		assertEquals("wntjd", users.get(2).getId());
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testReadUsersByName() {
		//
		List<User> users = dao.readUsersByName("박상희");
		
		//assert
		assertEquals(2, users.size());
		assertEquals("ekdgml", users.get(0).getId());
		assertEquals("ekdgml@naver.com", users.get(0).getEmail());
		assertEquals("ekdgml2", users.get(1).getId());
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testUpdateUser() {
		//
		User user = dao.readUser("ekdgml");
		user.setName("상희");
		dao.updateUser(user);
		
		//assert
		user = dao.readUser("ekdgml");
		assertEquals("상희", user.getName());
		assertEquals("ekdgml@naver.com", user.getEmail());
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testDeleteUser() {
		//
		assertNotNull(dao.readUser("ekdgml"));
		dao.deleteUser("ekdgml");
		
		//assert
		assertNull(dao.readUser("ekdgml"));
	}

}
