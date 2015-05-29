package com.ericsson.cac.sprint.shop.workflow.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.amdocs.dc.api.sensitivedetails.BillingAddressType;
import com.amdocs.dc.api.sensitivedetails.CardSensitiveDetails;
import com.amdocs.mfs.api.epp.v1.sprint.baseoperationrequest.CardType;
import com.amdocs.mfs.api.epp.v1.sprint.baseoperationrequest.PaymentRequestDetailBO;
import com.amdocs.mfs.api.epp.v1.sprint.baseoperationrequest.TokenTypeConstants;
import com.amdocs.mfs.api.epp.v1.sprint.baseoperationrequest.TransactionForConstants;
import com.amdocs.mfs.api.epp.v1.sprint.baseoperationresponse.Response;
import com.amdocs.mfs.api.epp.v1.sprint.paymentcommontypes.ActorApp;
import com.amdocs.mfs.api.epp.v1.sprint.paymentcommontypes.Address;
import com.amdocs.mfs.api.epp.v1.sprint.paymentcommontypes.AmountInfo;
import com.amdocs.mfs.api.epp.v1.sprint.paymentoperationinnerrequestresponsetypes.AuthorizePaymentRequest;
import com.amdocs.mfs.api.epp.v1.sprint.paymentoperationinnerrequestresponsetypes.AuthorizePaymentResponse;
import com.amdocs.mfs.api.epp.v1.sprint.paymentoperationinnerrequestresponsetypes.CancelAuthorizePaymentRequest;
import com.amdocs.mfs.api.epp.v1.sprint.paymentoperationinnerrequestresponsetypes.CancelAuthorizePaymentResponse;
import com.amdocs.mfs.api.epp.v1.sprint.paymentoperationinnerrequestresponsetypes.CapturePaymentRequest;
import com.amdocs.mfs.api.epp.v1.sprint.paymentoperationinnerrequestresponsetypes.CapturePaymentResponse;
import com.amdocs.mfs.api.epp.v1.sprint.paymentoperationinnerrequestresponsetypes.GeneratePreOrderTokenRequest;
import com.amdocs.mfs.api.epp.v1.sprint.paymentoperationinnerrequestresponsetypes.GeneratePreOrderTokenResponse;
import com.amdocs.mfs.api.epp.v1.sprint.paymentoperationinnerrequestresponsetypes.PreOrderTokenRequestBO;
import com.amdocs.mfs.api.epp.v1.sprint.walletbaseoperationrequest.ValidatePaymentOption;
import com.amdocs.mfs.api.epp.v1.sprint.walletbaseoperationresponse.FailureResponse;
import com.amdocs.mfs.api.epp.v1.sprint.walletbaseoperationresponse.PayLimitPaymentType;
import com.amdocs.mfs.api.epp.v1.sprint.walletbaseoperationresponse.PaymentMinMaxAmount;
import com.amdocs.mfs.api.epp.v1.sprint.walletbaseoperationresponse.PaymentMinMaxAmounts;
import com.amdocs.mfs.api.epp.v1.sprint.walletcommontypes.PaymentMethod;
import com.amdocs.mfs.api.epp.v1.sprint.walletoperationinnerrequestresponsetypes.ValidatePaymentOptionRequest;
import com.amdocs.mfs.api.epp.v1.sprint.walletoperationinnerrequestresponsetypes.ValidatePaymentOptionResponse;
import com.ericsson.cac.sprint.adapters.EPPPaymentManagementProxyService;
import com.ericsson.cac.sprint.adapters.EPPWalletManagementProxyService;
import com.ericsson.cac.sprint.adapters.hazelcast.HazelHelper;
import com.ericsson.cac.sprint.shop.constants.ShopWorkFlowConstants;
import com.ericsson.cac.sprint.shop.exceptions.ShopWorkFlowException;
import com.ericsson.cac.sprint.shop.workflow.ShopEppWorkFlow;
import com.ericsson.cac.sprint.shop.workflow.datamodel.AddressInfo;
import com.ericsson.cac.sprint.shop.workflow.datamodel.AuthorizeShopPaymentRequest;
import com.ericsson.cac.sprint.shop.workflow.datamodel.AuthorizeShopPaymentResponse;
import com.ericsson.cac.sprint.shop.workflow.datamodel.CalculateTaxRequest;
import com.ericsson.cac.sprint.shop.workflow.datamodel.CalculateTaxResponse;
import com.ericsson.cac.sprint.shop.workflow.datamodel.CancelAuthorizeShopRequest;
import com.ericsson.cac.sprint.shop.workflow.datamodel.CancelAuthorizeShopResponse;
import com.ericsson.cac.sprint.shop.workflow.datamodel.CaptureShopPaymentRequest;
import com.ericsson.cac.sprint.shop.workflow.datamodel.CaptureShopPaymentResponse;
import com.ericsson.cac.sprint.shop.workflow.datamodel.ChannelPolicy;
import com.ericsson.cac.sprint.shop.workflow.datamodel.CompletePurchaseRequest;
import com.ericsson.cac.sprint.shop.workflow.datamodel.CompletePurchaseResponse;
import com.ericsson.cac.sprint.shop.workflow.datamodel.CoverageAreaRequest;
import com.ericsson.cac.sprint.shop.workflow.datamodel.CoverageAreaResponse;
import com.ericsson.cac.sprint.shop.workflow.datamodel.EPPPaymentInfo;
import com.ericsson.cac.sprint.shop.workflow.datamodel.Equipment;
import com.ericsson.cac.sprint.shop.workflow.datamodel.EquipmentAvailabilityRequest;
import com.ericsson.cac.sprint.shop.workflow.datamodel.EquipmentAvailabilityResponse;
import com.ericsson.cac.sprint.shop.workflow.datamodel.GenerateShopTokenRequest;
import com.ericsson.cac.sprint.shop.workflow.datamodel.GenerateShopTokenResponse;
import com.ericsson.cac.sprint.shop.workflow.datamodel.Order;
import com.ericsson.cac.sprint.shop.workflow.datamodel.PaymentInfo;
import com.ericsson.cac.sprint.shop.workflow.datamodel.QueryChannelPolicyResponse;
import com.ericsson.cac.sprint.shop.workflow.datamodel.QueryTotalOrderAmountResponse;
import com.ericsson.cac.sprint.shop.workflow.datamodel.ShippingAndBillingRequest;
import com.ericsson.cac.sprint.shop.workflow.datamodel.ShippingAndBillingResponse;
import com.ericsson.cac.sprint.shop.workflow.datamodel.SubmitOrderRequest;
import com.ericsson.cac.sprint.shop.workflow.datamodel.SubmitOrderResponse;
import com.ericsson.cac.sprint.shop.workflow.datamodel.ValidateAddressRequest;
import com.ericsson.cac.sprint.shop.workflow.datamodel.ValidateAddressResponse;
import com.ericsson.cac.sprint.shop.workflow.datamodel.ValidateShopPaymentRequest;
import com.ericsson.cac.sprint.shop.workflow.datamodel.ValidateShopPaymentResponse;
import com.ericsson.cac.sprint.shop.workflow.util.CommonUtil;
import com.sprint.integration.eai.services.enterprisewalletservice.v1.enterprisewalletservice.Faultmessage;
@Component
public class ShopEppWorkFlowImpl implements ShopEppWorkFlow {
	public static final Logger LOGGER=LoggerFactory.getLogger(ShopEppWorkFlowImpl.class);
	@Autowired
	EPPPaymentManagementProxyService paymentServiceAdapter;
	@Autowired 
	EPPWalletManagementProxyService walletServiceAdapter;
	
	@Autowired
	ShopEaiWorkflowImpl eaiWorkflowImpl;	
	@Autowired
	ShopWorkflowImpl WorkflowImpl;
	@Value("${shop.workflow.vendorCode}")
	private String vendorCode;
	@Value("${shop.workflow.salesChannel}")
	private String salesChannel;
	@Value("${shop.workflow.accountType}")
	private String accountType;
	@Value("${shop.workflow.accountSubType}")
	private String accountSubType;
	@Value("${shop.workflow.paymentType}")
	private String paymentType;
	@Value("${shop.workflow.channelId}")
	private String channelId;
	@Value("${shop.workflow.actorId}")
	private String actorId;
	@Value("${shop.workflow.tokenType}")
	private String tokenType;
	
	@Value("${shop.workflow.seccess}")
	String successDesc;
	@Value("${shop.workflow.failure}")
	String failureDesc;
/*	@Value("${shop.workflow.epp.actor.channel}")
	String eppActorachannel;
	@Value("${shop.workflow.epp.actor.id}")
	String eppActorId;*/
	@Value("${shop.workflow.epp.generateToken.failureDesc}")
	String generateTokenFaliureDesc;
	@Value("${shop.workflow.epp.validatePayment.failureDesc}")
	String validatePaymentFailureDesc;
	@Value("${shop.workflow.epp.capturePayment.failureDesc}")
	String capturePaymentFailureDesc;
	@Value("${shop.workflow.epp.cancelPayment.failureDesc}")
	String cancelPaymentFailureDesc;
	@Value("${shop.workflow.epp.authorizePayment.failureDesc}")
	String authorizePaymentFailureDesc;
	
	@Value("${shop.workflow.eai.salesChannel}")
	private String eaiSalesChannel;
	
	@Value("${shop.workflow.epp.orderid.prefix}")
	private String eppOrderIdPrefix;
	@Value("${shop.workflow.epp.transactionid.prefix}")
	private String eppTransactionIdPrefix;
	@Value("${shop.workflow.epp.queryTotalOrderAmount}")
	private String queryTotalOrderAmount;
	
