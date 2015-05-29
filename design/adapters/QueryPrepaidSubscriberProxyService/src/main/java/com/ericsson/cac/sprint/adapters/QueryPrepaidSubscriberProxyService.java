package com.ericsson.cac.sprint.adapters;

import javax.xml.ws.Holder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sprint.integration.common.header.wsmessageheader.v2.WsMessageHeaderType;
import com.sprint.integration.interfaces.querybalanceandcharges.v2.querybalanceandchargesv2.QueryBalanceAndChargesV2RequestType;
import com.sprint.integration.interfaces.querybalanceandcharges.v2.querybalanceandchargesv2.QueryBalanceAndChargesV2ResponseType;
import com.sprint.integration.interfaces.queryprepaidallowance.v1.queryprepaidallowance.QueryPrepaidAllowanceRequestType;
import com.sprint.integration.interfaces.queryprepaidallowance.v1.queryprepaidallowance.QueryPrepaidAllowanceResponseType;
import com.sprint.integration.interfaces.queryprepaidsubscriberservice.v1.queryprepaidsubscriberservice.QueryPrepaidSubscriberPortType;
import com.sprint.integration.interfaces.queryprepaidsubscriberservice.v1.queryprepaidsubscriberservice.QueryPrepaidSubscriberService;
import com.sprint.integration.interfaces.queryprepaidsubscriberservice.v1.queryprepaidsubscriberservice.SoapFaultV2;
import com.sprint.integration.interfaces.queryprepaidusagedetails.v1.queryprepaidusagedetails.QueryPrepaidUsageDetailsRequestType;
import com.sprint.integration.interfaces.queryprepaidusagedetails.v1.queryprepaidusagedetails.QueryPrepaidUsageDetailsResponseType;
import com.sprint.integration.interfaces.querysubscriberprepaidinfo.v1.querysubscriberprepaidinfo.QuerySubscriberPrepaidInfoResponseType;
import com.sprint.integration.interfaces.querysubscriberprepaidinfo.v1.querysubscriberprepaidinfo.QuerySubscriberPrepaidInfoType;


/**
 * Created by echasis on 20/01/2015.
 */
@Component
public class QueryPrepaidSubscriberProxyService extends QueryPrepaidSubscriberService {
    @Autowired
    private QueryPrepaidSubscriberPortType stub;

    public QueryBalanceAndChargesV2ResponseType queryBalanceAndChargesV2(QueryBalanceAndChargesV2RequestType queryBalanceAndChargesV2Request, Holder<WsMessageHeaderType> head) throws SoapFaultV2{
        return stub.queryBalanceAndChargesV2(queryBalanceAndChargesV2Request, head);
    }
    
    public QueryPrepaidAllowanceResponseType queryPrepaidAllowance(QueryPrepaidAllowanceRequestType queryPrepaidAllowanceRequest, Holder<WsMessageHeaderType> head) throws SoapFaultV2 {
		return stub.queryPrepaidAllowance(queryPrepaidAllowanceRequest, head);
	}
	
	
	public QueryPrepaidUsageDetailsResponseType queryPrepaidUsageDetails(QueryPrepaidUsageDetailsRequestType queryPrepaidUsageDetailsRequest, Holder<WsMessageHeaderType> head) throws SoapFaultV2 {
		return stub.queryPrepaidUsageDetails(queryPrepaidUsageDetailsRequest, head);
	}
	
	public QuerySubscriberPrepaidInfoResponseType querySubscriberPrepaidInfo(QuerySubscriberPrepaidInfoType querySubscriberPrepaidInfoRequest, Holder<WsMessageHeaderType> head) throws SoapFaultV2 {
		return stub.querySubscriberPrepaidInfo(querySubscriberPrepaidInfoRequest, head);
	}
}
