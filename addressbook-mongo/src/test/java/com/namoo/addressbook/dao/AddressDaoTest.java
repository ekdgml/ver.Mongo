package com.namoo.addressbook.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mongodb.DB;
import com.namoo.addressbook.dao.mongo.AddressDaoMongo;
import com.namoo.addressbook.dao.shared.MongoClientFactory;
import com.namoo.addressbook.domain.Address;
import com.namoo.addressbook.domain.Person;

public class AddressDaoTest {
	//
	private AddressDao dao;
	
	@BeforeClass
	public static void initDatabase() {
		//
		MongoClientFactory.overrideDatabase("address-test");
	}
	
	@Before
	public void setUp() {
		//
		dao = new AddressDaoMongo();
	}
	
	@After
	public void tearDown() {
		//
		DB db = MongoClientFactory.getDB();
		db.dropDatabase();
	}
	
	@Test
	public void testCRUD() {
		//
		Person person = new Person();
		person.setEmail("ekdgml@naver.com");
		person.setName("박상희");
		person.setPhone("010-2941-1630");
		person.setCompany("코스타");
		
		Address address = new Address();
		address.setFullAddress("경기도 안양시 동안구");
		address.setZipCode("431-729");
		
		person.addAddress(address);
		
		int personId = dao.createPerson(person);
		
		//assert
		person = dao.readPerson(personId);
		assertNotNull(person);
		assertEquals("박상희", person.getName());
		assertEquals("ekdgml@naver.com", person.getEmail());
		assertEquals("코스타", person.getCompany());
		assertEquals("경기도 안양시 동안구", person.getAddresses().get(0).getFullAddress());
		
		//----------------------------------------------------------------------------------------
		//전화번호로 조회
		
		List<Person> persons = dao.readPersonsByName("박상희");
		
		//assert
		assertEquals("박상희", persons.get(0).getName());
		assertEquals("ekdgml@naver.com", persons.get(0).getEmail());
		assertEquals("경기도 안양시 동안구", persons.get(0).getAddresses().get(0).getFullAddress());
		//----------------------------------------------------------------------------------------
		// 이름으로 조회
		
		persons = dao.readPersonsByPhone("010-2941-1630");
		person = persons.get(0);
		
		//assert
		assertEquals("박상희", person.getName());
		assertEquals("ekdgml@naver.com", person.getEmail());
		assertEquals("코스타", person.getCompany());
		assertEquals("경기도 안양시 동안구", person.getAddresses().get(0).getFullAddress());
		
		//----------------------------------------------------------------------------------------
		//update
		person = dao.readPerson(personId);
		person.setEmail("test@naver.com");
		person.setName("상희");
		dao.updatePerson(person);
		
		//assert
		person = dao.readPerson(personId);
		assertEquals("상희", person.getName());
		assertEquals("test@naver.com", person.getEmail());
		assertEquals("경기도 안양시 동안구", person.getAddresses().get(0).getFullAddress());
		
		//----------------------------------------------------------------------------------------
		//delete
		dao.deletePerson(personId);
		
		//assert
		person = dao.readPerson(personId);
		assertNull(person);
	}
}
