package com.ericsson.cac.sprint.shop.workflow.datamodel;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.Gson;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ShippingAndBillingResponse {
	@XmlElement
	private Boolean addressValid;
	@XmlElement
	private Boolean paymentValid;
	@XmlElementWrapper(name="equipments")
	@XmlElement(name="equipment")
	private Equipment[] equipments;
	@XmlElement
	private String transactionId;
	@XmlElement
	private String orderId;
	@XmlElement
	private Integer status;
	@XmlElement
	private String description;
	@XmlElement
	private String CardType;
	@XmlElement
	private String paymentCardType;
	@XmlElement
	private String shippingFee;
	@XmlElement
	private Double totalAmt;
	
	@XmlElement
	private Date invoiceDate;
	
	

	public Boolean getAddressValid() {
		return addressValid;
	}

	public void setAddressValid(Boolean addressValid) {
		this.addressValid = addressValid;
	}

	public Boolean getPaymentValid() {
		return paymentValid;
	}

	public void setPaymentValid(Boolean paymentValid) {
		this.paymentValid = paymentValid;
	}

	public Equipment[] getEquipments() {
		return equipments;
	}

	public void setEquipments(Equipment[] equipments) {
		this.equipments = equipments;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getCardType() {
		return CardType;
	}

	public void setCardType(String cardType) {
		CardType = cardType;
	}

	public String getPaymentCardType() {
		return paymentCardType;
	}

	public void setPaymentCardType(String paymentCardType) {
		this.paymentCardType = paymentCardType;
	}

	public String getShippingFee() {
		return shippingFee;
	}

	public void setShippingFee(String shippingFee) {
		this.shippingFee = shippingFee;
	}

	public Double getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(Double totalAmt) {
		this.totalAmt = totalAmt;
	}
	
	@Override
	public String toString() {		
		Gson gson=new Gson();
		String gsonResult=gson.toJson(this);
		return gsonResult;
	}
}