	@Value("${shop.workflow.technicalErrorDuringInventoryCheck}")
	private String technicalErrorDuringInventoryCheck;
	@Value("${shop.workflow.problemProcessingOrder}")
	private String problemProcessingOrder;
	@Value("${shop.workflow.nominmaxconfigure}")
	private String nominmaxconfigure;
	@Value("${shop.workflow.validcardtext}")
	private String validcardtext;
	@Value("${shop.workflow.cardtext}")
	private String cardtext;
			
	//@Override
	private String actorIdPostfix="_actorId";
	private String actorChannelpostFix="_actorChannel";
	private CancelAuthorizeShopResponse cancelAuthorize(CancelAuthorizeShopRequest request,CompletePurchaseRequest completePurchaseRequest) {
		CancelAuthorizeShopResponse response = null;
		CancelAuthorizePaymentRequest cancelAuthorizePaymentRequest = new CancelAuthorizePaymentRequest();
		  ActorApp info = new ActorApp();
	        info.setActorChannel(getActorChannel(completePurchaseRequest.getBrandId()));
	        info.setActorId(getActorId(completePurchaseRequest.getBrandId()));
	        info.setSourceTransactionId(request.getTransactionId());
	        cancelAuthorizePaymentRequest.setApplicationInfo(info);
	        cancelAuthorizePaymentRequest.setPaymentId(request.getPaymentId());
	        CancelAuthorizePaymentResponse cancelAuthorizePaymentResponse;
	        try {

	        	try {
	        		cancelAuthorizePaymentResponse = paymentServiceAdapter.cancelAuthorizePayment(null, cancelAuthorizePaymentRequest);
	        		LOGGER.info("cancelAuthorize code:"+ cancelAuthorizePaymentResponse.getResponses().getResponse().getResponseCode());
	    			LOGGER.info("cancelAuthorize message:"+ cancelAuthorizePaymentResponse.getResponses().getResponse().getResponseMessage());
	    			LOGGER.info("cancelAuthorize details:"+ cancelAuthorizePaymentResponse.getResponses().getResponse().getResponseDescription());
	        		response = new CancelAuthorizeShopResponse();
	        		if(cancelAuthorizePaymentResponse.getResponses().getResponse().getResponseCode().equalsIgnoreCase(String.valueOf(0)) && 
	        				cancelAuthorizePaymentResponse.getCancelAuthorizePaymentResponseDetail()!=null){
	        			response.setCancelled(true);
	        			response.setStatus(ShopWorkFlowConstants.WS_SUCCESS_CODE);
	        			response.setDescription(successDesc);

	        		}
	        		else{
	        			response.setCancelled(false);
	        		}
	        	} catch (
        			DatatypeConfigurationException
        			| IOException
        			| JAXBException
        			| TransformerFactoryConfigurationError
        			| TransformerException
        			| com.sprint.integration.eai.services.enterprisepaymentservice.v1.enterprisepaymentservice.Faultmessage e) {
				
				
				if(e instanceof Faultmessage){
					Faultmessage e1=(Faultmessage)e;
					String  fauilureDesc=e1.getFaultInfo().getProviderError().get(0).getProviderErrorText();
					throw new ShopWorkFlowException(fauilureDesc, e);
				}else{
					throw new ShopWorkFlowException(problemProcessingOrder, e);
				}
				
			}catch (Exception e) {
				throw new ShopWorkFlowException(problemProcessingOrder, e);
			}	
		} catch (ShopWorkFlowException e) {
			response.setStatus(ShopWorkFlowConstants.WS_ERROR_CODE);
        	response.setDescription(e.getMessage());
        	response.setCancelled(false);
			LOGGER.error("got exception:" +e);			
		}

			
	        
		return response;
	}


	private AuthorizeShopPaymentResponse authorizePayment(AuthorizeShopPaymentRequest request,CompletePurchaseRequest completePurchaseRequest) {
		
		AuthorizePaymentRequest authorizePaymentRequest = new AuthorizePaymentRequest();
		authorizePaymentRequest.setExternalOrderId(request.getOrderId());
        String requestId=UUID.randomUUID().toString();
        System.out.println("requestId:"+requestId);
        authorizePaymentRequest.setRequestId(requestId);
        authorizePaymentRequest.setTransactionFor(TransactionForConstants.HARD_GOODS);
        AddressInfo billingInfo=request.getBillingInfo();
        PaymentInfo paymentInfo=request.getPaymentInfo();
        AuthorizeShopPaymentResponse authorizeShopPaymentResponse = null;
        ActorApp info = new ActorApp();
	    info.setActorChannel(getActorChannel(completePurchaseRequest.getBrandId()));
	    info.setActorId(getActorId(completePurchaseRequest.getBrandId()));
        info.setSourceTransactionId(request.getTransactionId());
        authorizePaymentRequest.setApplicationInfo(info);

        
        PaymentRequestDetailBO paymentRequestDetail = new PaymentRequestDetailBO();
        AmountInfo amounts = new AmountInfo();
        amounts.setTotalAmount(BigDecimal.valueOf(request.getTotalAmount()));
        amounts.setPostableAmount(BigDecimal.ZERO);
        
       paymentRequestDetail.setPreOrderToken(request.getToken());//"GRgbLgtv09yMr8V6Yx0eO1VBImyA0dzp"
        paymentRequestDetail.setAmounts(amounts);                
        Address address = new Address();             
        address.setAddressLine1(billingInfo.getAddress1());
        address.setAddressLine2(billingInfo.getAddress2());
        address.setCity(billingInfo.getCity());
        address.setState(billingInfo.getState());
        address.setCountry(ShopWorkFlowConstants.SHOP_DEFAULT_COUNTRY);
        address.setZipCode(billingInfo.getZipCode());
        paymentRequestDetail.setShippingAddress(address);
        paymentRequestDetail.setIsGuestPayment(true);
        paymentRequestDetail.setConsumerChoicePreference(CardType.CREDIT);
        
        authorizePaymentRequest.setPaymentRequestDetail(paymentRequestDetail);
       
        CardSensitiveDetails details = new CardSensitiveDetails();
        details.setFirstName(billingInfo.getFirstName());
        details.setLastName(billingInfo.getLastName());
        details.setCardNumber(paymentInfo.getCardNumber());
        details.setNameOnCard(billingInfo.getFirstName() + ShopWorkFlowConstants.WHITESPACE + billingInfo.getLastName());
        details.setExpirationDate(paymentInfo.getExpirationMonth()+ShopWorkFlowConstants.FORWARD_SLASH+paymentInfo.getExpirationYear());
        details.setCardVerificationCode(paymentInfo.getSecurityCode());
        
        BillingAddressType type = new BillingAddressType();
        type.setAddressLine1(billingInfo.getAddress1());
        type.setAddressLine2(billingInfo.getAddress2());
        type.setCity(billingInfo.getCity());
        type.setState(billingInfo.getState());
        type.setCountry(ShopWorkFlowConstants.SHOP_DEFAULT_COUNTRY);
        type.setZipCode(billingInfo.getZipCode());
        
        details.setBillingAddress(type);
		
		try {
			authorizeShopPaymentResponse = new AuthorizeShopPaymentResponse();
			AuthorizePaymentResponse authorizePaymentResponse =  paymentServiceAdapter.authorizePayment(null, authorizePaymentRequest, details);
			LOGGER.info("authorizePayment code:"+ authorizePaymentResponse.getResponses().getResponse().getResponseCode());
			LOGGER.info("authorizePayment message:"+ authorizePaymentResponse.getResponses().getResponse().getResponseMessage());
			LOGGER.info("authorizePayment details:"+ authorizePaymentResponse.getResponses().getResponse().getResponseDescription());
			if(authorizePaymentResponse.getAuthorizePaymentResponseDetail()!=null && authorizePaymentResponse.getAuthorizePaymentResponseDetail().getPaymentResponseDetails().getPaymentId()!=null  && 
	        		authorizePaymentResponse.getAuthorizePaymentResponseDetail().getPaymentResponseDetails().getAcquirerAuthCode()!=null){
	        	authorizeShopPaymentResponse.setPaymentId(authorizePaymentResponse.getAuthorizePaymentResponseDetail().getPaymentResponseDetails().getPaymentId());
	        	authorizeShopPaymentResponse.setStatus(ShopWorkFlowConstants.WS_SUCCESS_CODE);
	        	authorizeShopPaymentResponse.setDescription("Authorize payment Response Success");
	        	authorizeShopPaymentResponse.setAuthCode(authorizePaymentResponse.getAuthorizePaymentResponseDetail().getPaymentResponseDetails().getAcquirerAuthCode());
	        }else{
	        	if(authorizePaymentResponse.getResponses()!=null && authorizePaymentResponse.getResponses().getResponse()!=null &&
	        			authorizePaymentResponse.getResponses().getResponse().getResponseMessage()!=null){
	        			authorizeShopPaymentResponse.setDescription(authorizePaymentResponse.getResponses().getResponse().getResponseMessage());
	        	}else{
	        		List<Response> responses= authorizePaymentResponse.getResponses().getNestedResponses().getResponse();
		        	for(Response resposne:responses){
		        		if(!resposne.getResponseCode().equalsIgnoreCase("0")){
		        			authorizeShopPaymentResponse.setDescription(resposne.getResponseDescription());
		        			break;
		        		}
		        	}
	        	}
	        	
				authorizeShopPaymentResponse.setStatus(ShopWorkFlowConstants.WS_ERROR_CODE);
				authorizeShopPaymentResponse.setPaymentId("");
	        }
		} catch (com.sprint.integration.eai.services.enterprisepaymentservice.v1.enterprisepaymentservice.Faultmessage e) {
			LOGGER.error("Fault exception 2 Occured while calling Authorize Payment",e);
			authorizeShopPaymentResponse.setDescription(e.getFaultInfo().getProviderError().get(0).getProviderErrorText());
			authorizeShopPaymentResponse.setStatus(ShopWorkFlowConstants.WS_ERROR_CODE);
			authorizeShopPaymentResponse.setPaymentId("");
		} catch (Exception e) {
			LOGGER.error("Exception Occured while calling Authorize Payment",e);
			authorizeShopPaymentResponse.setDescription(problemProcessingOrder);
			authorizeShopPaymentResponse.setStatus(ShopWorkFlowConstants.WS_ERROR_CODE);
			authorizeShopPaymentResponse.setPaymentId("");
		}

		return authorizeShopPaymentResponse;
	}

