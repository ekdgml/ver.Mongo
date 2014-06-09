package com.namoo.addressbook.domain;

import java.util.ArrayList;
import java.util.List;

public class Person {
	//
	private int personId;
	private String phone;
	private String name;
	private String email;
	private String company;
	private List<Address> addresses;
	//--------------------------------------------------------------------
	
	public void addAddress(Address address) {
		//
		if (addresses == null) {
			//
			addresses = new ArrayList<Address>();
		}
		addresses.add(address);
	}
	
	//--------------------------------------------------------------------
	// getter, setter
	
	public String getPhone() {
		return phone;
	}

	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}
}
