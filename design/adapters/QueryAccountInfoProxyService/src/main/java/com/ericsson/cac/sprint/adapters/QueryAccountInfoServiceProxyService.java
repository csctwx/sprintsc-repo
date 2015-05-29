package com.ericsson.cac.sprint.adapters;

import javax.xml.ws.Holder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sprint.integration.common.header.wsmessageheader.v2.WsMessageHeaderType;
import com.sprint.integration.interfaces.queryaccountbasicinfo.v1.queryaccountbasicinfo.QueryAccountBasicInfo;
import com.sprint.integration.interfaces.queryaccountbasicinfo.v1.queryaccountbasicinfo.QueryAccountBasicInfoResponse;
import com.sprint.integration.interfaces.queryaccountinfoservice.v1.queryaccountinfoservice.QueryAccountInfoPortType;
import com.sprint.integration.interfaces.queryaccountinfoservice.v1.queryaccountinfoservice.QueryAccountInfoService;
import com.sprint.integration.interfaces.queryaccountinfoservice.v1.queryaccountinfoservice.SoapFaultv2;
import com.sprint.integration.interfaces.queryaccountsecurityinfo.v1.queryaccountsecurityinfo.QueryAccountSecurityInfo;
import com.sprint.integration.interfaces.queryaccountsecurityinfo.v1.queryaccountsecurityinfo.QueryAccountSecurityInfoResponse;



/**
 * Created by echasis on 20/01/2015.
 */
@Component
public class QueryAccountInfoServiceProxyService extends QueryAccountInfoService {
    @Autowired
    private QueryAccountInfoPortType stub;
    
	public QueryAccountBasicInfoResponse queryAccountBasicInfo(QueryAccountBasicInfo parameters, Holder<WsMessageHeaderType> head) throws SoapFaultv2 {
		return stub.queryAccountBasicInfo(parameters, head);
	}
	
	public QueryAccountSecurityInfoResponse queryAccountSecurityInfo(QueryAccountSecurityInfo parameters, Holder<WsMessageHeaderType> head) throws SoapFaultv2 {
		return stub.queryAccountSecurityInfo(parameters, head);
	}
}
