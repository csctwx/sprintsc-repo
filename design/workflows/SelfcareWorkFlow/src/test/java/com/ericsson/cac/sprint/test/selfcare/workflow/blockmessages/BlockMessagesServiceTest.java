package com.ericsson.cac.sprint.test.selfcare.workflow.blockmessages;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ericsson.cac.sprint.selfcare.workflow.AccountSettingsWorkflow;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.BlockedMessagesRequest;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.Recipient;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.StatusMessageResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.UserContext;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.UserContextRequest;
import com.ericsson.cac.sprint.test.selfcare.workflow.TestConfig;
import com.sprint.integration.eai.services.smspreferencemanagementservice.v1.smspreferencemanagementservice.SoapFault;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)

public class BlockMessagesServiceTest {
	@Autowired
	private AccountSettingsWorkflow service;
	private static final Logger LOGGER = LoggerFactory.getLogger(BlockMessagesServiceTest.class);

//	@Test
	public void testInValidateRecipient() throws SoapFault{
		
		UserContextRequest userContextRequest = new UserContextRequest();
		BlockedMessagesRequest blockMessagesRequest = new BlockedMessagesRequest();
		LOGGER.info("Setting msisdn");
		String msisdn = "3179566118";
		String sender = "testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest@sprint.com";
		
		UserContext userContext = new UserContext();
		userContext.setMsisdn(msisdn);
		userContextRequest.setUserContext(userContext);

		Recipient recipient = new Recipient();
		recipient.setSender(sender);
		Recipient[] recipients2 = { recipient };
		blockMessagesRequest.setRecipients(recipients2);
		LOGGER.info("Before calling response");
		StatusMessageResponse response = service.blockMessages(userContextRequest, blockMessagesRequest);
		LOGGER.info("After calling response");
		Assert.assertEquals("Sender is invalid", "SUCCESS", response.getStatusMessage().getDescription());

	}
	
	@Test
	public void TestValidRcepient() throws SoapFault{
		
		UserContextRequest userContextRequest = new UserContextRequest();
		BlockedMessagesRequest blockMessagesRequest = new BlockedMessagesRequest();
		LOGGER.info("Setting msisdn");
		String msisdn = "6212126685";
		//String sender = "test@sprint.com";
		
		UserContext userContext = new UserContext();
		userContext.setMsisdn(msisdn);
		userContextRequest.setUserContext(userContext);
		
		
		
		Recipient recipient = new Recipient();
		recipient.setSender("621212668");
		Recipient[] recipients2 = { recipient };
		blockMessagesRequest.setRecipients(recipients2);
		LOGGER.info("**response");
		StatusMessageResponse response = service.blockMessages(userContextRequest, blockMessagesRequest);
		LOGGER.info(" ****After calling response"+response.getStatusMessage().getDescription());
		Assert.assertEquals("Sender is valid", "SUCCESS", response.getStatusMessage().getDescription());

	}
	
}
