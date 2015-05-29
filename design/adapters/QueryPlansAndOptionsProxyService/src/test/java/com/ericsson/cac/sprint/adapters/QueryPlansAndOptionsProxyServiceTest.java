package com.ericsson.cac.sprint.adapters;

import java.math.BigInteger;
import java.util.GregorianCalendar;
import java.util.List;

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
import com.sprint.integration.eai.services.queryplansandoptionsservice.v1.queryplansandoptionsservice.Faultmessage;
import com.sprint.integration.interfaces.queryavailableoptions.v1.queryavailableoptions.PricePlanInfoResponseType;
import com.sprint.integration.interfaces.queryavailableoptions.v1.queryavailableoptions.QueryAvailableOptionsResponseType;
import com.sprint.integration.interfaces.queryavailableoptions.v1.queryavailableoptions.QueryAvailableOptionsType;
import com.sprint.integration.interfaces.queryavailableplans.v1.queryavailableplans.CallingApplicationInfoType;
import com.sprint.integration.interfaces.queryavailableplans.v1.queryavailableplans.DeviceInfoType;
import com.sprint.integration.interfaces.queryavailableplans.v1.queryavailableplans.OrderValidationModuleOrderTypeCodeType;
import com.sprint.integration.interfaces.queryavailableplans.v1.queryavailableplans.PricePlanListType;
import com.sprint.integration.interfaces.queryavailableplans.v1.queryavailableplans.PromoCodeInfoType;
import com.sprint.integration.interfaces.queryavailableplans.v1.queryavailableplans.QueryAvailablePlansResponseType;
import com.sprint.integration.interfaces.queryavailableplans.v1.queryavailableplans.QueryAvailablePlansType;
import com.sprint.integration.interfaces.queryavailableplans.v1.queryavailableplans.SearchCriteriaType;
import com.sprint.integration.interfaces.queryavailableplans.v1.queryavailableplans.ServiceAreaZipType;
import com.sprint.integration.interfaces.validateplansandoptions.v1.validateplansandoptions.ActionType;
import com.sprint.integration.interfaces.validateplansandoptions.v1.validateplansandoptions.BanInfoType;
import com.sprint.integration.interfaces.validateplansandoptions.v1.validateplansandoptions.OrderInfoType;
import com.sprint.integration.interfaces.validateplansandoptions.v1.validateplansandoptions.ServiceAgreementInfoType;
import com.sprint.integration.interfaces.validateplansandoptions.v1.validateplansandoptions.ServiceAreaInfoType;
import com.sprint.integration.interfaces.validateplansandoptions.v1.validateplansandoptions.TransactionTypeCodeType;
import com.sprint.integration.interfaces.validateplansandoptions.v1.validateplansandoptions.ValidatePlansAndOptionsResponseType;
import com.sprint.integration.interfaces.validateplansandoptions.v1.validateplansandoptions.ValidatePlansAndOptionsType;
import com.sprint.integration.interfaces.validateplansandoptions.v2.validateplansandoptionsv2.BanInfoDetailsType;
import com.sprint.integration.interfaces.validateplansandoptions.v2.validateplansandoptionsv2.RequestedActionInfoType;
import com.sprint.integration.interfaces.validateplansandoptions.v2.validateplansandoptionsv2.ValidatePlansAndOptionsV2RequestType;
import com.sprint.integration.interfaces.validateplansandoptions.v2.validateplansandoptionsv2.ValidatePlansAndOptionsV2ResponseType;

