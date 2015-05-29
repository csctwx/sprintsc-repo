/**
 * Sprint Template */
package com.ericsson.cac.sprint.test.selfcare.workflow.resetVM;

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
import com.ericsson.cac.sprint.test.selfcare.workflow.getblockedmessages.GetBlockedMessagesTest;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)

public class ResetVmTest {
	@Autowired
	private AccountSettingsWorkflow service;
	private static final Logger LOGGER = LoggerFactory.getLogger(GetBlockedMessagesTest.class);
	
	//@Test
	public void resetVMSubscriberIdMissingError() {

		try {
			UserContext context = new UserContext();
			UserContextRequest contextRequest = new UserContextRequest();
			context.setSubscriberId("");
			contextRequest.setUserContext(context);
			
			LOGGER.debug("Calling resetVM");			
			StatusMessageResponse response = service.resetVM(contextRequest);
			LOGGER.debug("After calling resetVM");
			
			//Assert.assertEquals("Subscriber ID is not null", "FAILURE", response.getStatusMessage().getReason());
			
			// error handling assert
			Assert.assertEquals(Integer.valueOf(2), response.getStatusMessage().getStatus());
			Assert.assertEquals("NOK", response.getStatusMessage().getDescription());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void resetVMSuccess() {

	
		UserContext userContext = new UserContext();
		userContext.setMsisdn("3179566118");
			//userContext.setSubscriberId("40622562121");
		//	80068822121
			UserContextRequest userContextRequest = new UserContextRequest();
			userContextRequest.setUserContext(userContext);
			try {	
				
				StatusMessageResponse response = service.resetVM(userContextRequest);
				Assert.assertEquals("Failed", response.getStatusMessage().getReason());
				
				// success handling assert
				Assert.assertEquals(Integer.valueOf(0), response.getStatusMessage().getStatus());
				
			} catch (Exception e) {
				Assert.fail(e.getMessage());
			}

	}

}