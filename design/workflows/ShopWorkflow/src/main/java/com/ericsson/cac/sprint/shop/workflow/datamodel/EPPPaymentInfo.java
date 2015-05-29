package com.ericsson.cac.sprint.shop.workflow.datamodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class EPPPaymentInfo {
	
	@XmlElement(required=true)
	private String paymentType; //CARD comes from properties
	@XmlElement(required=true)
	private String channelId; // from properties same as EPP
	@XmlElement(required=true)
	private String actorId; // from properties same as EPP
	@XmlElement(required=true)
	private String tokenId; // not sure response from generatePreOrderToken
	@XmlElement(required=true)
	private String tokenType; // not sure
	@XmlElement(required=true)
	private String externalOrderId; // Order Id 
	@XmlElement(required=false)
	private String globalOrderId;  // don't send
	
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public String getActorId() {
		return actorId;
	}
	public void setActorId(String actorId) {
		this.actorId = actorId;
	}
	public String getTokenId() {
		return tokenId;
	}
	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}
	public String getTokenType() {
		return tokenType;
	}
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
	public String getExternalOrderId() {
		return externalOrderId;
	}
	public void setExternalOrderId(String externalOrderId) {
		this.externalOrderId = externalOrderId;
	}
	public String getGlobalOrderId() {
		return globalOrderId;
	}
	public void setGlobalOrderId(String globalOrderId) {
		this.globalOrderId = globalOrderId;
	}	

}
