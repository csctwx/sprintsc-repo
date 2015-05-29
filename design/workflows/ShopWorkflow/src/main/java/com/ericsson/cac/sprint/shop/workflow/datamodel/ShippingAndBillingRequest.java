package com.ericsson.cac.sprint.shop.workflow.datamodel;

import com.ericsson.cac.sprint.shop.constants.ShopWorkFlowConstants;
import com.google.gson.Gson;

public class ShippingAndBillingRequest {
	
	private AddressInfo billingInfo;
	
	private AddressInfo shippingInfo;
	
	private PaymentInfo paymentInfo;
	
	private Equipment[] equipments;
	
	private String brandId;

	public AddressInfo getBillingInfo() {
		return billingInfo;
	}

	public void setBillingInfo(AddressInfo billingInfo) {
		this.billingInfo = billingInfo;
	}

	public PaymentInfo getPaymentInfo() {
		return paymentInfo;
	}

	public void setPaymentInfo(PaymentInfo paymentInfo) {
		this.paymentInfo = paymentInfo;
	}

	public Equipment[] getEquipments() {
		return equipments;
	}

	public void setEquipments(Equipment[] equipments) {
		this.equipments = equipments;
	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
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
	@Override
	public String toString() {		
		Gson gson=new Gson();
		String gsonResult=gson.toJson(this);
		return gsonResult;
	}
	
}
