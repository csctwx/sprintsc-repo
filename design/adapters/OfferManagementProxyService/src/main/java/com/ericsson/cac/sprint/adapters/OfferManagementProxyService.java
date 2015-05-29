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
import com.sprint.integration.interfaces.offermanagementservice.v1.offermanagementservice.OfferManagementService;
import com.sprint.integration.interfaces.offermanagementservice.v1.offermanagementservice.OfferManagementServicePortType;
import com.sprint.integration.interfaces.offermanagementservice.v1.offermanagementservice.SoapFaultv2;
import com.sprint.integration.interfaces.querytargetedoffers.v1.querytargetedoffers.QueryTargetedOffersResponseType;
import com.sprint.integration.interfaces.querytargetedoffers.v1.querytargetedoffers.QueryTargetedOffersType;
import com.sprint.integration.interfaces.setofferdisposition.v1.setofferdisposition.SetOfferDispositionResponseType;
import com.sprint.integration.interfaces.setofferdisposition.v1.setofferdisposition.SetOfferDispositionType;

@Component
public class OfferManagementProxyService extends OfferManagementService {
	@Autowired
	private OfferManagementServicePortType stub;

	public QueryTargetedOffersResponseType queryTargetedOffers(
			QueryTargetedOffersType queryTargetedOffersRequest,
			Holder<WsMessageHeaderType> head) throws SoapFaultv2 {
		return stub.queryTargetedOffers(queryTargetedOffersRequest, head);
	}

	public SetOfferDispositionResponseType setOfferDisposition(
			SetOfferDispositionType setOfferDispositionRequest,
			Holder<WsMessageHeaderType> head) throws SoapFaultv2 {
		return stub.setOfferDisposition(setOfferDispositionRequest, head);
	}

}
