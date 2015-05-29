package com.ericsson.cac.sprint.adapters;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

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
import com.sprint.integration.eai.services.prepaidaccountmanagementservice.v1.prepaidaccountmanagementservice.PrepaidAccountManagementPortType;
import com.sprint.integration.eai.services.prepaidaccountmanagementservice.v1.prepaidaccountmanagementservice.SoapFault;
import com.sprint.integration.interfaces.manageadvancedpayment.v1.manageadvancedpayment.ManageAdvancedPaymentRequestType;
import com.sprint.integration.interfaces.manageadvancedpayment.v1.manageadvancedpayment.ManageAdvancedPaymentResponseType;
import com.sprint.integration.interfaces.manageadvancedpayment.v1.manageadvancedpayment.SearchCriteriaType;
import com.sprint.integration.interfaces.rechargeaccountbycash.v1.rechargeaccountbycash.ChannelInfo;
import com.sprint.integration.interfaces.rechargeaccountbycash.v1.rechargeaccountbycash.RechargeAccountByCashRequest;
import com.sprint.integration.interfaces.rechargeaccountbycash.v1.rechargeaccountbycash.RechargeAccountByCashResponse;
import com.sprint.integration.interfaces.rechargeaccountbycash.v1.rechargeaccountbycash.RechargeAmountsInfo;
import com.sprint.integration.interfaces.rechargeaccountbycash.v1.rechargeaccountbycash.RechargeCashMethodInfo;
import com.sprint.integration.interfaces.rechargeaccountbycash.v1.rechargeaccountbycash.SubscriberInfo;
import com.sprint.integration.interfaces.rechargeaccountbycash.v1.rechargeaccountbycash.SubscriberPrimaryResourceInfo;
import com.sprint.integration.interfaces.rechargeaccountbyvoucher.v1.rechargeaccountbyvoucher.ChannelInfoType;
import com.sprint.integration.interfaces.rechargeaccountbyvoucher.v1.rechargeaccountbyvoucher.RechargeAccountByVoucherRequestType;
import com.sprint.integration.interfaces.rechargeaccountbyvoucher.v1.rechargeaccountbyvoucher.RechargeAccountByVoucherResponseType;
import com.sprint.integration.interfaces.rechargeaccountbyvoucher.v1.rechargeaccountbyvoucher.RechargeAmountInfoType;
import com.sprint.integration.interfaces.rechargeaccountbyvoucher.v1.rechargeaccountbyvoucher.RechargeVoucherMethodInfoType;
import com.sprint.integration.interfaces.rechargeaccountbyvoucher.v1.rechargeaccountbyvoucher.SubscriberInfoType;
import com.sprint.integration.interfaces.restartplan.v1.restartplan.RestartPlanRequestType;
import com.sprint.integration.interfaces.restartplan.v1.restartplan.RestartPlanResponseType;




