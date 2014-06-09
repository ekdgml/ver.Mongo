package com.namoo.addressbook.dao;

import java.util.List;

import com.namoo.addressbook.domain.Person;

public interface AddressDao {
	//
	int createPerson(Person person);
	Person readPerson(int personId);
	List<Person> readPersonsByPhone(String phone);
	List<Person> readPersonsByName(String name);
	void updatePerson(Person person);
	void deletePerson(int personId);
}
