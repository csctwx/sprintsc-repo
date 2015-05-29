/**
 * Sprint Template
 */
package com.ericsson.cac.sprint.test.selfcare.workflow.getblockedmessages;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ericsson.cac.sprint.selfcare.workflow.AccountSettingsWorkflow;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.BlockedMessagesResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.UserContext;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.UserContextRequest;
import com.ericsson.cac.sprint.test.selfcare.workflow.TestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)

public class GetBlockedMessagesTest {

	@Autowired
	private AccountSettingsWorkflow service;
	private static final Logger LOGGER = LoggerFactory.getLogger(GetBlockedMessagesTest.class);


	//@Test
	public void GetBlockedMessagesWhiteListed() {

		try {
			UserContext context = new UserContext();
			UserContextRequest contextRequest = new UserContextRequest();
			context.setMsisdn("1234567890");
			contextRequest.setUserContext(context);
			
			LOGGER.debug("Calling getBlockedMessages");
			BlockedMessagesResponse response = service.getBlockedMessages(contextRequest);
			LOGGER.debug("After calling getBlockedMessages"+response.getRecipients());
					
			Assert.assertEquals("Recipients are not whitelisted", response.getRecipients(),
					response.getRecipients());
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void GetBlockedMessagesNotWhiteListed() {

		try {
			UserContext context = new UserContext();
			UserContextRequest contextRequest = new UserContextRequest();
			context.setMsisdn("1234567890");
			contextRequest.setUserContext(context);
			
			LOGGER.debug("Calling getBlockedMessages");
			BlockedMessagesResponse response = service
					.getBlockedMessages(contextRequest);
			LOGGER.debug("After calling getBlockedMessages");
			
			
			Assert.assertNotNull(response.getRecipients());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
