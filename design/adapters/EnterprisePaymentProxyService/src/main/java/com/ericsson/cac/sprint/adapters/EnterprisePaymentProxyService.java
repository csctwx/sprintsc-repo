/**
 * 
 */
package com.ericsson.cac.sprint.adapters;

import javax.xml.ws.Holder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amdocs.mfs.api.epp.v1.sprint.encryption.EncryptedMessage;
import com.amdocs.mfs.api.epp.v1.sprint.encryption.OperationResponse;
import com.sprint.integration.common.header.wsmessageheader.v2.WsMessageHeaderType;
import com.sprint.integration.eai.services.enterprisepaymentservice.v1.enterprisepaymentservice.EnterprisePaymentPortType;
import com.sprint.integration.eai.services.enterprisepaymentservice.v1.enterprisepaymentservice.EnterprisePaymentService;
import com.sprint.integration.eai.services.enterprisepaymentservice.v1.enterprisepaymentservice.Faultmessage;
import com.sprint.integration.interfaces.eppkeyexchange.v1.eppkeyexchange.EppKeyExchangeRequestType;
import com.sprint.integration.interfaces.eppkeyexchange.v1.eppkeyexchange.EppKeyExchangeResponseType;

/**
 * @author ebabadd
 *
 */
@Component
public class EnterprisePaymentProxyService extends EnterprisePaymentService {
	
	@Autowired
	private EnterprisePaymentPortType stub;
	
	public EppKeyExchangeResponseType eppKeyExchange(EppKeyExchangeRequestType eppKeyExchangeRequest,Holder<WsMessageHeaderType> head) throws Faultmessage{
		return stub.eppKeyExchange(eppKeyExchangeRequest, head);
	}
	
	public OperationResponse processPayment(EncryptedMessage processPayment, Holder<WsMessageHeaderType> head) throws Faultmessage{
		return stub.processPayment(processPayment, head);
	}

}
