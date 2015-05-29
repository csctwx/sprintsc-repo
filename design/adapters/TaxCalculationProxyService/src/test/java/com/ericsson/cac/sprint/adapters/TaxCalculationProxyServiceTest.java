package com.ericsson.cac.sprint.adapters;

import java.math.BigInteger;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.Holder;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sprint.integration.common.header.wsmessageheader.v2.TrackingMessageHeaderType;
import com.sprint.integration.common.header.wsmessageheader.v2.WsMessageHeaderType;
import com.sprint.integration.eai.services.taxcalculationservice.v1.taxcalculationservice.Faultmessage2;
import com.sprint.integration.eai.services.taxcalculationservice.v1.taxcalculationservice.TaxCalculationServicePortType;
import com.sprint.integration.interfaces.calculateprepaidinvoicetax.v1.calculateprepaidinvoicetax.AddressInfoType;
import com.sprint.integration.interfaces.calculateprepaidinvoicetax.v1.calculateprepaidinvoicetax.CalculatePrepaidInvoiceTaxRequestType;
import com.sprint.integration.interfaces.calculateprepaidinvoicetax.v1.calculateprepaidinvoicetax.CalculatePrepaidInvoiceTaxResponseType;
import com.sprint.integration.interfaces.calculateprepaidinvoicetax.v1.calculateprepaidinvoicetax.InvoiceLineInfoType;
import com.sprint.integration.interfaces.calculateprepaidinvoicetax.v1.calculateprepaidinvoicetax.LocationInfoType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class TaxCalculationProxyServiceTest {
    private Logger logger = LoggerFactory.getLogger(TaxCalculationProxyServiceTest.class);
    
    @Autowired
    private TaxCalculationProxyService service;

    @Autowired
    private TaxCalculationServicePortType mockPort;
    
    private CalculatePrepaidInvoiceTaxResponseType calculatePrepaidInvoiceTaxResponse = null;

    WsMessageHeaderType successHead = new WsMessageHeaderType();
	Holder<WsMessageHeaderType> successHeader = new Holder<WsMessageHeaderType>(
			successHead);
	
    WsMessageHeaderType failHead = new WsMessageHeaderType();
	Holder<WsMessageHeaderType> failureHeader = new Holder<WsMessageHeaderType>(
			failHead);
	
	@Value("${TaxCalculationService.trackingHeadAppId}")
	private String trackingHeadAppId;
	@Value("${TaxCalculationService.trackingHeadAppUsrId}")
	private String trackingHeadAppUsrId;
	@Value("${TaxCalculationService.trackingHeadConsumerId}")
	private String trackingHeadConsumerId;
	@Value("${TaxCalculationService.trackingHeadTimeToLive}")
	private String trackingHeadTimeToLive;
	@Value("${TaxCalculationService.trackingHeadMessageId}")
	private String trackingHeadMessageId;
	@Value("${TaxCalculationService.trackingHeadConversationId}")
	private String trackingHeadConversationId;
	@Value("${TaxCalculationService.trackingHeadFailAppId}")
	private String trackingHeadFailAppId;
	@Value("${TaxCalculationService.trackingHeadFailAppUsrId}")
	private String trackingHeadFailAppUsrId;
	@Value("${TaxCalculationService.trackingHeadFailConsumerId}")
	private String trackingHeadFailConsumerId;
	@Value("${TaxCalculationService.trackingHeadFailTimeToLive}")
	private String trackingHeadFailTimeToLive;
	private XMLGregorianCalendar invoiceDate;
	GregorianCalendar c = new GregorianCalendar();

    @Before
    public void instructMock() throws Faultmessage2, DatatypeConfigurationException{

    	TrackingMessageHeaderType trackingHead = new TrackingMessageHeaderType();
		trackingHead.setApplicationId(trackingHeadAppId);
		trackingHead.setApplicationUserId(trackingHeadAppUsrId);
		trackingHead.setConsumerId(trackingHeadConsumerId);
		trackingHead.setTimeToLive(new BigInteger(trackingHeadTimeToLive));
		trackingHead.setMessageId(trackingHeadMessageId);
		trackingHead.setConversationId(trackingHeadConversationId);
		
		trackingHead.setMessageDateTimeStamp(DatatypeFactory.newInstance().newXMLGregorianCalendar(c));
		successHead = new WsMessageHeaderType();
		successHead.setTrackingMessageHeader(trackingHead);
		successHeader = new Holder<WsMessageHeaderType>(successHead);
		
		
		TrackingMessageHeaderType trackingHeadFail = new TrackingMessageHeaderType();
		trackingHeadFail.setApplicationId(trackingHeadFailAppId);
		trackingHeadFail.setApplicationUserId(trackingHeadFailAppUsrId);
		trackingHeadFail.setConsumerId(trackingHeadFailConsumerId);
		trackingHeadFail.setTimeToLive(new BigInteger(trackingHeadFailTimeToLive));
		trackingHeadFail.setMessageDateTimeStamp(DatatypeFactory.newInstance().newXMLGregorianCalendar(c));

		failHead = new WsMessageHeaderType();
		failHead.setTrackingMessageHeader(trackingHead);
		failureHeader = new Holder<WsMessageHeaderType>(failHead);

    }


    @Test
    public void testcalculatePrepaidInvoiceTax() throws Faultmessage2, DatatypeConfigurationException {
    	
    	CalculatePrepaidInvoiceTaxRequestType calculatePrepaidInvoiceTaxRequest = new CalculatePrepaidInvoiceTaxRequestType();
    	InvoiceLineInfoType invoiceLineInfo = new InvoiceLineInfoType();
    	invoiceDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
    	invoiceLineInfo.setInvoiceDate(invoiceDate);
    	invoiceLineInfo.setTaxableAmount(new java.math.BigDecimal("0.0"));
    	AddressInfoType addressInfo = new AddressInfoType();
    	LocationInfoType acceptanceLocationInfo = new LocationInfoType();
    	acceptanceLocationInfo.setState("Haryana");
    	calculatePrepaidInvoiceTaxRequest.setAddressInfo(addressInfo);
    	calculatePrepaidInvoiceTaxRequest.setInvoiceLineInfo(invoiceLineInfo);
    	
    	CalculatePrepaidInvoiceTaxResponseType calculatePrepaidInvoiceTaxResponse1 = service.calculatePrepaidInvoiceTax(calculatePrepaidInvoiceTaxRequest, successHeader);
    		Assert.assertEquals("100345",calculatePrepaidInvoiceTaxResponse1.getCallingTransactionId());
    		
        }
    
}
