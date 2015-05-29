package com.ericsson.cac.sprint.shop.workflow.datamodel;

import java.math.BigInteger;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.ericsson.cac.sprint.shop.constants.ShopWorkFlowConstants;
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CalculateTaxResponse {
	@XmlElement(required=true)
	private String invoiceNumber;
	@XmlElement(required=false)
	private Integer lineNumber=ShopWorkFlowConstants.DEFAULT_LINENUMBER;
	@XmlElement(required=true)
	private Double taxOnAmount;
	@XmlElement(required=true)
	private Double taxAmount;
	@XmlElement(required=true)
	private Double totalAmount;
	@XmlElement(required=true)
	private Integer status;
	@XmlElement(required=true)
	private String description;
	@XmlElement
	private BigInteger transactionId;
	
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public Integer getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(Integer lineNumber) {
		this.lineNumber = lineNumber;
	}
	public Double getTaxOnAmount() {
		return taxOnAmount;
	}
	public void setTaxOnAmount(Double taxOnAmount) {
		this.taxOnAmount = taxOnAmount;
	}
	public Double getTaxAmount() {
		return taxAmount;
	}
	public void setTaxAmount(Double taxAmount) {
		this.taxAmount = taxAmount;
	}
	public Double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BigInteger getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(BigInteger transactionId) {
		this.transactionId = transactionId;
	}
	
	
}
