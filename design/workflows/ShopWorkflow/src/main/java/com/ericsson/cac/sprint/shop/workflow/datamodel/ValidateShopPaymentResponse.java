package com.ericsson.cac.sprint.shop.workflow.datamodel;

public class ValidateShopPaymentResponse {
	
	private Boolean valid;
	private Integer status;
	private String description;
	private String cardType;
	private String paymentCardType;
	
	public Boolean getValid() {
		return valid;
	}
	public void setValid(Boolean valid) {
		this.valid = valid;
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
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getPaymentCardType() {
		return paymentCardType;
	}
	public void setPaymentCardType(String paymentCardType) {
		this.paymentCardType = paymentCardType;
	}
	
	
}