	private CaptureShopPaymentResponse capturePayment(CaptureShopPaymentRequest request,CompletePurchaseRequest completePurchaseRequest) {
        CapturePaymentRequest capturePaymentRequest = new CapturePaymentRequest();
        CaptureShopPaymentResponse shopPaymentResponse = null;
              ActorApp info = new ActorApp();
              info.setActorChannel(getActorChannel(completePurchaseRequest.getBrandId()));
              info.setActorId(getActorId(completePurchaseRequest.getBrandId()));
              info.setSourceTransactionId(request.getTransactionId());
              capturePaymentRequest.setApplicationInfo(info);
          	  AddressInfo billAddressInfo=request.getBillingInfo();
        	  PaymentInfo paymentInfo=request.getPaymentInfo();
        capturePaymentRequest.setPaymentId(request.getPaymentId());
       
        
        CardSensitiveDetails details = new CardSensitiveDetails();
        
        details.setFirstName(billAddressInfo.getFirstName());
        details.setLastName(billAddressInfo.getLastName());
        details.setCardNumber(paymentInfo.getCardNumber());
        details.setNameOnCard(billAddressInfo.getFirstName()+ShopWorkFlowConstants.WHITESPACE+billAddressInfo.getLastName());
        details.setExpirationDate(paymentInfo.getExpirationMonth()+ShopWorkFlowConstants.FORWARD_SLASH+paymentInfo.getExpirationYear());
        details.setCardVerificationCode(paymentInfo.getSecurityCode());
        
        BillingAddressType type = new BillingAddressType();
        type.setAddressLine1(billAddressInfo.getAddress1());
        type.setAddressLine2(billAddressInfo.getAddress2());
        type.setCity(billAddressInfo.getCity());
        type.setState(billAddressInfo.getState());
        type.setCountry(ShopWorkFlowConstants.SHOP_DEFAULT_COUNTRY);
        type.setZipCode(billAddressInfo.getZipCode());
        
        details.setBillingAddress(type);
        shopPaymentResponse = new CaptureShopPaymentResponse();
        try {
        	CapturePaymentResponse capturePaymentResponse = paymentServiceAdapter.capturePayment(null, capturePaymentRequest, details);
        	LOGGER.info("capturePayment code:"+ capturePaymentResponse.getResponses().getResponse().getResponseCode());
			LOGGER.info("capturePayment message:"+ capturePaymentResponse.getResponses().getResponse().getResponseMessage());
			LOGGER.info("capturePayment details:"+ capturePaymentResponse.getResponses().getResponse().getResponseDescription());

        	Response firstResposne= capturePaymentResponse.getResponses().getNestedResponses().getResponse().get(0);
        	if(firstResposne.getResponseCode().equalsIgnoreCase("0") && capturePaymentResponse.getCapturePaymentResponseDetail().getPaymentResponseDetails()!=null){
        		shopPaymentResponse.setCaptured(true);
        	}
        	else{
        		shopPaymentResponse.setCaptured(false);
        	}
        	shopPaymentResponse.setStatus(ShopWorkFlowConstants.WS_SUCCESS_CODE);
        	shopPaymentResponse.setDescription("capture payment success");

        	}catch (com.sprint.integration.eai.services.enterprisepaymentservice.v1.enterprisepaymentservice.Faultmessage e) {
        		shopPaymentResponse.setStatus(ShopWorkFlowConstants.WS_ERROR_CODE);
	        	shopPaymentResponse.setCaptured(false);
	        	shopPaymentResponse.setDescription(e.getFaultInfo().getProviderError().get(0).getProviderErrorText());
			}catch ( Exception e) {
	        	shopPaymentResponse.setStatus(ShopWorkFlowConstants.WS_ERROR_CODE);
	        	shopPaymentResponse.setCaptured(false);
	        	shopPaymentResponse.setDescription(problemProcessingOrder);
	        } 

        return shopPaymentResponse;
	}


private GenerateShopTokenResponse generateToken(GenerateShopTokenRequest request, CompletePurchaseRequest completePurchaseRequest) {
	GenerateShopTokenResponse tokenResponse=new GenerateShopTokenResponse();
	GeneratePreOrderTokenRequest generatePreOrderTokenRequest = new GeneratePreOrderTokenRequest();

	ActorApp info = new ActorApp();
	info.setActorChannel(getActorChannel(completePurchaseRequest.getBrandId()));
	info.setActorId(getActorId(completePurchaseRequest.getBrandId()));
	info.setSourceTransactionId(request.getTransactionId());
	generatePreOrderTokenRequest.setApplicationInfo(info);
	AddressInfo billAddressInfo=request.getBillingInfo();
	PaymentInfo paymentInfo=request.getPaymentInfo();
	PreOrderTokenRequestBO token = new PreOrderTokenRequestBO();
	token.setExternalOrderId(request.getOrderId());
	token.setConsumerChoicePreference(CardType.CREDIT);
	token.setTokenType(TokenTypeConstants.CARD_TOKEN);

	generatePreOrderTokenRequest.setPreOrderTokenRequest(token);
	CardSensitiveDetails details = new CardSensitiveDetails();
	details.setFirstName(billAddressInfo.getFirstName());
	details.setLastName(billAddressInfo.getLastName());
	details.setCardNumber(paymentInfo.getCardNumber());
	details.setNameOnCard(billAddressInfo.getFirstName() + ShopWorkFlowConstants.WHITESPACE+billAddressInfo.getLastName());
	details.setExpirationDate(paymentInfo.getExpirationMonth()+ShopWorkFlowConstants.FORWARD_SLASH+paymentInfo.getExpirationYear());
	details.setCardVerificationCode(paymentInfo.getSecurityCode());

	BillingAddressType type = new BillingAddressType();
	type.setAddressLine1(billAddressInfo.getFirstName());
	type.setAddressLine2(billAddressInfo.getLastName());
	type.setCity(billAddressInfo.getCity());
	type.setState(billAddressInfo.getState());
	type.setCountry(ShopWorkFlowConstants.SHOP_DEFAULT_COUNTRY);
	type.setZipCode(billAddressInfo.getZipCode());

	details.setBillingAddress(type);
	
	try {

		try {

			GeneratePreOrderTokenResponse generatePreOrderTokenResponse = paymentServiceAdapter.generatePreOrderToken(null, generatePreOrderTokenRequest, details);
			tokenResponse.setToken(generatePreOrderTokenResponse.getPreOrderTokenResponse().getGlobalToken());
			LOGGER.info("GeneratePreOrderToken code:"+ generatePreOrderTokenResponse.getResponses().getResponse().getResponseCode());
			LOGGER.info("GeneratePreOrderToken message:"+ generatePreOrderTokenResponse.getResponses().getResponse().getResponseMessage());
			LOGGER.info("GeneratePreOrderToken details:"+ generatePreOrderTokenResponse.getResponses().getResponse().getResponseDescription());
			if(generatePreOrderTokenResponse.getResponses().getResponse().getResponseCode().equalsIgnoreCase("0") ){
				tokenResponse.setStatus(ShopWorkFlowConstants.WS_SUCCESS_CODE);
				tokenResponse.setDescription("generate token success");
			}else{
				LOGGER.info("Error Happend while generating pre-order token" + ":: " + generatePreOrderTokenResponse.getResponses().getResponse().getResponseDescription());
				throw new ShopWorkFlowException(problemProcessingOrder);
			}
			
		}catch (
				DatatypeConfigurationException
				| IOException
				| JAXBException
				| TransformerFactoryConfigurationError
				| TransformerException
				| com.sprint.integration.eai.services.enterprisepaymentservice.v1.enterprisepaymentservice.Faultmessage e) {
			
			
			if(e instanceof Faultmessage){
				Faultmessage e1=(Faultmessage)e;
				String  fauilureDesc=e1.getFaultInfo().getProviderError().get(0).getProviderErrorText();
				throw new ShopWorkFlowException(fauilureDesc, e);
			}else{
				throw new ShopWorkFlowException(problemProcessingOrder, e);
			}
			
		}catch (Exception e) {
			throw new ShopWorkFlowException(problemProcessingOrder, e);
		}	
	} catch (ShopWorkFlowException e) {
		
		LOGGER.error("got exception:" +e);
		tokenResponse.setStatus(ShopWorkFlowConstants.WS_ERROR_CODE);
		tokenResponse.setDescription(e.getMessage());
		
		tokenResponse.setToken("");
	}
	return tokenResponse;
}

