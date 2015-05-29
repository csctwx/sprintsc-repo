package com.ericsson.cac.sprint.shop.exceptions;

public class ShopWorkFlowException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ShopWorkFlowException(String message, Throwable t) {
		super(message, t);
	}
	public ShopWorkFlowException(Throwable t) {
		super(t);
	}
	public ShopWorkFlowException(String message) {
		super(message);
	}
}
