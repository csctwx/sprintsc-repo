/**
 * 
 */
package com.ericsson.cac.sprint.selfcare.workflow.datamodel;

/**
 * @author emascol
 *
 */
public class PaymentHistoryPagination {
	
	private PaymentHistory[] payments;//Required = false,Payment history
	private int totalPaymentHistoryCount = 0;
	
	/**
	 * @return the payments
	 */
	public PaymentHistory[] getPayments() {
		return payments;
	}
	/**
	 * @param payments the payments to set
	 */
	public void setPayments(PaymentHistory[] payments) {
		this.payments = payments;
	}
	/**
	 * @return the totalPaymentHistoryCount
	 */
	public int getTotalPaymentHistoryCount() {
		return totalPaymentHistoryCount;
	}
	/**
	 * @param totalPaymentHistoryCount the totalPaymentHistoryCount to set
	 */
	public void setTotalPaymentHistoryCount(int totalPaymentHistoryCount) {
		this.totalPaymentHistoryCount = totalPaymentHistoryCount;
	}

}
