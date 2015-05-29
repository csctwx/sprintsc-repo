package com.ericsson.cac.sprint.adapters;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.amdocs.dc.api.sensitivedetails.CardSensitiveDetails;
import com.amdocs.mfs.api.epp.v1.sprint.paymentoperationinnerrequestresponsetypes.AuthorizePaymentRequest;
import com.amdocs.mfs.api.epp.v1.sprint.walletoperationinnerrequestresponsetypes.BanInfo;
import com.sprint.integration.eai.services.enterprisepaymentservice.v1.enterprisepaymentservice.Faultmessage;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class EPPServiceTest {
	
	private Logger logger = LoggerFactory.getLogger(EnterprisePaymentProxyServiceTest.class);
	@Autowired
    private EPPService service;
    
    @Test
    public void testauthorizePayment() throws DatatypeConfigurationException, JAXBException, Faultmessage, TransformerFactoryConfigurationError, TransformerException {
    	BanInfo ban;
		AuthorizePaymentRequest request;
		CardSensitiveDetails details;
		service.authorizePayment(ban, request, details);
        
    }
}
