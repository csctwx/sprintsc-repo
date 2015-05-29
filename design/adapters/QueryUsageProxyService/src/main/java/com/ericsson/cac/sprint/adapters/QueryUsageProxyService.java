package com.ericsson.cac.sprint.adapters;

import javax.xml.ws.Holder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sprint.integration.common.header.wsmessageheader.v2.WsMessageHeaderType;
import com.sprint.integration.interfaces.queryprepaidbalanceandthresholdinfo.v1.queryprepaidbalanceandthresholdinfo.QueryPrepaidBalanceAndThresholdInfoRequestType;
import com.sprint.integration.interfaces.queryprepaidbalanceandthresholdinfo.v1.queryprepaidbalanceandthresholdinfo.QueryPrepaidBalanceAndThresholdInfoResponseType;
import com.sprint.integration.interfaces.queryusageservice.v1.queryusageservice.QueryUsageService;
import com.sprint.integration.interfaces.queryusageservice.v1.queryusageservice.QueryUsageServicePortType;
import com.sprint.integration.interfaces.queryusageservice.v1.queryusageservice.SoapFault;





@Component
public class QueryUsageProxyService extends QueryUsageService {
    @Autowired
    private QueryUsageServicePortType stub;
    
	public QueryPrepaidBalanceAndThresholdInfoResponseType queryPrepaidBalanceAndThresholdInfo(QueryPrepaidBalanceAndThresholdInfoRequestType  queryPrepaidBalanceAndThresholdReq, Holder<WsMessageHeaderType> head) throws SoapFault{
		return stub.queryPrepaidBalanceAndThresholdInfo(queryPrepaidBalanceAndThresholdReq, head);
	}
	
		
}
