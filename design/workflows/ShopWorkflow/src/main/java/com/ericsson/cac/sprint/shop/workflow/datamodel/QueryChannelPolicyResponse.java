package com.ericsson.cac.sprint.shop.workflow.datamodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.amdocs.mfs.api.epp.v1.sprint.walletbaseoperationresponse.PaymentAllowedPOs;
import com.amdocs.mfs.api.epp.v1.sprint.walletbaseoperationresponse.PaymentMinMaxAmounts;
import com.amdocs.mfs.api.epp.v1.sprint.walletbaseoperationresponse.Responses;
import com.amdocs.mfs.api.epp.v1.sprint.walletbaseoperationresponse.WalletAllowedPOs;
import com.amdocs.mfs.api.epp.v1.sprint.walletbaseoperationresponse.WalletMaxPOs;
import com.google.gson.Gson;
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class QueryChannelPolicyResponse {
	
	/*@XmlElement
	private Responses responses;			
	@XmlElement
	private WalletMaxPOs walletMaxPOs;		
	@XmlElement
	private WalletAllowedPOs walletAllowedPOs;		
	@XmlElement
	private PaymentAllowedPOs paymentAllowedPOs;
	@XmlElement
	private PaymentMinMaxAmounts paymentMinMaxAmounts;*/
	
	
	@XmlElement
	private Integer status;
	@XmlElement
	private String description;
	@XmlElementWrapper(name="channelPolicies")
	@XmlElement(name="channelPolicy")
	ChannelPolicy[] channelPolicy;
	
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
	public ChannelPolicy[] getChannelPolicy() {
		return channelPolicy;
	}
	public void setChannelPolicy(ChannelPolicy[] channelPolicy) {
		this.channelPolicy = channelPolicy;
	}	
	
	@Override
	public String toString() {		
		Gson gson=new Gson();
		String gsonResult=gson.toJson(this);
		return gsonResult;
	}
}
