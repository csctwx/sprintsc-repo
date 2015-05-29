package com.ericsson.cac.sprint.shop.workflow.datamodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class GetListPhonesResponse {

	@XmlElementWrapper(name="phones")
	@XmlElement(name="phone")
	private Phone[] phones;
	@XmlElement
	private Integer status;
	@XmlElement
	private String description;

	/**
	 * @return the phones
	 */
	public Phone[] getPhones() {
		return phones;
	}

	/**
	 * @param phones
	 *            the phones to set
	 */
	public void setPhones(Phone[] phones) {
		if(phones!=null){
			if (phones.length == 0) {
				this.phones = new Phone[0];
			} else {
				this.phones = phones.clone();
			}
		}else{
			this.phones =null;
		}
	}

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}
