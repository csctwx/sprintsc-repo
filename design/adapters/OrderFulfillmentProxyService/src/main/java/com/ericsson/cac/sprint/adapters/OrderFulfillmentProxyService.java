package com.ericsson.cac.sprint.adapters;

import javax.xml.ws.Holder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sprint.integration.common.header.wsmessageheader.v2.WsMessageHeaderType;
import com.sprint.integration.eai.services.orderfulfillmentservice.v1.orderfulfillmentservice.FaultmessageV2;
import com.sprint.integration.eai.services.orderfulfillmentservice.v1.orderfulfillmentservice.OrderFulfillmentService;
import com.sprint.integration.eai.services.orderfulfillmentservice.v1.orderfulfillmentservice.OrderFulfillmentServicePortType;
import com.sprint.integration.interfaces.queryequipmentavailability.v1.queryequipmentavailability.QueryEquipmentAvailabilityRequestType;
import com.sprint.integration.interfaces.queryequipmentavailability.v1.queryequipmentavailability.QueryEquipmentAvailabilityResponseType;








@Component
public class OrderFulfillmentProxyService extends OrderFulfillmentService {
    @Autowired
    private OrderFulfillmentServicePortType stub;
    
    public QueryEquipmentAvailabilityResponseType queryEquipmentAvailability(QueryEquipmentAvailabilityRequestType  queryEquipmentAvailabilityRequest, Holder<WsMessageHeaderType> head) throws FaultmessageV2{
		return stub.queryEquipmentAvailability(queryEquipmentAvailabilityRequest, head);
	}
	
	
		
}
