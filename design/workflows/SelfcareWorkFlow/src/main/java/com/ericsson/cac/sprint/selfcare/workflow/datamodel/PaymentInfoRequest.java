package com.ericsson.cac.sprint.selfcare.workflow.datamodel;

import java.io.Serializable;

public class PaymentInfoRequest implements Serializable {
	private CreditCard creditCard;//Required = true,Credit card info
	/**
	 * @return the creditCard
	 */
	public CreditCard getCreditCard() {
		return creditCard;
	}
	/**
	 * @param creditCard the creditCard to set
	 */
	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}
	/**
	 * @return the billingAddress
	 */
	public Address getBillingAddress() {
		return billingAddress;
	}
	/**
	 * @param billingAddress the billingAddress to set
	 */
	public void setBillingAddress(Address billingAddress) {
		this.billingAddress = billingAddress;
	}
	/**
	 * @return the autoPay
	 */
	public Boolean getAutoPay() {
		return autoPay;
	}
	/**
	 * @param autoPay the autoPay to set
	 */
	public void setAutoPay(Boolean autoPay) {
		this.autoPay = autoPay;
	}
	/**
	 * @return the async
	 */
	public AsyncRequest getAsync() {
		return async;
	}
	/**
	 * @param async the async to set
	 */
	public void setAsync(AsyncRequest async) {
		this.async = async;
	}
	private Address billingAddress;//Required = true,Billing address
	private Boolean autoPay;//Required = true,True means auto pay is enabled (setup).
	private AsyncRequest async;//Required = false,A-sync request info.


}
