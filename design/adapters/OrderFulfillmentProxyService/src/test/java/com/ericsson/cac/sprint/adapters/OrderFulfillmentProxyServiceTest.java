package com.ericsson.cac.sprint.adapters;

import java.math.BigInteger;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
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

import com.sprint.integration.common.errordetailsv2.ErrorDetailsType;
import com.sprint.integration.common.header.wsmessageheader.v2.TrackingMessageHeaderType;
import com.sprint.integration.common.header.wsmessageheader.v2.WsMessageHeaderType;
import com.sprint.integration.eai.services.orderfulfillmentservice.v1.orderfulfillmentservice.FaultmessageV2;
import com.sprint.integration.eai.services.orderfulfillmentservice.v1.orderfulfillmentservice.OrderFulfillmentServicePortType;
import com.sprint.integration.interfaces.queryequipmentavailability.v1.queryequipmentavailability.CallingApplicationInfoType;
import com.sprint.integration.interfaces.queryequipmentavailability.v1.queryequipmentavailability.EquipmentAvailablilityIntoType;
import com.sprint.integration.interfaces.queryequipmentavailability.v1.queryequipmentavailability.EquipmentAvailablilityListType;
import com.sprint.integration.interfaces.queryequipmentavailability.v1.queryequipmentavailability.EquipmentListType;
import com.sprint.integration.interfaces.queryequipmentavailability.v1.queryequipmentavailability.QueryEquipmentAvailabilityRequestType;
import com.sprint.integration.interfaces.queryequipmentavailability.v1.queryequipmentavailability.QueryEquipmentAvailabilityResponseType;



/**
 * Created by esvwxal on 29/01/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class OrderFulfillmentProxyServiceTest {
	
    private Logger logger = LoggerFactory.getLogger(OrderFulfillmentProxyServiceTest.class);
    
    @Autowired
    private OrderFulfillmentProxyService service;

    @Autowired
    private OrderFulfillmentServicePortType mockPort;
    
    private QueryEquipmentAvailabilityResponseType queryEquipmentAvailabilityResponse = null;
    private EquipmentAvailablilityListType equipmentAvailablilityList = null;
        


    WsMessageHeaderType successHead = new WsMessageHeaderType();
	Holder<WsMessageHeaderType> successHeader = new Holder<WsMessageHeaderType>(
			successHead);
	
    WsMessageHeaderType failHead = new WsMessageHeaderType();
	Holder<WsMessageHeaderType> failureHeader = new Holder<WsMessageHeaderType>(
			failHead);
	
	@Value("${OrderFulfillmentService.trackingHeadAppId}")
	private String trackingHeadAppId;
	@Value("${OrderFulfillmentService.trackingHeadAppUsrId}")
	private String trackingHeadAppUsrId;
	@Value("${OrderFulfillmentService.trackingHeadConsumerId}")
	private String trackingHeadConsumerId;
	@Value("${OrderFulfillmentService.trackingHeadTimeToLive}")
	private String trackingHeadTimeToLive;
	@Value("${OrderFulfillmentService.trackingHeadMessageId}")
	private String trackingHeadMessageId;
	@Value("${OrderFulfillmentService.trackingHeadConversationId}")
	private String trackingHeadConversationId;
	@Value("${OrderFulfillmentService.trackingHeadFailAppId}")
	private String trackingHeadFailAppId;
	@Value("${OrderFulfillmentService.trackingHeadFailAppUsrId}")
	private String trackingHeadFailAppUsrId;
	@Value("${OrderFulfillmentService.trackingHeadFailConsumerId}")
	private String trackingHeadFailConsumerId;
	@Value("${OrderFulfillmentService.trackingHeadFailTimeToLive}")
	private String trackingHeadFailTimeToLive;


    @Before
    public void instructMock() throws FaultmessageV2, DatatypeConfigurationException{

    	TrackingMessageHeaderType trackingHead = new TrackingMessageHeaderType();
		trackingHead.setApplicationId(trackingHeadAppId);
		trackingHead.setApplicationUserId(trackingHeadAppUsrId);
		trackingHead.setConsumerId(trackingHeadConsumerId);
		trackingHead.setTimeToLive(new BigInteger(trackingHeadTimeToLive));
		trackingHead.setMessageId(trackingHeadMessageId);
		trackingHead.setConversationId(trackingHeadConversationId);
		GregorianCalendar c = new GregorianCalendar();
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


	queryEquipmentAvailabilityResponse = new QueryEquipmentAvailabilityResponseType();
	equipmentAvailablilityList = new EquipmentAvailablilityListType();
	EquipmentAvailablilityIntoType equipmentAvailablilityInfo1 = new EquipmentAvailablilityIntoType();
	equipmentAvailablilityInfo1.setOrderSubTypeCode("EQP");
	equipmentAvailablilityInfo1.setModelId("SPHL710ASB");
	equipmentAvailablilityInfo1.setBrandCode("SPP");
	equipmentAvailablilityList.getEquipmentAvailabilityInfo().add(equipmentAvailablilityInfo1);
	queryEquipmentAvailabilityResponse.setEquipmentAvailabilityList(equipmentAvailablilityList);
	
		
	
	
	
	ErrorDetailsType details = new ErrorDetailsType();
	


	}


    @Test
    public void testQueryEquipmentAvailability() throws FaultmessageV2 {
    	
    	QueryEquipmentAvailabilityRequestType queryEquipmentAvailabilityRequest = new QueryEquipmentAvailabilityRequestType();
    	
    	CallingApplicationInfoType callingApplicationInfo = new CallingApplicationInfoType();
    	callingApplicationInfo.setVendorCode("WU");
    	
    	EquipmentListType equipmentList = new EquipmentListType();
    	equipmentList.setOrderSubTypeCode("EQP");
    	
    	queryEquipmentAvailabilityRequest.setCallingApplicationInfo(callingApplicationInfo);
    	queryEquipmentAvailabilityRequest.setEquipmentAvailabilityList(equipmentList);
    	
    	QueryEquipmentAvailabilityResponseType queryEquipmentAvailabilityResponse2 = service.queryEquipmentAvailability(queryEquipmentAvailabilityRequest, successHeader);
    	Assert.assertEquals(false, queryEquipmentAvailabilityResponse2.getEquipmentAvailabilityList().getEquipmentAvailabilityInfo().get(0).isAvailableForSaleInd());
    } 	  
    
}