/**
 * Created by esvwxal on 21/01/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)

public class QueryPlansAndOptionsProxyServiceTest {
    private Logger logger = LoggerFactory.getLogger(QueryPlansAndOptionsProxyServiceTest.class);
    
    @Autowired
    private QueryPlansAndOptionsProxyService service;

   /* @Autowired
    private QueryPlansAndOptionsPortType mockPort;*/

    
    private QueryAvailableOptionsResponseType queryAvailableOptionsResponse = null;
   // private SubQueryAvailableOptionsResponseType subQueryAvailableOptionsResponse = null;    
    private List<PricePlanInfoResponseType> pricePlanInfo = null;
    private ValidatePlansAndOptionsResponseType validatePlansAndOptionsResponse = null;
    
    private ValidatePlansAndOptionsV2ResponseType validatePlansAndOptionsV2Response = null;

    WsMessageHeaderType successHead = null;
	Holder<WsMessageHeaderType> successHeader = null;
	
    WsMessageHeaderType failHead = null;
	Holder<WsMessageHeaderType> failureHeader = null;
	
	QueryAvailablePlansResponseType queryAvailablePlansResponse = null;

	@Value("${QueryPlansAndOptionsService.trackingHeadAppId}")
	private String trackingHeadAppId;
	@Value("${QueryPlansAndOptionsService.trackingHeadAppUsrId}")
	private String trackingHeadAppUsrId;
	@Value("${QueryPlansAndOptionsService.trackingHeadConsumerId}")
	private String trackingHeadConsumerId;
	@Value("${QueryPlansAndOptionsService.trackingHeadTimeToLive}")
	private String trackingHeadTimeToLive;
	@Value("${QueryPlansAndOptionsService.trackingHeadMessageId}")
	private String trackingHeadMessageId;
	@Value("${QueryPlansAndOptionsService.trackingHeadConversationId}")
	private String trackingHeadConversationId;
	@Value("${QueryPlansAndOptionsService.trackingHeadFailAppId}")
	private String trackingHeadFailAppId;
	@Value("${QueryPlansAndOptionsService.trackingHeadFailAppUsrId}")
	private String trackingHeadFailAppUsrId;
	@Value("${QueryPlansAndOptionsService.trackingHeadFailConsumerId}")
	private String trackingHeadFailConsumerId;
	@Value("${QueryPlansAndOptionsService.trackingHeadFailTimeToLive}")
	private String trackingHeadFailTimeToLive;
	
	private XMLGregorianCalendar orderDate;
	GregorianCalendar c = new GregorianCalendar();
	
    @Before
    public void instructMock() throws Faultmessage, DatatypeConfigurationException{

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
    /*
     * queryAvailablePlans
     */
       
    queryAvailablePlansResponse = new QueryAvailablePlansResponseType();
    PromoCodeInfoType promoCodeInfoType  = new PromoCodeInfoType();
    promoCodeInfoType.setValidPromoCodeInd(true);
    queryAvailablePlansResponse.setPromoCodeInfo(promoCodeInfoType);
    
    PricePlanListType pricePlanList = new PricePlanListType();
    com.sprint.integration.interfaces.queryavailableplans.v1.queryavailableplans.PricePlanInfoType pricePlanInfo1 = new com.sprint.integration.interfaces.queryavailableplans.v1.queryavailableplans.PricePlanInfoType();
    pricePlanInfo1.setPricePlanSoc("SPP551LTE");
    pricePlanInfo1.setSocDesc("$45/Unltd Min/Text");
    pricePlanList.getPricePlanInfo().add(pricePlanInfo1);
    queryAvailablePlansResponse.setPricePlanList(pricePlanList);

	/*when(mockPort.queryAvailablePlans(any(QueryAvailablePlansType.class), eq(successHeader))).thenReturn(queryAvailablePlansResponse);
	
	ErrorDetailsType details = new ErrorDetailsType();
	
	when(mockPort.queryAvailablePlans(any(QueryAvailablePlansType.class), eq(failureHeader))).thenThrow(new Faultmessage("test fail", details));*/
	
	
	/*
	 * queryAvailableOptions
	 */
	
    QueryAvailableOptionsResponseType queryAvailableOptionsResponse = new QueryAvailableOptionsResponseType();
	PricePlanInfoResponseType pricePlanInfoResponse1 = new PricePlanInfoResponseType();
	pricePlanInfoResponse1.setPricePlanSoc("abcd");
	PricePlanInfoResponseType pricePlanInfoResponse2 = new PricePlanInfoResponseType();
	pricePlanInfoResponse2.setPricePlanSoc("efgh");
	
	//pricePlanInfo = new ArrayList<PricePlanInfoResponseType>();
	
	queryAvailableOptionsResponse.getPricePlanInfo().add(pricePlanInfoResponse1);
	queryAvailableOptionsResponse.getPricePlanInfo().add(pricePlanInfoResponse2);
	
	//pricePlanInfo.add(pricePlanInfoResponse1);
	//pricePlanInfo.add(pricePlanInfoResponse1);
	
	
	//subQueryAvailableOptionsResponse = new SubQueryAvailableOptionsResponseType();
	//subQueryAvailableOptionsResponse.setPricePlanInfo(pricePlanInfo);
	
	
	/*when(mockPort.queryAvailableOptions(any(QueryAvailableOptionsType.class), eq(successHeader))).thenReturn(queryAvailableOptionsResponse);
	
	when(mockPort.queryAvailableOptions(any(QueryAvailableOptionsType.class), eq(failureHeader))).thenThrow(new Faultmessage("test fail", details));*/
	
	
	/*
	 * validatePlansAndOptions
	 */
	validatePlansAndOptionsResponse = new ValidatePlansAndOptionsResponseType();
	validatePlansAndOptionsResponse.setValidationSucceededInd(true);
	validatePlansAndOptionsResponse.setLtsRemainingSubscribers(new BigInteger("2"));
	/*when(mockPort.validatePlansAndOptions(any(ValidatePlansAndOptionsType.class), eq(successHeader))).thenReturn(validatePlansAndOptionsResponse);
	
	when(mockPort.validatePlansAndOptions(any(ValidatePlansAndOptionsType.class), eq(failureHeader))).thenThrow(new Faultmessage("test fail", details));*/
	
	
   }


    @Test
    public void testQueryAvailablePlans() throws Faultmessage{
            QueryAvailablePlansType queryAvailablePlansRequest = new QueryAvailablePlansType();
        	CallingApplicationInfoType callingApplicationInfoType = new CallingApplicationInfoType();
        	callingApplicationInfoType.setPin("1331");
        	callingApplicationInfoType.setVendorCode("WU");
        	queryAvailablePlansRequest.setCallingApplicationInfo(callingApplicationInfoType);
        	
        	SearchCriteriaType searchCriteria = new SearchCriteriaType();
        	
        	com.sprint.integration.interfaces.queryavailableplans.v1.queryavailableplans.ServiceAreaInfoType ServiceAreaInfo = new com.sprint.integration.interfaces.queryavailableplans.v1.queryavailableplans.ServiceAreaInfoType();
        	ServiceAreaZipType ServiceAreaZip = new ServiceAreaZipType();
        	ServiceAreaZip.setZipCode("75063");
        	ServiceAreaInfo.setServiceAreaZip(ServiceAreaZip);
        	
        	DeviceInfoType deviceInfo = new DeviceInfoType();
        	deviceInfo.setItemId("SPHL710ASB");
        	
        	com.sprint.integration.interfaces.queryavailableplans.v1.queryavailableplans.OrderInfoType orderInfo = new com.sprint.integration.interfaces.queryavailableplans.v1.queryavailableplans.OrderInfoType();
        	orderInfo.setOrderType(OrderValidationModuleOrderTypeCodeType.NEW);
        	orderInfo.setSalesChannel("B");
        	
        	com.sprint.integration.interfaces.queryavailableplans.v1.queryavailableplans.BanInfoType banInfo = new com.sprint.integration.interfaces.queryavailableplans.v1.queryavailableplans.BanInfoType();
        	banInfo.setBan("100565543");
        	banInfo.setAccountType("I");
        	banInfo.setAccountSubType("M");
        	banInfo.setNumberSubscribers(new BigInteger("2"));
        	
        	searchCriteria.setServiceAreaInfo(ServiceAreaInfo);
        	searchCriteria.setDeviceInfo(deviceInfo);
        	searchCriteria.setOrderInfo(orderInfo);
        	searchCriteria.setBanInfo(banInfo);
        	
        	queryAvailablePlansRequest.setSearchCriteria(searchCriteria);
        	
        	
        	QueryAvailablePlansResponseType queryAvailablePlansResponse2 = service.queryAvailablePlans(queryAvailablePlansRequest, successHeader);
        	
        	PromoCodeInfoType resultPromoCodeInfoType  = new PromoCodeInfoType();
        	resultPromoCodeInfoType.setValidPromoCodeInd(true);
            
            //assert queryAvailablePlansResponse2.getPricePlanList().getPricePlanInfo().get(0).getPricePlanSoc().equals(queryAvailablePlansResponse.getPricePlanList().getPricePlanInfo().get(0).getPricePlanSoc()) : "Responses are not same";
            Assert.assertEquals("Responses are not sameexpected", queryAvailablePlansResponse.getPricePlanList().getPricePlanInfo().get(0).getPricePlanSoc(), queryAvailablePlansResponse2.getPricePlanList().getPricePlanInfo().get(0).getPricePlanSoc());
        	
        
    }
    
    @Test
    public void testQueryAvailableOptions() throws Faultmessage{
    		QueryAvailableOptionsType queryAvailableOptionsRequest = new QueryAvailableOptionsType();
    		com.sprint.integration.interfaces.queryavailableoptions.v1.queryavailableoptions.CallingApplicationInfoType callingApplicationInfo = new com.sprint.integration.interfaces.queryavailableoptions.v1.queryavailableoptions.CallingApplicationInfoType();
    		callingApplicationInfo.setPin("1331");
    		callingApplicationInfo.setVendorCode("WU");
    		queryAvailableOptionsRequest.setCallingApplicationInfo(callingApplicationInfo);
    		
    		com.sprint.integration.interfaces.queryavailableoptions.v1.queryavailableoptions.SearchCriteriaType searchCriteria = new com.sprint.integration.interfaces.queryavailableoptions.v1.queryavailableoptions.SearchCriteriaType();
    		
    		com.sprint.integration.interfaces.queryavailableoptions.v1.queryavailableoptions.BanInfoType banInfo = new com.sprint.integration.interfaces.queryavailableoptions.v1.queryavailableoptions.BanInfoType();
    		banInfo.setBan("100565543");
    		banInfo.setAccountType("I");
    		banInfo.setAccountSubType("M");
    		
    		com.sprint.integration.interfaces.queryavailableoptions.v1.queryavailableoptions.ServiceAreaInfoType serviceAreaInfo = new com.sprint.integration.interfaces.queryavailableoptions.v1.queryavailableoptions.ServiceAreaInfoType();
    		com.sprint.integration.interfaces.queryavailableoptions.v1.queryavailableoptions.ServiceAreaZipType serviceAreaZip = new com.sprint.integration.interfaces.queryavailableoptions.v1.queryavailableoptions.ServiceAreaZipType();
    		serviceAreaZip.setZipCode("75063");
    		serviceAreaInfo.setServiceAreaZip(serviceAreaZip);
    		
    		com.sprint.integration.interfaces.queryavailableoptions.v1.queryavailableoptions.OrderInfoType orderInfo = new com.sprint.integration.interfaces.queryavailableoptions.v1.queryavailableoptions.OrderInfoType();
    		orderInfo.setOrderType(com.sprint.integration.interfaces.queryavailableoptions.v1.queryavailableoptions.OrderValidationModuleOrderTypeCodeType.NEW);
    		orderInfo.setSalesChannel("B");
    		orderInfo.setNumberSubscribers(new BigInteger("2"));
    		
    		com.sprint.integration.interfaces.queryavailableoptions.v1.queryavailableoptions.DeviceInfoType deviceInfo = new com.sprint.integration.interfaces.queryavailableoptions.v1.queryavailableoptions.DeviceInfoType();
    		deviceInfo.setItemId("SPHL710ASB");
    		
    		searchCriteria.setBanInfo(banInfo);
    		searchCriteria.setServiceAreaInfo(serviceAreaInfo);
    		searchCriteria.setOrderInfo(orderInfo);
    		searchCriteria.setDeviceInfo(deviceInfo);
    		
    		queryAvailableOptionsRequest.setSearchCriteria(searchCriteria);
    		
    		
    		QueryAvailableOptionsResponseType queryAvailableOptionsResponse = service.queryAvailableOptions(queryAvailableOptionsRequest, successHeader);
    		
    		
    		//assert queryAvailableOptionsResponse.getPricePlanInfo().get(0).getAddOnSocList().getAddOnSocInfo().get(0).getSoc().equals("PHNINS") : "Responses are not same";
    		Assert.assertEquals("Responses are not same", "PHNINS", queryAvailableOptionsResponse.getPricePlanInfo().get(0).getAddOnSocList().getAddOnSocInfo().get(0).getSoc());
    }
    
    
    @Test
    public void testValidatePlansAndOptions() throws Faultmessage, DatatypeConfigurationException{
    	ValidatePlansAndOptionsType validatePlansAndOptionsRequest = new ValidatePlansAndOptionsType();
    	com.sprint.integration.interfaces.validateplansandoptions.v1.validateplansandoptions.CallingApplicationInfoType callingApplicationInfoType = new com.sprint.integration.interfaces.validateplansandoptions.v1.validateplansandoptions.CallingApplicationInfoType();
    	callingApplicationInfoType.setPin("1331");
    	callingApplicationInfoType.setVendorCode("WU");
    	validatePlansAndOptionsRequest.setCallingApplicationInfo(callingApplicationInfoType);
    	
    	ServiceAgreementInfoType serviceAgreementInfo = new ServiceAgreementInfoType();
    	
    	ActionType actionType = new ActionType();
    	TransactionTypeCodeType transactionTypeCode = TransactionTypeCodeType.CHECK;
    	actionType.setActionType(transactionTypeCode);
    	actionType.setSubscriberId("40622562121");
    	serviceAgreementInfo.setAction(actionType);
    	
    	BanInfoType banInfo = new BanInfoType();
    	banInfo.setBan("100565543");
    	serviceAgreementInfo.setBanInfo(banInfo);
    	
    	OrderInfoType orderInfo = new OrderInfoType();
    	GregorianCalendar c = new GregorianCalendar();
    	orderInfo.setOrderDate(DatatypeFactory.newInstance()
    			.newXMLGregorianCalendar(c));
    	serviceAgreementInfo.setOrderInfo(orderInfo);
    	
    	ServiceAreaInfoType serviceAreaInfo = new ServiceAreaInfoType();
    	//serviceAreaInfo.setSubMarket("abcdefgh");
    	com.sprint.integration.interfaces.validateplansandoptions.v1.validateplansandoptions.ServiceAreaZipType serviceAreaZip = new com.sprint.integration.interfaces.validateplansandoptions.v1.validateplansandoptions.ServiceAreaZipType();
    	serviceAreaZip.setZipCode("75063");
    	serviceAreaInfo.setServiceAreaZip(serviceAreaZip);
    	serviceAgreementInfo.setServiceAreaInfo(serviceAreaInfo);
    	
    	com.sprint.integration.interfaces.validateplansandoptions.v1.validateplansandoptions.DeviceInfoType deviceInfo = new com.sprint.integration.interfaces.validateplansandoptions.v1.validateplansandoptions.DeviceInfoType();
    	deviceInfo.setItemId("SPHL710ASB");
    	serviceAgreementInfo.setDeviceInfo(deviceInfo);
    	
    	validatePlansAndOptionsRequest.setServiceAgreementInfo(serviceAgreementInfo);
    	
    	
    	ValidatePlansAndOptionsResponseType validatePlansAndOptionsResponse2 = service.validatePlansAndOptions(validatePlansAndOptionsRequest, successHeader);
    	
    	//assert validatePlansAndOptionsResponse2.isDataSocInd() == validatePlansAndOptionsResponse.isDataSocInd() : "Responses are not same";
    	Assert.assertEquals("Responses are not same", validatePlansAndOptionsResponse.isDataSocInd(), validatePlansAndOptionsResponse2.isDataSocInd());
    	
    	
    }
    
    @Test
    public void testvalidatePlansAndOptionsV2() throws Faultmessage, DatatypeConfigurationException{
    	ValidatePlansAndOptionsV2RequestType validatePlansAndOptionsV2 = new ValidatePlansAndOptionsV2RequestType();
    	
    	com.sprint.integration.interfaces.validateplansandoptions.v2.validateplansandoptionsv2.ServiceAgreementInfoType serviceAgreementInfoType = 
    			new com.sprint.integration.interfaces.validateplansandoptions.v2.validateplansandoptionsv2.ServiceAgreementInfoType();
    	
    	RequestedActionInfoType requestedActionInfoType = new RequestedActionInfoType();
    	com.sprint.integration.interfaces.validateplansandoptions.v2.validateplansandoptionsv2.TransactionTypeCodeType transactionTypeCode=
    			com.sprint.integration.interfaces.validateplansandoptions.v2.validateplansandoptionsv2.TransactionTypeCodeType.CREATE;
    	requestedActionInfoType.setActionType(transactionTypeCode);
    	
    	com.sprint.integration.interfaces.validateplansandoptions.v2.validateplansandoptionsv2.BanInfoType banInfoType = new com.sprint.integration.interfaces.validateplansandoptions.v2.validateplansandoptionsv2.BanInfoType();
    	BanInfoDetailsType banInfoDetailsType = new BanInfoDetailsType();
    	banInfoDetailsType.setBan(100565543);
    	
    	com.sprint.integration.interfaces.validateplansandoptions.v2.validateplansandoptionsv2.OrderInfoType orderInfoType = new com.sprint.integration.interfaces.validateplansandoptions.v2.validateplansandoptionsv2.OrderInfoType();
    	orderDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
    	orderInfoType.setOrderDate(orderDate);
    	
    	com.sprint.integration.interfaces.validateplansandoptions.v2.validateplansandoptionsv2.ServiceAreaInfoType serviceAreaInfoType = new com.sprint.integration.interfaces.validateplansandoptions.v2.validateplansandoptionsv2.ServiceAreaInfoType();
    	serviceAreaInfoType.setSubMarket("bangalore");
    	
    	serviceAgreementInfoType.setBanInfo(banInfoType);
    	serviceAgreementInfoType.setServiceAreaInfo(serviceAreaInfoType);
    	serviceAgreementInfoType.setOrderInfo(orderInfoType);
    	serviceAgreementInfoType.setRequestedActionInfo(requestedActionInfoType);
    	validatePlansAndOptionsV2.setServiceAgreementInfo(serviceAgreementInfoType);
    	
    	ValidatePlansAndOptionsV2ResponseType validatePlansAndOptionsV2Response = service.validatePlansAndOptionsV2(validatePlansAndOptionsV2, successHeader);
    	
    	
    	Assert.assertEquals(true, validatePlansAndOptionsV2Response.getValidationErrorInfo());
    	
    	
    }
    
}
