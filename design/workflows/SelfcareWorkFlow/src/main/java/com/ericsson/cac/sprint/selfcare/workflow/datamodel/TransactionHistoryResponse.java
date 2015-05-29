package com.ericsson.cac.sprint.selfcare.workflow.datamodel;

import java.io.Serializable;

public class TransactionHistoryResponse implements Serializable{
	private TransactionHistory transactionHistory;//Required = true,Transaction history
	
	private StatusMessageResponse statusMessageResponse;

	public StatusMessageResponse getStatusMessageResponse() {
		return statusMessageResponse;
	}

	public void setStatusMessageResponse(
			StatusMessageResponse statusMessageResponse) {
		this.statusMessageResponse = statusMessageResponse;
	}
	
	
	/**
	 * @return the transactionHistory
	 */
	public TransactionHistory getTransactionHistory() {
		return transactionHistory;
	}
	/**
	 * @param transactionHistory the transactionHistory to set
	 */
	public void setTransactionHistory(TransactionHistory transactionHistory) {
		this.transactionHistory = transactionHistory;
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
	private AsyncResponse async;//Required = false,A-sync response info


}
