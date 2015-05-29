package com.ericsson.cac.sprint.shop.workflow.datamodel;

public class ChannelPolicy {
	
	private String paymentMethod;
	private Double minAmount;
	private Double maxAmount;
	private Double ceilingAmount;
	
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public Double getMinAmount() {
		return minAmount;
	}
	public void setMinAmount(Double minAmount) {
		this.minAmount = minAmount;
	}
	public Double getMaxAmount() {
		return maxAmount;
	}
	public void setMaxAmount(Double maxAmount) {
		this.maxAmount = maxAmount;
	}
	public Double getCeilingAmount() {
		return ceilingAmount;
	}
	public void setCeilingAmount(Double ceilingAmount) {
		this.ceilingAmount = ceilingAmount;
	}

}
