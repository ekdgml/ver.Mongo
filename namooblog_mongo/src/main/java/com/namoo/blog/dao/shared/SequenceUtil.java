package com.namoo.blog.dao.shared;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public class SequenceUtil {
	//
	public static int next(DB db, String key) {
		//
		DBCollection coll = db.getCollection("sequences");
		DBObject query = new BasicDBObject("key", key);
		DBObject doc = new BasicDBObject("$inc", new BasicDBObject("value", 1));
		//DBObject fields = new BasicDBObject("value", 1) --> value라는 값을 가진 것만 내보내줘!
		
		DBObject sequence = coll.findAndModify(
				query, // query
				null,  // fields
				null,  // sort
				false, // remove
				doc,  // update
				true, // returnNew
				true); // upsert (없으면 만들어라)
		
		return (Integer) sequence.get("value");
	}
}
