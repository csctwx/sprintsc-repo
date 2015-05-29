package com.ericsson.cac.sprint.selfcare.workflow.datamodel;

import java.io.Serializable;
import java.util.Date;

public class AccountBalance implements Serializable{
	private String accountStatus;//Required = true ,Values: Current/Sufficient, Overdue,
	private float nextMonthCharges;//Required = true ,Next month charges
	private float cashBalance;//Required = true ,Cash balance
	private float totalCharges;//Required = true ,Total charges
	private float dueAmount;//Required = true ,Due amount
	private Date dueBy;//Required = true ,Due date
	private Integer billingCycleDay;//Required = true ,Billing cycle day
	private String currency;//Required = false,Currency for above values. If not specified $ would be the default value.
	private String balanceInfo;//Required = false,Balance info
	/**
	 * @return the accountStatus
	 */
	public String getAccountStatus() {
		return accountStatus;
	}
	/**
	 * @param accountStatus the accountStatus to set
	 */
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
	/**
	 * @return the nextMonthCharges
	 */
	public float getNextMonthCharges() {
		return nextMonthCharges;
	}
	/**
	 * @param nextMonthCharges the nextMonthCharges to set
	 */
	public void setNextMonthCharges(float nextMonthCharges) {
		this.nextMonthCharges = nextMonthCharges;
	}
	/**
	 * @return the cashBalance
	 */
	public float getCashBalance() {
		return cashBalance;
	}
	/**
	 * @param cashBalance the cashBalance to set
	 */
	public void setCashBalance(float cashBalance) {
		this.cashBalance = cashBalance;
	}
	/**
	 * @return the totalCharges
	 */
	public float getTotalCharges() {
		return totalCharges;
	}
	/**
	 * @param totalCharges the totalCharges to set
	 */
	public void setTotalCharges(float totalCharges) {
		this.totalCharges = totalCharges;
	}
	/**
	 * @return the dueAmount
	 */
	public float getDueAmount() {
		return dueAmount;
	}
	/**
	 * @param dueAmount the dueAmount to set
	 */
	public void setDueAmount(float dueAmount) {
		this.dueAmount = dueAmount;
	}
	/**
	 * @return the dueBy
	 */
	public Date getDueBy() {
		return dueBy;
	}
	/**
	 * @param dueBy the dueBy to set
	 */
	public void setDueBy(Date dueBy) {
		this.dueBy = dueBy;
	}
	/**
	 * @return the billingCycleDay
	 */
	public Integer getBillingCycleDay() {
		return billingCycleDay;
	}
	/**
	 * @param billingCycleDay the billingCycleDay to set
	 */
	public void setBillingCycleDay(Integer billingCycleDay) {
		this.billingCycleDay = billingCycleDay;
	}
	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}
	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	/**
	 * @return the balanceInfo
	 */
	public String getBalanceInfo() {
		return balanceInfo;
	}
	/**
	 * @param balanceInfo the balanceInfo to set
	 */
	public void setBalanceInfo(String balanceInfo) {
		this.balanceInfo = balanceInfo;
	}


}
