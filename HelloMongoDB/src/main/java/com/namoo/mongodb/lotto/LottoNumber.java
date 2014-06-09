package com.namoo.mongodb.lotto;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MapReduceCommand;
import com.mongodb.MapReduceOutput;
import com.mongodb.MongoClient;

public class LottoNumber {
	
	public LottoNumber() {
		
	}
	
	public static void main(String[] args) throws UnknownHostException {

		// MongoDB에 접속한다.
		MongoClient mongoClient = new MongoClient();
		DB db = mongoClient.getDB("mydb");

		
		// 컬렉션 선택하기.
		DBCollection coll = db.getCollection("lottonumber");
		
		
		// 컬렉션 이름 목록을 조회한다.
				Set<String> names = db.getCollectionNames();
				for (String name : names) {
					System.out.println(name);
				}
		
		
		// 맵리듀스 만들기
		String map = 
				"function() {" +
					 "var numbers = this.number.split(',');" +
					 "for (var i = 0; i < numbers.length; i++) {" +
					 "   emit(numbers[i], {count : 1, name : this.name})}}";
		
		String reduce =
				"function(key, emits) {" +
						"var total = 0;" +
						"var name = [];" +
						"for (var i = 0; i < emits.length; i++) {" +
								"total += emits[i].count;" +
								"if (name.indexOf(emits[i].name) < 0) {" +
								"   name.push(emits[i].name);}}" +
								"return {count : total, name : name};}";
		
		MapReduceCommand cmd = new MapReduceCommand(
		        coll, 
		        map, 
		        reduce, 
		        "lotto_count", 
		        MapReduceCommand.OutputType.INLINE, 
		        null);
		
		MapReduceOutput out = coll.mapReduce(cmd);

		List<LottoValue> lottoValues = new ArrayList<LottoValue>();
		for (DBObject o : out.results()) {
		    
		    LottoValue lottoValue = new LottoValue();
		    lottoValue.setNumber(Integer.parseInt((String) o.get("_id")));
		    
		    DBObject value = (DBObject) o.get("value");
		    double lottoCount = (double) value.get("count");
		    lottoValue.setTotal((int) lottoCount);
		    
			System.out.println(o.toString());
			lottoValues.add(lottoValue);
		}
		
		Collections.sort(lottoValues);
		
		for (int i = 0; i < 6; i++) {
		    System.out.println(lottoValues.get(i).getNumber() + " ");
		}
		
		System.out.println("Done");
	}
}

