package com.ericsson.cac.sprint.adapters;

import javax.xml.ws.Holder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sprint.integration.common.header.wsmessageheader.v2.WsMessageHeaderType;
import com.sprint.integration.eai.services.subscribermanagementservice.v1.subscribermanagementservice.FaultMessage;
import com.sprint.integration.eai.services.subscribermanagementservice.v1.subscribermanagementservice.SubscriberManagementService;
import com.sprint.integration.eai.services.subscribermanagementservice.v1.subscribermanagementservice.SubscriberManagementServicePortType;
import com.sprint.integration.interfaces.managesubscriberservices.v1.managesubscriberservices.ManageSubscriberServicesResponseType;
import com.sprint.integration.interfaces.managesubscriberservices.v1.managesubscriberservices.ManageSubscriberServicesType;
import com.sprint.integration.interfaces.restoresubscriber.v1.restoresubscriber.RestoreSubscriberRequestType;
import com.sprint.integration.interfaces.restoresubscriber.v1.restoresubscriber.RestoreSubscriberResponseType;
import com.sprint.integration.interfaces.submitequipmentpurchaseorder.v1.submitequipmentpurchaseorder.SubmitEquipmentPurchaseOrderRequestType;
import com.sprint.integration.interfaces.submitequipmentpurchaseorder.v1.submitequipmentpurchaseorder.SubmitEquipmentPurchaseOrderResponseType;
import com.sprint.integration.interfaces.suspendsubscriber.v1.suspendsubscriber.SuspendSubscriberRequestType;
import com.sprint.integration.interfaces.suspendsubscriber.v1.suspendsubscriber.SuspendSubscriberResponseType;
import com.sprint.integration.interfaces.swapcsa.v1.swapcsa.SwapCsaRequestType;
import com.sprint.integration.interfaces.swapcsa.v1.swapcsa.SwapCsaResponseType;
import com.sprint.integration.interfaces.swapsubscriberequipment.v1.swapsubscriberequipment.SwapSubscriberEquipmentResponseType;
import com.sprint.integration.interfaces.swapsubscriberequipment.v1.swapsubscriberequipment.SwapSubscriberEquipmentType;
import com.sprint.integration.interfaces.updatesubscriberdetails.v1.updatesubscriberdetails.UpdateSubscriberDetailsRequestType;
import com.sprint.integration.interfaces.updatesubscriberdetails.v1.updatesubscriberdetails.UpdateSubscriberDetailsResponseType;


/**
 * Created by echasis on 23/01/2015.
 */
@Component
public class SubscriberManagementProxyService extends SubscriberManagementService {
    @Autowired
    private SubscriberManagementServicePortType stub;
	
	public ManageSubscriberServicesResponseType manageSubscriberServices(ManageSubscriberServicesType parameters,Holder<WsMessageHeaderType> head) throws FaultMessage {
		return stub.manageSubscriberServices(parameters,head);
	}
    
    public SwapSubscriberEquipmentResponseType swapSubscriberEquipment(SwapSubscriberEquipmentType parameters,Holder<WsMessageHeaderType> head) throws FaultMessage {
		return stub.swapSubscriberEquipment(parameters,head);
	}
	
	public SwapCsaResponseType swapCsa(SwapCsaRequestType swapCsa,Holder<WsMessageHeaderType> head) throws FaultMessage {
		return stub.swapCsa(swapCsa,head);
	}
	
	public RestoreSubscriberResponseType restoreSubscriber(RestoreSubscriberRequestType restoreSubscriber,Holder<WsMessageHeaderType> head) throws FaultMessage {
		return stub.restoreSubscriber(restoreSubscriber,head);
	}
	
	public SubmitEquipmentPurchaseOrderResponseType submitEquipmentPurchaseOrder(SubmitEquipmentPurchaseOrderRequestType submitEquipmentPurchaseOrderRequest,Holder<WsMessageHeaderType> head) throws FaultMessage {
		return stub.submitEquipmentPurchaseOrder(submitEquipmentPurchaseOrderRequest,head);
	}
	
	public SuspendSubscriberResponseType suspendSubscriber(SuspendSubscriberRequestType suspendSubscriber,Holder<WsMessageHeaderType> head) throws FaultMessage {
		System.out.println("came here");
		return stub.suspendSubscriber(suspendSubscriber,head);
	}
	
	public UpdateSubscriberDetailsResponseType updateSubscriberDetails(UpdateSubscriberDetailsRequestType parameters,Holder<WsMessageHeaderType> head) throws FaultMessage {
		return stub.updateSubscriberDetails(parameters,head);
	}
}
