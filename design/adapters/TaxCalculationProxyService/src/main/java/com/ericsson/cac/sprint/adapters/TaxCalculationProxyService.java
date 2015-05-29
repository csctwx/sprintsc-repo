package com.ericsson.cac.sprint.adapters;

import javax.xml.ws.Holder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sprint.integration.common.header.wsmessageheader.v2.WsMessageHeaderType;
import com.sprint.integration.eai.services.taxcalculationservice.v1.taxcalculationservice.Faultmessage2;
import com.sprint.integration.eai.services.taxcalculationservice.v1.taxcalculationservice.TaxCalculationService;
import com.sprint.integration.eai.services.taxcalculationservice.v1.taxcalculationservice.TaxCalculationServicePortType;
import com.sprint.integration.interfaces.calculateprepaidinvoicetax.v1.calculateprepaidinvoicetax.CalculatePrepaidInvoiceTaxRequestType;
import com.sprint.integration.interfaces.calculateprepaidinvoicetax.v1.calculateprepaidinvoicetax.CalculatePrepaidInvoiceTaxResponseType;






@Component
public class TaxCalculationProxyService extends TaxCalculationService {
   @Autowired
  private TaxCalculationServicePortType stub;
    


	public CalculatePrepaidInvoiceTaxResponseType calculatePrepaidInvoiceTax(CalculatePrepaidInvoiceTaxRequestType  calculatePrepaidInvoiceTaxRequest, Holder<WsMessageHeaderType> head) throws Faultmessage2{
		return stub.calculatePrepaidInvoiceTax(calculatePrepaidInvoiceTaxRequest, head);
	}
	
		
}
