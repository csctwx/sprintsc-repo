package com.ericsson.cac.sprint.adapters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;

import java.net.MalformedURLException;

import com.sprint.integration.common.header.wsmessageheader.v2.WsMessageHeaderType;
import com.sprint.integration.interfaces.querysubscriberservices.v1.querysubscriberservices.QuerySubscriberServicesResponse;
import com.sprint.integration.interfaces.querysubscriberservices.v1.querysubscriberservices.QuerySubscriberServicesRequest;
import com.sprint.integration.interfaces.querysubscriberinfoservice.v1.querysubscriberinfoservice.QuerySubscriberInfoPortType;
import com.sprint.integration.interfaces.querysubscriberinfoservice.v1.querysubscriberinfoservice.QuerySubscriberInfoService;
import com.sprint.integration.interfaces.querysubscriberinfoservice.v1.querysubscriberinfoservice.SoapFaultV2;



@Component
public class QuerySubscriberInfoProxyService extends QuerySubscriberInfoService {
    @Autowired
    private QuerySubscriberInfoPortType stub;
    



	public QuerySubscriberServicesResponse querySubscriberServices(QuerySubscriberServicesRequest  querySubscriberServicesRequest, Holder<WsMessageHeaderType> head) throws SoapFaultV2{
		return stub.querySubscriberServices(querySubscriberServicesRequest, head);
	}
	
	
}