	private ValidateShopPaymentResponse validatePayment(ValidateShopPaymentRequest request,ShippingAndBillingRequest shippingAndBillingRequest) {
		LOGGER.info("Before calling validate payment:");
		ValidateShopPaymentResponse validateShopPaymentResponse = new ValidateShopPaymentResponse();
		ValidatePaymentOptionRequest validatePaymentOptionRequest = new ValidatePaymentOptionRequest();
		  com.amdocs.mfs.api.epp.v1.sprint.walletcommontypes.ActorApp info = new com.amdocs.mfs.api.epp.v1.sprint.walletcommontypes.ActorApp();
	        info.setActorChannel(getActorChannel(shippingAndBillingRequest.getBrandId()));
	        info.setActorId(getActorId(shippingAndBillingRequest.getBrandId()));
	        info.setSourceTransactionId(request.getTransactionId());
	        AddressInfo billAddressInfo=request.getBillingInfo();
	        
	        PaymentInfo paymentInfo=request.getPaymentInfo();
	        validatePaymentOptionRequest.setApplicationInfo(info);
	        
	        ValidatePaymentOption paymentOption = new ValidatePaymentOption();
	        paymentOption.setPaymentMethod(PaymentMethod.CARD);
	        validatePaymentOptionRequest.setWalletItem(paymentOption);
	        
	        CardSensitiveDetails details = new CardSensitiveDetails();
	        
	        details.setFirstName(billAddressInfo.getFirstName());
	        details.setLastName(billAddressInfo.getLastName());
	        details.setCardNumber(paymentInfo.getCardNumber());
	        details.setNameOnCard(billAddressInfo.getFirstName()+ShopWorkFlowConstants.WHITESPACE+billAddressInfo.getLastName());
	        details.setExpirationDate(paymentInfo.getExpirationMonth()+ShopWorkFlowConstants.FORWARD_SLASH+paymentInfo.getExpirationYear());
	        details.setCardVerificationCode(paymentInfo.getSecurityCode());
	        
	        BillingAddressType type = new BillingAddressType();
	        type.setAddressLine1(billAddressInfo.getAddress1());
	        type.setAddressLine2(billAddressInfo.getAddress2());
	        type.setCity(billAddressInfo.getCity());
	        type.setState(billAddressInfo.getState());
	        type.setCountry(ShopWorkFlowConstants.SHOP_DEFAULT_COUNTRY);
	        type.setZipCode(billAddressInfo.getZipCode());
	        
	        details.setBillingAddress(type);
	        try {

	        	try {
	        		ValidatePaymentOptionResponse validatePaymentOptionResponse = walletServiceAdapter.validatePayment(null, validatePaymentOptionRequest, details);
	        		LOGGER.info("After calling validate payment response code");
	        		LOGGER.info("validatePayment code:"+ validatePaymentOptionResponse.getResponses().getResponse().getResponseCode());
	    			LOGGER.info("validatePayment message:"+ validatePaymentOptionResponse.getResponses().getResponse().getResponseMessage());
	    			LOGGER.info("validatePayment details:"+ validatePaymentOptionResponse.getResponses().getResponse().getResponseDescription());
	        		if(validatePaymentOptionResponse.getResponses().getResponse().getResponseCode().equalsIgnoreCase(String.valueOf(0))){
	        			validateShopPaymentResponse.setValid(true);
	        			validateShopPaymentResponse.setDescription("validate payment success");
	        			validateShopPaymentResponse.setStatus(ShopWorkFlowConstants.WS_SUCCESS_CODE);
	        			if(validatePaymentOptionResponse.getValidatePaymentOption().getCardPaymentOption()!=null)
	        			{
	        				validateShopPaymentResponse.setCardType(validatePaymentOptionResponse.getValidatePaymentOption().getCardPaymentOption().getCardType().name());
	        				validateShopPaymentResponse.setPaymentCardType(validatePaymentOptionResponse.getValidatePaymentOption().getCardPaymentOption().getCardBrand());
	        			}
	        			
	        		}else{
	        				if(validatePaymentOptionResponse.getValidatePaymentOption().getValidationFailureResponses()!=null){
	        				List<FailureResponse> validateFailureResponsesList=validatePaymentOptionResponse.getValidatePaymentOption().getValidationFailureResponses().getValidationFailure();
	        				if(validateFailureResponsesList.size()>0){
	        					validateShopPaymentResponse.setDescription(validateFailureResponsesList.get(0).getErrorMessage());
	        				}
	        			}else if(validatePaymentOptionResponse.getResponses().getResponse()!=null){
	        				if(validatePaymentOptionResponse.getResponses().getResponse().getResponseDescription()!=null &&
	        						!validatePaymentOptionResponse.getResponses().getResponse().getResponseDescription().isEmpty()){
	        					validateShopPaymentResponse.setDescription(validatePaymentOptionResponse.getResponses().getResponse().getResponseDescription());
	        				}else{
	        					validateShopPaymentResponse.setDescription(validatePaymentFailureDesc);
	        				}
	        				
						}else{
							LOGGER.info("validate payment failure");
							validateShopPaymentResponse.setDescription(validatePaymentFailureDesc);
						}
	        			if(CommonUtil.isStringNullOfEmpty(validateShopPaymentResponse.getDescription())){
	        				validateShopPaymentResponse.setDescription(problemProcessingOrder);
	        			}
	        			validateShopPaymentResponse.setValid(false);
	            		validateShopPaymentResponse.setStatus(ShopWorkFlowConstants.WS_ERROR_CODE);
	        		}
	        		
	        	}  catch (
	        			DatatypeConfigurationException
	        			| IOException
	        			| JAXBException
	        			| TransformerFactoryConfigurationError
	        			| TransformerException
	        			| Faultmessage e) {if(e instanceof Faultmessage){
	        				Faultmessage e1=(Faultmessage)e;
	        				String  fauilureDesc=e1.getFaultInfo().getProviderError().get(0).getProviderErrorText();
	        				throw new ShopWorkFlowException(fauilureDesc, e);
	        			}else{
	        				throw new ShopWorkFlowException(problemProcessingOrder, e);
	        			}
	        	}catch (Exception e) {
	        		throw new ShopWorkFlowException(problemProcessingOrder, e);
				}	
	        } catch (ShopWorkFlowException e) {
	        	//validateShopPaymentResponse.setDescription(e.getMessage());
	        	validateShopPaymentResponse.setDescription(problemProcessingOrder);
        		validateShopPaymentResponse.setStatus(ShopWorkFlowConstants.WS_ERROR_CODE);
        		validateShopPaymentResponse.setValid(false);
	        }
	        return validateShopPaymentResponse;	      
	}


