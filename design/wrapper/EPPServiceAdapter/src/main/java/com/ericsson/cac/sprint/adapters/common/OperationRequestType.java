package com.ericsson.cac.sprint.adapters.common;

import com.amdocs.dc.api.sensitivedetails.ACHSensitiveDetails;
import com.amdocs.dc.api.sensitivedetails.CardSensitiveDetails;
import com.amdocs.dc.api.sensitivedetails.PayPalSensitiveDetails;
import com.amdocs.mfs.api.epp.v1.sprint.baseoperationrequest.PaymentBaseRequest;
import com.amdocs.mfs.api.epp.v1.sprint.paymentcommontypes.BanInfoAC;
import com.amdocs.mfs.api.epp.v1.sprint.walletbaseoperationrequest.BaseWalletOperationRequest;

public enum OperationRequestType {
	WALLET(BaseWalletOperationRequest.class), 
	PAYMENT(PaymentBaseRequest.class), 
	BAN(BanInfoAC.class),
	CARD(CardSensitiveDetails.class), 
	ACH(ACHSensitiveDetails.class), PAYPAL(PayPalSensitiveDetails.class);
	
	private Class relatedClass;
	
	private OperationRequestType(Class c) {
		this.relatedClass = c;
	}
	
	public Class getRelatedClass(){
		return relatedClass;
	}
}
