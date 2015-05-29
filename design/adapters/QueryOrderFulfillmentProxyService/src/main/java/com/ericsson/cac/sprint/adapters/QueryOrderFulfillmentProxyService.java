package com.ericsson.cac.sprint.adapters;

import javax.xml.ws.Holder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sprint.integration._public.interfaces.queryorderfulfillmentservice.v1.Faultmessage2;
import com.sprint.integration._public.interfaces.queryorderfulfillmentservice.v1.QueryOrderFulfillmentPortType;
import com.sprint.integration._public.interfaces.queryorderfulfillmentservice.v1.QueryOrderFulfillmentService;
import com.sprint.integration.common.header.wsmessageheader.v2.WsMessageHeaderType;
import com.sprint.integration.interfaces.queryaccountorders.v1.queryaccountorders.QueryAccountOrdersResponseType;
import com.sprint.integration.interfaces.queryaccountorders.v1.queryaccountorders.QueryAccountOrdersType;
import com.sprint.integration.interfaces.queryorderinfo.v1.queryorderinfo.QueryOrderInfoResponseType;
import com.sprint.integration.interfaces.queryorderinfo.v1.queryorderinfo.QueryOrderInfoType;


/*
 * @author eshsaho
 */


@Component
public class QueryOrderFulfillmentProxyService extends QueryOrderFulfillmentService {
    @Autowired
    private QueryOrderFulfillmentPortType stub;
    
	public QueryOrderInfoResponseType queryOrderInfoResponseType(QueryOrderInfoType  queryOrderInfoTypeReq, Holder<WsMessageHeaderType> head) throws Faultmessage2{
		return stub.queryOrderInfo(queryOrderInfoTypeReq, head);
	}
	
	public QueryAccountOrdersResponseType queryAccountOrdersResponseType(QueryAccountOrdersType  queryAccountOrdersTypeReq, Holder<WsMessageHeaderType> head) throws Faultmessage2{
		return stub.queryAccountOrders(queryAccountOrdersTypeReq, head);
	}
		
}
