package com.ericsson.cac.sprint.shop.workflow.datamodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SubmitOrderRequest {
	@XmlElement(required=true)
	private String vendorCode;//WU from properties
	@XmlElement(required=false)
	private String salesChannel; // WEB_SPP from properties
	@XmlElement(required=true)
	private String orderId;    // unique id  length 10 use UUID
	@XmlElement(required=false)
	private String accountType; // If blank in config then don't send. Currently keep it blank
	@XmlElement(required=false)
	private String accountSubType; // If blank in config then don't send. Currently keep it blank
	@XmlElement(required=true)
	private AddressInfo shippingInfo; // set to billing info from request
	@XmlElement(required=true)
	private AddressInfo billingInfo; // set to billing info from request
	@XmlElement(required=true)
	private  EPPPaymentInfo paymentType;
	@XmlElement(required=true)
	private  Order orderLineInfo;
	@XmlElement(required=true)
	private PaymentInfo paymentInfo;
	@XmlElement(required=true)
	private String authorizationCode;
	@XmlElement(required=true)
	private String eppPaymentId;
	
	/**
	 * @return the vendorCode
	 */
	public String getVendorCode() {
		return vendorCode;
	}
	/**
	 * @return the salesChannel
	 */
	public String getSalesChannel() {
		return salesChannel;
	}
	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}
	/**
	 * @return the accountType
	 */
	public String getAccountType() {
		return accountType;
	}
	/**
	 * @return the accountSubType
	 */
	public String getAccountSubType() {
		return accountSubType;
	}
	/**
	 * @return the shippingInfo
	 */
	public AddressInfo getShippingInfo() {
		return shippingInfo;
	}
	/**
	 * @return the billingInfo
	 */
	public AddressInfo getBillingInfo() {
		return billingInfo;
	}
	/**
	 * @return the paymentType
	 */
	public EPPPaymentInfo getPaymentType() {
		return paymentType;
	}
	/**
	 * @return the orderLineInfo
	 */
	public Order getOrderLineInfo() {
		return orderLineInfo;
	}
	/**
	 * @param vendorCode the vendorCode to set
	 */
	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}
	/**
	 * @param salesChannel the salesChannel to set
	 */
	public void setSalesChannel(String salesChannel) {
		this.salesChannel = salesChannel;
	}
	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	/**
	 * @param accountType the accountType to set
	 */
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	/**
	 * @param accountSubType the accountSubType to set
	 */
	public void setAccountSubType(String accountSubType) {
		this.accountSubType = accountSubType;
	}
	/**
	 * @param shippingInfo the shippingInfo to set
	 */
	public void setShippingInfo(AddressInfo shippingInfo) {
		this.shippingInfo = shippingInfo;
	}
	/**
	 * @param billingInfo the billingInfo to set
	 */
	public void setBillingInfo(AddressInfo billingInfo) {
		this.billingInfo = billingInfo;
	}
	/**
	 * @param paymentType the paymentType to set
	 */
	public void setPaymentType(EPPPaymentInfo paymentType) {
		this.paymentType = paymentType;
	}
	/**
	 * @param orderLineInfo the orderLineInfo to set
	 */
	public void setOrderLineInfo(Order orderLineInfo) {
		this.orderLineInfo = orderLineInfo;
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
	 * @return the authorizationCode
	 */
	public String getAuthorizationCode() {
		return authorizationCode;
	}
	/**
	 * @param authorizationCode the authorizationCode to set
	 */
	public void setAuthorizationCode(String authorizationCode) {
		this.authorizationCode = authorizationCode;
	}
	/**
	 * @return the eppPaymentId
	 */
	public String getEppPaymentId() {
		return eppPaymentId;
	}
	/**
	 * @param eppPaymentId the eppPaymentId to set
	 */
	public void setEppPaymentId(String eppPaymentId) {
		this.eppPaymentId = eppPaymentId;
	}	
	
	

}
