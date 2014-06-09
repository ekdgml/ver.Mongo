package com.namoo.blog.dao.mongo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.WriteConcern;

@Configuration
@PropertySource("classpath:mongo.properties")
@EnableMongoRepositories(basePackages = "com.namoo.blog.dao.mongo.repository")
public class MongoConfiguration extends AbstractMongoConfiguration {
	//
	@Autowired Environment env;

	@Override
	protected String getDatabaseName() {
		// 
		return env.getProperty("database.name");
	}
	
	@Override
	public @Bean Mongo mongo() throws Exception {
		// 
		// MongoDB SAFE MODE 활성화
		MongoClientOptions options = MongoClientOptions.builder()
				.writeConcern(WriteConcern.SAFE)
				.build();
		
		return new MongoClient(env.getProperty("database.host"), options);
	}

	// ---------------------------------------------------------------------------------------------
	
	public @Bean MongoRepositoryFactory getMongoRepositoryFactory() throws Exception {
		//
		return new MongoRepositoryFactory(mongoTemplate());
	}
	
}
