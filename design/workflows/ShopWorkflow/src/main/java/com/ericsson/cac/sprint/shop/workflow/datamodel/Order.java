package com.ericsson.cac.sprint.shop.workflow.datamodel;

public class Order {
	
	private Equipment[] equipments;	
	private Double subTotalAmount;	
	private Double taxAmount;	
	private Double totalAmount;
	/**
	 * @return the equipments
	 */
	public Equipment[] getEquipments() {
		return equipments;
	}
	/**
	 * @param equipments the equipments to set
	 */
	public void setEquipments(Equipment[] equipments) {
		this.equipments = equipments;
	}
	/**
	 * @return the subTotalAmount
	 */
	public Double getSubTotalAmount() {
		return subTotalAmount;
	}
	/**
	 * @param subTotalAmount the subTotalAmount to set
	 */
	public void setSubTotalAmount(Double subTotalAmount) {
		this.subTotalAmount = subTotalAmount;
	}
	/**
	 * @return the taxAmount
	 */
	public Double getTaxAmount() {
		return taxAmount;
	}
	/**
	 * @param taxAmount the taxAmount to set
	 */
	public void setTaxAmount(Double taxAmount) {
		this.taxAmount = taxAmount;
	}
	/**
	 * @return the totalAmount
	 */
	public Double getTotalAmount() {
		return totalAmount;
	}
	/**
	 * @param totalAmount the totalAmount to set
	 */
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}	

}
