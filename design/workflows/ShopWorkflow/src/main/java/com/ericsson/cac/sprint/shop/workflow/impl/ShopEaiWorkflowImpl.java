package com.ericsson.cac.sprint.shop.workflow.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.ws.Holder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.remoting.soap.SoapFaultException;
import org.springframework.stereotype.Component;

import com.ericsson.cac.sprint.adapters.AddressManagementProxyService;
import com.ericsson.cac.sprint.adapters.PromotionManagementProxyService;
import com.ericsson.cac.sprint.adapters.QueryCsaProxyService;
import com.ericsson.cac.sprint.adapters.SubscriberManagementProxyService;
import com.ericsson.cac.sprint.adapters.TaxCalculationProxyService;
import com.ericsson.cac.sprint.shop.constants.ShopWorkFlowConstants;
import com.ericsson.cac.sprint.shop.workflow.ShopEaiWorkflow;
import com.ericsson.cac.sprint.shop.workflow.datamodel.CalculateTaxRequest;
import com.ericsson.cac.sprint.shop.workflow.datamodel.CalculateTaxResponse;
import com.ericsson.cac.sprint.shop.workflow.datamodel.CoverageAreaRequest;
import com.ericsson.cac.sprint.shop.workflow.datamodel.CoverageAreaResponse;
import com.ericsson.cac.sprint.shop.workflow.datamodel.DiscountItem;
import com.ericsson.cac.sprint.shop.workflow.datamodel.EPPPaymentInfo;
import com.ericsson.cac.sprint.shop.workflow.datamodel.Equipment;
import com.ericsson.cac.sprint.shop.workflow.datamodel.SubmitOrderRequest;
import com.ericsson.cac.sprint.shop.workflow.datamodel.SubmitOrderResponse;
import com.ericsson.cac.sprint.shop.workflow.datamodel.ValidateAddressRequest;
import com.ericsson.cac.sprint.shop.workflow.datamodel.ValidateAddressResponse;
import com.ericsson.cac.sprint.shop.workflow.datamodel.ValidatePromoCodeRequest;
import com.ericsson.cac.sprint.shop.workflow.datamodel.ValidatePromoCodeResponse;
import com.ericsson.cac.sprint.shop.workflow.util.CommonUtil;
import com.sprint.integration.common.header.wsmessageheader.v2.TrackingMessageHeaderType;
import com.sprint.integration.common.header.wsmessageheader.v2.WsMessageHeaderType;
import com.sprint.integration.eai.services.addressmanagementservice.v1.addressmanagementservice.SoapFault;
import com.sprint.integration.eai.services.promotionmanagementservice.v1.promotionmanagementservice.SoapFaultV2;
import com.sprint.integration.eai.services.querycsa.v1.querycsa.Faultmessage2;
import com.sprint.integration.eai.services.subscribermanagementservice.v1.subscribermanagementservice.FaultMessage;
import com.sprint.integration.interfaces.calculateprepaidinvoicetax.v1.calculateprepaidinvoicetax.CalculatePrepaidInvoiceTaxRequestType;
import com.sprint.integration.interfaces.calculateprepaidinvoicetax.v1.calculateprepaidinvoicetax.CalculatePrepaidInvoiceTaxResponseType;
import com.sprint.integration.interfaces.calculateprepaidinvoicetax.v1.calculateprepaidinvoicetax.InvoiceLineInfoType;
import com.sprint.integration.interfaces.calculateprepaidinvoicetax.v1.calculateprepaidinvoicetax.LocationInfoType;
import com.sprint.integration.interfaces.querycsa.v2.querycsav2.CoverageQualityType;
import com.sprint.integration.interfaces.querycsa.v2.querycsav2.GeocodingTypeCodeType;
import com.sprint.integration.interfaces.querycsa.v2.querycsav2.PostalCodeType;
import com.sprint.integration.interfaces.querycsa.v2.querycsav2.QueryCsaV2ResponseType;
import com.sprint.integration.interfaces.querycsa.v2.querycsav2.QueryCsaV2Type;
import com.sprint.integration.interfaces.submitequipmentpurchaseorder.v1.submitequipmentpurchaseorder.AccountTypeInfoType;
import com.sprint.integration.interfaces.submitequipmentpurchaseorder.v1.submitequipmentpurchaseorder.AddressInfoType;
import com.sprint.integration.interfaces.submitequipmentpurchaseorder.v1.submitequipmentpurchaseorder.AuthorizationInfoType;
import com.sprint.integration.interfaces.submitequipmentpurchaseorder.v1.submitequipmentpurchaseorder.BillingInfoType;
import com.sprint.integration.interfaces.submitequipmentpurchaseorder.v1.submitequipmentpurchaseorder.CallingApplicationInfoType;
import com.sprint.integration.interfaces.submitequipmentpurchaseorder.v1.submitequipmentpurchaseorder.DevicePaymentInfoType;
import com.sprint.integration.interfaces.submitequipmentpurchaseorder.v1.submitequipmentpurchaseorder.EppPaymentInfoType;
import com.sprint.integration.interfaces.submitequipmentpurchaseorder.v1.submitequipmentpurchaseorder.EquipmentOrderLineInfoType;
import com.sprint.integration.interfaces.submitequipmentpurchaseorder.v1.submitequipmentpurchaseorder.EquipmentOrderLineListType;
import com.sprint.integration.interfaces.submitequipmentpurchaseorder.v1.submitequipmentpurchaseorder.NameInfoType;
import com.sprint.integration.interfaces.submitequipmentpurchaseorder.v1.submitequipmentpurchaseorder.OrderInfoType;
import com.sprint.integration.interfaces.submitequipmentpurchaseorder.v1.submitequipmentpurchaseorder.OrderLineInfoType;
import com.sprint.integration.interfaces.submitequipmentpurchaseorder.v1.submitequipmentpurchaseorder.PaymentAccountInfoType;
import com.sprint.integration.interfaces.submitequipmentpurchaseorder.v1.submitequipmentpurchaseorder.PaymentCardInfoType;
import com.sprint.integration.interfaces.submitequipmentpurchaseorder.v1.submitequipmentpurchaseorder.PhoneNumberInfoType;
import com.sprint.integration.interfaces.submitequipmentpurchaseorder.v1.submitequipmentpurchaseorder.ShippingInfoType;
import com.sprint.integration.interfaces.submitequipmentpurchaseorder.v1.submitequipmentpurchaseorder.SubmitEquipmentPurchaseOrderRequestType;
import com.sprint.integration.interfaces.submitequipmentpurchaseorder.v1.submitequipmentpurchaseorder.SubmitEquipmentPurchaseOrderResponseType;
import com.sprint.integration.interfaces.submitequipmentpurchaseorder.v1.submitequipmentpurchaseorder.TaxTransactionInfoType;
import com.sprint.integration.interfaces.submitequipmentpurchaseorder.v1.submitequipmentpurchaseorder.TransactionTypeCodeType;
import com.sprint.integration.interfaces.validateaddress.v1.validateaddress.RequestAddressType;
import com.sprint.integration.interfaces.validateaddress.v1.validateaddress.ValidateAddressResponseType;
import com.sprint.integration.interfaces.validateaddress.v1.validateaddress.ValidateAddressType;
import com.sprint.integration.interfaces.validatepromotioninfo.v1.validatepromotioninfo.PromotionInfoType;
import com.sprint.integration.interfaces.validatepromotioninfo.v1.validatepromotioninfo.RequestItemInfoType;
import com.sprint.integration.interfaces.validatepromotioninfo.v1.validatepromotioninfo.RequestItemListType;
import com.sprint.integration.interfaces.validatepromotioninfo.v1.validatepromotioninfo.ResponseItemInfoType;
import com.sprint.integration.interfaces.validatepromotioninfo.v1.validatepromotioninfo.ValidatePromotionInfoRequestType;
import com.sprint.integration.interfaces.validatepromotioninfo.v1.validatepromotioninfo.ValidatePromotionInfoResponseType;

