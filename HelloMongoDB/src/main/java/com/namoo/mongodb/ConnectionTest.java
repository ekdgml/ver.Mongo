package com.namoo.mongodb;

import java.net.UnknownHostException;
import java.util.Set;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class ConnectionTest {

	public static void main(String[] args) throws UnknownHostException {
		//
		//DB 접속!
		MongoClient mongoClient = new MongoClient();
		DB db = mongoClient.getDB("mybs");
		
		//collectionName 목록조회!
		Set<String> names = db.getCollectionNames();
		
		for (String name : names) {
			System.out.println(name);
		}
		DBCollection coll = db.getCollection("songs");

		// count : doc of collection
		System.out.println("count: " + coll.count());
		
		// select doc of collection
		DBCursor cursor = coll.find();
		printResult(cursor);
		
		// 특정 조건에 따른 결과 조회
		System.out.println("==============================================");
		DBObject query = new BasicDBObject();
		query.put("name", "애국가2절");
		
		cursor = coll.find(query);
		printResult(cursor);
		System.out.println(cursor);
		
		//add doc of collection
		System.out.println("==============================================");
		DBObject doc = new BasicDBObject();
		doc.put("name", "you you you");
		doc.put("lyrics", "너를 너를 너를");
		
		coll.insert(doc);
		
		// update
		System.out.println("==============================================");
		query = new BasicDBObject("name", "you you you");
		DBObject newDoc = new BasicDBObject();
		newDoc.put("$set", new BasicDBObject("lyrics", "플라이 투 더 스카이"));
		
		coll.update(query, newDoc);
		
		doc = coll.findOne(query);
		System.out.println("==========>수정된가사");
		System.out.println(doc.get("lyrics"));
		
		// delete
		coll.remove(query);
		System.out.println(doc);
		
		cursor = coll.find();
		//printResult(cursor);
	}

	private static void printResult(DBCursor cursor) {
		while (cursor.hasNext()) {
			DBObject a = cursor.next();
			String name = (String) a.get("name");
			String lyrics = (String) a.get("lyrics");
			
			System.out.println("name: "+ name);
			System.out.println("lyrics: "+ lyrics);
		}
	}

}
