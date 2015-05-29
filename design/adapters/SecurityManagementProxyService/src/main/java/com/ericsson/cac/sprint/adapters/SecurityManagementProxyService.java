package com.ericsson.cac.sprint.adapters;

import javax.xml.ws.Holder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sprint.integration.common.header.wsmessageheader.v2.WsMessageHeaderType;
import com.sprint.integration.eai.services.securitymanagementservice.v1.securitymanagementservice.SecurityManagementService;
import com.sprint.integration.eai.services.securitymanagementservice.v1.securitymanagementservice.SecurityManagementServicePortType;
import com.sprint.integration.eai.services.securitymanagementservice.v1.securitymanagementservice.SoapFault;
import com.sprint.integration.interfaces.resendsecurityinfo.v1.resendsecurityinfo.ResendSecurityInfoResponseType;
import com.sprint.integration.interfaces.resendsecurityinfo.v1.resendsecurityinfo.ResendSecurityInfoType;
import com.sprint.integration.interfaces.updatevoicemailpasscode.v1.updatevoicemailpasscode.UpdateVoiceMailPasscodeResponseType;
import com.sprint.integration.interfaces.updatevoicemailpasscode.v1.updatevoicemailpasscode.UpdateVoiceMailPasscodeType;
import com.sprint.integration.interfaces.validateaccountpin.v1.validateaccountpin.InfoType;
import com.sprint.integration.interfaces.validateaccountpin.v1.validateaccountpin.ValidationErrorListType;






@Component
public class SecurityManagementProxyService extends SecurityManagementService {
    @Autowired
    private SecurityManagementServicePortType stub;
    

    public ResendSecurityInfoResponseType resendSecurityInfo (ResendSecurityInfoType resendSecurityInfoRequest , Holder<WsMessageHeaderType> head) throws SoapFault{
		return stub.resendSecurityInfo(resendSecurityInfoRequest,head);
	}
	
	public ValidationErrorListType validateAccountPin (InfoType validateAccountPinRequest ,String pin, Holder<WsMessageHeaderType> head) throws SoapFault{
		return stub.validateAccountPin(validateAccountPinRequest, pin, head);		
	
}
	public UpdateVoiceMailPasscodeResponseType updateVoiceMailPasscode (UpdateVoiceMailPasscodeType updateVoiceMailRequest ,Holder<WsMessageHeaderType> head) throws SoapFault{
		return stub.updateVoiceMailPasscode(updateVoiceMailRequest,head);		
	
}
}
