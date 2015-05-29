package com.ericsson.cac.sprint.adapters;

import javax.xml.ws.Holder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sprint.integration.common.header.wsmessageheader.v2.WsMessageHeaderType;
import com.sprint.integration.eai.services.appledevicemanagementservice.v1.appledevicemanagementservice.AppleDeviceManagementServicePortType;
import com.sprint.integration.eai.services.appledevicemanagementservice.v1.appledevicemanagementservice.AppleDeviceManagementServiceService;
import com.sprint.integration.eai.services.appledevicemanagementservice.v1.appledevicemanagementservice.Faultmessage;
import com.sprint.integration.interfaces.queryappledevicestatus.v1.queryappledevicestatus.QueryAppleDeviceStatusRequestType;
import com.sprint.integration.interfaces.queryappledevicestatus.v1.queryappledevicestatus.QueryAppleDeviceStatusResponseType;





@Component
public class AppleDeviceManagementProxyService extends AppleDeviceManagementServiceService {
    @Autowired
    private AppleDeviceManagementServicePortType stub;
    

	public QueryAppleDeviceStatusResponseType queryAppleDeviceStatus(QueryAppleDeviceStatusRequestType  queryAppleDeviceStatusRequest, Holder<WsMessageHeaderType> head) throws Faultmessage{
		return stub.queryAppleDeviceStatus(queryAppleDeviceStatusRequest, head);
	}
	
		
}
