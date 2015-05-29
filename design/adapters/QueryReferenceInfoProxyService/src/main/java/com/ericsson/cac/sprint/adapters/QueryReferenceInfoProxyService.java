package com.ericsson.cac.sprint.adapters;

import javax.xml.ws.Holder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sprint.integration.common.header.wsmessageheader.v2.WsMessageHeaderType;
import com.sprint.integration.eai.services.queryreferenceinfoservice.v1.queryreferenceinfoservice.Faultmessage;
import com.sprint.integration.eai.services.queryreferenceinfoservice.v1.queryreferenceinfoservice.QueryReferenceInfoPortType;
import com.sprint.integration.eai.services.queryreferenceinfoservice.v1.queryreferenceinfoservice.QueryReferenceInfoService;
import com.sprint.integration.interfaces.queryreferencesecurityinfo.v1.queryreferencesecurityinfo.QueryReferenceSecurityInfoRequest;
import com.sprint.integration.interfaces.queryreferencesecurityinfo.v1.queryreferencesecurityinfo.QueryReferenceSecurityInfoResponse;





@Component
public class QueryReferenceInfoProxyService extends QueryReferenceInfoService {
    @Autowired
    private QueryReferenceInfoPortType stub;
    
	public QueryReferenceSecurityInfoResponse queryReferenceSecurityInfo(QueryReferenceSecurityInfoRequest  queryReferenceSecurityInfoReq, Holder<WsMessageHeaderType> head) throws Faultmessage{
		return stub.queryReferenceSecurityInfo(queryReferenceSecurityInfoReq, head);
	}
	
		
}
