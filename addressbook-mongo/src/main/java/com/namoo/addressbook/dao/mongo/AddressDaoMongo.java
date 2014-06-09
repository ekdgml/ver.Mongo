package com.namoo.addressbook.dao.mongo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.namoo.addressbook.dao.AddressDao;
import com.namoo.addressbook.dao.shared.MongoClientFactory;
import com.namoo.addressbook.dao.shared.SequenceUtil;
import com.namoo.addressbook.domain.Address;
import com.namoo.addressbook.domain.Person;

public class AddressDaoMongo implements AddressDao {
	//
	private static final String COLLECTION = "persons";
	
	@Override
	public int createPerson(Person person) {
		//
		DB db = MongoClientFactory.getDB();
		// 방법1. Object -> DBObject(k,v)로 일일이 put!
		// 방법2. Object -> JSON (JSON 문자열이 DBObject로 변환됨)
		
		int personId = SequenceUtil.next(db, "person");
		person.setPersonId(personId);
		
		DBObject doc = createDBObject(person);
		DBCollection coll = db.getCollection(COLLECTION);
		coll.insert(doc);
		
		return personId;
	}
	
	@Override
	public Person readPerson(int personId) {
		//
		DB db = MongoClientFactory.getDB();
		DBCollection coll = db.getCollection(COLLECTION);
		
		DBObject doc = coll.findOne(new BasicDBObject("personId", personId));
		if (doc != null) {
			return mapToPerson(doc);
		}
		return null;
	}

	@Override
	public List<Person> readPersonsByPhone(String phone) {
		//
		DB db = MongoClientFactory.getDB();
		DBCollection coll = db.getCollection(COLLECTION);
		
		DBCursor cursor = coll.find(new BasicDBObject("phone", phone));
		
		List<Person> persons = new ArrayList<Person>();
		while (cursor.hasNext()) {
			//
			//cursor.next() 반환값은 DBObject!
			Person person = mapToPerson(cursor.next());
			persons.add(person);
		}
		return persons;
	}

	@Override
	public List<Person> readPersonsByName(String name) {
		//
		DB db = MongoClientFactory.getDB();
		DBCollection coll = db.getCollection(COLLECTION);
		
		DBObject query = new BasicDBObject("name", name);
		
		DBCursor cursor = coll.find(query);
		
		List<Person> persons = new ArrayList<Person>();
		while (cursor.hasNext()) {
			//
			//cursor.next() 반환값은 DBObject!
			Person person = mapToPerson(cursor.next());
			persons.add(person);
		}
		return persons;
	}

	@Override
	public void updatePerson(Person person) {
		//
		DB db = MongoClientFactory.getDB();
		DBCollection coll = db.getCollection(COLLECTION);
		
		DBObject query = new BasicDBObject("personId", person.getPersonId());
		DBObject doc = createDBObject(person);
		
		coll.update(query, doc);
	}

	@Override
	public void deletePerson(int personId) {
		//
		DB db = MongoClientFactory.getDB();
		DBCollection coll = db.getCollection(COLLECTION);
		
		coll.remove(new BasicDBObject("personId", personId));
	}
	
	//-------------------------------------------------------------------------
	//private method
	
//	JSON 사용으로 간편!	
//	private DBObject createDBObject(Person person) {
//		//
//		String json = new Gson().toJson(person);
//		return (DBObject) JSON.parse(json);
//	}
	
	/**
	 * DBObjec -> Person
	 * select
	 * 
	 * @param DBObject
	 * @return person
	 */
	private Person mapToPerson(DBObject doc) {
		//
		Person person = new Person();
		person.setPersonId((int) doc.get("personId"));
		person.setEmail((String) doc.get("email"));
		person.setName((String) doc.get("name"));
		person.setPhone((String) doc.get("phone"));
		person.setCompany((String) doc.get("company")); 
		
		BasicDBList addressDocs = (BasicDBList) doc.get("addresses");
		Iterator<Object> iter = addressDocs.iterator();
			while (iter.hasNext()) {
				DBObject addressDoc = (DBObject) iter.next();
				
				Address address = new Address();
				address.setZipCode((String) addressDoc.get("zipCode"));
				address.setFullAddress((String) addressDoc.get("fullAddress"));
				
				person.addAddress(address);
			}
		return person;
	}
	
	/**
	 * Person -> DBObject
	 * insert
	 * 
	 * @param person
	 * @return DBObject
	 */
	private DBObject createDBObject(Person person) {
		//
		DBObject doc = new BasicDBObject();
		doc.put("phone", person.getPhone());
		doc.put("name", person.getName());
		doc.put("email", person.getEmail());
		doc.put("personId", person.getPersonId());
		doc.put("company", person.getCompany());
		
		if (person.getAddresses() != null) {
			//
			BasicDBList addressDocs = new BasicDBList();
			
			for (Address address : person.getAddresses()) {
				DBObject addressDoc = new BasicDBObject();
				addressDoc.put("zipCode", address.getZipCode());
				addressDoc.put("fullAddress", address.getFullAddress());
				
				addressDocs.add(addressDoc);
			}
			doc.put("addresses", addressDocs);
		}
		return doc;
	}
}
