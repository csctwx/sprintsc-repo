package com.ericsson.cac.sprint.adapters;

import java.io.IOException;
import java.math.BigInteger;

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
import com.amdocs.mfs.api.epp.v1.sprint.baseoperationrequest.TokenTypeConstants;
import com.amdocs.mfs.api.epp.v1.sprint.paymentcommontypes.ActorApp;
import com.amdocs.mfs.api.epp.v1.sprint.paymentcommontypes.BanInfoAC;
import com.amdocs.mfs.api.epp.v1.sprint.paymentcommontypes.BooleanType;
import com.amdocs.mfs.api.epp.v1.sprint.paymentoperationinnerrequestresponsetypes.AuthorizePaymentRequest;
import com.amdocs.mfs.api.epp.v1.sprint.paymentoperationinnerrequestresponsetypes.GeneratePreOrderTokenRequest;
import com.amdocs.mfs.api.epp.v1.sprint.paymentoperationinnerrequestresponsetypes.GeneratePreOrderTokenResponse;
import com.amdocs.mfs.api.epp.v1.sprint.paymentoperationinnerrequestresponsetypes.PreOrderTokenRequestBO;
import com.sprint.integration.eai.services.enterprisepaymentservice.v1.enterprisepaymentservice.Faultmessage;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class GeneratePreOrderTokenTest {
	
	private Logger logger = LoggerFactory.getLogger(GeneratePreOrderTokenTest.class);
	@Autowired
    private EPPServiceAdapter service;
    
    @Test
    public void testGeneratePreOrderToken() throws DatatypeConfigurationException, JAXBException, Faultmessage, TransformerFactoryConfigurationError, TransformerException, com.sprint.integration.eai.services.enterprisewalletservice.v1.enterprisewalletservice.Faultmessage, IOException {
    	GeneratePreOrderTokenRequest request = new GeneratePreOrderTokenRequest();
        
//      AuthorizePaymentRequest authorizePayment = new AuthorizePaymentRequest();
      ActorApp info = new ActorApp();
      info.setActorChannel("WEB_VMU");
      info.setActorId("249597750");
      info.setSourceTransactionId("TOP_249597750_1425924217911");
      request.setApplicationInfo(info);
      
      PreOrderTokenRequestBO token = new PreOrderTokenRequestBO();
      token.setExternalOrderId("KHK_TEST_ORDER_996");
      token.setConsumerChoicePreference(CardType.CREDIT);
      token.setTokenType(TokenTypeConstants.CARD_TOKEN);
      
      request.setPreOrderTokenRequest(token);
      
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
		
		/*BanInfoAC ban = new BanInfoAC();
		ban.setAccountAge(BigInteger.ONE);
		ban.setBan("1");
		ban.setCustomerAccountSubType("C");
		ban.setCustomerAccountType("C");
		ban.setCustomerBrand("Prepaid");
		ban.setCustomerCreditClass("custo");
		ban.setCustomerSubBrand("Postpaid");
		ban.setIsSpendingLimitAccount(BooleanType.FALSE);
		ban.setUsgBan(BooleanType.FALSE);*/
				
		GeneratePreOrderTokenResponse generatePreOrderTokenResponse = service.generatePreOrderToken(null, request, details);
		logger.info("order Id::"+generatePreOrderTokenResponse.getPreOrderTokenResponse().getExternalOrderId());
		logger.info("Token::"+generatePreOrderTokenResponse.getPreOrderTokenResponse().getGlobalToken());
		Assert.assertEquals("0",generatePreOrderTokenResponse.getResponses().getResponse().getResponseCode());
        
    }
}
