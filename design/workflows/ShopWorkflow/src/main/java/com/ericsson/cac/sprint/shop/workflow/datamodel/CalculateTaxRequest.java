package com.ericsson.cac.sprint.shop.workflow.datamodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CalculateTaxRequest {
	@XmlElement(required=true)
	private AddressInfo billingInfo;
	@XmlElement(required=true)
	private String invoiceNumber;
	@XmlElement(required=true)
	private String itemId;
	@XmlElement(required=false)
	private Integer lineNumber;
	@XmlElement(required=false)
	private Integer quantity;	
	@XmlElement(required=true)
	private Double taxOnAmount;
	
	public AddressInfo getBillingInfo() {
		return billingInfo;
	}
	public void setBillingInfo(AddressInfo billingInfo) {
		this.billingInfo = billingInfo;
	}
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public Integer getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(Integer lineNumber) {
		this.lineNumber = lineNumber;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
		
	public Double getTaxOnAmount() {
		return taxOnAmount;
	}
	public void setTaxOnAmount(Double taxOnAmount) {
		this.taxOnAmount = taxOnAmount;
	}
	
	
}
