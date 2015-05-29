package com.ericsson.cac.sprint.test.selfcare.workflow.updatePin;

/**
 * Sprint Template */

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
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.UpdatePinRequest;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.UserContext;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.UserContextRequest;
import com.ericsson.cac.sprint.test.selfcare.workflow.TestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class UpdatePinTest {

	@Autowired
	private AccountSettingsWorkflow service;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(UpdatePinTest.class);

	UserContext userContext = new UserContext();
	UserContextRequest userContextRequest = new UserContextRequest();

	// serContext.setMsisdn("3179566118");
	// ("40622562121");
	// 80068822121

	@Test
	public void updatepinSuccess() {

		UserContext userContext = new UserContext();
		// userContext.setMsisdn("3179566118");
		userContext.setSubscriberId("40622562121");
		// 80068822121
		UserContextRequest userContextRequest = new UserContextRequest();
		userContextRequest.setUserContext(userContext);
		UpdatePinRequest updatePinRequest = new UpdatePinRequest();
		updatePinRequest.setPin("123456");
		updatePinRequest.setSecretQuestion("b01");
		updatePinRequest.setSecretAnswer("Scrambled Answer");
		try {

			StatusMessageResponse response = service.updatePin(
					userContextRequest, updatePinRequest);
			
			// success handling assert
			Assert.assertEquals(Integer.valueOf(0), response.getStatusMessage().getStatus());

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}

	}

	// @Test
	public void updatepinError() {

		UserContext userContext = new UserContext();
		// userContext.setMsisdn("3179566118");
		userContext.setSubscriberId("40622562121");
		// 80068822121
		UserContextRequest userContextRequest = new UserContextRequest();
		userContextRequest.setUserContext(userContext);
		UpdatePinRequest updatePinRequest = new UpdatePinRequest();
		updatePinRequest.setPin("123456");
		updatePinRequest.setSecretQuestion("What is your pet's name?");
		updatePinRequest.setSecretAnswer("Scrambled Answer");
		try {

			StatusMessageResponse response = service.updatePin(
					userContextRequest, updatePinRequest);
			
			// error handling assert
			Assert.assertEquals(Integer.valueOf(2), response.getStatusMessage().getStatus());
			Assert.assertEquals("NOK", response.getStatusMessage().getDescription());

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}

	}

}
