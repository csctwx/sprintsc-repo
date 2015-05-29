package com.ericsson.cac.sprint.shop.workflow.datamodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.Gson;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CompletePurchaseRequest {
	@XmlElement
	private AddressInfo billingInfo;
	@XmlElement
	private AddressInfo shippingInfo;
	@XmlElement
	private PaymentInfo paymentInfo;
	@XmlElement
	private Equipment[] equipments;
	@XmlElement
	private String transactionId;
	@XmlElement
	private String orderId;	
	@XmlElement
	private String brandId;
	@XmlElement
	private String flag;
	/**
	 * @return the billingInfo
	 */
	public AddressInfo getBillingInfo() {
		return billingInfo;
	}
	/**
	 * @param billingInfo the billingInfo to set
	 */
	public void setBillingInfo(AddressInfo billingInfo) {
		this.billingInfo = billingInfo;
	}
	/**
	 * @return the paymentInfo
	 */
	public PaymentInfo getPaymentInfo() {
		return paymentInfo;
	}
	/**
	 * @param paymentInfo the paymentInfo to set
	 */
	public void setPaymentInfo(PaymentInfo paymentInfo) {
		this.paymentInfo = paymentInfo;
	}
	/**
	 * @return the equipments
	 */
	public Equipment[] getEquipments() {
		return equipments;
	}
	/**
	 * @param equipments the equipments to set
	 */
	public void setEquipments(Equipment[] equipments) {
		this.equipments = equipments;
	}
	/**
	 * @return the transactionId
	 */
	public String getTransactionId() {
		return transactionId;
	}
	/**
	 * @param transactionId the transactionId to set
	 */
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}
	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	/**
	 * @return the shippingInfo
	 */
	public AddressInfo getShippingInfo() {
		return shippingInfo;
	}
	/**
	 * @param shippingInfo the shippingInfo to set
	 */
	public void setShippingInfo(AddressInfo shippingInfo) {
		this.shippingInfo = shippingInfo;
	}
	public String getBrandId() {
		return brandId;
	}
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
	/**
	 * @return the flag
	 */
	public String getFlag() {
		return flag;
	}
	/**
	 * @param flag the flag to set
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	public String toString()
	{
		return new Gson().toJson(this);
	}

}
