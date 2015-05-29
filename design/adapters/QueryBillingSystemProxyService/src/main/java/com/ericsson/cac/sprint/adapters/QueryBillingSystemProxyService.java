package com.ericsson.cac.sprint.adapters;

import javax.xml.ws.Holder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sprint.integration.common.header.wsmessageheader.v2.WsMessageHeaderType;
import com.sprint.integration.interfaces.querybillingsystemcode.v5.querybillingsystemcodev5.QueryBillingSystemCodeV5RequestType;
import com.sprint.integration.interfaces.querybillingsystemcode.v5.querybillingsystemcodev5.QueryBillingSystemCodeV5ResponseType;
import com.sprint.integration.interfaces.querybillingsystemservice.v1.querybillingsystemservice.QueryBillingSystemPortType;
import com.sprint.integration.interfaces.querybillingsystemservice.v1.querybillingsystemservice.QueryBillingSystemService;
import com.sprint.integration.interfaces.querybillingsystemservice.v1.querybillingsystemservice.SoapFault;





@Component
public class QueryBillingSystemProxyService extends QueryBillingSystemService {
    @Autowired
    private QueryBillingSystemPortType stub;
    
	public QueryBillingSystemCodeV5ResponseType queryBillingSystemCodeV5(QueryBillingSystemCodeV5RequestType  queryBillingSystemCodeV5Request, Holder<WsMessageHeaderType> head) throws SoapFault{
		return stub.queryBillingSystemCodeV5(queryBillingSystemCodeV5Request, head);
	}
	
}
