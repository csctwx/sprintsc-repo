package com.ericsson.cac.sprint.selfcare.workflow.datamodel;

import java.io.Serializable;

public class TransactionHistory implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PaymentHistoryPagination paymentHistoryPagination;//Required = false,Payment history
	private OrderHistory[] orders;//Required = false,Order history	
	
	/**
	 * @return the orders
	 */
	public OrderHistory[] getOrders() {
		return orders;
	}
	/**
	 * @param orders the orders to set
	 */
	public void setOrders(OrderHistory[] orders) {
		this.orders = orders;
	}
	
	
	/**
	 * @return the paymentHistoryPagination
	 */
	public PaymentHistoryPagination getPaymentHistoryPagination() {
		return paymentHistoryPagination;
	}
	/**
	 * @param paymentHistoryPagination the paymentHistoryPagination to set
	 */
	public void setPaymentHistoryPagination(PaymentHistoryPagination paymentHistoryPagination) {
		this.paymentHistoryPagination = paymentHistoryPagination;
	}
	

}
