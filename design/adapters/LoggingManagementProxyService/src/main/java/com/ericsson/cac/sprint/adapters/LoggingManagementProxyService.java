package com.ericsson.cac.sprint.adapters;

import javax.xml.ws.Holder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sprint.integration.common.header.wsmessageheader.v2.WsMessageHeaderType;
import com.sprint.integration.eai.services.loggingmanagementservice.v1.loggingmanagementservice.LoggingManagementService;
import com.sprint.integration.eai.services.loggingmanagementservice.v1.loggingmanagementservice.LoggingManagementServicePortType;
import com.sprint.integration.eai.services.loggingmanagementservice.v1.loggingmanagementservice.SoapFault;
import com.sprint.integration.interfaces.queryloginattemptstatus.v1.queryloginattemptstatus.QueryLoginAttemptStatusRequestType;
import com.sprint.integration.interfaces.queryloginattemptstatus.v1.queryloginattemptstatus.QueryLoginAttemptStatusResponseType;
import com.sprint.integration.interfaces.submitloginattempt.v1.submitloginattempt.SubmitLoginAttemptRequestType;
import com.sprint.integration.interfaces.submitloginattempt.v1.submitloginattempt.SubmitLoginAttemptResponseType;




@Component
public class LoggingManagementProxyService extends LoggingManagementService {
    @Autowired
    private LoggingManagementServicePortType stub;
    
   /* @Autowired
    private Configuration config;*/


	public QueryLoginAttemptStatusResponseType queryLoginAttemptStatus(QueryLoginAttemptStatusRequestType  queryLoginAttemptStatusRequest, Holder<WsMessageHeaderType> head) throws SoapFault{
		return stub.queryLoginAttemptStatus(queryLoginAttemptStatusRequest, head);
	}
	
	public SubmitLoginAttemptResponseType submitLoginAttempt(SubmitLoginAttemptRequestType submitLoginAttemptRequest, Holder<WsMessageHeaderType> head) throws SoapFault{
		return stub.submitLoginAttempt(submitLoginAttemptRequest, head);
	}
	
	
}