@Component
public class ShopEaiWorkflowImpl implements ShopEaiWorkflow {

	@Autowired
	private PromotionManagementProxyService promotionManagementProxyService;

	@Autowired
	private QueryCsaProxyService queryCsaProxyService;
	@Autowired
	TaxCalculationProxyService taxCalculationProxyService;
	@Autowired
	private AddressManagementProxyService addressManagementProxyService;

	@Autowired
	private SubscriberManagementProxyService subscriberManagementProxyService;

	@Autowired
	private ShopEppWorkFlowImpl shopEppWorkFlowImpl;

	@Value("${shop.workflow.seccess}")
	String successDesc;
	@Value("${shop.workflow.failure}")
	String failureDesc;
	
	@Value("${shop.workflow.failureForAdderss}")
	String failureDescForAdderss;

	@Value("${shop.workflow.trackingHeadAppId}")
	private String trackingHeadAppId;
	@Value("${shop.workflow.trackingHeadAppUsrId}")
	private String trackingHeadAppUsrId;
	@Value("${shop.workflow.trackingHeadConsumerId}")
	private String trackingHeadConsumerId;
	@Value("${shop.workflow.trackingHeadTimeToLive}")
	private String trackingHeadTimeToLive;
	@Value("${shop.workflow.trackingHeadMessageId}")
	private String trackingHeadMessageId;
	@Value("${shop.workflow.trackingHeadConversationId}")
	private String trackingHeadConversationId;
	@Value("${shop.workflow.trackingHeadFailAppId}")
	private String trackingHeadFailAppId;
	@Value("${shop.workflow.trackingHeadFailAppUsrId}")
	private String trackingHeadFailAppUsrId;
	@Value("${shop.workflow.trackingHeadFailConsumerId}")
	private String trackingHeadFailConsumerId;
	@Value("${shop.workflow.trackingHeadFailTimeToLive}")
	private String trackingHeadFailTimeToLive;
	@Value("${shop.workflow.vendorCode}")
	private String vendorCode;
	@Value("${shop.workflow.salesChannel}")
	private String salesChannel;
	@Value("${shop.workflow.accountType}")
	private String accountType;
	@Value("${shop.workflow.accountSubType}")
	private String accountSubType;

	@Value("${shop.workflow.eai.productCategory}")
	private String calculateTaxProductCategory;
	@Value("${shop.workflow.orderTypeCode}")
	private String eaiSalesOrderTypeCode;
	@Value("${shop.workflow.dealerCode}")
	private String eaiDealerCode;
	@Value("${shop.workflow.eai.vendorCode}")
	private String eaiVendorCode;
	@Value("${shop.workflow.eai.salesChannel}")
	private String eaiSalesChannel;
	@Value("${shop.workflow.eai.shippingCode.ground}")
	private String groundShippingCode;
	@Value("${shop.workflow.eai.shippingFee.ground}")
	private String groundShippingFee;
	@Value("${shop.workflow.eai.shippingCode.air}")
	private String airShippingCode;
	@Value("${shop.workflow.eai.shippingFee.air}")
	private String airShippingFee;
	
	
	
