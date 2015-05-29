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
import com.amdocs.mfs.api.epp.v1.sprint.paymentoperationinnerrequestresponsetypes.CancelAuthorizePaymentRequest;
import com.amdocs.mfs.api.epp.v1.sprint.paymentoperationinnerrequestresponsetypes.CancelAuthorizePaymentResponse;
import com.sprint.integration.eai.services.enterprisepaymentservice.v1.enterprisepaymentservice.Faultmessage;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class CancelAuthorizePaymentTest {
	
	private Logger logger = LoggerFactory.getLogger(CancelAuthorizePaymentTest.class);
	@Autowired
    private EPPServiceAdapter service;
    
    @Test
    public void testCancelAuthorizePayment() throws DatatypeConfigurationException, JAXBException, Faultmessage, TransformerFactoryConfigurationError, TransformerException, com.sprint.integration.eai.services.enterprisewalletservice.v1.enterprisewalletservice.Faultmessage, IOException {
    	
		CancelAuthorizePaymentRequest request = new CancelAuthorizePaymentRequest();

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

		ActorApp info = new ActorApp();
		info.setActorChannel("WEB_VMU");
		info.setActorId("249597750");
		info.setSourceTransactionId("TOP_249597750_1425924217986");
		request.setApplicationInfo(info);

		//request.setReferenceId("1234");
		request.setPaymentId("K4RC7MO0a9nVyyux0MKO4DFqSfrN2qA5k9Pw");

		CancelAuthorizePaymentResponse cancelAuthorizePaymentResponse = service.cancelAuthorizePayment(null, request);
		Assert.assertEquals("0",cancelAuthorizePaymentResponse.getResponses().getResponse().getResponseCode());
    }
}
