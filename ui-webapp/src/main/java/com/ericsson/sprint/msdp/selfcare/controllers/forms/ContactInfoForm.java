package com.ericsson.sprint.msdp.selfcare.controllers.forms;

public class ContactInfoForm {

	private String firstName;
	private String mi;
	private String lastName;
	private String phoneNumber;
	private String altNumber;
	private String email;

	private String addr1Line1;
	private String addr1Line2;
	private String addr1City;
	private String addr1State;
	private String addr1Zip;

	private String addr2Line1;
	private String addr2Line2;
	private String addr2City;
	private String addr2State;
	private String addr2Zip;

	private String birthDayMonth;
	private String birthDayDate;
	private String birthDayYear;

	private String preferredLanguage;

	public ContactInfoForm() {
		super();
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMi() {
		return mi;
	}

	public void setMi(String mi) {
		this.mi = mi;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAltNumber() {
		return altNumber;
	}

	public void setAltNumber(String altNumber) {
		this.altNumber = altNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddr1Line1() {
		return addr1Line1;
	}

	public void setAddr1Line1(String addr1Line1) {
		this.addr1Line1 = addr1Line1;
	}

	public String getAddr1Line2() {
		return addr1Line2;
	}

	public void setAddr1Line2(String addr1Line2) {
		this.addr1Line2 = addr1Line2;
	}

	public String getAddr1City() {
		return addr1City;
	}

	public void setAddr1City(String addr1City) {
		this.addr1City = addr1City;
	}

	public String getAddr1State() {
		return addr1State;
	}

	public void setAddr1State(String addr1State) {
		this.addr1State = addr1State;
	}

	public String getAddr1Zip() {
		return addr1Zip;
	}

	public void setAddr1Zip(String addr1Zip) {
		this.addr1Zip = addr1Zip;
	}

	public String getAddr2Line1() {
		return addr2Line1;
	}

	public void setAddr2Line1(String addr2Line1) {
		this.addr2Line1 = addr2Line1;
	}

	public String getAddr2Line2() {
		return addr2Line2;
	}

	public void setAddr2Line2(String addr2Line2) {
		this.addr2Line2 = addr2Line2;
	}

	public String getAddr2City() {
		return addr2City;
	}

	public void setAddr2City(String addr2City) {
		this.addr2City = addr2City;
	}

	public String getAddr2State() {
		return addr2State;
	}

	public void setAddr2State(String addr2State) {
		this.addr2State = addr2State;
	}

	public String getAddr2Zip() {
		return addr2Zip;
	}

	public void setAddr2Zip(String addr2Zip) {
		this.addr2Zip = addr2Zip;
	}

	public String getBirthDayMonth() {
		return birthDayMonth;
	}

	public void setBirthDayMonth(String birthDayMonth) {
		this.birthDayMonth = birthDayMonth;
	}

	public String getBirthDayDate() {
		return birthDayDate;
	}

	public void setBirthDayDate(String birthDayDate) {
		this.birthDayDate = birthDayDate;
	}

	public String getBirthDayYear() {
		return birthDayYear;
	}

	public void setBirthDayYear(String birthDayYear) {
		this.birthDayYear = birthDayYear;
	}

	public String getPreferredLanguage() {
		return preferredLanguage;
	}

	public void setPreferredLanguage(String preferredLanguage) {
		this.preferredLanguage = preferredLanguage;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ContactInfoForm [firstName=");
		builder.append(firstName);
		builder.append(", mi=");
		builder.append(mi);
		builder.append(", lastName=");
		builder.append(lastName);
		builder.append(", phoneNumber=");
		builder.append(phoneNumber);
		builder.append(", altNumber=");
		builder.append(altNumber);
		builder.append(", email=");
		builder.append(email);
		builder.append(", addr1Line1=");
		builder.append(addr1Line1);
		builder.append(", addr1Line2=");
		builder.append(addr1Line2);
		builder.append(", addr1City=");
		builder.append(addr1City);
		builder.append(", addr1State=");
		builder.append(addr1State);
		builder.append(", addr1Zip=");
		builder.append(addr1Zip);
		builder.append(", addr2Line1=");
		builder.append(addr2Line1);
		builder.append(", addr2Line2=");
		builder.append(addr2Line2);
		builder.append(", addr2City=");
		builder.append(addr2City);
		builder.append(", addr2State=");
		builder.append(addr2State);
		builder.append(", addr2Zip=");
		builder.append(addr2Zip);
		builder.append(", birthDayMonth=");
		builder.append(birthDayMonth);
		builder.append(", birthDayDate=");
		builder.append(birthDayDate);
		builder.append(", birthDayYear=");
		builder.append(birthDayYear);
		builder.append(", preferredLanguage=");
		builder.append(preferredLanguage);
		builder.append("]");
		return builder.toString();
	}

}
