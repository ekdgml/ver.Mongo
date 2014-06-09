package com.namoo.blog.dao.shared;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.MongoClient;

public class MongoClientFactory {
	//
	private static MongoClientFactory instance = new MongoClientFactory();
	
	private String databseName = "address";
	
	private MongoClient mongoClient;
	
	private MongoClientFactory() {
		//
		try {
			mongoClient = new MongoClient();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	//----------------------------------------------------------------------------------
	
	public static DB getDB() {
		//
		return instance.mongoClient.getDB(instance.databseName);
	}
	
	public static void overrideDatabase(String databaseName) {
		//
		instance.databseName = databaseName;
	}
}
