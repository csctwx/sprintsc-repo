package com.ericsson.cac.sprint.adapters;

import javax.xml.ws.Holder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sprint.integration.common.header.wsmessageheader.v2.WsMessageHeaderType;
import com.sprint.integration.eai.services.queryplansandoptionsservice.v1.queryplansandoptionsservice.Faultmessage;
import com.sprint.integration.eai.services.queryplansandoptionsservice.v1.queryplansandoptionsservice.QueryPlansAndOptionsPortType;
import com.sprint.integration.eai.services.queryplansandoptionsservice.v1.queryplansandoptionsservice.QueryPlansAndOptionsService;
import com.sprint.integration.interfaces.queryavailableoptions.v1.queryavailableoptions.QueryAvailableOptionsResponseType;
import com.sprint.integration.interfaces.queryavailableoptions.v1.queryavailableoptions.QueryAvailableOptionsType;
import com.sprint.integration.interfaces.queryavailableplans.v1.queryavailableplans.QueryAvailablePlansResponseType;
import com.sprint.integration.interfaces.queryavailableplans.v1.queryavailableplans.QueryAvailablePlansType;
import com.sprint.integration.interfaces.validateplansandoptions.v1.validateplansandoptions.ValidatePlansAndOptionsResponseType;
import com.sprint.integration.interfaces.validateplansandoptions.v1.validateplansandoptions.ValidatePlansAndOptionsType;
import com.sprint.integration.interfaces.validateplansandoptions.v2.validateplansandoptionsv2.ValidatePlansAndOptionsV2RequestType;
import com.sprint.integration.interfaces.validateplansandoptions.v2.validateplansandoptionsv2.ValidatePlansAndOptionsV2ResponseType;


@Component
public class QueryPlansAndOptionsProxyService extends QueryPlansAndOptionsService {
    @Autowired
    private QueryPlansAndOptionsPortType stub;
    
    /*@Autowired
    private Configuration config;*/


	public QueryAvailablePlansResponseType queryAvailablePlans(QueryAvailablePlansType  queryAvailablePlans, Holder<WsMessageHeaderType> head) throws Faultmessage{
		return stub.queryAvailablePlans(queryAvailablePlans, head);
	}

	public QueryAvailableOptionsResponseType queryAvailableOptions(QueryAvailableOptionsType  queryAvailableOptions, Holder<WsMessageHeaderType> head) throws Faultmessage{
		return stub.queryAvailableOptions(queryAvailableOptions, head);
	} 
	
	public ValidatePlansAndOptionsResponseType validatePlansAndOptions(ValidatePlansAndOptionsType  queryAvailableOptions, Holder<WsMessageHeaderType> head) throws Faultmessage{
		return stub.validatePlansAndOptions(queryAvailableOptions, head);
	}
	
	public ValidatePlansAndOptionsV2ResponseType validatePlansAndOptionsV2(ValidatePlansAndOptionsV2RequestType  validatePlansAndOptionsV2Request, Holder<WsMessageHeaderType> head) throws Faultmessage{
		return stub.validatePlansAndOptionsV2(validatePlansAndOptionsV2Request, head);
	}
}
