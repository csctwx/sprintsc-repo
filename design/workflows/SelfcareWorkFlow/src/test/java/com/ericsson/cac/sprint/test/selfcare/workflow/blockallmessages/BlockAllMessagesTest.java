package com.ericsson.cac.sprint.test.selfcare.workflow.blockallmessages;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ericsson.cac.sprint.selfcare.workflow.AccountSettingsWorkflow;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.StatusMessageResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.UserContext;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.UserContextRequest;
import com.ericsson.cac.sprint.test.selfcare.workflow.TestConfig;

/**
 * BlockAllMessagesTest is used to test all the scenarios for the service layer
 * class
 * 
 * @author Ericsson
 * @version $Revision: 1.0 $
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class BlockAllMessagesTest {

	@Autowired
	private AccountSettingsWorkflow service;
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(BlockAllMessagesTest.class);

	//@Test
	public void testBlockAllMessagesWithNoSubscriberIdError() {

		try {

			String subId = "";
			UserContextRequest request = new UserContextRequest();
			UserContext userContext = new UserContext();
			userContext.setSubscriberId(subId);
			request.setUserContext(userContext);

			LOGGER.debug("Calling blockAllMessages");
			StatusMessageResponse response = service.blockAllMessages(request);
			LOGGER.debug("After calling blockAllMessages");

			Assert.assertEquals("FAILURE", response.getStatusMessage().getDescription());

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
	
	
	@Test
	public void testBlockAllMessages() {

		try {

			String subId = "6212126685";
			UserContextRequest request = new UserContextRequest();
			UserContext userContext = new UserContext();
			userContext.setMsisdn(subId);
			
			request.setUserContext(userContext);

			LOGGER.debug("Calling blockAllMessages");
			StatusMessageResponse response = service.blockAllMessages(request);
			LOGGER.debug("After calling blockAllMessages");

//			Assert.assertEquals("SUCCESS", response.getStatusMessage().getDescription());
			Assert.assertEquals("23000", response.getStatusMessage().getDescription());

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
	
}