	@Override
	public CompletePurchaseResponse doCompletePurchase(CompletePurchaseRequest request) {
		
		LOGGER.info("doCompletePurchase transactionId :: "+request.getTransactionId());
		LOGGER.debug("doCompletePurchase transactionId :: "+request.getTransactionId()+" CompletePurchaseRequest :: "+request);
		
		if(CommonUtil.isStringNullOfEmpty(request.getBrandId())){
			request.setBrandId("SPP");
		}
		CompletePurchaseResponse completePurchaseResponse = new CompletePurchaseResponse();
		EquipmentAvailabilityRequest equipmentAvailabilityReq = new EquipmentAvailabilityRequest();
		EquipmentAvailabilityResponse availabilityResponse = new EquipmentAvailabilityResponse();
		boolean equipmentAvailableFlag = true;
		
		equipmentAvailabilityReq.setBrandId(request.getBrandId());		
		Equipment[] equipments = request.getEquipments();
		for(Equipment equipment : equipments){
			equipmentAvailabilityReq.setIspreorder(equipment.isPreOrder());
			equipmentAvailabilityReq.setModelId(equipment.getModelId());
			availabilityResponse = WorkflowImpl.getEquipmentAvailability(equipmentAvailabilityReq);
			
			LOGGER.debug("doCompletePurchase transactionId :: "+request.getTransactionId()+" availabilityResponse :: "+availabilityResponse);
			
			
			if(availabilityResponse!=null && availabilityResponse.getEquipmentAvailability()==null){
				LOGGER.info("doCompletePurchase transactionId :: "+request.getTransactionId()+" Equipment availability check fails");
				completePurchaseResponse.setStatus(ShopWorkFlowConstants.WS_ERROR_CODE);
				completePurchaseResponse.setDescription(technicalErrorDuringInventoryCheck);
				return completePurchaseResponse;
			}
			
			if(equipmentAvailabilityReq.isIspreorder() && !availabilityResponse.getEquipmentAvailability().getPreOrderIndicator()){
				equipmentAvailableFlag = false;
				break;
			}else if(!equipmentAvailabilityReq.isIspreorder() && !availabilityResponse.getEquipmentAvailability().getAvailableForSaleIndicator()){
				equipmentAvailableFlag = false;
				break;
			}
		}
		if(equipmentAvailableFlag){
			LOGGER.info("doCompletePurchase transactionId :: "+request.getTransactionId()+" Equipment availability check passes");
			GenerateShopTokenResponse generateShopTokenResponse = generatePreOrderToken(request);
			
			if (generateShopTokenResponse.getStatus() == 0) {
				LOGGER.info("doCompletePurchase transactionId :: "+request.getTransactionId()+" Calling Authorize Shop Payment");
				AuthorizeShopPaymentResponse authorizeShopPaymentResponse = authorizePayment(request, generateShopTokenResponse);

				if (authorizeShopPaymentResponse.getStatus() == 0) {
					LOGGER.info("doCompletePurchase transactionId :: "+request.getTransactionId()+" Calling Submit Order");
					SubmitOrderResponse orderResponse = submitOrder(request, generateShopTokenResponse, authorizeShopPaymentResponse);
					
					if (orderResponse.getStatus() == 0) {
						LOGGER.info("doCompletePurchase transactionId :: "+request.getTransactionId()+" Calling Capture Shop Payment.");
						CaptureShopPaymentResponse captureShopPaymentResponse = capturePayment(request, authorizeShopPaymentResponse);
						if (captureShopPaymentResponse.getStatus() == 0) {
							LOGGER.info("doCompletePurchase transactionId :: "+request.getTransactionId()+" Capture Shop Payment successful.");
							completePurchaseResponse.setStatus(0);
							completePurchaseResponse.setFastOrderKey(orderResponse.getFastOrderkey());
							completePurchaseResponse.setOrderCompleted(true);
							completePurchaseResponse.setDescription(successDesc);
						} else {
							LOGGER.info("doCompletePurchase transactionId :: "+request.getTransactionId()+" Capture Shop Payment failure.");
							completePurchaseResponse.setStatus(101);
							completePurchaseResponse.setOrderCompleted(false);
							completePurchaseResponse.setDescription(captureShopPaymentResponse.getDescription());
						}
					} else {
						LOGGER.info("doCompletePurchase transactionId :: "+request.getTransactionId()+" Submit Order failure.");
						CancelAuthorizeShopResponse cancelAuthorizeShopResponse = cancelAuthorize(request, authorizeShopPaymentResponse);
						if (cancelAuthorizeShopResponse.getStatus() == 0) {
							LOGGER.info("doCompletePurchase transactionId :: "+request.getTransactionId()+" Cancel Authorize Shop success.");
							completePurchaseResponse.setStatus(ShopWorkFlowConstants.WS_ERROR_CODE);
							completePurchaseResponse.setDescription(orderResponse.getDescription());
						} else {
							LOGGER.info("doCompletePurchase transactionId :: "+request.getTransactionId()+" Cancel Authorize Shop failure.");
							completePurchaseResponse.setStatus(ShopWorkFlowConstants.WS_ERROR_CODE);
							completePurchaseResponse.setDescription(cancelAuthorizeShopResponse.getDescription());
						}
					}
				} else {
					LOGGER.info("doCompletePurchase transactionId :: "+request.getTransactionId()+" Authorize Shop Payment failure.");
					completePurchaseResponse.setStatus(ShopWorkFlowConstants.WS_ERROR_CODE);
					completePurchaseResponse.setDescription(authorizeShopPaymentResponse.getDescription());
				}
			} else {
				LOGGER.info("doCompletePurchase transactionId :: "+request.getTransactionId()+" Generate Shop Token failure.");
				completePurchaseResponse.setStatus(ShopWorkFlowConstants.WS_ERROR_CODE);
				completePurchaseResponse.setDescription(generateShopTokenResponse.getDescription());
			}
		}else{
			LOGGER.info("doCompletePurchase transactionId :: "+request.getTransactionId()+" Equipment availability check fails");
			completePurchaseResponse.setStatus(ShopWorkFlowConstants.WS_ERROR_CODE);
			completePurchaseResponse.setDescription(availabilityResponse.getDescription());
		}
		LOGGER.debug("doCompletePurchase transactionId :: "+request.getTransactionId()+" completePurchaseResponse ::"+completePurchaseResponse);
		return completePurchaseResponse;
	}


	private CancelAuthorizeShopResponse cancelAuthorize(
			CompletePurchaseRequest request,
			AuthorizeShopPaymentResponse authorizeShopPaymentResponse) {
		LOGGER.info("doCompletePurchase transactionId :: "+request.getTransactionId()+" inside cancelAuthorize");
		CancelAuthorizeShopRequest cancelAuthorizeShopRequest = new CancelAuthorizeShopRequest();
		cancelAuthorizeShopRequest.setPaymentInfo(request.getPaymentInfo());
		AddressInfo billingInfo=request.getBillingInfo();
		if(billingInfo==null){
			billingInfo=request.getShippingInfo();
		}
		cancelAuthorizeShopRequest.setBillingInfo(billingInfo);
		cancelAuthorizeShopRequest.setPaymentId(authorizeShopPaymentResponse.getPaymentId());
		cancelAuthorizeShopRequest.setTransactionId(request.getTransactionId());
		CancelAuthorizeShopResponse cancelAuthorizeShopResponse = this.cancelAuthorize(cancelAuthorizeShopRequest,request);
		return cancelAuthorizeShopResponse;
	}


	private CaptureShopPaymentResponse capturePayment(
			CompletePurchaseRequest request,
			AuthorizeShopPaymentResponse authorizeShopPaymentResponse) {
		LOGGER.info("doCompletePurchase transactionId :: "+request.getTransactionId()+" inside capturePayment");
		CaptureShopPaymentRequest capturePaymentRequest = new CaptureShopPaymentRequest();
		AddressInfo billingInfo=request.getBillingInfo();
		if(billingInfo==null){
			billingInfo=request.getShippingInfo();
		}
		capturePaymentRequest.setBillingInfo(billingInfo);
		capturePaymentRequest.setPaymentInfo(request.getPaymentInfo());
		capturePaymentRequest.setPaymentId(authorizeShopPaymentResponse.getPaymentId());					
		CaptureShopPaymentResponse captureShopPaymentResponse = this.capturePayment(capturePaymentRequest,request);
		return captureShopPaymentResponse;
	}


	private SubmitOrderResponse submitOrder(CompletePurchaseRequest request,
			GenerateShopTokenResponse generateShopTokenResponse, AuthorizeShopPaymentResponse paymentResponse) {
		LOGGER.info("doCompletePurchase transactionId :: "+request.getTransactionId()+" inside submitOrder");
		SubmitOrderRequest orderRequest = new SubmitOrderRequest();

		orderRequest.setOrderId(request.getOrderId());
		
		if(request.getBillingInfo()==null){
			orderRequest.setBillingInfo(request.getShippingInfo());
		}else{
			orderRequest.setBillingInfo(request.getBillingInfo());
		}		
		
		orderRequest.setShippingInfo(request.getShippingInfo());
		
		orderRequest.setVendorCode(vendorCode);
		orderRequest.setSalesChannel(eaiSalesChannel);
		orderRequest.setAccountType(accountType);
		orderRequest.setAccountSubType(accountSubType);
		orderRequest.setAuthorizationCode(paymentResponse.getAuthCode());
		orderRequest.setEppPaymentId(paymentResponse.getPaymentId());
		orderRequest.setPaymentInfo(request.getPaymentInfo());
		LOGGER.debug("doCompletePurchase transactionId :: "+request.getTransactionId()+" AuthorizationCode: "+paymentResponse.getAuthCode()+" EppPaymentId: "+paymentResponse.getPaymentId()+" PaymentInfo: "+request.getPaymentInfo());
		
		EPPPaymentInfo eppPaymentInfo = new EPPPaymentInfo();
		eppPaymentInfo.setActorId(actorId);
		eppPaymentInfo.setChannelId(channelId);
		eppPaymentInfo.setExternalOrderId(request.getOrderId());
		eppPaymentInfo.setPaymentType(paymentType);
		eppPaymentInfo.setTokenId(generateShopTokenResponse.getToken());
		eppPaymentInfo.setTokenType(tokenType);
		eppPaymentInfo.setGlobalOrderId(null);
		orderRequest.setPaymentType(eppPaymentInfo);
		LOGGER.debug("doCompletePurchase transactionId :: "+request.getTransactionId()+" ActorId: "+actorId+" ChannelId: "+channelId+" PaymentType: "+paymentType+" TokenType:"+tokenType+" OrderId: "+request.getOrderId());
		Order orderLineInfo = new Order();
		Equipment[] equipments = request.getEquipments();		
		
		double subTotalAmt = subtotalAmount(equipments);		
		double taxamount = taxAmount(equipments);

		orderLineInfo.setSubTotalAmount(subTotalAmt);
		orderLineInfo.setTaxAmount(taxamount);
		orderLineInfo.setTotalAmount(subTotalAmt + taxamount);
		orderLineInfo.setEquipments(equipments);

		orderRequest.setOrderLineInfo(orderLineInfo);

		SubmitOrderResponse orderResponse = eaiWorkflowImpl.submitOrder(orderRequest);
		
		LOGGER.info("doCompletePurchase transactionId :: "+request.getTransactionId()+" fastOrderKey ::"+orderResponse.getFastOrderkey());
		
		return orderResponse;
	}


	private double subtotalAmount(Equipment[] equipments) {
		double subTotalAmt = 0.0;
		for (Equipment equipment : equipments) {
			subTotalAmt += (equipment.getModelPrice()*equipment.getQuantity());			
		}
		return subTotalAmt;
	}


	private double taxAmount(Equipment[] equipments) {
		double taxamount=0.0;		
		for (Equipment equipment : equipments) {
			taxamount += (equipment.getEquipmentTaxAmt()*equipment.getQuantity());
		}
		return taxamount;
	}