	@Value("${shop.workflow.submitorder.trackingHeadAppId}")
	private String trackingorderHeadAppId;
	@Value("${shop.workflow.submitorder.trackingHeadAppUsrId}")
	private String trackingorderHeadAppUsrId;
	@Value("${shop.workflow.submitorder.trackingHeadConsumerId}")
	private String trackingorderHeadConsumerId;
	@Value("${shop.workflow.submitorder.trackingHeadTimeToLive}")
	private String trackingorderHeadTimeToLive;
	@Value("${shop.workflow.submitorder.trackingHeadMessageId}")
	private String trackingorderHeadMessageId;
	@Value("${shop.workflow.submitorder.trackingHeadConversationId}")
	private String trackingorderHeadConversationId;
	@Value("${shop.workflow.submitorder.trackingHeadFailAppId}")
	private String trackingorderHeadFailAppId;
	@Value("${shop.workflow.submitorder.trackingHeadFailAppUsrId}")
	private String trackingorderHeadFailAppUsrId;
	@Value("${shop.workflow.submitorder.trackingHeadFailConsumerId}")
	private String trackingorderHeadFailConsumerId;
	@Value("${shop.workflow.submitorder.trackingHeadFailTimeToLive}")
	private String trackingorderHeadFailTimeToLive;
	@Value("${shop.workflow.problemProcessingOrder}")
	private String problemProcessingOrder;
	@Value("${shop.workflow.problemValidatingPromocode}")
	private String problemValidatingPromocode;
	@Value("${shop.workflow.calculateTax}")
	private String calculateTax;
	@Value("${shop.workflow.zipCodeNotFound}")
	private String zipCodeNotFound;
	/*
	 * @Value("${TaxCalculationService.calculateTaxTrackingHeadAppId}") private
	 * String calculateTaxTrackingHeadAppId;
	 * 
	 * @Value("${TaxCalculationService.calculateTaxTrackingHeadAppUsrId}")
	 * private String calculateTaxTrackingHeadAppUsrId;
	 * 
	 * @Value("${TaxCalculationService.calculateTaxTrackingHeadConsumerId}")
	 * private String calculateTaxTrackingHeadConsumerId;
	 * 
	 * @Value("${TaxCalculationService.calculateTaxTrackingHeadTimeToLive}")
	 * private String calculateTaxTrackingHeadTimeToLive;
	 * 
	 * @Value("${TaxCalculationService.calculateTaxTrackingHeadMessageId}")
	 * private String calculateTaxTrackingHeadMessageId;
	 * 
	 * @Value("${TaxCalculationService.calculateTaxTrackingHeadConversationId}")
	 * private String calculateTaxTrackingHeadConversationId;
	 * 
	 * @Value("${TaxCalculationService.calculateTaxTrackingHeadFailAppId}")
	 * private String calculateTaxTrackingHeadFailAppId;
	 * 
	 * @Value("${TaxCalculationService.calculateTaxTrackingHeadFailAppUsrId}")
	 * private String calculateTaxTrackingHeadFailAppUsrId;
	 * 
	 * @Value("${TaxCalculationService.calculateTaxTrackingHeadFailConsumerId}")
	 * private String calculateTaxTrackingHeadFailConsumerId;
	 * 
	 * @Value("${TaxCalculationService.calculateTaxTrackingHeadFailTimeToLive}")
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ShopEaiWorkflowImpl.class);

	public ValidatePromoCodeResponse validatePromoCode(
			ValidatePromoCodeRequest request) {
		LOGGER.info("Calling worflow validatePromoCode method");
		
		Holder<WsMessageHeaderType> head = populateHeader();

		ValidatePromotionInfoRequestType validatePromotionInfoRequest = new ValidatePromotionInfoRequestType();
		// Holder<WsMessageHeaderType> head = new Holder<WsMessageHeaderType>();
		PromotionInfoType promotionInfoType = new PromotionInfoType();
		promotionInfoType.setPromoCode(request.getPromoCode());
		promotionInfoType.setCustomerType(request.getCustomerType());

		ValidatePromotionInfoResponseType validatePromotionInfoResponseType = new ValidatePromotionInfoResponseType();

		ValidatePromoCodeResponse validatePromoCodeResponse = new ValidatePromoCodeResponse();

		if (promotionInfoType.getPromoCode() != null
				&& promotionInfoType.getCustomerType() != null) {
			validatePromotionInfoRequest.setPromotionInfo(promotionInfoType);
		}
		if (request.getEquipments() != null && request.getEquipments().length>0) {
			RequestItemListType requestItemListType = new RequestItemListType();
			for(Equipment equipment:request.getEquipments())
			{
				RequestItemInfoType requestItemInfoType = new RequestItemInfoType();
				requestItemInfoType.setItemId(equipment.getModelId());
				requestItemInfoType.setPricePerItemId(BigDecimal.valueOf(equipment.getModelPrice()));
				requestItemInfoType.setQuantity(BigInteger.valueOf(equipment.getQuantity()));
				
				requestItemListType.getItemIdInfo().add(requestItemInfoType);
			}
			validatePromotionInfoRequest.setItemIdList(requestItemListType);
		}

		try {

			validatePromotionInfoResponseType = promotionManagementProxyService
					.validatePromotionInfo(validatePromotionInfoRequest, head);

			validatePromoCodeResponse
					.setShippingFee(String
							.valueOf(validatePromotionInfoResponseType
									.getShippingFee()));
			validatePromoCodeResponse.setTotalOriginalPrice(String
					.valueOf(validatePromotionInfoResponseType
							.getTotalOriginalPrice()));
			validatePromoCodeResponse.setTotalDiscountedPrice(String
					.valueOf(validatePromotionInfoResponseType
							.getTotalDiscountedPrice()));
			validatePromoCodeResponse
					.setPromoLegalese(validatePromotionInfoResponseType
							.getPromoLegalese());
			validatePromoCodeResponse
					.setSuccessMessage(validatePromotionInfoResponseType
							.getSuccessMessage());
			List<DiscountItem> discounts = new ArrayList<DiscountItem>();
			if(validatePromotionInfoResponseType.getItemIdList()!=null && validatePromotionInfoResponseType.getItemIdList().getItemIdInfo()!=null){
				for(ResponseItemInfoType responseItem:validatePromotionInfoResponseType.getItemIdList().getItemIdInfo()){
					DiscountItem item = new DiscountItem();
					item.setItemId(responseItem.getItemId());
					item.setQuantity(responseItem.getQuantity());
					item.setEligibleQuantity(responseItem.getEligibleQuantity());
					item.setOriginalPricePerItemId(responseItem.getOriginalPricePerItemId());
					item.setDiscountAmount(responseItem.getDiscountAmount());
					item.setPromotionName(responseItem.getPromotionName());
					discounts.add(item);
				}
			}
			if(discounts.size()>0){
				validatePromoCodeResponse.setDiscounts(discounts.toArray(new DiscountItem[discounts.size()]));
			}

			validatePromoCodeResponse.setStatus(0);
			validatePromoCodeResponse.setDescription(successDesc);

		} catch (SoapFaultV2 e) {			
			LOGGER.error("WSException_Exception occured in Validate PromoCode" +e.getFaultInfo().getProviderError().get(0).getProviderErrorText() );
			LOGGER.error("Error Trace :: ",e);
			validatePromoCodeResponse.setStatus(ShopWorkFlowConstants.WS_ERROR_CODE);
			validatePromoCodeResponse.setDescription(problemValidatingPromocode);
		}
		catch (Exception e)
		{
			validatePromoCodeResponse.setStatus(ShopWorkFlowConstants.WS_ERROR_CODE);
			validatePromoCodeResponse.setDescription(problemProcessingOrder);
		}
		return validatePromoCodeResponse;
	}

	private Holder<WsMessageHeaderType> populateHeader() {
		WsMessageHeaderType successHead = new WsMessageHeaderType();
		TrackingMessageHeaderType trackingHead = new TrackingMessageHeaderType();
		trackingHead.setApplicationId(trackingHeadAppId);
		trackingHead.setApplicationUserId(trackingHeadAppUsrId);
		trackingHead.setConsumerId(trackingHeadConsumerId);
		trackingHead.setTimeToLive(new BigInteger(trackingHeadTimeToLive));
		trackingHead.setMessageId(trackingHeadMessageId);
		trackingHead.setConversationId(trackingHeadConversationId);
		//trackingHead.setMessageDateTimeStamp(CommonUtil.getCurrentDateInFormat());
		GregorianCalendar c = new GregorianCalendar();
		try {
			trackingHead.setMessageDateTimeStamp(DatatypeFactory.newInstance()
					.newXMLGregorianCalendar(c));
		} catch (DatatypeConfigurationException e1) {
			e1.printStackTrace();
		}
		successHead = new WsMessageHeaderType();
		successHead.setTrackingMessageHeader(trackingHead);
		Holder<WsMessageHeaderType> head = new Holder<WsMessageHeaderType>(
				successHead);
		return head;
	}
	
	private Holder<WsMessageHeaderType> submitorderpopulateHeader() {
		WsMessageHeaderType successHead = new WsMessageHeaderType();
		TrackingMessageHeaderType trackingHead = new TrackingMessageHeaderType();
		trackingHead.setApplicationId(trackingorderHeadAppId);
		trackingHead.setApplicationUserId(trackingorderHeadAppUsrId);
		trackingHead.setConsumerId(trackingorderHeadConsumerId);
		trackingHead.setTimeToLive(new BigInteger(trackingorderHeadTimeToLive));
		trackingHead.setMessageId(trackingorderHeadMessageId);
		trackingHead.setConversationId(trackingorderHeadConversationId);
		trackingHead.setMessageDateTimeStamp(CommonUtil.getCurrentDateInSubmitOrderFormat());
		/*GregorianCalendar c = new GregorianCalendar();
		try {
			trackingHead.setMessageDateTimeStamp(DatatypeFactory.newInstance()
					.newXMLGregorianCalendar(c));
		} catch (DatatypeConfigurationException e1) {
			e1.printStackTrace();
		}*/
		successHead = new WsMessageHeaderType();
		successHead.setTrackingMessageHeader(trackingHead);
		Holder<WsMessageHeaderType> head = new Holder<WsMessageHeaderType>(
				successHead);
		return head;
	}
	

	public CoverageAreaResponse checkCoverageArea(CoverageAreaRequest request) {

		LOGGER.info("Calling workflow checkCoverageArea method");
		
		QueryCsaV2Type body = new QueryCsaV2Type();
		Holder<WsMessageHeaderType> head = populateHeader();
		PostalCodeType postalCodeType = new PostalCodeType();
		postalCodeType.setUspsPostalCd(request.getShippingInfo().getZipCode());
		QueryCsaV2ResponseType queryCsaV2ResponseType = new QueryCsaV2ResponseType();
		CoverageAreaResponse coverageAreaResponse = new CoverageAreaResponse();

		try {
			body.setGeoCode(GeocodingTypeCodeType.ZIP);
			body.setZip(postalCodeType);
			queryCsaV2ResponseType = queryCsaProxyService
					.queryCsaV2(body, head);

			if (queryCsaV2ResponseType.getCoverageQualityCdma().equals(
					CoverageQualityType.NO_COVERAGE)
					&& queryCsaV2ResponseType.getCoverageQualityIden().equals(
							CoverageQualityType.NO_COVERAGE)) {
				coverageAreaResponse.setCovered(false);
			} else {
				coverageAreaResponse.setCovered(true);
			}

			LOGGER.info(request.getShippingInfo().getZipCode()+":: is Pincode covered ::" + coverageAreaResponse.getCovered());
			
			coverageAreaResponse.setZipcode(queryCsaV2ResponseType.getZip()
					.getUspsPostalCd());
			coverageAreaResponse
					.setStatus(ShopWorkFlowConstants.WS_SUCCESS_CODE);
			coverageAreaResponse.setDescription(successDesc);

		} catch (Faultmessage2 e) {
			LOGGER.error("Faultmessage2 Exception occured in Check Coverage" +e.getFaultInfo().getProviderError().get(0).getProviderErrorText() );
			LOGGER.error("Error Trace :: ",e);
			coverageAreaResponse.setStatus(ShopWorkFlowConstants.WS_ERROR_CODE);
			coverageAreaResponse.setDescription(e.getFaultInfo().getProviderError().get(0).getProviderErrorText()==null?problemProcessingOrder:e.getFaultInfo().getProviderError().get(0).getProviderErrorText());
			if(coverageAreaResponse.getDescription().equalsIgnoreCase("Could not geocode to Zip.")){
				coverageAreaResponse.setDescription(zipCodeNotFound);
			}
			
		} catch (SoapFaultException e) {
			LOGGER.error("SoapFaultException occured",e);
			coverageAreaResponse.setStatus(ShopWorkFlowConstants.WS_ERROR_CODE);
			coverageAreaResponse.setDescription(e.getFaultString()==null?problemProcessingOrder:e.getFaultString());			
		} catch (Exception e) {
			LOGGER.error("WSException_Exception occured",e);
			coverageAreaResponse.setStatus(ShopWorkFlowConstants.WS_ERROR_CODE);
			coverageAreaResponse.setDescription(problemProcessingOrder);			
		}

		return coverageAreaResponse;
	}

	public ValidateAddressResponse validateAddress(
			ValidateAddressRequest request) {

		LOGGER.info("Calling workflow validateAddress method");
		
		ValidateAddressType validateAddressType = new ValidateAddressType();

		RequestAddressType addressInfo = new RequestAddressType();
		addressInfo.setCountry(ShopWorkFlowConstants.DEFAULT_COUNTRY);
		addressInfo.setAddressLine1(request.getShippingInfo().getAddress1());
		addressInfo.setAddressLine2(request.getShippingInfo().getAddress2());
		addressInfo.setCity(request.getShippingInfo().getCity());
		addressInfo.setState(request.getShippingInfo().getState());
		addressInfo.setZipCode(request.getShippingInfo().getZipCode());

		validateAddressType.setAddressInfo(addressInfo);

		Holder<WsMessageHeaderType> head = populateHeader();

		ValidateAddressResponse validateAddressResponse = new ValidateAddressResponse();

		try {
			ValidateAddressResponseType validateAddressResponseType = addressManagementProxyService
					.validateAddress(validateAddressType, head);

			validateAddressResponse.setAddressType(validateAddressResponseType
					.getValidatedAddressList().getValidatedAddressInfo().get(0)
					.getAddressType());
			validateAddressResponse.setConfidence(Integer
					.parseInt(validateAddressResponseType
							.getValidatedAddressList()
							.getValidatedAddressInfo().get(0)
							.getAdditionalAddressInfo().getConfidence()));

			LOGGER.debug("Incoming Confidence value is  :: "
					+ validateAddressResponse.getConfidence());
			if (validateAddressResponse.getConfidence() > ShopWorkFlowConstants.CONFIGURABLE_CONFOIDENCE) {
				LOGGER.info("Input Address exist");
				validateAddressResponse.setAddressExist(true);
				validateAddressResponse
				.setStatus(ShopWorkFlowConstants.WS_SUCCESS_CODE);
		validateAddressResponse.setDescription(successDesc);
			} else {
				LOGGER.info("Input Address does not exist");
				validateAddressResponse.setAddressExist(false);
				validateAddressResponse
				.setStatus(ShopWorkFlowConstants.WS_ERROR_CODE);		
		validateAddressResponse.setDescription(failureDescForAdderss);
			}
			

		}

		catch (SoapFault e) {
			LOGGER.error("Exception occured in Valiadle Address" +e.getFaultInfo().getProviderError().get(0).getProviderErrorText() );
			LOGGER.error("Error Trace :: ");
			validateAddressResponse
					.setStatus(ShopWorkFlowConstants.WS_ERROR_CODE);
			validateAddressResponse.setDescription(e.getFaultInfo().getProviderError().get(0).getProviderErrorText()==null?problemProcessingOrder:e.getFaultInfo().getProviderError().get(0).getProviderErrorText());
		} catch (Exception e) {
			LOGGER.error("WSException_Exception occured");
			validateAddressResponse
					.setStatus(ShopWorkFlowConstants.WS_ERROR_CODE);
			validateAddressResponse.setDescription(problemProcessingOrder);
		}

		return validateAddressResponse;
	}

	public SubmitOrderResponse submitOrder(SubmitOrderRequest request) {
		
		LOGGER.info("Calling workflow SubmitOrder method");

		SubmitEquipmentPurchaseOrderRequestType submitEquipmentPurchaseOrderRequest = new SubmitEquipmentPurchaseOrderRequestType();
		Holder<WsMessageHeaderType> head = submitorderpopulateHeader();
		
		SubmitOrderResponse submitOrderResponse = new SubmitOrderResponse();

		SubmitEquipmentPurchaseOrderResponseType submitEquipmentPurchaseOrderResponseType = new SubmitEquipmentPurchaseOrderResponseType();
		try {
			CallingApplicationInfoType callingApplicationInfoType = new CallingApplicationInfoType();
			OrderInfoType orderInfoType = new OrderInfoType();
			AccountTypeInfoType accountTypeInfoType = new AccountTypeInfoType();
			ShippingInfoType shippingInfoType = new ShippingInfoType();
			NameInfoType nameInfoTypeShip = new NameInfoType();
			AddressInfoType addressInfoTypeShip = new AddressInfoType();
			PhoneNumberInfoType phoneNumberInfoTypeShip = new PhoneNumberInfoType();
			NameInfoType nameInfoTypeBil = new NameInfoType();
			AddressInfoType addressInfoTypeBil = new AddressInfoType();
			PhoneNumberInfoType phoneNumberInfoTypeBil = new PhoneNumberInfoType();
			OrderLineInfoType orderLineInfoType = new OrderLineInfoType();
			DevicePaymentInfoType devicePaymentInfoType = new DevicePaymentInfoType();
			EquipmentOrderLineListType equipmentOrderLineListType = new EquipmentOrderLineListType();
			BillingInfoType billingInfoType = new BillingInfoType();

				callingApplicationInfoType.setVendorCode(eaiVendorCode);
			
			
				callingApplicationInfoType.setSalesChannel(eaiSalesChannel);
			
			callingApplicationInfoType.setOrderId(request.getOrderId());

			// orderInfoType

			if (request.getAccountType() != null
					&& !request.getAccountType().trim().equals("")) {
				accountTypeInfoType.setAccountType(request.getAccountType());
			} else {
				accountTypeInfoType.setAccountType(accountType);
			}
			if (request.getAccountSubType() != null
					&& !request.getAccountSubType().trim().equals("")) {
				accountTypeInfoType.setAccountSubType(request
						.getAccountSubType());
			} else {
				accountTypeInfoType.setAccountSubType(accountSubType);
			}
			
			//TODO Check the values 
			/*values to check start----------------------------------------*/
			orderInfoType.setOrderTypeCode(eaiSalesOrderTypeCode);
			orderInfoType.setOrderDate(CommonUtil.getCurrentDateInSubmitOrderFormat());
			orderInfoType.setDealerCode(eaiDealerCode);
			orderInfoType.setPreOrderInd(false);
			/*values to check end----------------------------------------*/
			orderInfoType.setAccountTypeInfo(accountTypeInfoType);

			// shiping

			nameInfoTypeShip.setFirstName(request.getShippingInfo()
					.getFirstName());
			nameInfoTypeShip.setLastName(request.getShippingInfo()
					.getLastName());
			phoneNumberInfoTypeShip.setHomeNumber(request.getShippingInfo()
					.getPhone());
			phoneNumberInfoTypeShip.setWorkNumber(request.getShippingInfo()
					.getPhone());
			addressInfoTypeShip.setStreetAddress1(request.getShippingInfo()
					.getAddress1());
			addressInfoTypeShip.setStreetAddress2(request.getShippingInfo()
					.getAddress2());
			addressInfoTypeShip.setCity(request.getShippingInfo().getCity());
			addressInfoTypeShip.setState(request.getShippingInfo().getState());
			addressInfoTypeShip.setZipCode(request.getShippingInfo()
					.getZipCode());

			shippingInfoType.setNameInfo(nameInfoTypeShip);
			shippingInfoType.setPhoneNumberInfo(phoneNumberInfoTypeShip);
			shippingInfoType.setAddressInfo(addressInfoTypeShip);
			// need to check this field??
			shippingInfoType.setShippingMethod(request.getShippingInfo()
					.getShippingOption());
			shippingInfoType.setShippingTypeCode(new BigInteger(request.getShippingInfo().getShippingType()));
			shippingInfoType.setShippingFee(new BigDecimal(request.getShippingInfo().getShippingFee()));
			// this field is not there?? need to check this field
			// shippingInfoType.setShippingFee(new
			// java.math.BigDecimal("1000"));
			shippingInfoType.setEmailAddress(request.getShippingInfo()
					.getEmail());
			
			//changed by mitul, need to take type and fee from request
//			if(!CommonUtil.isStringNullOfEmpty(shippingMethod)){
//				if(shippingMethod.equalsIgnoreCase(ShopWorkFlowConstants.SHIPPING_TYPE_GROUND)){
//					shippingInfoType.setShippingTypeCode(BigInteger.valueOf(Long.parseLong(groundShippingCode)));
//					shippingInfoType.setShippingFee(BigDecimal.valueOf(Double.valueOf(groundShippingFee)));
//				}else if(shippingMethod.equalsIgnoreCase(ShopWorkFlowConstants.SHIPPING_TYPE_AIR)){
//					shippingInfoType.setShippingTypeCode(BigInteger.valueOf(Long.parseLong(airShippingCode)));
//					shippingInfoType.setShippingFee(BigDecimal.valueOf(Double.valueOf(airShippingFee)));
//				}
//			}
			
			// billingInfoType

			nameInfoTypeBil.setFirstName(request.getBillingInfo()
					.getFirstName());
			nameInfoTypeBil.setLastName(request.getBillingInfo().getLastName());
			phoneNumberInfoTypeBil.setHomeNumber(request.getBillingInfo()
					.getPhone());
			phoneNumberInfoTypeBil.setWorkNumber(request.getBillingInfo()
					.getPhone());
			addressInfoTypeBil.setStreetAddress1(request.getBillingInfo()
					.getAddress1());
			addressInfoTypeBil.setStreetAddress2(request.getBillingInfo()
					.getAddress2());
			addressInfoTypeBil.setCity(request.getBillingInfo().getCity());
			addressInfoTypeBil.setState(request.getBillingInfo().getState());
			addressInfoTypeBil
					.setZipCode(request.getBillingInfo().getZipCode());

			billingInfoType.setNameInfo(nameInfoTypeBil);
			billingInfoType.setPhoneNumberInfo(phoneNumberInfoTypeBil);
			billingInfoType.setAddressInfo(addressInfoTypeBil);
			billingInfoType
					.setEmailAddress(request.getBillingInfo().getEmail());
			// TODO
			AuthorizationInfoType authorizationInfoType = new AuthorizationInfoType();
			authorizationInfoType.setAuthorizationCode(request.getAuthorizationCode());
			authorizationInfoType.setEppPaymentId(request.getEppPaymentId());
			
			PaymentAccountInfoType paymentAccountInfoType = new PaymentAccountInfoType();
			PaymentCardInfoType cardInfoType = new PaymentCardInfoType();
			
			//TODO check the value
			cardInfoType.setPaymentCardType(paymentCardTypeFromCardType(request.getPaymentInfo().getPaymentCardType()));
			//cardInfoType.setPaymentCardType(request.getPaymentInfo().getCardType());
			cardInfoType.setPaymentCardHolderName(request.getBillingInfo().getFirstName()+ShopWorkFlowConstants.WHITESPACE+request.getBillingInfo().getLastName());
			cardInfoType.setPaymentCardExpirationDate(CommonUtil.getCurrentDateInFormat(request.getPaymentInfo().getExpirationYear()+ShopWorkFlowConstants.HYPHEN
					+request.getPaymentInfo().getExpirationMonth(), "yyyy-MM"));
			paymentAccountInfoType.setPaymentCardInfo(cardInfoType);			
			devicePaymentInfoType.setPaymentAccountInfo(paymentAccountInfoType);
			devicePaymentInfoType.setAuthorizationInfo(authorizationInfoType);
			devicePaymentInfoType.setPaymentType(ShopWorkFlowConstants.PAYMENT_TYPE);
			EppPaymentInfoType eppPaymentInfo = new EppPaymentInfoType();
			EPPPaymentInfo modelPayementInfo=request.getPaymentType();
			eppPaymentInfo.setActorId(modelPayementInfo.getActorId());
			eppPaymentInfo.setChannelId(modelPayementInfo.getChannelId());
			eppPaymentInfo.setExternalOrderId(modelPayementInfo.getExternalOrderId());
			eppPaymentInfo.setGlobalTokenId(modelPayementInfo.getTokenId());
			eppPaymentInfo.setTokenId(modelPayementInfo.getTokenId());
			eppPaymentInfo.setTokenType(TransactionTypeCodeType.valueOf(modelPayementInfo.getTokenType()));
			
			devicePaymentInfoType.setEppPaymentInfo(eppPaymentInfo);
			Equipment[] equipment = request.getOrderLineInfo().getEquipments();

			for (int i = 0; i < equipment.length; i++) {
				TaxTransactionInfoType taxTransactionInfoType = new TaxTransactionInfoType();
				EquipmentOrderLineInfoType equipmentOrderLineInfoType = new EquipmentOrderLineInfoType();
				//TODO check value --------------------
				equipmentOrderLineInfoType.setAccessoryInd(false);
				equipmentOrderLineInfoType.setEquipmentTaxAmt(BigDecimal.valueOf(8.00));
				//------------------------------------------------
				equipmentOrderLineInfoType
						.setModelId(equipment[i].getModelId());
				equipmentOrderLineInfoType.setModelPrice(BigDecimal
						.valueOf(equipment[i].getModelPrice()));
				equipmentOrderLineInfoType.setOrderLineId(equipment[i]
						.getOrderLineId());
				/*taxTransactionInfoType.setTaxTransactionId(BigInteger
						.valueOf(Long.parseLong(equipment[i]
								.getTaxTransactionId())));*/
				taxTransactionInfoType.setTaxTransactionId(equipment[i].getTaxTransactionId());
				taxTransactionInfoType.setInvoiceDate(CommonUtil.getDateInFormat(equipment[i].getInvoiceDate(), "yyyy-MM-DD"));
				// need to convert
				equipmentOrderLineInfoType
						.setTaxTransactionInfo(taxTransactionInfoType);
				equipmentOrderLineListType.getEquipmentOrderLineInfo().add(
						equipmentOrderLineInfoType);
			}
			orderLineInfoType.setOrderLineList(equipmentOrderLineListType);
			orderLineInfoType.setOrderSubTotalAmount(BigDecimal.valueOf(request
					.getOrderLineInfo().getSubTotalAmount()));
			orderLineInfoType.setOrderTaxAmount(BigDecimal.valueOf(request
					.getOrderLineInfo().getTaxAmount()));
			orderLineInfoType.setOrderTotalAmount(BigDecimal.valueOf(request
					.getOrderLineInfo().getTotalAmount()));
			
			// setting in submitEquipmentPurchaseOrderRequest
			submitEquipmentPurchaseOrderRequest.setBillinginfo(billingInfoType);
			submitEquipmentPurchaseOrderRequest.setOrderInfo(orderInfoType);
			submitEquipmentPurchaseOrderRequest
					.setShippingInfo(shippingInfoType);
			submitEquipmentPurchaseOrderRequest
					.setDevicePaymentInfo(devicePaymentInfoType);
			submitEquipmentPurchaseOrderRequest
					.setCallingApplicationInfo(callingApplicationInfoType);
			submitEquipmentPurchaseOrderRequest
					.setOrderLineInfo(orderLineInfoType);
			submitEquipmentPurchaseOrderResponseType = subscriberManagementProxyService
					.submitEquipmentPurchaseOrder(
							submitEquipmentPurchaseOrderRequest, head);
			if(submitEquipmentPurchaseOrderResponseType!=null && submitEquipmentPurchaseOrderResponseType.getFastOrderkey()!=null){
				submitOrderResponse
				.setFastOrderkey(submitEquipmentPurchaseOrderResponseType
						.getFastOrderkey());
				LOGGER.info("Order Processed with firstorder key::"+submitOrderResponse.getFastOrderkey());
				submitOrderResponse.setStatus(ShopWorkFlowConstants.WS_SUCCESS_CODE);
				submitOrderResponse.setDescription(successDesc);
	
			}else{
				LOGGER.info("error in Order Processing");
				submitOrderResponse.setStatus(ShopWorkFlowConstants.WS_ERROR_CODE);
				submitOrderResponse.setDescription(problemProcessingOrder);
			}
			} catch (FaultMessage e) {
			LOGGER.error("Error Occured while calling for submitorder" + e.getFaultInfo().getProviderError().get(0).getProviderErrorText());
			LOGGER.error("Error Trace:: ",e);
			System.out.println("Error Occured while calling for submitorder" + e.getFaultInfo().getProviderError().get(0).getProviderErrorText());
			submitOrderResponse.setStatus(ShopWorkFlowConstants.WS_ERROR_CODE);
			submitOrderResponse.setDescription(e.getFaultInfo().getProviderError().get(0).getProviderErrorText()==null?problemProcessingOrder:e.getFaultInfo().getProviderError().get(0).getProviderErrorText());
		}catch (Exception e) {
			LOGGER.error("Error Trace:: ",e);
			submitOrderResponse.setStatus(ShopWorkFlowConstants.WS_ERROR_CODE);
			submitOrderResponse.setDescription(problemProcessingOrder);
		}
		return submitOrderResponse;
	}

	/**
	 * This Method calculates tax for the entered shipping address
	 * 
	 * */

	public CalculateTaxResponse calculateTax(CalculateTaxRequest request) {
		
		LOGGER.info("Calling worflow calculatetax method");

		CalculateTaxResponse calculateTaxResponse = new CalculateTaxResponse();
		CalculatePrepaidInvoiceTaxRequestType calculatePrepaidInvoiceTaxRequest = new CalculatePrepaidInvoiceTaxRequestType();
		com.sprint.integration.interfaces.calculateprepaidinvoicetax.v1.calculateprepaidinvoicetax.AddressInfoType addressInfoType = new com.sprint.integration.interfaces.calculateprepaidinvoicetax.v1.calculateprepaidinvoicetax.AddressInfoType();
		LocationInfoType locationInfoType = new LocationInfoType();
		InvoiceLineInfoType invoiceLineInfoType = new InvoiceLineInfoType();
		CalculatePrepaidInvoiceTaxResponseType responseType = null;
		BigDecimal taxableAmount = null;
		if (request.getTaxOnAmount() != null) {
			taxableAmount = BigDecimal.valueOf(request.getTaxOnAmount());
		}
		/*
		 * //TODO invoiceLineInfoType.setCallingTransactionId(null);
		 */
		// TODO
		invoiceLineInfoType.setInvoiceLineNumber(BigInteger.valueOf(request
				.getLineNumber()));
		// TODO
		invoiceLineInfoType.setInvoiceNumber(request.getInvoiceNumber());
		// TODO
		/* invoiceLineInfoType.setProductText(null); */
		// TODO
		invoiceLineInfoType.setQuantity(BigInteger.valueOf(request
				.getQuantity()));

		invoiceLineInfoType.setInvoiceDate(CommonUtil.getCurrentDateInFormat());
		invoiceLineInfoType.setTaxableAmount(taxableAmount);
		invoiceLineInfoType.setProductCategory(calculateTaxProductCategory);
		// locationInfoType.setZipCode(modelAddressInfo.getZipCode());
		// TODO
		locationInfoType.setZipCode(request.getBillingInfo().getZipCode());
		calculatePrepaidInvoiceTaxRequest
				.setInvoiceLineInfo(invoiceLineInfoType);
		// calculatePrepaidInvoiceTaxRequest.setAddressInfo(addressInfoType);
		Holder<WsMessageHeaderType> head = null;
		addressInfoType.setAcceptanceLocationInfo(locationInfoType);
		calculatePrepaidInvoiceTaxRequest.setAddressInfo(addressInfoType);

		head = populateHeader();
		/*
		 * TrackingMessageHeaderType calculateTaxTrackingHead = new
		 * TrackingMessageHeaderType();
		 * calculateTaxTrackingHead.setApplicationId("hh");
		 * calculateTaxTrackingHead.setApplicationUserId("ss");
		 * calculateTaxTrackingHead.setConsumerId("s");
		 * calculateTaxTrackingHead.setTimeToLive(new BigInteger("300"));
		 * calculateTaxTrackingHead.setMessageId("1234");
		 * calculateTaxTrackingHead.setConversationId("123123");
		 * 
		 * 
		 * try {
		 * calculateTaxTrackingHead.setMessageDateTimeStamp(DatatypeFactory
		 * .newInstance
		 * ().newXMLGregorianCalendar("2015-03-13T16:44:22.093+05:30")); } catch
		 * (DatatypeConfigurationException e1) { // TODO Auto-generated catch
		 * block e1.printStackTrace(); } WsMessageHeaderType successHead = new
		 * WsMessageHeaderType();
		 * successHead.setTrackingMessageHeader(calculateTaxTrackingHead); head
		 * = new Holder<WsMessageHeaderType>(successHead);
		 */

		try {

			responseType = taxCalculationProxyService
					.calculatePrepaidInvoiceTax(
							calculatePrepaidInvoiceTaxRequest, head);
			if (responseType != null) {
				
				calculateTaxResponse.setInvoiceNumber(request
						.getInvoiceNumber());
				calculateTaxResponse.setLineNumber(request.getLineNumber());
				calculateTaxResponse.setTaxAmount(responseType
						.getTotalTaxAmount().doubleValue());
				calculateTaxResponse.setTaxOnAmount(request.getTaxOnAmount());
				calculateTaxResponse.setTotalAmount(responseType
						.getTotalAmount().doubleValue());
				calculateTaxResponse.setTransactionId(responseType.getTransactionId());
				calculateTaxResponse.setDescription(successDesc);
				calculateTaxResponse
						.setStatus(ShopWorkFlowConstants.WS_SUCCESS_CODE);
				LOGGER.info("Calling worflow calculatetax method executed successfully");
			}
			else
			{
				LOGGER.info("Error processing calculatetax");
				calculateTaxResponse.setStatus(ShopWorkFlowConstants.WS_ERROR_CODE);
				calculateTaxResponse.setDescription(problemProcessingOrder);
			}

		} catch (com.sprint.integration.eai.services.taxcalculationservice.v1.taxcalculationservice.Faultmessage2 e) {
			LOGGER.debug("After Exception , Creating taxcalculationservice Response Object");
			LOGGER.error("Exception in Calculate tax :: Message : :" +e.getFaultInfo().getProviderError().get(0).getProviderErrorText());
			LOGGER.error("Error :: ",e);
			calculateTaxResponse.setStatus(ShopWorkFlowConstants.WS_ERROR_CODE);
			calculateTaxResponse.setDescription(e.getFaultInfo().getProviderError().get(0).getProviderErrorText()==null?problemProcessingOrder:e.getFaultInfo().getProviderError().get(0).getProviderErrorText());
		} catch (Throwable e) {
			LOGGER.debug("After Exception , Creating taxcalculationservice Response Object");
			LOGGER.error("Error :: ",e);
			calculateTaxResponse.setStatus(ShopWorkFlowConstants.WS_ERROR_CODE);
			calculateTaxResponse.setDescription(problemProcessingOrder);
		}
		return calculateTaxResponse;
	}
	
	private String paymentCardTypeFromCardType(String fullCardTypeName){
		String singleLetterCardType=null;
		
		if(ShopWorkFlowConstants.VISA_CARDTYPE.equalsIgnoreCase(fullCardTypeName)){
			singleLetterCardType=ShopWorkFlowConstants.VISA_CARDTYPE_FINAL;
		}else if(ShopWorkFlowConstants.AMEX_CARDTYPE.equalsIgnoreCase(fullCardTypeName)){
			singleLetterCardType=ShopWorkFlowConstants.AMEX_CARDTYPE_FINAL;
		}else if(ShopWorkFlowConstants.MASTERCARD_CARDTYPE.equalsIgnoreCase(fullCardTypeName)){
			singleLetterCardType=ShopWorkFlowConstants.MASTERCARD_CARDTYPE_FINAL;
		}else if(ShopWorkFlowConstants.DISCOVER_CARDTYPE.equalsIgnoreCase(fullCardTypeName)){
			singleLetterCardType=ShopWorkFlowConstants.DISCOVER_CARDTYPE_FINAL;
		}else if(ShopWorkFlowConstants.DINERS_CLUB_CARDTYPE.equalsIgnoreCase(fullCardTypeName)){
			singleLetterCardType=ShopWorkFlowConstants.DINERS_CLUB_CARDTYPE_FINAL;
		}else if(ShopWorkFlowConstants.DINERS_CARDTYPE.equalsIgnoreCase(fullCardTypeName)){
			singleLetterCardType=ShopWorkFlowConstants.DINERS_CLUB_CARDTYPE_FINAL;
		}
		return singleLetterCardType;
	}
		
		
	}

