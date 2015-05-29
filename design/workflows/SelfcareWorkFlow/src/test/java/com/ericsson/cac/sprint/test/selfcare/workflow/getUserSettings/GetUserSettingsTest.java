package com.ericsson.cac.sprint.test.selfcare.workflow.getUserSettings;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ericsson.cac.sprint.selfcare.workflow.AccountSettingsWorkflow;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.SubscriberSettingsResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.UserContext;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.UserContextRequest;
import com.ericsson.cac.sprint.test.selfcare.workflow.TestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class GetUserSettingsTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(GetUserSettingsTest.class);
	
	@Autowired
	 private AccountSettingsWorkflow service;
	 
	@Test
	public void testGetUserSettings() {

		LOGGER.debug("Before calling testGetUserSettings.. ");
		UserContextRequest userContextRequest = new UserContextRequest();
		UserContext userContext = new UserContext();
		
		// artf498294
		//userContext.setMsisdn("7709905282");
		//userContext.setSubscriberId("80068822121");
		 
		userContext.setMsisdn("9133878656");
		userContext.setSubscriberId("50784362121");
		userContextRequest.setUserContext(userContext);
		SubscriberSettingsResponse response = new SubscriberSettingsResponse();

		try {

			response = service.getUserSettings(userContextRequest);

			for (int i = 0; i < response.getSubscriberSettings().length; i++) {
				System.out.println("Setting ID: "
						+ response.getSubscriberSettings()[i].getId());
				System.out.println("Setting Name: "
						+ response.getSubscriberSettings()[i].getTitle());
				System.out.println("Setting Enabled?: "
						+ response.getSubscriberSettings()[i]
								.isSettingEnabled());
			}

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}

		Assert.assertNotNull(response.getSubscriberSettings());
		Assert.assertTrue(response.getSubscriberSettings().length > 1);
		
		// success handling assert
		Assert.assertEquals(Integer.valueOf(0), response.getStatusMessage().getStatus());
		
		// artf498294
		/*if("Content Filter".equalsIgnoreCase(response.getSubscriberSettings()[0].getTitle())) {
			Assert.assertEquals("Off", response.getSubscriberSettings()[0].getDescription());
		}
		Assert.assertEquals(Integer.valueOf(2), response.getStatusMessage().getStatus());*/
		
	}
	 
	/*@Test
	public void testGetUserSettingsNoSubscriberId() {

		LOGGER.debug("Before calling testGetUserSettings.. ");
		UserContextRequest userContextRequest = new UserContextRequest();
		UserContext userContext = new UserContext();

		userContext.setSubscriberId("");
		userContextRequest.setUserContext(userContext);
		SubscriberSettingsResponse response = new SubscriberSettingsResponse();
		try {

			response = service.getUserSettings(userContextRequest);

			System.out.println(response.getSubscriberSettings());

			// Assert.assertNull(response.getSubscriberSettings());

			// error handling assert
			Assert.assertEquals(Integer.valueOf(2), response
					.getStatusMessage().getStatus());
			Assert.assertEquals("NOK", response
					.getStatusMessage().getDescription());

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}*/

}
