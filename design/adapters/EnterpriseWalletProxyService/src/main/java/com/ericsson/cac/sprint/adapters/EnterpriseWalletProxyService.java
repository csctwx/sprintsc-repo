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
import com.sprint.integration.eai.services.enterprisewalletservice.v1.enterprisewalletservice.EnterpriseWalletPortType;
import com.sprint.integration.eai.services.enterprisewalletservice.v1.enterprisewalletservice.EnterpriseWalletService;
import com.sprint.integration.eai.services.enterprisewalletservice.v1.enterprisewalletservice.Faultmessage;

/**
 * @author ebabadd
 *
 */
@Component
public class EnterpriseWalletProxyService extends EnterpriseWalletService{
	
	@Autowired
	private EnterpriseWalletPortType stub;
	
	public OperationResponse processWallet(EncryptedMessage processWallet,Holder<WsMessageHeaderType> head) throws Faultmessage{
		return stub.processWallet(processWallet, head);
	}

}
