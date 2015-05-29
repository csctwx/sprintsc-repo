package com.ericsson.cac.sprint.adapters;

import javax.xml.ws.Holder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sprint.integration.common.header.wsmessageheader.v2.WsMessageHeaderType;
import com.sprint.integration.eai.services.addressmanagementservice.v1.addressmanagementservice.AddressManagementService;
import com.sprint.integration.eai.services.addressmanagementservice.v1.addressmanagementservice.AddressManagementServicePortType;
import com.sprint.integration.eai.services.addressmanagementservice.v1.addressmanagementservice.SoapFault;
import com.sprint.integration.interfaces.validateaddress.v1.validateaddress.ValidateAddressResponseType;
import com.sprint.integration.interfaces.validateaddress.v1.validateaddress.ValidateAddressType;





@Component
public class AddressManagementProxyService extends AddressManagementService {
    @Autowired
    private AddressManagementServicePortType stub;
    
	public ValidateAddressResponseType validateAddress(ValidateAddressType  validateAddressType, Holder<WsMessageHeaderType> head) throws SoapFault{
		return stub.validateAddress(validateAddressType, head);
	}
	
		
}
