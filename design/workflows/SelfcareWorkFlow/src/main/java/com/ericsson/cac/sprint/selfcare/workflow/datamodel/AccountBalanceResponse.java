package com.ericsson.cac.sprint.selfcare.workflow.datamodel;

import java.io.Serializable;

public class AccountBalanceResponse implements Serializable {
	private AccountBalance accountBalance;// Required = true,Account balance

	private StatusMessageResponse statusMessageResponse;

	public StatusMessageResponse getStatusMessageResponse() {
		return statusMessageResponse;
	}

	public void setStatusMessageResponse(
			StatusMessageResponse statusMessageResponse) {
		this.statusMessageResponse = statusMessageResponse;
	}

	/**
	 * @return the accountBalance
	 */
	public AccountBalance getAccountBalance() {
		return accountBalance;
	}

	/**
	 * @param accountBalance
	 *            the accountBalance to set
	 */
	public void setAccountBalance(AccountBalance accountBalance) {
		this.accountBalance = accountBalance;
	}

	/**
	 * @return the async
	 */
	public AsyncResponse getAsync() {
		return async;
	}

	/**
	 * @param async
	 *            the async to set
	 */
	public void setAsync(AsyncResponse async) {
		this.async = async;
	}

	private AsyncResponse async;// Required = false,A-sync response info

}
