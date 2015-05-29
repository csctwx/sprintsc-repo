package com.ericsson.cac.sprint.adapters;

import javax.xml.ws.Holder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sprint.integration.common.header.wsmessageheader.v2.WsMessageHeaderType;
import com.sprint.integration.eai.services.smspreferencemanagementservice.v1.smspreferencemanagementservice.SmsPreferenceManagementService;
import com.sprint.integration.eai.services.smspreferencemanagementservice.v1.smspreferencemanagementservice.SmsPreferenceManagementServicePortType;
import com.sprint.integration.eai.services.smspreferencemanagementservice.v1.smspreferencemanagementservice.SoapFault;
import com.sprint.integration.interfaces.querysmspreferenceinfo.v1.querysmspreferenceinfo.QuerySmsPreferenceInfoRequestType;
import com.sprint.integration.interfaces.querysmspreferenceinfo.v1.querysmspreferenceinfo.QuerySmsPreferenceInfoResponseType;
import com.sprint.integration.interfaces.updatesmspreferenceinfo.v1.updatesmspreferenceinfo.UpdateSmsPreferenceInfoRequestType;
import com.sprint.integration.interfaces.updatesmspreferenceinfo.v1.updatesmspreferenceinfo.UpdateSmsPreferenceInfoResponseType;


/**
 * Created by echasis on 22/01/2015.
 */
@Component
public class SmsPreferenceManagementProxyService extends SmsPreferenceManagementService {
    @Autowired
    private SmsPreferenceManagementServicePortType stub;
    
	public QuerySmsPreferenceInfoResponseType querySmsPreferenceInfo(QuerySmsPreferenceInfoRequestType querySmsPreferenceInfo,Holder<WsMessageHeaderType> head) throws SoapFault {
		return stub.querySmsPreferenceInfo(querySmsPreferenceInfo,head);
	}
	
	public UpdateSmsPreferenceInfoResponseType updateSmsPreferenceInfo(UpdateSmsPreferenceInfoRequestType updateSmsPreferenceInfo,Holder<WsMessageHeaderType> head) throws SoapFault {
		return stub.updateSmsPreferenceInfo(updateSmsPreferenceInfo,head);
	}
    
}
