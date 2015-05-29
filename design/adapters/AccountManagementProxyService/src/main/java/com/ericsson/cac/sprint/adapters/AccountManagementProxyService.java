package com.ericsson.cac.sprint.adapters;

import javax.xml.ws.Holder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sprint.integration.common.header.wsmessageheader.v2.WsMessageHeaderType;
import com.sprint.integration.eai.services.accountmanagementservice.v1.accountmanagementservice.AccountManagementService;
import com.sprint.integration.eai.services.accountmanagementservice.v1.accountmanagementservice.AccountManagementServicePortType;
import com.sprint.integration.eai.services.accountmanagementservice.v1.accountmanagementservice.SoapFault;
import com.sprint.integration.interfaces.updateaccountdetails.v1.updateaccountdetails.UpdateAccountDetailsRequestType;
import com.sprint.integration.interfaces.updateaccountdetails.v1.updateaccountdetails.UpdateAccountDetailsResponseType;





@Component
public class AccountManagementProxyService extends AccountManagementService {
    @Autowired
    private AccountManagementServicePortType stub;
    
	public UpdateAccountDetailsResponseType updateAccountDetails(UpdateAccountDetailsRequestType  updateAccountDetailsRequest, Holder<WsMessageHeaderType> head) throws SoapFault{
		return stub.updateAccountDetails(updateAccountDetailsRequest, head);
	}
	
		
}
