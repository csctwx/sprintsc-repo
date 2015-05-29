package com.ericsson.cac.sprint.shop.workflow.datamodel;

import java.math.BigInteger;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Equipment {
	
	@XmlElement
	private String orderLineId;
	@XmlElement
	@NotNull
	private String modelId;
	@XmlElement
	@NotNull
	private Double modelPrice;
	@XmlElement
	private Double equipmentTaxAmt;
	@XmlElement
	private Boolean accessoryInd;
	@XmlElement
	private BigInteger taxTransactionId;
	@XmlElement
	private String invoiceDate;
	@XmlElement
	@NotNull
	private int quantity;
	@XmlElement
	private Double subTotalAmt;	
	@XmlElement
	private boolean isPreOrder = false;
	@XmlElement
	private Double discount;
	
	
	public Double getTotalAmt() {
		return subTotalAmt;
	}

	public void setTotalAmt(Double totalAmt) {
		this.subTotalAmt = totalAmt;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the orderLineId
	 */
	public String getOrderLineId() {
		return orderLineId;
	}

	/**
	 * @param orderLineId the orderLineId to set
	 */
	public void setOrderLineId(String orderLineId) {
		this.orderLineId = orderLineId;
	}

	/**
	 * @return the modelId
	 */
	public String getModelId() {
		return modelId;
	}

	/**
	 * @param modelId the modelId to set
	 */
	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	/**
	 * @return the modelPrice
	 */
	public Double getModelPrice() {
		return modelPrice;
	}

	/**
	 * @param modelPrice the modelPrice to set
	 */
	public void setModelPrice(Double modelPrice) {
		this.modelPrice = modelPrice;
	}

	/**
	 * @return the equipmentTaxAmt
	 */
	public Double getEquipmentTaxAmt() {
		return equipmentTaxAmt;
	}

	/**
	 * @param equipmentTaxAmt the equipmentTaxAmt to set
	 */
	public void setEquipmentTaxAmt(Double equipmentTaxAmt) {
		this.equipmentTaxAmt = equipmentTaxAmt;
	}

	/**
	 * @return the accessoryInd
	 */
	public Boolean getAccessoryInd() {
		return accessoryInd;
	}

	/**
	 * @param accessoryInd the accessoryInd to set
	 */
	public void setAccessoryInd(Boolean accessoryInd) {
		this.accessoryInd = accessoryInd;
	}

	/**
	 * @return the taxTransactionId
	 */
	public BigInteger getTaxTransactionId() {
		return taxTransactionId;
	}

	/**
	 * @param taxTransactionId the taxTransactionId to set
	 */
	public void setTaxTransactionId(BigInteger taxTransactionId) {
		this.taxTransactionId = taxTransactionId;
	}

	/**
	 * @return the invoiceDate
	 */
	public String getInvoiceDate() {
		return invoiceDate;
	}

	/**
	 * @param invoiceDate the invoiceDate to set
	 */
	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public Double getSubTotalAmt() {
		return subTotalAmt;
	}

	public void setSubTotalAmt(Double subTotalAmt) {
		this.subTotalAmt = subTotalAmt;
	}

	/**
	 * @return the isPreOrder
	 */
	public boolean isPreOrder() {
		return isPreOrder;
	}

	/**
	 * @param isPreOrder the isPreOrder to set
	 */
	public void setPreOrder(boolean isPreOrder) {
		this.isPreOrder = isPreOrder;
	}

	/**
	 * @return the discount
	 */
	public Double getDiscount() {
		return discount;
	}

	/**
	 * @param discount the discount to set
	 */
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	
	
}
