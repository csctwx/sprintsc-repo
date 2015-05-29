package com.ericsson.cac.sprint.adapters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;










import com.sprint.integration.common.header.wsmessageheader.v2.WsMessageHeaderType;
import com.sprint.integration.eai.services.querydeviceinfoservice.v1.querydeviceinfoservice.Faultmessage2;
import com.sprint.integration.eai.services.querydeviceinfoservice.v1.querydeviceinfoservice.QueryDeviceInfoPortType;
import com.sprint.integration.eai.services.querydeviceinfoservice.v1.querydeviceinfoservice.QueryDeviceInfoService;
import com.sprint.integration.interfaces.querydevicecapabilities.v2.querydevicecapabilitiesv2.QueryDeviceCapabilitiesV2ResponseType;
import com.sprint.integration.interfaces.querydevicecapabilities.v2.querydevicecapabilitiesv2.QueryDeviceCapabilitiesV2Type;
import com.sprint.integration.interfaces.querydeviceinfo.v1.querydeviceinfo.QueryDeviceInfoResponseType;
import com.sprint.integration.interfaces.querydeviceinfo.v1.querydeviceinfo.QueryDeviceInfoType;
import com.sprint.integration.interfaces.validatedevice.v9.validatedevicev9.ValidateDeviceV9ResponseType;
import com.sprint.integration.interfaces.validatedevice.v9.validatedevicev9.ValidateDeviceV9RequestType;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;

import java.net.MalformedURLException;








@Component
public class QueryDeviceInfoProxyService extends QueryDeviceInfoService {
    @Autowired
    private QueryDeviceInfoPortType stub;
    
    public QueryDeviceInfoResponseType queryDeviceInfo(QueryDeviceInfoType  queryCsaRequest, Holder<WsMessageHeaderType> head) throws Faultmessage2{
		return stub.queryDeviceInfo(queryCsaRequest, head);
	}
	
    public ValidateDeviceV9ResponseType validateDeviceV9(ValidateDeviceV9RequestType validateDeviceV9Request, Holder<WsMessageHeaderType> head) throws Faultmessage2{
    	return stub.validateDeviceV9(validateDeviceV9Request, head);
    }
    
    public QueryDeviceCapabilitiesV2ResponseType queryDeviceCapabilitiesV2(QueryDeviceCapabilitiesV2Type queryDeviceCapabilitiesV2Request, Holder<WsMessageHeaderType> head) throws Faultmessage2{
    	return stub.queryDeviceCapabilitiesV2(queryDeviceCapabilitiesV2Request, head);
    }
	
		
}