	private AuthorizeShopPaymentResponse authorizePayment(
			CompletePurchaseRequest request,
			GenerateShopTokenResponse generateShopTokenResponse) {
		LOGGER.info("doCompletePurchase transactionId :: "+request.getTransactionId()+"inside authorizePayment");
		
		AuthorizeShopPaymentRequest authorizeShopPaymentRequest = new AuthorizeShopPaymentRequest();
		AddressInfo billingInfo=request.getBillingInfo();
		if(billingInfo==null){
			billingInfo=request.getShippingInfo();
		}
		authorizeShopPaymentRequest.setBillingInfo(billingInfo);
		authorizeShopPaymentRequest.setPaymentInfo(request.getPaymentInfo());
		authorizeShopPaymentRequest.setOrderId(request.getOrderId());
		authorizeShopPaymentRequest.setTransactionId(request.getTransactionId());
		authorizeShopPaymentRequest.setToken(generateShopTokenResponse.getToken());
		LOGGER.info("doCompletePurchase transactionId :: "+request.getTransactionId()+" Generated token: "+generateShopTokenResponse.getToken());
		Equipment[] equipments = request.getEquipments();
		
		double shipping = 0.0;
		
		if(request.getShippingInfo().getShippingFee()!=null && !request.getShippingInfo().getShippingFee().isEmpty()){
			shipping = Double.valueOf(request.getShippingInfo().getShippingFee());
		}
		
		authorizeShopPaymentRequest.setTotalAmount(subtotalAmount(equipments)+taxAmount(equipments)+shipping);
		
		LOGGER.info("doCompletePurchase transactionId :: "+request.getTransactionId()+" TotalAmount ::"+ authorizeShopPaymentRequest.getTotalAmount());
		
		AuthorizeShopPaymentResponse authorizeShopPaymentResponse = this.authorizePayment(authorizeShopPaymentRequest,request);
		return authorizeShopPaymentResponse;
	}


	private GenerateShopTokenResponse generatePreOrderToken(CompletePurchaseRequest request) {
		LOGGER.info("doCompletePurchase transactionId :: "+request.getTransactionId()+" inside generatePreOrderToken");
		GenerateShopTokenRequest generateShopTokenRequest = new GenerateShopTokenRequest();
		AddressInfo billingInfo=request.getBillingInfo();
		if(billingInfo==null){
			billingInfo=request.getShippingInfo();
		}
		generateShopTokenRequest.setBillingInfo(billingInfo);
		generateShopTokenRequest.setPaymentInfo(request.getPaymentInfo());
		generateShopTokenRequest.setOrderId(request.getOrderId());
		generateShopTokenRequest.setTransactionId(request.getTransactionId());

		GenerateShopTokenResponse generateShopTokenResponse = this.generateToken(generateShopTokenRequest,request);
		
		return generateShopTokenResponse;
	}
	
	@Override
	public ShippingAndBillingResponse doShippingBilling(ShippingAndBillingRequest request) {
		String transactionId = getTransactionId();
		LOGGER.info("generated transactionId for LOGGER for the current ShippingBilling Request :: transactionId :: "+request+ "::"+ transactionId);
		LOGGER.info("transactionId ::"+transactionId+" inside doShippingBilling");
		Boolean iscontinue = true;
		ShippingAndBillingResponse shippingAndBillingResponse = new ShippingAndBillingResponse();
		CoverageAreaResponse coverageAreaResponse = null;
		SimpleDateFormat sd = new SimpleDateFormat("YYYY-MM-dd"); 
		if(request.getBrandId() == null || request.getBrandId().trim().isEmpty()){
			request.setBrandId("SPP");
		}
		
		
		LOGGER.info("transactionId ::"+transactionId+" IN Valid Order Amount");
		
		QueryChannelPolicyResponse queryChannelPolicyResponse = getQueryChannelPolicy(request.getBrandId());
		LOGGER.debug("transactionId ::"+transactionId+" queryChannelPolicyResponse is ::"+queryChannelPolicyResponse);
		LOGGER.info("transactionId ::"+transactionId+" queryChannelPolicyResponse status is  ::"+queryChannelPolicyResponse.getStatus());
		if(queryChannelPolicyResponse.getStatus() == 0)
		{	
			Double totalAmt = gettotalorderAmt(request);
			LOGGER.info("transactionId ::"+transactionId+" totalAmt is ::"+totalAmt);
			for(ChannelPolicy channelPolicy: queryChannelPolicyResponse.getChannelPolicy()){
				if(channelPolicy.getPaymentMethod().equals("Guest")){
					if(channelPolicy.getMinAmount()<=totalAmt 
							&& totalAmt<=channelPolicy.getMaxAmount() 
							&& totalAmt<=Double.valueOf(queryTotalOrderAmount().getQueryTotalOrderAmount()))
					{
						LOGGER.info("transactionId ::"+transactionId+" valid totalAmt ::");
						
						for (Equipment equipment : request.getEquipments()) {
							if (!equipment.getAccessoryInd()) {
								LOGGER.info("transactionId ::"+transactionId+" IN Check Coverage");
								CoverageAreaRequest coverageAreaRequest = new CoverageAreaRequest();
								coverageAreaRequest.setShippingInfo(request.getShippingInfo());
								coverageAreaResponse = eaiWorkflowImpl.checkCoverageArea(coverageAreaRequest);
								if (coverageAreaResponse.getStatus()==ShopWorkFlowConstants.WS_ERROR_CODE || !coverageAreaResponse.getCovered()) {
									iscontinue = false;	
									LOGGER.info("transactionId ::"+transactionId+":: "+coverageAreaResponse.getZipcode()+ "zipcode Outside of coverage area");
								}
								break;
							}
						}

						if (iscontinue) {							
							LOGGER.info("transactionId ::"+transactionId+ "  IN Validate Address,Check coverage SUCCESS");							
							String orderId = getOrderId();
							LOGGER.info("transactionId ::"+transactionId+ " OrderId for this transaction is :: "+orderId);
							ValidateAddressRequest validateAddressRequest = new ValidateAddressRequest();
							validateAddressRequest.setShippingInfo(request.getShippingInfo());
							
							ValidateAddressResponse validateAddressResponse = eaiWorkflowImpl.validateAddress(validateAddressRequest);
							LOGGER.debug("transactionId ::"+transactionId+"ValidateAddress Resposne is :: "+validateAddressResponse);
							LOGGER.info("transactionId ::"+transactionId+"ValidateAddress Resposne confidence value is :: "+validateAddressResponse.getConfidence());
							ValidateShopPaymentResponse validateShopPaymentResponse = null;

							if (validateAddressResponse.getStatus()==ShopWorkFlowConstants.WS_SUCCESS_CODE && validateAddressResponse.getAddressExist()) {
								LOGGER.info("transactionId ::"+transactionId+ " IN Validate Payment");
								
								ValidateShopPaymentRequest validateShopPaymentRequest = new ValidateShopPaymentRequest();
								AddressInfo billAddressInfo=request.getBillingInfo();
								if(billAddressInfo==null){
									billAddressInfo=request.getShippingInfo();
								}
								validateShopPaymentRequest.setBillingInfo(billAddressInfo);
								validateShopPaymentRequest.setPaymentInfo(request.getPaymentInfo());
								validateShopPaymentRequest.setOrderId(orderId);
								validateShopPaymentRequest.setTransactionId(transactionId);
								
								validateShopPaymentResponse = this.validatePayment(validateShopPaymentRequest,request);
								LOGGER.debug("transactionId ::"+transactionId+"validateShopPaymentResponse Resposne is :: ",validateShopPaymentResponse);
								if (validateShopPaymentResponse.getStatus()==ShopWorkFlowConstants.WS_SUCCESS_CODE && validateShopPaymentResponse.getValid()) {
									String cardTypeShopResponse = validateShopPaymentResponse.getPaymentCardType();
									if(cardTypeShopResponse!=null){
										cardTypeShopResponse = cardTypeShopResponse.trim().toLowerCase();
										LOGGER.debug("cardTypeShopResponse ::"+cardTypeShopResponse);
									}
									String cardTypeShopRequest = request.getPaymentInfo().getPaymentCardType();
									if(cardTypeShopRequest!=null){
										cardTypeShopRequest = cardTypeShopRequest.trim().toLowerCase();
										LOGGER.debug("cardTypeShopRequest ::"+cardTypeShopRequest);
									} else{
										shippingAndBillingResponse.setStatus(ShopWorkFlowConstants.WS_ERROR_CODE);
										shippingAndBillingResponse.setDescription("Payment Card Type not provided in request");
										return shippingAndBillingResponse;
									}
									if(!cardTypeShopResponse.contains(cardTypeShopRequest)){
										shippingAndBillingResponse.setStatus(ShopWorkFlowConstants.WS_ERROR_CODE);
										shippingAndBillingResponse.setDescription(validcardtext+" "+request.getPaymentInfo().getPaymentCardType()+" "+cardtext);
										return shippingAndBillingResponse;
									}
									shippingAndBillingResponse.setCardType(validateShopPaymentResponse.getCardType());
									shippingAndBillingResponse.setPaymentCardType(validateShopPaymentResponse.getPaymentCardType());
									LOGGER.info("transactionId ::"+transactionId +"In Calculate Tax");
									int i = 0;
									boolean iscaltaxsuccess = true;
									CalculateTaxResponse calculateTaxResponse = null;
									Double totaleqpamt = 0.0;
									LOGGER.info("transactionId ::"+transactionId +"No of Equipments :: ",request.getEquipments().length);
									for(Equipment equipment : request.getEquipments())
									{
									
									CalculateTaxRequest calculateTaxRequest = new CalculateTaxRequest();
									calculateTaxRequest.setItemId(equipment.getModelId());
									calculateTaxRequest.setInvoiceNumber(orderId);
									calculateTaxRequest.setBillingInfo(billAddressInfo);
									calculateTaxRequest.setLineNumber(Integer.valueOf(equipment.getOrderLineId()));
									calculateTaxRequest.setQuantity(Integer.valueOf(equipment.getQuantity()));
									if(equipment.getDiscount()==null){
										equipment.setDiscount(0.0);
									}
									calculateTaxRequest.setTaxOnAmount(equipment.getModelPrice()-equipment.getDiscount());
									
									calculateTaxResponse = eaiWorkflowImpl.calculateTax(calculateTaxRequest);
									
									if (calculateTaxResponse.getStatus() == ShopWorkFlowConstants.WS_SUCCESS_CODE) {
										request.getEquipments()[i]
												.setEquipmentTaxAmt(calculateTaxResponse.getTaxAmount());
//										double discount = 0;
//										if(request.getEquipments()[i].getDiscount()!=null){
//											discount = request.getEquipments()[i].getDiscount();
//										}
										request.getEquipments()[i]
												.setSubTotalAmt((calculateTaxResponse.getTotalAmount()*equipment.getQuantity()));
										request.getEquipments()[i].setTaxTransactionId(calculateTaxResponse.getTransactionId());
										request.getEquipments()[i].setInvoiceDate(sd.format(new Date()));
										
										if(request.getEquipments()[i].getSubTotalAmt()!=null)
											totaleqpamt += request.getEquipments()[i].getSubTotalAmt();
									}
									else
									{
										iscaltaxsuccess = false;
										break;
									}
									
									i++;
									
									}
									
									LOGGER.info("transactionId ::"+transactionId +"Total equipment Amt ::"+ totaleqpamt);

									
				                   if(iscaltaxsuccess)
				                   {   LOGGER.info("transactionId ::"+transactionId +"Tax Calculation completed successfully");	   	
				                	   shippingAndBillingResponse.setEquipments(request.getEquipments());
				                	   shippingAndBillingResponse.setPaymentValid(true);
				                	   shippingAndBillingResponse.setAddressValid(true);
				                	   shippingAndBillingResponse.setTransactionId(transactionId);
				                	   shippingAndBillingResponse.setOrderId(orderId);
				                	   shippingAndBillingResponse.setShippingFee(request.getShippingInfo().getShippingFee());
				                	   if(request.getShippingInfo().getShippingFee()!=null && request.getShippingInfo().getShippingFee().trim().isEmpty())
				                	   {
				                		   shippingAndBillingResponse.setTotalAmt(totaleqpamt);
				                	   }
				                	   else
				                	   {
				                		   shippingAndBillingResponse.setTotalAmt(totaleqpamt+Double.valueOf(request.getShippingInfo().getShippingFee()));   
				                	   }
				                	   shippingAndBillingResponse.setCardType(validateShopPaymentResponse.getCardType());
				                	   shippingAndBillingResponse.setPaymentCardType(validateShopPaymentResponse.getPaymentCardType());
				                	   LOGGER.info("transactionId ::"+transactionId +"Shipping and Billing is success");
				                	   shippingAndBillingResponse.setStatus(ShopWorkFlowConstants.WS_SUCCESS_CODE);
				                	   shippingAndBillingResponse.setDescription(successDesc);
				                	   
									} else {
										LOGGER.info("transactionId ::"+transactionId +"Shipping and Billing failied in calculate tax :: "+calculateTaxResponse.getDescription());
										shippingAndBillingResponse.setStatus(ShopWorkFlowConstants.WS_ERROR_CODE);
										shippingAndBillingResponse.setDescription(calculateTaxResponse.getDescription());
									}

								} else {
									LOGGER.info("transactionId ::"+transactionId +"Shipping and Billing failed in validatePayment :: "+validateShopPaymentResponse.getDescription());
									shippingAndBillingResponse.setPaymentValid(false);
									shippingAndBillingResponse.setStatus(ShopWorkFlowConstants.WS_ERROR_CODE);
									shippingAndBillingResponse.setDescription(validateShopPaymentResponse.getDescription());
								}
							} else {
								LOGGER.info("transactionId ::"+transactionId +"Shipping and Billing failied in validate address :: "+validateAddressResponse.getDescription());
								shippingAndBillingResponse.setAddressValid(false);
								shippingAndBillingResponse.setStatus(ShopWorkFlowConstants.WS_ERROR_CODE);
								shippingAndBillingResponse.setDescription(validateAddressResponse.getDescription());
							}
						} else {
							LOGGER.info("transactionId ::"+transactionId +"Shipping and Billing failied in coverage area checking :: "+coverageAreaResponse.getDescription());
							shippingAndBillingResponse.setStatus(ShopWorkFlowConstants.WS_ERROR_CODE);
							shippingAndBillingResponse.setDescription(coverageAreaResponse.getDescription());
						}

					}
					else
					{
						LOGGER.info("transactionId ::"+transactionId +"Shipping and Billing failed as order amount is not valid");
						shippingAndBillingResponse.setStatus(ShopWorkFlowConstants.WS_ERROR_CODE);
						shippingAndBillingResponse.setDescription("Order Amount is not valid");
					}
				}
			}
			
		
		
		}
		else
		{
			LOGGER.info("transactionId ::"+transactionId +"Shipping and Billing failed in queryChannelPolicy :: "+queryChannelPolicyResponse.getDescription());
			shippingAndBillingResponse.setStatus(ShopWorkFlowConstants.WS_ERROR_CODE);
			shippingAndBillingResponse.setDescription(queryChannelPolicyResponse.getDescription());
		}
		
		
		LOGGER.info("transactionId ::"+transactionId +"Shipping and Billing response :: "+ shippingAndBillingResponse);
		return shippingAndBillingResponse;
	}
	