/**
 * Created by esvwxal on 21/01/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class PrepaidAccountManagementProxyServiceTest {
    private Logger logger = LoggerFactory.getLogger(PrepaidAccountManagementProxyServiceTest.class);
    
    @Autowired
    private PrepaidAccountManagementProxyService service;

    @Autowired
    private PrepaidAccountManagementPortType mockPort;
    
    
    private ManageAdvancedPaymentResponseType manageAdvancedPaymentResponse = null;
    private RechargeAccountByCashResponse rechargeAccountByCashResponse = null;
    private RechargeAccountByVoucherResponseType rechargeAccountByVoucherResponse= null;
    private RestartPlanResponseType restartPlanResponse = null;

    WsMessageHeaderType successHead = new WsMessageHeaderType();
	Holder<WsMessageHeaderType> successHeader = new Holder<WsMessageHeaderType>(
			successHead);
	
    WsMessageHeaderType failHead = new WsMessageHeaderType();
	Holder<WsMessageHeaderType> failureHeader = new Holder<WsMessageHeaderType>(
			failHead);
	@Value("${PrepaidAccountManagementService.trackingHeadAppId}")
	private String trackingHeadAppId;
	@Value("${PrepaidAccountManagementService.trackingHeadAppUsrId}")
	private String trackingHeadAppUsrId;
	@Value("${PrepaidAccountManagementService.trackingHeadConsumerId}")
	private String trackingHeadConsumerId;
	@Value("${PrepaidAccountManagementService.trackingHeadTimeToLive}")
	private String trackingHeadTimeToLive;
	@Value("${PrepaidAccountManagementService.trackingHeadMessageId}")
	private String trackingHeadMessageId;
	@Value("${PrepaidAccountManagementService.trackingHeadConversationId}")
	private String trackingHeadConversationId;
	@Value("${PrepaidAccountManagementService.trackingHeadFailAppId}")
	private String trackingHeadFailAppId;
	@Value("${PrepaidAccountManagementService.trackingHeadFailAppUsrId}")
	private String trackingHeadFailAppUsrId;
	@Value("${PrepaidAccountManagementService.trackingHeadFailConsumerId}")
	private String trackingHeadFailConsumerId;
	@Value("${PrepaidAccountManagementService.trackingHeadFailTimeToLive}")
	private String trackingHeadFailTimeToLive;

    @Before
    public void instructMock() throws SoapFault, DatatypeConfigurationException{

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


       
	ErrorDetailsType details = new ErrorDetailsType();
	manageAdvancedPaymentResponse = new ManageAdvancedPaymentResponseType(); 
	manageAdvancedPaymentResponse.setMessage("abcdef");		
	when(mockPort.manageAdvancedPayment(any(ManageAdvancedPaymentRequestType.class), eq(successHeader))).thenReturn(manageAdvancedPaymentResponse);
	when(mockPort.manageAdvancedPayment(any(ManageAdvancedPaymentRequestType.class),eq(failureHeader))).thenThrow(new SoapFault("test fail", details));
	
	
	
	rechargeAccountByCashResponse = new RechargeAccountByCashResponse();
	RechargeAmountsInfo rechargeAmountsInfo= new RechargeAmountsInfo();
	rechargeAmountsInfo.setInputCurrency("$");
	rechargeAmountsInfo.setInputAmount(100.00);
	rechargeAccountByCashResponse.setRechargeAmountsInfo(rechargeAmountsInfo);
	when(mockPort.rechargeAccountByCash(any(RechargeAccountByCashRequest.class), eq(successHeader))).thenReturn(rechargeAccountByCashResponse);
	when(mockPort.rechargeAccountByCash(any(RechargeAccountByCashRequest.class),eq(failureHeader))).thenThrow(new SoapFault("test fail", details));
	
	
	
	rechargeAccountByVoucherResponse = new RechargeAccountByVoucherResponseType();
	RechargeAmountInfoType rechargeAmountInfo = new RechargeAmountInfoType();
	rechargeAmountInfo.setInputAmount(100.00);
	rechargeAmountInfo.setInputCurrency("$");
	rechargeAccountByVoucherResponse.setRechargeAmountsInfo(rechargeAmountInfo);
	when(mockPort.rechargeAccountByVoucher(any(RechargeAccountByVoucherRequestType.class), eq(successHeader))).thenReturn(rechargeAccountByVoucherResponse);
	when(mockPort.rechargeAccountByVoucher(any(RechargeAccountByVoucherRequestType.class),eq(failureHeader))).thenThrow(new SoapFault("test fail", details));
	
	
	restartPlanResponse = new RestartPlanResponseType();
	restartPlanResponse.setMessage("abcdfgh");
	when(mockPort.restartPlan(any(RestartPlanRequestType.class), eq(successHeader))).thenReturn(restartPlanResponse);
	when(mockPort.restartPlan(any(RestartPlanRequestType.class),eq(failureHeader))).thenThrow(new SoapFault("test fail", details));
	
	}
    
    
    
    


    @Test
    public void testRanageAdvancedPayment() throws SoapFault {
    	
    	ManageAdvancedPaymentRequestType manageAdvancedPaymentRequest = new ManageAdvancedPaymentRequestType();
    	manageAdvancedPaymentRequest.setActionInd("1234");
    	SearchCriteriaType searchCriteria = new SearchCriteriaType();
    	searchCriteria.setSubscriberId("1234");
    	manageAdvancedPaymentRequest.setSubscriberInfo(searchCriteria);
    	
    	ManageAdvancedPaymentResponseType manageAdvancedPaymentResponse2 = service.manageAdvancedPayment(manageAdvancedPaymentRequest, successHeader);
    	Assert.assertEquals("Responses are not same", manageAdvancedPaymentResponse2.getMessage().equals(manageAdvancedPaymentResponse.getMessage()));
   
    }
    
    @Test
    public void testRechargeAccountByCash() throws SoapFault {
    	
    	RechargeAccountByCashRequest rechargeAccountByCashRequest = new RechargeAccountByCashRequest();
    	SubscriberInfo subscriberInfo = new SubscriberInfo();
    	subscriberInfo.setMdn("12345");
    	SubscriberPrimaryResourceInfo subscriberPrimaryResourceInfo = new SubscriberPrimaryResourceInfo();
    	subscriberPrimaryResourceInfo.setSubscriberInfo(subscriberInfo);
    	rechargeAccountByCashRequest.setSubscriberPrimaryResourceInfo(subscriberPrimaryResourceInfo);
    	
    	ChannelInfo channelInfo = new ChannelInfo();
    	channelInfo.setReferenceId("1234");
    	channelInfo.setMerchantId("5678");
    	channelInfo.setRetailerId("9876");
    	channelInfo.setTspTransactionId("9877");
    	rechargeAccountByCashRequest.setChannelInfo(channelInfo);
    	
    	RechargeCashMethodInfo rechargeCashMethodInfo = new RechargeCashMethodInfo();
    	rechargeCashMethodInfo.setCurrency("$");
    	
    	rechargeAccountByCashRequest.setRechargeCashMethodInfo(rechargeCashMethodInfo);
    	
    	RechargeAccountByCashResponse rechargeAccountByCashResponse2 = service.rechargeAccountByCash(rechargeAccountByCashRequest, successHeader);
    	Assert.assertEquals("Responses are not same", rechargeAccountByCashResponse2.getRechargeAmountsInfo().getInputAmount().equals(rechargeAccountByCashResponse.getRechargeAmountsInfo().getInputAmount()));
    }
    
    @Test
    public void testRechargeAccountByVoucher() throws SoapFault {
    	
    	RechargeAccountByVoucherRequestType rechargeAccountByVoucherRequest = new RechargeAccountByVoucherRequestType();
    	SubscriberInfoType subscriberInfo =  new SubscriberInfoType();
    	subscriberInfo.setMdn("6789");
    	rechargeAccountByVoucherRequest.setSubscriberInfo(subscriberInfo);
    	
    	ChannelInfoType channelInfo = new ChannelInfoType();
    	channelInfo.setReferenceId("6789");
    	rechargeAccountByVoucherRequest.setChannelInfo(channelInfo);
    	
    	RechargeVoucherMethodInfoType rechargeVoucherMethodInfo = new RechargeVoucherMethodInfoType();
    	rechargeVoucherMethodInfo.setCoverDebitInd(true);
    	rechargeAccountByVoucherRequest.setRechargeVoucherMethodInfo(rechargeVoucherMethodInfo);
    	
    	RechargeAccountByVoucherResponseType rechargeAccountByVoucherResponse2 = service.rechargeAccountByVoucher(rechargeAccountByVoucherRequest, successHeader);
    	Assert.assertEquals("Responses are not same", rechargeAccountByVoucherResponse2.getRechargeAmountsInfo().getInputAmount().equals(rechargeAccountByVoucherResponse.getRechargeAmountsInfo().getInputAmount()));
    	
    	}
    
    @Test
    public void testRestartPlan() throws SoapFault {
    	
    	RestartPlanRequestType restartPlanRequest = new RestartPlanRequestType();
    	com.sprint.integration.interfaces.restartplan.v1.restartplan.SearchCriteriaType searchCriteria = new com.sprint.integration.interfaces.restartplan.v1.restartplan.SearchCriteriaType();
    	searchCriteria.setMdn("5677");
    	searchCriteria.setSubscriberId("7890");
    	restartPlanRequest.setSubscriberInfo(searchCriteria);
    	
    	logger.info("*&^%$#@!*&^%$#@!"+restartPlanRequest.getSubscriberInfo().getSubscriberId());
    	
    	RestartPlanResponseType RestartPlanResponse2 = service.restartPlan(restartPlanRequest, successHeader);
    	Assert.assertEquals("Responses are not same", RestartPlanResponse2.getMessage().equals(restartPlanResponse.getMessage()));
    	  	
    }
    
    
    
  }
