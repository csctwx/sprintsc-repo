package com.ericsson.cac.sprint.adapters;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
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
import com.sprint.integration.eai.services.promotionmanagementservice.v1.promotionmanagementservice.PromotionManagementServicePortType;
import com.sprint.integration.eai.services.promotionmanagementservice.v1.promotionmanagementservice.SoapFaultV2;
import com.sprint.integration.interfaces.validatepromotioninfo.v1.validatepromotioninfo.PromotionInfoType;
import com.sprint.integration.interfaces.validatepromotioninfo.v1.validatepromotioninfo.RequestItemInfoType;
import com.sprint.integration.interfaces.validatepromotioninfo.v1.validatepromotioninfo.RequestItemListType;
import com.sprint.integration.interfaces.validatepromotioninfo.v1.validatepromotioninfo.ResponseItemInfoType;
import com.sprint.integration.interfaces.validatepromotioninfo.v1.validatepromotioninfo.ResponseItemListType;
import com.sprint.integration.interfaces.validatepromotioninfo.v1.validatepromotioninfo.ValidatePromotionInfoRequestType;
import com.sprint.integration.interfaces.validatepromotioninfo.v1.validatepromotioninfo.ValidatePromotionInfoResponseType;



/**
 * Created by esvwxal on 30/01/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class PromotionManagementProxyServiceTest {
	
    private Logger logger = LoggerFactory.getLogger(PromotionManagementProxyServiceTest.class);
    
    @Autowired
    private PromotionManagementProxyService service;

    @Autowired
    private PromotionManagementServicePortType mockPort;
    
    private ValidatePromotionInfoResponseType validatePromotionInfoResponse = null;
        


    WsMessageHeaderType successHead = new WsMessageHeaderType();
	Holder<WsMessageHeaderType> successHeader = new Holder<WsMessageHeaderType>(
			successHead);
	
    WsMessageHeaderType failHead = new WsMessageHeaderType();
	Holder<WsMessageHeaderType> failureHeader = new Holder<WsMessageHeaderType>(
			failHead);
	
	@Value("${PromotionManagementService.trackingHeadAppId}")
	private String trackingHeadAppId;
	@Value("${PromotionManagementService.trackingHeadAppUsrId}")
	private String trackingHeadAppUsrId;
	@Value("${PromotionManagementService.trackingHeadConsumerId}")
	private String trackingHeadConsumerId;
	@Value("${PromotionManagementService.trackingHeadTimeToLive}")
	private String trackingHeadTimeToLive;
	@Value("${PromotionManagementService.trackingHeadMessageId}")
	private String trackingHeadMessageId;
	@Value("${PromotionManagementService.trackingHeadConversationId}")
	private String trackingHeadConversationId;
	@Value("${PromotionManagementService.trackingHeadFailAppId}")
	private String trackingHeadFailAppId;
	@Value("${PromotionManagementService.trackingHeadFailAppUsrId}")
	private String trackingHeadFailAppUsrId;
	@Value("${PromotionManagementService.trackingHeadFailConsumerId}")
	private String trackingHeadFailConsumerId;
	@Value("${PromotionManagementService.trackingHeadFailTimeToLive}")
	private String trackingHeadFailTimeToLive;


    @Before
    public void instructMock() throws SoapFaultV2, DatatypeConfigurationException{

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


   
	validatePromotionInfoResponse = new ValidatePromotionInfoResponseType();
	ResponseItemListType responseItemList = new ResponseItemListType();
	ResponseItemInfoType responseItemInfo1 = new ResponseItemInfoType();
	responseItemInfo1.setItemId("12345");
	responseItemInfo1.setQuantity(new BigInteger("50"));
	responseItemInfo1.setOriginalPricePerItemId(new BigDecimal(20));
	responseItemList.getItemIdInfo().add(responseItemInfo1);
	validatePromotionInfoResponse.setItemIdList(responseItemList);
	
	validatePromotionInfoResponse.setTotalOriginalPrice(new BigDecimal(55));
	validatePromotionInfoResponse.setTotalDiscountedPrice(new BigDecimal(50));
	validatePromotionInfoResponse.setPromoLegalese("xyz");
	validatePromotionInfoResponse.setSuccessMessage("success");
	
	
	
	
		
	when(mockPort.validatePromotionInfo(any(ValidatePromotionInfoRequestType.class), eq(successHeader))).thenReturn(validatePromotionInfoResponse);
	
	
	ErrorDetailsType details = new ErrorDetailsType();
	
	when(
			mockPort.validatePromotionInfo(
					any(ValidatePromotionInfoRequestType.class),
					eq(failureHeader))).thenThrow(new SoapFaultV2("test fail", details));

	}


    @Test
    public void testValidatePromotionInfo() throws SoapFaultV2 {
    	
    	ValidatePromotionInfoRequestType validatePromotionInfoRequest = new ValidatePromotionInfoRequestType();
    	PromotionInfoType promotionInfo = new PromotionInfoType();
    	promotionInfo.setCustomerType("abcdef");
    	promotionInfo.setPromoCode("12345");
    	
    	validatePromotionInfoRequest.setPromotionInfo(promotionInfo);
    	
    	RequestItemInfoType requestItemInfo1 = new RequestItemInfoType();
    	requestItemInfo1.setItemId("12345");
    	requestItemInfo1.setPricePerItemId(new BigDecimal(20));
    	requestItemInfo1.setQuantity(new BigInteger("100"));
    	
    	RequestItemListType requestItemList= new RequestItemListType();
    	requestItemList.getItemIdInfo().add(requestItemInfo1);
    	
    	validatePromotionInfoRequest.setItemIdList(requestItemList);
    	
    	   	
    	ValidatePromotionInfoResponseType validatePromotionInfoResponse2 = service.validatePromotionInfo(validatePromotionInfoRequest, successHeader);
    	Assert.assertEquals("Responses are not same", (validatePromotionInfoResponse2.getTotalOriginalPrice().equals(validatePromotionInfoResponse.getTotalOriginalPrice()) && validatePromotionInfoResponse2.getTotalDiscountedPrice().equals(validatePromotionInfoResponse.getTotalDiscountedPrice())));
    } 	  
    
}