	/*@Override
	public QueryChannelPolicyResponse getQueryChannelPolicy(){
		
		com.amdocs.mfs.api.epp.v1.sprint.walletoperationinnerrequestresponsetypes.QueryChannelPolicyRequest queryChannelPolicyRequest=null;
		com.amdocs.mfs.api.epp.v1.sprint.walletoperationinnerrequestresponsetypes.QueryChannelPolicyResponse queryChannelPolicyResponse=null;
		QueryChannelPolicyResponse response= new QueryChannelPolicyResponse();
		
		try {
			com.amdocs.mfs.api.epp.v1.sprint.walletcommontypes.ActorApp actorApp = new com.amdocs.mfs.api.epp.v1.sprint.walletcommontypes.ActorApp();
			actorApp.setActorChannel(eppActorachannel);
			actorApp.setActorId(eppActorId);	
			queryChannelPolicyRequest = new com.amdocs.mfs.api.epp.v1.sprint.walletoperationinnerrequestresponsetypes.QueryChannelPolicyRequest();
			queryChannelPolicyRequest.setApplicationInfo(actorApp);
			
			queryChannelPolicyResponse= walletServiceAdapter.queryChannelPolicy(queryChannelPolicyRequest);
			response.setPaymentAllowedPOs(queryChannelPolicyResponse.getPaymentAllowedPOs());
			response.setPaymentMinMaxAmounts(queryChannelPolicyResponse.getPaymentMinMaxAmounts());
			response.setWalletAllowedPOs(queryChannelPolicyResponse.getWalletAllowedPOs());
			response.setWalletMaxPOs(queryChannelPolicyResponse.getWalletMaxPOs());
			response.setStatus(ShopWorkFlowConstants.WS_SUCCESS_CODE);
			//channelPolicyResponse.setPaymentMinMaxAmounts(queryChannelPolicyResponse.get);
			
		} catch (Exception e) {
			LOGGER.error("Exception Happened while calling for Wallet Min Max Values",e);
			response.setDescription(failureDesc);
			response.setStatus(ShopWorkFlowConstants.WS_ERROR_CODE);
			
		}
		
		return response;
	}*/
	
	
	@Override
	public QueryChannelPolicyResponse getQueryChannelPolicy(String brandId){
		
		com.amdocs.mfs.api.epp.v1.sprint.walletoperationinnerrequestresponsetypes.QueryChannelPolicyRequest queryChannelPolicyRequest=null;
		com.amdocs.mfs.api.epp.v1.sprint.walletoperationinnerrequestresponsetypes.QueryChannelPolicyResponse queryChannelPolicyResponse=null;
		QueryChannelPolicyResponse response= new QueryChannelPolicyResponse();
	try
	{
		try {
			com.amdocs.mfs.api.epp.v1.sprint.walletcommontypes.ActorApp actorApp = new com.amdocs.mfs.api.epp.v1.sprint.walletcommontypes.ActorApp();
			actorApp.setActorChannel(ShopWorkFlowConstants.actorInfoMap.get(brandId+actorChannelpostFix));
			actorApp.setActorId(ShopWorkFlowConstants.actorInfoMap.get(brandId+actorIdPostfix));	
			queryChannelPolicyRequest = new com.amdocs.mfs.api.epp.v1.sprint.walletoperationinnerrequestresponsetypes.QueryChannelPolicyRequest();
			queryChannelPolicyRequest.setApplicationInfo(actorApp);
			ChannelPolicy[] channelPolicies;
			List<ChannelPolicy> channelPoliciesList = new ArrayList<ChannelPolicy>();
			String key = actorApp.getActorChannel();
			
			Object queryChannelPolicyResponseObj = HazelHelper.getObjectFromMap(key,"EPPWalletManagementProxyService") ;
			
			if(queryChannelPolicyResponseObj == null){
				LOGGER.debug("removing from cache:");
				HazelHelper.removeObjectFromMap(key,"EPPWalletManagementProxyService");
				LOGGER.debug("calling wallet service from EPP adapter:");
				queryChannelPolicyResponse= walletServiceAdapter.queryChannelPolicy(queryChannelPolicyRequest);
				LOGGER.info("queryChannelPolicy code:"+ queryChannelPolicyResponse.getResponses().getResponse().getResponseCode());
    			LOGGER.info("queryChannelPolicy message:"+ queryChannelPolicyResponse.getResponses().getResponse().getResponseMessage());
    			LOGGER.info("queryChannelPolicy details:"+ queryChannelPolicyResponse.getResponses().getResponse().getResponseDescription());
				LOGGER.debug("putting value to cache:");
				HazelHelper.putObjectToMap(key, queryChannelPolicyResponse, "EPPWalletManagementProxyService");
			}else{
				LOGGER.debug("fetching from cache:");
				if(queryChannelPolicyResponseObj instanceof com.amdocs.mfs.api.epp.v1.sprint.walletoperationinnerrequestresponsetypes.QueryChannelPolicyResponse){
					queryChannelPolicyResponse = (com.amdocs.mfs.api.epp.v1.sprint.walletoperationinnerrequestresponsetypes.QueryChannelPolicyResponse) queryChannelPolicyResponseObj;
				}				
			}
			if(queryChannelPolicyResponse!=null)
			{
				PaymentMinMaxAmounts paymentMinMaxAmounts = queryChannelPolicyResponse.getPaymentMinMaxAmounts();
				if(paymentMinMaxAmounts!=null)
				{
					List<PaymentMinMaxAmount> paymentMinMaxAmountList = paymentMinMaxAmounts.getPaymentMinMaxAmount();
					
					if(paymentMinMaxAmountList!=null)
					{
						for(PaymentMinMaxAmount paymentMinMaxAmount : paymentMinMaxAmountList){			
							if(paymentMinMaxAmount.getTransactionForName().name().trim().equals("HARD_GOODS")){
								List<PayLimitPaymentType> payLimitPaymentTypeList = paymentMinMaxAmount.getPaymentType();
								if(payLimitPaymentTypeList!= null)
								{
									for(PayLimitPaymentType payLimitPaymentType : payLimitPaymentTypeList){
										if(payLimitPaymentType.getPaymentTypeName().equals("Guest")
												|| payLimitPaymentType.getPaymentTypeName().equals("Non-Wallet")
												|| payLimitPaymentType.getPaymentTypeName().equals("Wallet")){
											ChannelPolicy channelPolicy = new ChannelPolicy();
											channelPolicy.setPaymentMethod(payLimitPaymentType.getPaymentTypeName());
											channelPolicy.setMinAmount(payLimitPaymentType.getMinAmount().doubleValue());
											channelPolicy.setMaxAmount(payLimitPaymentType.getMaxAmount().doubleValue());
											channelPolicy.setCeilingAmount(payLimitPaymentType.getCeilingAmount().doubleValue());
											channelPoliciesList.add(channelPolicy);																
											//break;
										}
									}
									channelPolicies = channelPoliciesList.toArray(new ChannelPolicy[channelPoliciesList.size()]);
									response.setChannelPolicy(channelPolicies);
									response.setStatus(ShopWorkFlowConstants.WS_SUCCESS_CODE);
									response.setDescription(successDesc);
								}																						
								break;					
							}
						}
						
					}
					
				}
			}
				
			if(response.getChannelPolicy() == null)
			{
				response.setStatus(ShopWorkFlowConstants.WS_ERROR_CODE);
				response.setDescription(nominmaxconfigure);
			}			
						
		} catch (
    			DatatypeConfigurationException
    			| IOException
    			| JAXBException
    			| TransformerFactoryConfigurationError
    			| TransformerException
    			| Faultmessage e) {if(e instanceof Faultmessage){
    				Faultmessage e1=(Faultmessage)e;
    				String  fauilureDesc=e1.getFaultInfo().getProviderError().get(0).getProviderErrorText();
    				throw new ShopWorkFlowException(fauilureDesc, e);
    			}else{
    				throw new ShopWorkFlowException(problemProcessingOrder, e);
    			}
    	}catch (Exception e) {
    		LOGGER.error("Error Happened in QueryChannelPolicy::",e);
    		throw new ShopWorkFlowException(problemProcessingOrder);
		}
	}
    catch (ShopWorkFlowException e) {
    	response.setDescription(e.getMessage());
		response.setStatus(ShopWorkFlowConstants.WS_ERROR_CODE);
		
    }
		
		return response;
	}
	
