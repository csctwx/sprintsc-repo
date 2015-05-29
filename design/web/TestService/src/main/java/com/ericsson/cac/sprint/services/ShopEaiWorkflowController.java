package com.ericsson.cac.sprint.services;

import java.io.IOException;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.amdocs.dc.api.sensitivedetails.BillingAddressType;
import com.amdocs.dc.api.sensitivedetails.CardSensitiveDetails;
import com.amdocs.mfs.api.epp.v1.sprint.walletbaseoperationrequest.ValidatePaymentOption;
import com.amdocs.mfs.api.epp.v1.sprint.walletcommontypes.PaymentMethod;
import com.amdocs.mfs.api.epp.v1.sprint.walletoperationinnerrequestresponsetypes.ValidatePaymentOptionRequest;
import com.amdocs.mfs.api.epp.v1.sprint.walletoperationinnerrequestresponsetypes.ValidatePaymentOptionResponse;
import com.ericsson.cac.sprint.adapters.EPPServiceAdapter;
import com.sprint.integration.eai.services.enterprisewalletservice.v1.enterprisewalletservice.Faultmessage;

@Controller
@RequestMapping(BaseController.SHOP_URI_PREFIX)
public class ShopEaiWorkflowController implements BaseController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ShopEaiWorkflowController.class);
	
	@Autowired
	private EPPServiceAdapter adapter;
	
    @RequestMapping(value = "/validatePayment", method = RequestMethod.GET, produces={MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public @ResponseBody ValidatePaymentOptionResponse validatePayment() {
    	com.amdocs.mfs.api.epp.v1.sprint.walletcommontypes.ActorApp info = new com.amdocs.mfs.api.epp.v1.sprint.walletcommontypes.ActorApp();
        info.setActorChannel("WEB_VMU");
        info.setActorId("249597750");
        info.setSourceTransactionId("TOP_249597750_1425924217551");
        
        ValidatePaymentOptionRequest request = new ValidatePaymentOptionRequest();
        request.setApplicationInfo(info);
        
        ValidatePaymentOption paymentOption = new ValidatePaymentOption();
        paymentOption.setPaymentMethod(PaymentMethod.CARD);
        request.setWalletItem(paymentOption);
        
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
        type.setCountry("US");
        type.setZipCode("10453");
        
        details.setBillingAddress(type);
        
        ValidatePaymentOptionResponse response = new ValidatePaymentOptionResponse();
		try {
			response = adapter.validatePayment(null, request, details);
			System.out.println("code:"+response.getResponses().getResponse().getResponseCode());
			System.out.println("message:"+response.getResponses().getResponse().getResponseMessage());
			System.out.println("details:"+response.getResponses().getResponse().getResponseDescription());
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		} catch (Faultmessage e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        return response;
    }
    
}
