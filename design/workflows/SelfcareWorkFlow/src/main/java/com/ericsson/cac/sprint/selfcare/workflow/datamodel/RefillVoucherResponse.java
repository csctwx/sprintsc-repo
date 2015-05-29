package com.ericsson.cac.sprint.selfcare.workflow.datamodel;

import java.io.Serializable;

public class RefillVoucherResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RefillVoucher[] refillStatus;
	private AccountBalance balance;
	private AsyncResponse async;
	/**
	 * @return the refillStatus
	 */
	public RefillVoucher[] getRefillStatus() {
		return refillStatus;
	}
	/**
	 * @param refillStatus the refillStatus to set
	 */
	public void setRefillStatus(RefillVoucher[] refillStatus) {
		this.refillStatus = refillStatus;
	}
	/**
	 * @return the balance
	 */
	public AccountBalance getBalance() {
		return balance;
	}
	/**
	 * @param balance the balance to set
	 */
	public void setBalance(AccountBalance balance) {
		this.balance = balance;
	}
	/**
	 * @return the async
	 */
	public AsyncResponse getAsync() {
		return async;
	}
	/**
	 * @param async the async to set
	 */
	public void setAsync(AsyncResponse async) {
		this.async = async;
	}
	
	
}
