package com.ericsson.cac.sprint.adapters;

import javax.xml.ws.Holder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sprint.integration.common.header.wsmessageheader.v2.WsMessageHeaderType;
import com.sprint.integration.interfaces.queryprogramminginstructions.v1.queryprogramminginstructions.QueryProgrammingInstructionsRequestType;
import com.sprint.integration.interfaces.queryprogramminginstructions.v1.queryprogramminginstructions.QueryProgrammingInstructionsResponseType;
import com.sprint.integration.interfaces.queryprovisioninginfoservice.v1.queryprovisioninginfoservice.QueryProvisioningInfoService;
import com.sprint.integration.interfaces.queryprovisioninginfoservice.v1.queryprovisioninginfoservice.QueryProvisioningInfoServicePortType;
import com.sprint.integration.interfaces.queryprovisioninginfoservice.v1.queryprovisioninginfoservice.SoapFault;



@Component
public class QueryProvisioningInfoProxyService extends QueryProvisioningInfoService 
	{
    @Autowired
    private QueryProvisioningInfoServicePortType stub;
    
    public QueryProgrammingInstructionsResponseType queryProgrammingInstructions (QueryProgrammingInstructionsRequestType queryProgrammingInstructionsRequest , Holder<WsMessageHeaderType> head) throws SoapFault{
		return stub.queryProgrammingInstructions(queryProgrammingInstructionsRequest,head);
	}
	
}
