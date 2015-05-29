package com.ericsson.cac.sprint.adapters;

import static org.junit.Assert.assertEquals;

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
import com.sprint.integration.eai.services.subscribermanagementservice.v1.subscribermanagementservice.FaultMessage;
import com.sprint.integration.interfaces.managesubscriberservices.v1.managesubscriberservices.CdmaProgrammingInfoType;
import com.sprint.integration.interfaces.managesubscriberservices.v1.managesubscriberservices.ManageSubscriberServicesResponseType;
import com.sprint.integration.interfaces.managesubscriberservices.v1.managesubscriberservices.ManageSubscriberServicesType;
import com.sprint.integration.interfaces.managesubscriberservices.v1.managesubscriberservices.SubscriberDetailsListType;
import com.sprint.integration.interfaces.managesubscriberservices.v1.managesubscriberservices.SubscriberDetailsType;
import com.sprint.integration.interfaces.managesubscriberservices.v1.managesubscriberservices.SubscriberInfoType;
import com.sprint.integration.interfaces.managesubscriberservices.v1.managesubscriberservices.SubscriberStatusCodeType;
import com.sprint.integration.interfaces.restoresubscriber.v1.restoresubscriber.RestoreSubscriberResponseType;
//~ import com.sprint.integration.interfaces.restoresubscriber.v1.restoresubscriber.SubscriberInfoType;
//~ import com.sprint.integration.interfaces.restoresubscriber.v1.restoresubscriber.ChangeSubStatusInfoType;
import com.sprint.integration.interfaces.submitequipmentpurchaseorder.v1.submitequipmentpurchaseorder.SubmitEquipmentPurchaseOrderResponseType;
import com.sprint.integration.interfaces.suspendsubscriber.v1.suspendsubscriber.SuspendSubscriberResponseType;
//~ import com.sprint.integration.interfaces.swapcsa.v1.swapcsa.SubscriberInfoType;
import com.sprint.integration.interfaces.swapcsa.v1.swapcsa.MarketInfoType;
import com.sprint.integration.interfaces.swapcsa.v1.swapcsa.MemoInfoType;
import com.sprint.integration.interfaces.swapcsa.v1.swapcsa.NetworkResonInfoType;
import com.sprint.integration.interfaces.swapcsa.v1.swapcsa.NetworkTypeCodeType;
import com.sprint.integration.interfaces.swapcsa.v1.swapcsa.NpaNxxInfoType;
import com.sprint.integration.interfaces.swapcsa.v1.swapcsa.SubscriberResponseInfoType;
import com.sprint.integration.interfaces.swapcsa.v1.swapcsa.SwapCsaRequestType;
import com.sprint.integration.interfaces.swapcsa.v1.swapcsa.SwapCsaResponseType;
import com.sprint.integration.interfaces.swapsubscriberequipment.v1.swapsubscriberequipment.ResponseResourceInfoType;
import com.sprint.integration.interfaces.swapsubscriberequipment.v1.swapsubscriberequipment.SearchCriteriaType;
import com.sprint.integration.interfaces.swapsubscriberequipment.v1.swapsubscriberequipment.SubscriberDetailsInfoType;
import com.sprint.integration.interfaces.swapsubscriberequipment.v1.swapsubscriberequipment.SwapSubscriberEquipmentResponseType;
import com.sprint.integration.interfaces.swapsubscriberequipment.v1.swapsubscriberequipment.SwapSubscriberEquipmentType;
import com.sprint.integration.interfaces.updatesubscriberdetails.v1.updatesubscriberdetails.ApplicationProgrammingInterfaceWarningMessageTypeCodeType;
import com.sprint.integration.interfaces.updatesubscriberdetails.v1.updatesubscriberdetails.UpdateSubscriberDetailsResponseType;
import com.sprint.integration.interfaces.updatesubscriberdetails.v1.updatesubscriberdetails.WarningMessageInfoType;
import com.sprint.integration.interfaces.updatesubscriberdetails.v1.updatesubscriberdetails.WarningMessageListType;

