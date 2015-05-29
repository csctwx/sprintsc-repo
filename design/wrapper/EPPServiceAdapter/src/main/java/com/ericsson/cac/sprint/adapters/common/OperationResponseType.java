package com.ericsson.cac.sprint.adapters.common;

import com.amdocs.mfs.api.epp.v1.sprint.baseoperationresponse.PaymentBaseResponse;
import com.amdocs.mfs.api.epp.v1.sprint.walletbaseoperationresponse.BaseWalletOperationResponse;

public enum OperationResponseType {
	
	WALLET(BaseWalletOperationResponse.class), 
	PAYMENT(PaymentBaseResponse.class);
	
	private Class relatedClass;
	
	private OperationResponseType(Class c) {
		this.relatedClass = c;
	}
	
	public Class getRelatedClass(){
		return relatedClass;
	}
}
