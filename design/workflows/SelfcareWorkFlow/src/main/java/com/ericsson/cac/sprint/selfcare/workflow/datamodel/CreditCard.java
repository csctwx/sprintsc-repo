package com.ericsson.cac.sprint.selfcare.workflow.datamodel;

import java.io.Serializable;

public class CreditCard implements Serializable{
	private String cardType;//Required = true ,Values: visa, mastercard
	private String number;//Required = true,Credit card number
	private String nameOnCard;//Required = true,Name on card
	private Integer expiryMonth;//Required = true,Expiry month
	private Integer expiryYear;//Required = true,Expiry year
	private String ccv;//Required = true,CCV
	private Boolean pinless;//Required = true,True means pin-less card
	/**
	 * @return the cardType
	 */
	public String getCardType() {
		return cardType;
	}
	/**
	 * @param cardType the cardType to set
	 */
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	/**
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}
	/**
	 * @param number the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}
	/**
	 * @return the nameOnCard
	 */
	public String getNameOnCard() {
		return nameOnCard;
	}
	/**
	 * @param nameOnCard the nameOnCard to set
	 */
	public void setNameOnCard(String nameOnCard) {
		this.nameOnCard = nameOnCard;
	}
	/**
	 * @return the expiryMonth
	 */
	public Integer getExpiryMonth() {
		return expiryMonth;
	}
	/**
	 * @param expiryMonth the expiryMonth to set
	 */
	public void setExpiryMonth(Integer expiryMonth) {
		this.expiryMonth = expiryMonth;
	}
	/**
	 * @return the expiryYear
	 */
	public Integer getExpiryYear() {
		return expiryYear;
	}
	/**
	 * @param expiryYear the expiryYear to set
	 */
	public void setExpiryYear(Integer expiryYear) {
		this.expiryYear = expiryYear;
	}
	/**
	 * @return the ccv
	 */
	public String getCcv() {
		return ccv;
	}
	/**
	 * @param ccv the ccv to set
	 */
	public void setCcv(String ccv) {
		this.ccv = ccv;
	}
	/**
	 * @return the pinless
	 */
	public Boolean getPinless() {
		return pinless;
	}
	/**
	 * @param pinless the pinless to set
	 */
	public void setPinless(Boolean pinless) {
		this.pinless = pinless;
	}


}