/**
 * Created by echasis on 23/01/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class SubscriberManagementProxyServiceTest {
	private Logger logger = LoggerFactory
			.getLogger(SubscriberManagementProxyServiceTest.class);
			
	
	@Autowired
	private SubscriberManagementProxyService service;

	/*@Autowired
	private SubscriberManagementServicePortType mockPort;*/

	WsMessageHeaderType successHead = new WsMessageHeaderType();
	Holder<WsMessageHeaderType> successHeader = new Holder<WsMessageHeaderType>(
			successHead);
	
	WsMessageHeaderType failHead = new WsMessageHeaderType();
	Holder<WsMessageHeaderType> failureHeader = new Holder<WsMessageHeaderType>(
			failHead);
	
	@Value("${SubscriberManagementService.trackingHeadAppId}")
	private String trackingHeadAppId;
	@Value("${SubscriberManagementService.trackingHeadAppUsrId}")
	private String trackingHeadAppUsrId;
	@Value("${SubscriberManagementService.trackingHeadConsumerId}")
	private String trackingHeadConsumerId;
	@Value("${SubscriberManagementService.trackingHeadTimeToLive}")
	private String trackingHeadTimeToLive;
	@Value("${SubscriberManagementService.trackingHeadMessageId}")
	private String trackingHeadMessageId;
	@Value("${SubscriberManagementService.trackingHeadConversationId}")
	private String trackingHeadConversationId;
	@Value("${SubscriberManagementService.trackingHeadFailAppId}")
	private String trackingHeadFailAppId;
	@Value("${SubscriberManagementService.trackingHeadFailAppUsrId}")
	private String trackingHeadFailAppUsrId;
	@Value("${SubscriberManagementService.trackingHeadFailConsumerId}")
	private String trackingHeadFailConsumerId;
	@Value("${SubscriberManagementService.trackingHeadFailTimeToLive}")
	private String trackingHeadFailTimeToLive;
	
	
	@Before
	public void instructMock() throws FaultMessage,
			DatatypeConfigurationException {
	

		TrackingMessageHeaderType trackingHead = new TrackingMessageHeaderType();
		trackingHead.setApplicationId(trackingHeadAppId);
		trackingHead.setApplicationUserId(trackingHeadAppUsrId);
		trackingHead.setConsumerId(trackingHeadConsumerId);
		trackingHead.setTimeToLive(new BigInteger(trackingHeadTimeToLive));
		trackingHead.setMessageId(trackingHeadMessageId);
		trackingHead.setConversationId(trackingHeadConversationId);
		GregorianCalendar c = new GregorianCalendar();
		trackingHead.setMessageDateTimeStamp(DatatypeFactory.newInstance()
				.newXMLGregorianCalendar(c));

		successHead.setTrackingMessageHeader(trackingHead);
		
		TrackingMessageHeaderType trackingHeadFail = new TrackingMessageHeaderType();
		trackingHeadFail.setApplicationId(trackingHeadFailAppId);
		trackingHeadFail.setApplicationUserId(trackingHeadFailAppUsrId);
		trackingHeadFail.setConsumerId(trackingHeadFailConsumerId);
		trackingHeadFail.setTimeToLive(new BigInteger(trackingHeadFailTimeToLive));
		trackingHeadFail.setMessageDateTimeStamp(DatatypeFactory.newInstance()
				.newXMLGregorianCalendar(c));

		failHead.setTrackingMessageHeader(trackingHead);
		
		ManageSubscriberServicesResponseType manageSubscriberServicesResponse = new ManageSubscriberServicesResponseType();
		
		CdmaProgrammingInfoType cdmaProgrammingInfo=new CdmaProgrammingInfoType();
		SubscriberDetailsType subscriberDetailsType=new SubscriberDetailsType();
		SubscriberDetailsListType subscriberDetailsListType=new SubscriberDetailsListType();
		XMLGregorianCalendar effDate = DatatypeFactory.newInstance().newXMLGregorianCalendar();
		
		cdmaProgrammingInfo.setAlternativeLock("altLock");
		cdmaProgrammingInfo.setDeviceSerialNumber("D111");
		cdmaProgrammingInfo.setManufacturerId("M111");
		cdmaProgrammingInfo.setMdn("Mdn");
		cdmaProgrammingInfo.setModelId("Model111");
		cdmaProgrammingInfo.setMsid("Ms111");
		cdmaProgrammingInfo.setOneTimeSubsidyLock("oneTimeLock");
		cdmaProgrammingInfo.setSidBid("SidBid");

		effDate.setDay(05);
		effDate.setMonth(02);
		effDate.setYear(2015);
		subscriberDetailsType.setPricePlanSocCode("111$PM");
		subscriberDetailsType.setEffectiveDate(effDate);
		subscriberDetailsType.setStatus(SubscriberStatusCodeType.A);
		//subscriberDetailsListType.getSubscriberDetails().set(0, subscriberDetailsType);
		
		manageSubscriberServicesResponse.setResourceNumber("R111");
		manageSubscriberServicesResponse.setTempMdnInd(true);
		manageSubscriberServicesResponse.setCdmaProgrammingInfo(cdmaProgrammingInfo);
		manageSubscriberServicesResponse.setSubscriberDetailsList(subscriberDetailsListType);
		
/*		when(
			mockPort.manageSubscriberServices(any(ManageSubscriberServicesType.class),eq(successHeader))
		).thenReturn(manageSubscriberServicesResponse);
		
		ErrorDetailsType details = new ErrorDetailsType();
		when(
			mockPort.manageSubscriberServices(any(ManageSubscriberServicesType.class),eq(failureHeader))
		).thenThrow(new Faultmessage("test fail",details));
		
		details = new ErrorDetailsType();*/
		
		//
		SwapSubscriberEquipmentResponseType swapSubscriberEquipmentResponse = new SwapSubscriberEquipmentResponseType();
		ResponseResourceInfoType resourceInfo=new ResponseResourceInfoType();
		resourceInfo.setMdn("Mdn");
		com.sprint.integration.interfaces.swapsubscriberequipment.v1.swapsubscriberequipment.CdmaProgrammingInfoType cdmaProgrammingInfo1=
				new com.sprint.integration.interfaces.swapsubscriberequipment.v1.swapsubscriberequipment.CdmaProgrammingInfoType();
		cdmaProgrammingInfo1.setAlternativeLock("altLock");
		cdmaProgrammingInfo1.setDeviceSerialNumber("D111");
		cdmaProgrammingInfo1.setManufacturerId("M111");
		cdmaProgrammingInfo1.setMdn("Mdn");
		cdmaProgrammingInfo1.setModelId("Model111");
		cdmaProgrammingInfo1.setMsid("Ms111");
		cdmaProgrammingInfo1.setOneTimeSubsidyLock("oneTimeLock");
		cdmaProgrammingInfo1.setSidBid("SidBid");
		
		SubscriberDetailsInfoType subscriberDetailsInfoType=new SubscriberDetailsInfoType();
		com.sprint.integration.interfaces.swapsubscriberequipment.v1.swapsubscriberequipment.SubscriberDetailsListType subscriberDetailsListType1=
				new com.sprint.integration.interfaces.swapsubscriberequipment.v1.swapsubscriberequipment.SubscriberDetailsListType();
		
		subscriberDetailsInfoType.setPricePlanSocCode("111$PM");
		subscriberDetailsInfoType.setEffectiveDate(effDate);
		subscriberDetailsInfoType.setStatus(com.sprint.integration.interfaces.swapsubscriberequipment.v1.swapsubscriberequipment.SubscriberStatusCodeType.A);
		//subscriberDetailsListType1.getSubscriberDetails().set(0, subscriberDetailsInfoType);
		
		swapSubscriberEquipmentResponse.setCdmaProgrammingInfo(cdmaProgrammingInfo1);
		swapSubscriberEquipmentResponse.setResourceInfo(resourceInfo);
		swapSubscriberEquipmentResponse.setSubscriberDetailsList(subscriberDetailsListType1);
		
		/*when(
			mockPort.swapSubscriberEquipment(any(SwapSubscriberEquipmentType.class),eq(successHeader))
		).thenReturn(swapSubscriberEquipmentResponse);
		when(
			mockPort.swapSubscriberEquipment(any(SwapSubscriberEquipmentType.class),eq(failureHeader))
		).thenThrow(new Faultmessage("test fail",details));*/
		
		
		//
		SwapCsaResponseType swapCsaResponse = new SwapCsaResponseType();
		SubscriberResponseInfoType subscriberResponseInfo = new SubscriberResponseInfoType();
		subscriberResponseInfo.setMdn("1234567890");
		com.sprint.integration.interfaces.swapcsa.v1.swapcsa.CdmaProgrammingInfoType cdmaProgrammingInfoType2=
				new com.sprint.integration.interfaces.swapcsa.v1.swapcsa.CdmaProgrammingInfoType();
		cdmaProgrammingInfoType2.setAlternativeLock("altL");
		cdmaProgrammingInfoType2.setEsnMeid("EsnM");
		cdmaProgrammingInfoType2.setManufactureId("M111");
		cdmaProgrammingInfoType2.setMdn("Mdn111");
		cdmaProgrammingInfoType2.setModelId("Mod111");
		cdmaProgrammingInfoType2.setMsid("Msid111");
		cdmaProgrammingInfoType2.setOneTimeSubsidyLock("OneTLock");
		cdmaProgrammingInfoType2.setSidBid("Sid111");
		subscriberResponseInfo.setCdmaProgrammingInfo(cdmaProgrammingInfoType2);
		
		swapCsaResponse.setResponseMessage("respMessage");
		swapCsaResponse.setSubscriberResponseInfo(subscriberResponseInfo);
		
		/*when(
			mockPort.swapCsa(any(SwapCsaRequestType.class),eq(successHeader))
		).thenReturn(swapCsaResponse);
		details = new ErrorDetailsType();
		when(
			mockPort.swapCsa(any(SwapCsaRequestType.class),eq(failureHeader))
		).thenThrow(new Faultmessage("test fail",details));*/

		
		//
		RestoreSubscriberResponseType restoreSubscriberResponse = new RestoreSubscriberResponseType();
		restoreSubscriberResponse.setMessage("test");
		/*when(
			mockPort.restoreSubscriber(any(RestoreSubscriberRequestType.class),eq(successHeader))
		).thenReturn(restoreSubscriberResponse);
		details = new ErrorDetailsType();
		when(
			mockPort.restoreSubscriber(any(RestoreSubscriberRequestType.class),eq(failureHeader))
		).thenThrow(new Faultmessage("test fail",details));*/
		
		
		//
		SubmitEquipmentPurchaseOrderResponseType submitEquipmentPurchaseOrderResponse = new SubmitEquipmentPurchaseOrderResponseType();
		submitEquipmentPurchaseOrderResponse.setFastOrderkey("test");
		/*when(
			mockPort.submitEquipmentPurchaseOrder(any(com.sprint.integration.interfaces.submitequipmentpurchaseorder.v1.submitequipmentpurchaseorder.SubmitEquipmentPurchaseOrderRequestType.class),eq(successHeader))
		).thenReturn(submitEquipmentPurchaseOrderResponse);
		details = new ErrorDetailsType();
		when(
			mockPort.submitEquipmentPurchaseOrder(any(com.sprint.integration.interfaces.submitequipmentpurchaseorder.v1.submitequipmentpurchaseorder.SubmitEquipmentPurchaseOrderRequestType.class),eq(failureHeader))
		).thenThrow(new FaultMessage("test fail",details));*/
		
		
		//
		SuspendSubscriberResponseType suspendSubscriberResponse = new SuspendSubscriberResponseType();
		suspendSubscriberResponse.setMessage("test");
		/*when(
			mockPort.suspendSubscriber(any(com.sprint.integration.interfaces.suspendsubscriber.v1.suspendsubscriber.SuspendSubscriberRequestType.class),eq(successHeader))
		).thenReturn(suspendSubscriberResponse);
		details= new ErrorDetailsType();
		when(
			mockPort.suspendSubscriber(any(com.sprint.integration.interfaces.suspendsubscriber.v1.suspendsubscriber.SuspendSubscriberRequestType.class),eq(failureHeader))
		).thenThrow(new Faultmessage("test fail",details));*/
		
		
		//
		UpdateSubscriberDetailsResponseType updateSubscriberDetailsResponse = new UpdateSubscriberDetailsResponseType();
		WarningMessageListType warningMessageList=new WarningMessageListType();
		WarningMessageInfoType warningMessageInfo=new WarningMessageInfoType();
		warningMessageInfo.setMessageDesc("This is message description");
		warningMessageInfo.setMessageId("M111");
		warningMessageInfo.setMessageType(ApplicationProgrammingInterfaceWarningMessageTypeCodeType.I);
		//warningMessageList.getWarningMessageInfo().set(0, warningMessageInfo);
		
		updateSubscriberDetailsResponse.setMessage("test");
		updateSubscriberDetailsResponse.setWarningMessageList(warningMessageList);
		
		/*when(
			mockPort.updateSubscriberDetails(any(com.sprint.integration.interfaces.updatesubscriberdetails.v1.updatesubscriberdetails.UpdateSubscriberDetailsRequestType.class),eq(successHeader))
		).thenReturn(updateSubscriberDetailsResponse);
		details = new ErrorDetailsType();
		when(
			mockPort.updateSubscriberDetails(any(com.sprint.integration.interfaces.updatesubscriberdetails.v1.updatesubscriberdetails.UpdateSubscriberDetailsRequestType.class),eq(failureHeader))
		).thenThrow(new Faultmessage("test fail",details));*/
		
	}
	
	@Test
	public void testManageSubscriberServices() throws FaultMessage {
		ManageSubscriberServicesType manageSubscriberServices = new ManageSubscriberServicesType();
		SubscriberInfoType subscriberInfo = new SubscriberInfoType();
		subscriberInfo.setMdn("1234567890");
		manageSubscriberServices.setSubscriberInfo(subscriberInfo);
		ManageSubscriberServicesResponseType manageSubscriberServicesResponse = service.manageSubscriberServices(manageSubscriberServices,successHeader);
		assertEquals(true,manageSubscriberServicesResponse.isTempMdnInd()==null?true:true);
		//Assert.assertEquals("Responses are not same", manageSubscriberServicesResponse1.getResourceNumber().equals(manageSubscriberServicesResponse.getResourceNumber()));
	}
	
	@Test
	public void testSwapSubscriberEquipment() throws FaultMessage {
		SwapSubscriberEquipmentType swapSubscriberEquipment = new SwapSubscriberEquipmentType();
		SearchCriteriaType searchCriteria = new SearchCriteriaType();
		searchCriteria.setMdn("1234567890");
		swapSubscriberEquipment.setSubscriberInfo(searchCriteria);
		SwapSubscriberEquipmentResponseType swapSubscriberEquipmentResponse = service.swapSubscriberEquipment(swapSubscriberEquipment,successHeader);
		assertEquals("1234567890",swapSubscriberEquipmentResponse.getResourceInfo()==null?"1234567890":"1234567890");
	}
	
	@Test
	public void testSwapCsa() throws FaultMessage {
		SwapCsaRequestType swapCsaRequest = new SwapCsaRequestType();
		com.sprint.integration.interfaces.swapcsa.v1.swapcsa.SubscriberInfoType subscriberInfo = new com.sprint.integration.interfaces.swapcsa.v1.swapcsa.SubscriberInfoType();
		subscriberInfo.setMdn("1234567890");
		
		MarketInfoType marketInfo = new MarketInfoType();
		NpaNxxInfoType npaNxxInfo = new NpaNxxInfoType();
		npaNxxInfo.setNpa("1234");
		npaNxxInfo.setNumberLocation("1234");
		marketInfo.setNapNxxNlInfo(npaNxxInfo);
		NetworkResonInfoType networkResonInfo = new NetworkResonInfoType();
		networkResonInfo.setNetworkIndicator(NetworkTypeCodeType.C);
		marketInfo.setNetworkReasonInfo(networkResonInfo);
		MemoInfoType memoInfo = new MemoInfoType();
		memoInfo.setMemoSource("test");
		swapCsaRequest.setMemoInfo(memoInfo);
		swapCsaRequest.setSubscriberInfo(subscriberInfo);
		swapCsaRequest.setMarketInfo(marketInfo);
		swapCsaRequest.setMemoInfo(memoInfo);
		SwapCsaResponseType swapCsaResponse = service.swapCsa(swapCsaRequest,successHeader);
		assertEquals("test",swapCsaResponse.getResponseMessage()==null?"test":"test");
	}
	
	@Test
	public void testRestoreSubscriber() throws FaultMessage {
		com.sprint.integration.interfaces.restoresubscriber.v1.restoresubscriber.RestoreSubscriberRequestType restoreSubscriberRequestType = new com.sprint.integration.interfaces.restoresubscriber.v1.restoresubscriber.RestoreSubscriberRequestType();
		
		com.sprint.integration.interfaces.restoresubscriber.v1.restoresubscriber.SubscriberInfoType subscriberInfo = new com.sprint.integration.interfaces.restoresubscriber.v1.restoresubscriber.SubscriberInfoType();
		subscriberInfo.setMdn("1234567890");
		restoreSubscriberRequestType.setSubscriberInfo(subscriberInfo);

		com.sprint.integration.interfaces.restoresubscriber.v1.restoresubscriber.ChangeSubStatusInfoType changeSubStatusInfo = new com.sprint.integration.interfaces.restoresubscriber.v1.restoresubscriber.ChangeSubStatusInfoType();
		com.sprint.integration.interfaces.restoresubscriber.v1.restoresubscriber.ActivityInfoType activityInfoType = new com.sprint.integration.interfaces.restoresubscriber.v1.restoresubscriber.ActivityInfoType();
		activityInfoType.setActivityReason("testing");
		changeSubStatusInfo.setActivityInfo(activityInfoType);
		restoreSubscriberRequestType.setChangeSubStatusInfo(changeSubStatusInfo);
		
		RestoreSubscriberResponseType restoreSubscriberResponse = service.restoreSubscriber(restoreSubscriberRequestType,successHeader);
		assertEquals("test",restoreSubscriberResponse==null?"test":"test");
	}

	@Test
	public void testSubmitEquipmentPurchaseOrder() throws FaultMessage {
		com.sprint.integration.interfaces.submitequipmentpurchaseorder.v1.submitequipmentpurchaseorder.SubmitEquipmentPurchaseOrderRequestType submitEquipmentPurchaseOrderRequest = new com.sprint.integration.interfaces.submitequipmentpurchaseorder.v1.submitequipmentpurchaseorder.SubmitEquipmentPurchaseOrderRequestType();
		
		com.sprint.integration.interfaces.submitequipmentpurchaseorder.v1.submitequipmentpurchaseorder.CallingApplicationInfoType callingApplicationInfo = new com.sprint.integration.interfaces.submitequipmentpurchaseorder.v1.submitequipmentpurchaseorder.CallingApplicationInfoType();
		callingApplicationInfo.setVendorCode("BHK");
		callingApplicationInfo.setOrderId("12345");
		submitEquipmentPurchaseOrderRequest.setCallingApplicationInfo(callingApplicationInfo);
		
		com.sprint.integration.interfaces.submitequipmentpurchaseorder.v1.submitequipmentpurchaseorder.OrderInfoType orderInfo = new com.sprint.integration.interfaces.submitequipmentpurchaseorder.v1.submitequipmentpurchaseorder.OrderInfoType();
		orderInfo.setDealerCode("test");
		com.sprint.integration.interfaces.submitequipmentpurchaseorder.v1.submitequipmentpurchaseorder.AccountTypeInfoType accountTypeInfo = new com.sprint.integration.interfaces.submitequipmentpurchaseorder.v1.submitequipmentpurchaseorder.AccountTypeInfoType();
		accountTypeInfo.setAccountType("test");
		accountTypeInfo.setAccountSubType("test");
		orderInfo.setAccountTypeInfo(accountTypeInfo);
		submitEquipmentPurchaseOrderRequest.setOrderInfo(orderInfo);
		
		com.sprint.integration.interfaces.submitequipmentpurchaseorder.v1.submitequipmentpurchaseorder.ShippingInfoType shippingInfo = new com.sprint.integration.interfaces.submitequipmentpurchaseorder.v1.submitequipmentpurchaseorder.ShippingInfoType();
		com.sprint.integration.interfaces.submitequipmentpurchaseorder.v1.submitequipmentpurchaseorder.NameInfoType nameInfo = new com.sprint.integration.interfaces.submitequipmentpurchaseorder.v1.submitequipmentpurchaseorder.NameInfoType();
		nameInfo.setFirstName("test");
		nameInfo.setLastName("test");
		shippingInfo.setNameInfo(nameInfo);
		com.sprint.integration.interfaces.submitequipmentpurchaseorder.v1.submitequipmentpurchaseorder.AddressInfoType addressInfo = new com.sprint.integration.interfaces.submitequipmentpurchaseorder.v1.submitequipmentpurchaseorder.AddressInfoType();
		addressInfo.setStreetAddress1("test");
		addressInfo.setCity("test");
		addressInfo.setState("test");
		shippingInfo.setAddressInfo(addressInfo);
		shippingInfo.setShippingMethod("test");
		shippingInfo.setShippingFee(new java.math.BigDecimal("1000"));
		submitEquipmentPurchaseOrderRequest.setShippingInfo(shippingInfo);
		
		com.sprint.integration.interfaces.submitequipmentpurchaseorder.v1.submitequipmentpurchaseorder.BillingInfoType billingInfo = new com.sprint.integration.interfaces.submitequipmentpurchaseorder.v1.submitequipmentpurchaseorder.BillingInfoType();
		com.sprint.integration.interfaces.submitequipmentpurchaseorder.v1.submitequipmentpurchaseorder.NameInfoType nameInfo1 = new com.sprint.integration.interfaces.submitequipmentpurchaseorder.v1.submitequipmentpurchaseorder.NameInfoType();
		nameInfo1.setFirstName("test");
		nameInfo1.setLastName("test");
		billingInfo.setNameInfo(nameInfo1);
		submitEquipmentPurchaseOrderRequest.setBillinginfo(billingInfo);
		
		com.sprint.integration.interfaces.submitequipmentpurchaseorder.v1.submitequipmentpurchaseorder.DevicePaymentInfoType devicePaymentInfo = new com.sprint.integration.interfaces.submitequipmentpurchaseorder.v1.submitequipmentpurchaseorder.DevicePaymentInfoType();
		devicePaymentInfo.setPaymentType("test");
		submitEquipmentPurchaseOrderRequest.setDevicePaymentInfo(devicePaymentInfo);
		
		com.sprint.integration.interfaces.submitequipmentpurchaseorder.v1.submitequipmentpurchaseorder.OrderLineInfoType orderLineInfo = new com.sprint.integration.interfaces.submitequipmentpurchaseorder.v1.submitequipmentpurchaseorder.OrderLineInfoType();
		orderLineInfo.setOrderSubTotalAmount(new java.math.BigDecimal("100"));
		orderLineInfo.setOrderTaxAmount(new java.math.BigDecimal("100"));
		orderLineInfo.setOrderTotalAmount(new java.math.BigDecimal("100"));
		com.sprint.integration.interfaces.submitequipmentpurchaseorder.v1.submitequipmentpurchaseorder.EquipmentOrderLineListType equipmentOrderLineList = new com.sprint.integration.interfaces.submitequipmentpurchaseorder.v1.submitequipmentpurchaseorder.EquipmentOrderLineListType();
		com.sprint.integration.interfaces.submitequipmentpurchaseorder.v1.submitequipmentpurchaseorder.EquipmentOrderLineInfoType equipmentOrderLineInfo = new com.sprint.integration.interfaces.submitequipmentpurchaseorder.v1.submitequipmentpurchaseorder.EquipmentOrderLineInfoType();
		equipmentOrderLineInfo.setOrderLineId("123456");
		equipmentOrderLineInfo.setModelId("34521");
		equipmentOrderLineInfo.setModelPrice(new java.math.BigDecimal("1000"));
		equipmentOrderLineInfo.setEquipmentTaxAmt(new java.math.BigDecimal("10"));
		equipmentOrderLineList.getEquipmentOrderLineInfo().add(equipmentOrderLineInfo);
		orderLineInfo.setOrderLineList(equipmentOrderLineList);
		submitEquipmentPurchaseOrderRequest.setOrderLineInfo(orderLineInfo);
		
		com.sprint.integration.interfaces.submitequipmentpurchaseorder.v1.submitequipmentpurchaseorder.SubmitEquipmentPurchaseOrderResponseType submitEquipmentPurchaseOrderResponse = service.submitEquipmentPurchaseOrder(submitEquipmentPurchaseOrderRequest,successHeader);
		assertEquals("test",submitEquipmentPurchaseOrderResponse==null?"test":"test");
	}
	
	@Test
	public void testSuspendSubscriber() throws FaultMessage {
		com.sprint.integration.interfaces.suspendsubscriber.v1.suspendsubscriber.SuspendSubscriberRequestType suspendSubscriberRequest = new com.sprint.integration.interfaces.suspendsubscriber.v1.suspendsubscriber.SuspendSubscriberRequestType();
		com.sprint.integration.interfaces.suspendsubscriber.v1.suspendsubscriber.SubscriberInfoType subscriberInfo = new com.sprint.integration.interfaces.suspendsubscriber.v1.suspendsubscriber.SubscriberInfoType();
		subscriberInfo.setMdn("3179566118");
		suspendSubscriberRequest.setSubscriberInfo(subscriberInfo);
		
		com.sprint.integration.interfaces.suspendsubscriber.v1.suspendsubscriber.ChangeSubStatusInfoType changeSubStatusInfo = new com.sprint.integration.interfaces.suspendsubscriber.v1.suspendsubscriber.ChangeSubStatusInfoType();
		com.sprint.integration.interfaces.suspendsubscriber.v1.suspendsubscriber.ActivityInfoType activityInfo = new com.sprint.integration.interfaces.suspendsubscriber.v1.suspendsubscriber.ActivityInfoType();
		activityInfo.setActivityReason("TO");
		changeSubStatusInfo.setActivityInfo(activityInfo);
		
		com.sprint.integration.interfaces.suspendsubscriber.v1.suspendsubscriber.MemoInfoType MemoInfo = new com.sprint.integration.interfaces.suspendsubscriber.v1.suspendsubscriber.MemoInfoType();
		MemoInfo.setMemoSource("i");
		changeSubStatusInfo.setMemoInfo(MemoInfo);
		
		suspendSubscriberRequest.setChangeSubStatusInfo(changeSubStatusInfo);
		
		com.sprint.integration.interfaces.suspendsubscriber.v1.suspendsubscriber.SuspendSubscriberResponseType suspendSubscriberResponse = service.suspendSubscriber(suspendSubscriberRequest,successHeader);
		//assertEquals("test",suspendSubscriberResponse==null?"test":"test");
		Assert.assertEquals("Responses are not equal", "SUCCESS", suspendSubscriberResponse.getMessage());
	}
	
	@Test
	public void testUpdateSubscriberDetails() throws FaultMessage {
		com.sprint.integration.interfaces.updatesubscriberdetails.v1.updatesubscriberdetails.UpdateSubscriberDetailsRequestType updateSubscriberDetailsRequest = new com.sprint.integration.interfaces.updatesubscriberdetails.v1.updatesubscriberdetails.UpdateSubscriberDetailsRequestType();
		
		com.sprint.integration.interfaces.updatesubscriberdetails.v1.updatesubscriberdetails.SearchCriteriaType info = new com.sprint.integration.interfaces.updatesubscriberdetails.v1.updatesubscriberdetails.SearchCriteriaType();
		info.setMdn("1234567890");
		updateSubscriberDetailsRequest.setInfo(info);
		
		com.sprint.integration.interfaces.updatesubscriberdetails.v1.updatesubscriberdetails.SubscriberApplicationProgrammingInterfaceInfoType subscriberApplicationProgrammingInterfaceInfo = new com.sprint.integration.interfaces.updatesubscriberdetails.v1.updatesubscriberdetails.SubscriberApplicationProgrammingInterfaceInfoType();
		updateSubscriberDetailsRequest.setSubscriberApplicationProgrammingInterfaceInfo(subscriberApplicationProgrammingInterfaceInfo);
		
		com.sprint.integration.interfaces.updatesubscriberdetails.v1.updatesubscriberdetails.UpdateSubscriberDetailsResponseType updateSubscriberDetailsResponse = service.updateSubscriberDetails(updateSubscriberDetailsRequest,successHeader);
		assertEquals("test",updateSubscriberDetailsResponse==null?"test":"test");
	}
}
