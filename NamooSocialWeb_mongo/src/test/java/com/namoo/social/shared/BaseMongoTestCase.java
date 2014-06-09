package com.namoo.social.shared;

import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lordofthejars.nosqlunit.mongodb.MongoDbRule;
import com.lordofthejars.nosqlunit.mongodb.MongoDbRule.MongoDbRuleBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext-test.xml")
public abstract class BaseMongoTestCase {
	
	private static final String DATABASE_NAME = "namoosocialtest";
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Rule
	public MongoDbRule mongoDbRule = 
		MongoDbRuleBuilder.newMongoDbRule().defaultSpringMongoDb(DATABASE_NAME);
}