	/*private PayLimitPaymentType getMinMax()
	{
		//QueryChannelPolicyRequest request = new QueryChannelPolicyRequest();
		PayLimitPaymentType response = null;
		
		QueryChannelPolicyResponse queryChannelPolicyResponse = getQueryChannelPolicy();
		
		System.out.println(new Gson().toJson(queryChannelPolicyResponse));
		
		if(queryChannelPolicyResponse!=null)
		{
			PaymentMinMaxAmounts paymentMinMaxAmounts = queryChannelPolicyResponse.getPaymentMinMaxAmounts();
			for(PaymentAllowedPO paymentAllowedPO : queryChannelPolicyResponse.getPaymentAllowedPOs().getPaymentAllowedPO())
			{
				for(TransactionFor transactionFor : paymentAllowedPO.getTransactionFor())
				{
					for(AllowedPayMethodPaymentType allowedPayMethodPaymentType : transactionFor.getPaymentTypes().getPaymentType())
					{
						for(AllowedPaymentMethod allowedPaymentMethod :allowedPayMethodPaymentType.getAllowedPaymentMethod())
						{
							if(allowedPaymentMethod.getPaymentSubMethod().equals("CARD"))
							{
								
							}
						}
					}
				}
			}
			
			if(paymentMinMaxAmounts!=null)
			{
				List<PaymentMinMaxAmount> paymentMinMaxAmountList = paymentMinMaxAmounts.getPaymentMinMaxAmount();
				
				if(paymentMinMaxAmountList!=null)
				{
					for(PaymentMinMaxAmount paymentMinMaxAmount : paymentMinMaxAmountList){			
						if(paymentMinMaxAmount.getTransactionForName().name().trim().equals("HARD_GOODS")){
							List<PayLimitPaymentType> payLimitPaymentTypeList = paymentMinMaxAmount.getPaymentType();
							if(payLimitPaymentTypeList!= null)
							{
								for(PayLimitPaymentType payLimitPaymentType : payLimitPaymentTypeList){
									if(payLimitPaymentType.getPaymentTypeName().equals("Guest")){
										response = 	payLimitPaymentType;
										break;
									}
								}
							}
							break;					
						}
					}
					
				}
				
			}
			
		}
		return response;
	}*/

	
	private Double gettotalorderAmt(ShippingAndBillingRequest shippingAndBillingRequest)
	{
		LOGGER.info("Call total Order method");
		Equipment[] equipments = shippingAndBillingRequest.getEquipments();
		
		Double totalamt = 0.00;
		
		if(equipments!=null)
		{
			for(Equipment equipment : equipments)
			{
				
					totalamt+=(equipment.getQuantity()*equipment.getModelPrice());
			}
		}
		
		return totalamt;
	}

	
	/*private boolean isvalidorderamount(ShippingAndBillingRequest shippingAndBillingRequest)
	{
		LOGGER.info("Call isvalidorderamount method");
		Equipment[] equipments = shippingAndBillingRequest.getEquipments();
		
		boolean isvalid = false;
		Double totalamt = 0.00;
		
		if(equipments!=null)
		{
			for(Equipment equipment : equipments)
			{
				
					totalamt+=(equipment.getQuantity()*equipment.getModelPrice());
			}
		}
				
		PayLimitPaymentType payLimitPaymentType = getMinMax();
		
		if(payLimitPaymentType!=null)
		{
			double minAmt;
			double maxAmt;
			
			if(payLimitPaymentType.getMinAmount()!=null)
			{
				minAmt = payLimitPaymentType.getMinAmount().doubleValue();
			
				maxAmt = payLimitPaymentType.getMaxAmount().doubleValue();
			
				if(minAmt<=totalamt && totalamt<=maxAmt & totalamt<=Double.valueOf(queryTotalOrderAmount()))
				{
					isvalid = true;
				}
				
			}
		}
		
		return isvalid;
	}*/
	
	@Override
	public QueryTotalOrderAmountResponse queryTotalOrderAmount()
	{
		QueryTotalOrderAmountResponse amountResponse = new QueryTotalOrderAmountResponse();
		amountResponse.setQueryTotalOrderAmount(queryTotalOrderAmount);
		return amountResponse; 
	}
	
	private synchronized String  getOrderId()
	{
		long number = (long) Math.floor(Math.random() * 9000000000L) + 1000000000L;
		return eppOrderIdPrefix+String.valueOf(number);
	}
	
	private synchronized String getTransactionId()
	{
		long number = (long) Math.floor(Math.random() * 9000000000L) + 1000000000L;
		return eppTransactionIdPrefix+String.valueOf(number);
	}

	private String getActorChannel(String brandId){
		return ShopWorkFlowConstants.actorInfoMap.get(brandId+actorChannelpostFix);
		
	}
	private String getActorId(String brandId){
		return ShopWorkFlowConstants.actorInfoMap.get(brandId+actorIdPostfix);
	}
}

