package com.ericsson.cac.sprint.adapters;

import java.math.BigDecimal;
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
import com.amdocs.mfs.api.epp.v1.sprint.baseoperationrequest.PaymentAllocationRequestInfoList.PaymentAllocationRequestInfo;
import com.amdocs.mfs.api.epp.v1.sprint.paymentcommontypes.ActorApp;
import com.amdocs.mfs.api.epp.v1.sprint.paymentcommontypes.BanInfoAC;
import com.amdocs.mfs.api.epp.v1.sprint.paymentcommontypes.BooleanType;
import com.amdocs.mfs.api.epp.v1.sprint.paymentoperationinnerrequestresponsetypes.AuthorizePaymentRequest;
import com.amdocs.mfs.api.epp.v1.sprint.paymentoperationinnerrequestresponsetypes.CapturePaymentRequest;
import com.amdocs.mfs.api.epp.v1.sprint.paymentoperationinnerrequestresponsetypes.CapturePaymentResponse;
import com.sprint.integration.eai.services.enterprisepaymentservice.v1.enterprisepaymentservice.Faultmessage;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class CapturePaymentTest {

	private Logger logger = LoggerFactory.getLogger(CapturePaymentTest.class);
	@Autowired
	private EPPServiceAdapter service;

	@Test
	public void testCapturePayment()
			throws TransformerFactoryConfigurationError, Exception {
		CapturePaymentRequest request = new CapturePaymentRequest();

		request.setPaymentId("K4RC7MOzUrL53v57PMtsLjQHa0bOB8Xq0F7T");

		ActorApp info = new ActorApp();
		info.setActorChannel("WEB_VMU");
		info.setActorId("249597750");
		info.setSourceTransactionId("TOP_249597750_142592421799");
		request.setApplicationInfo(info);

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
		CapturePaymentResponse response = service.capturePayment(null, request, details);

		Assert.assertEquals("0", response.getResponses().getResponse());

	}
}
