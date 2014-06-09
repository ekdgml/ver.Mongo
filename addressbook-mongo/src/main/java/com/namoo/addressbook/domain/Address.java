package com.namoo.addressbook.domain;

public class Address {
	//
	private String zipCode;
	private String fullAddress;
	
	//---------------------------------------------------------
	// getter, setter
	
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getFullAddress() {
		return fullAddress;
	}
	public void setFullAddress(String fullAddress) {
		this.fullAddress = fullAddress;
	}
}
