package com.namoo.addressbook.dao.shared;

import org.junit.Test;

import com.mongodb.DB;

public class SequenceUtilTest {
	//
	@Test
	public void test() {
		//
		MongoClientFactory.overrideDatabase("test");
		DB db = MongoClientFactory.getDB();
		
		int seq = SequenceUtil.next(db, "dummy");
		System.out.println(seq);
	}

}
