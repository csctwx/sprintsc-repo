package com.ericsson.cac.sprint.adapters;

import javax.xml.ws.Holder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sprint.integration.common.header.wsmessageheader.v2.WsMessageHeaderType;
import com.sprint.integration.eai.services.promotionmanagementservice.v1.promotionmanagementservice.PromotionManagementService;
import com.sprint.integration.eai.services.promotionmanagementservice.v1.promotionmanagementservice.PromotionManagementServicePortType;
import com.sprint.integration.eai.services.promotionmanagementservice.v1.promotionmanagementservice.SoapFaultV2;
import com.sprint.integration.interfaces.validatepromotioninfo.v1.validatepromotioninfo.ValidatePromotionInfoRequestType;
import com.sprint.integration.interfaces.validatepromotioninfo.v1.validatepromotioninfo.ValidatePromotionInfoResponseType;








@Component
public class PromotionManagementProxyService extends PromotionManagementService {
    @Autowired
    private PromotionManagementServicePortType stub;
    
    public ValidatePromotionInfoResponseType validatePromotionInfo(ValidatePromotionInfoRequestType  validatePromotionInfoRequest, Holder<WsMessageHeaderType> head) throws SoapFaultV2{
		return stub.validatePromotionInfo(validatePromotionInfoRequest, head);
	}
	
	
		
}
