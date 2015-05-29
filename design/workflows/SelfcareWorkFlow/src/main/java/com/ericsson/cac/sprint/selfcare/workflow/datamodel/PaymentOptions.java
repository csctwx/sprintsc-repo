package com.ericsson.cac.sprint.selfcare.workflow.datamodel;

import java.io.Serializable;

public class PaymentOptions implements Serializable{
	private Boolean autoPay;//Required = true ,True means auto pay is enabled
	private String autoPayType;//Required = false ,Values: monthly, 45/90 days
	private Boolean advancePayEligible;//Required = true, True means advance pay is eligible
	private Boolean advancePay;//Required = true,True means advance pay is enabled
	private CreditCard creditCard;//Required = false,Credit card info
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
	 * @return the autoPayType
	 */
	public String getAutoPayType() {
		return autoPayType;
	}
	/**
	 * @param autoPayType the autoPayType to set
	 */
	public void setAutoPayType(String autoPayType) {
		this.autoPayType = autoPayType;
	}
	/**
	 * @return the advancePayEligible
	 */
	public Boolean getAdvancePayEligible() {
		return advancePayEligible;
	}
	/**
	 * @param advancePayEligible the advancePayEligible to set
	 */
	public void setAdvancePayEligible(Boolean advancePayEligible) {
		this.advancePayEligible = advancePayEligible;
	}
	/**
	 * @return the advancePay
	 */
	public Boolean getAdvancePay() {
		return advancePay;
	}
	/**
	 * @param advancePay the advancePay to set
	 */
	public void setAdvancePay(Boolean advancePay) {
		this.advancePay = advancePay;
	}
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


}
