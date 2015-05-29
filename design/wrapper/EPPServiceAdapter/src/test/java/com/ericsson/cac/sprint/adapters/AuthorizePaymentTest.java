package com.ericsson.cac.sprint.adapters;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.UUID;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.amdocs.dc.api.sensitivedetails.BillingAddressType;
import com.amdocs.dc.api.sensitivedetails.CardSensitiveDetails;
import com.amdocs.mfs.api.epp.v1.sprint.baseoperationrequest.CardType;
import com.amdocs.mfs.api.epp.v1.sprint.baseoperationrequest.PaymentRequestDetailBO;
import com.amdocs.mfs.api.epp.v1.sprint.baseoperationrequest.TransactionForConstants;
import com.amdocs.mfs.api.epp.v1.sprint.paymentcommontypes.ActorApp;
import com.amdocs.mfs.api.epp.v1.sprint.paymentcommontypes.Address;
import com.amdocs.mfs.api.epp.v1.sprint.paymentcommontypes.AmountInfo;
import com.amdocs.mfs.api.epp.v1.sprint.paymentcommontypes.BanInfoAC;
import com.amdocs.mfs.api.epp.v1.sprint.paymentcommontypes.BooleanType;
import com.amdocs.mfs.api.epp.v1.sprint.paymentoperationinnerrequestresponsetypes.AuthorizePaymentRequest;
import com.amdocs.mfs.api.epp.v1.sprint.paymentoperationinnerrequestresponsetypes.AuthorizePaymentResponse;
import com.sprint.integration.eai.services.enterprisepaymentservice.v1.enterprisepaymentservice.Faultmessage;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class AuthorizePaymentTest {
	
	private Logger logger = LoggerFactory.getLogger(AuthorizePaymentTest.class);
	@Autowired
    private EPPServiceAdapter service;
    
    @Test
    public void testAuthorizePayment() throws DatatypeConfigurationException, JAXBException, Faultmessage, TransformerFactoryConfigurationError, TransformerException, com.sprint.integration.eai.services.enterprisewalletservice.v1.enterprisewalletservice.Faultmessage {
    	AuthorizePaymentRequest request= new AuthorizePaymentRequest();

		request.setExternalOrderId("KHK_TEST_ORDER_001");
		String requestId = UUID.randomUUID().toString();
		request.setRequestId(requestId);
		request.setTransactionFor(TransactionForConstants.HARD_GOODS);

		ActorApp info = new ActorApp();
		info.setActorChannel("WEB_VMU");
		info.setActorId("249597750");
		// info.setActorId("AIDTest");
		info.setSourceTransactionId("TOP_249597750_1425924217551");
		request.setApplicationInfo(info);

		// PaymentAllocationRequestInfo allocationInfo= new
		// PaymentAllocationRequestInfo();
		// allocationInfo.setAllocationType("a");
		// allocationInfo.setAmount(BigDecimal.valueOf(0));
		// allocationInfo.setEntitySequenceNo(BigInteger.valueOf(0));
		//
		// PaymentAllocationRequestInfoList allocationRequestInfoList = new
		// PaymentAllocationRequestInfoList();
		// allocationRequestInfoList.getPaymentAllocationRequestInfo().add(allocationInfo);

		// AdditionalInfo additionalInfo = new AdditionalInfo();
		// additionalInfo.setBypassRule(BooleanType.TRUE);
		// additionalInfo.setDirectDebitVoucher("directDebitVoucher0");
		// additionalInfo.setEmailId("abc@gmail.com");
		// additionalInfo.setLoanSequenceNumber(BigInteger.valueOf(500000000));
		// additionalInfo.setMerchantId("merchantId0");
		// additionalInfo.setPricePlanSocCode("pricePlanSocCode0");
		// additionalInfo.setPaymentAllocationRequestInfoList(allocationRequestInfoList);

		// request.setAdditionalInformation(additionalInfo);

		PaymentRequestDetailBO paymentRequestDetail = new PaymentRequestDetailBO();
		AmountInfo amounts = new AmountInfo();
		amounts.setTotalAmount(BigDecimal.valueOf(100L));
		amounts.setPostableAmount(BigDecimal.ZERO);

		paymentRequestDetail.setPreOrderToken("Z1TPL3AUR2RR4113");// "GRgbLgtv09yMr8V6Yx0eO1VBImyA0dzp"
		paymentRequestDetail.setAmounts(amounts);
		// paymentRequestDetail.setAchAccountType(ACHAccountType.SAVINGS);

		/*BanInfoAC ban = new BanInfoAC();
		ban.setAccountAge(BigInteger.ONE);
		ban.setBan(new BigInteger("1"));
		ban.setCustomerAccountSubType("C");
		ban.setCustomerAccountType("C");
		ban.setCustomerBrand("Prepaid");
		ban.setCustomerCreditClass("custo");
		ban.setCustomerSubBrand("Postpaid");
		ban.setIsSpendingLimitAccount(BooleanType.FALSE);
		ban.setUsgBan(BooleanType.FALSE);*/
		
		// WalletInfo wallet = new WalletInfo();
		// wallet.setPaymentOptionId("1");
		// wallet.setPaymentOptionToken("1");
		//
		// PaypalInfo payPal= new PaypalInfo();
		// payPal.setPaypalToken("TOKEN");

		Address address = new Address();

		address.setAddressLine1("DOUG");
		address.setAddressLine2("CHASE");
		address.setCity("3 Main St");
		address.setState("NY");
		address.setCountry("us");
		address.setZipCode("10453");

		paymentRequestDetail.setShippingAddress(address);
		paymentRequestDetail.setIsGuestPayment(true);
		paymentRequestDetail.setConsumerChoicePreference(CardType.CREDIT);

		request.setPaymentRequestDetail(paymentRequestDetail);

		CardSensitiveDetails details = new CardSensitiveDetails();

		details.setFirstName("Mitul");
		details.setLastName("Tyagi");
		details.setCardNumber("4112344112344113");
		details.setNameOnCard("Mitul Tyagi");
		details.setExpirationDate("05/17");
		details.setCardVerificationCode("011");

		BillingAddressType type = new BillingAddressType();
		type.setAddressLine1("DOUG");
		type.setAddressLine2("CHASE");
		type.setCity("3 Main St");
		type.setState("NY");
		type.setCountry("us");
		type.setZipCode("10453");

		details.setBillingAddress(type);
        
		AuthorizePaymentResponse authorizePaymentResponse;
		try {
			authorizePaymentResponse = service.authorizePayment(null, request, details);
			Assert.assertEquals("0",authorizePaymentResponse.getResponses().getResponse().getResponseCode());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
        
    }
}
