package com.namoo.blog.shared;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;

import com.lordofthejars.nosqlunit.mongodb.MongoDbRule;
import com.lordofthejars.nosqlunit.mongodb.MongoDbRule.MongoDbRuleBuilder;
import com.namoo.blog.dao.shared.MongoClientFactory;

public class BaseMongoTestCase {
	//
	private static final String DATABASE_NAME = "namooblogtest";
	
	@Rule //추가적인기능을 할 수 있는 junit의 annotation
	public MongoDbRule mongoDbRule = MongoDbRuleBuilder.newMongoDbRule().defaultManagedMongoDb(DATABASE_NAME);
	
	@BeforeClass
	public static void initDatabase() {
		//
		MongoClientFactory.overrideDatabase(DATABASE_NAME);
	}
	
	@AfterClass
	public static void dropDatabase() {
		//
		MongoClientFactory.getDB().dropDatabase();
	}
}
