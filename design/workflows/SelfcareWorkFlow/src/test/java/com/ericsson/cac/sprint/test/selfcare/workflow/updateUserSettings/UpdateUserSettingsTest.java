package com.ericsson.cac.sprint.test.selfcare.workflow.updateUserSettings;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ericsson.cac.sprint.selfcare.workflow.AccountSettingsWorkflow;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.StatusMessageResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.SubscriberSetting;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.SubscriberSettingsRequest;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.UserContext;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.UserContextRequest;
import com.ericsson.cac.sprint.test.selfcare.workflow.TestConfig;
import com.ericsson.cac.sprint.test.selfcare.workflow.updatePin.UpdatePinTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class UpdateUserSettingsTest {

	@Autowired
	private AccountSettingsWorkflow accountSettingsWorkflow;

	private static final Logger LOGGER = LoggerFactory.getLogger(UpdatePinTest.class);


	@BeforeClass
	public static void runBeforeClass() {
		System.out.println("Before class");
		System.out.println("Before class done");

	}
	
	@Test
	public void updateUserSettingsSuccess() {
		try {
			System.out.println("updateUserSettingsSuccess");
			
			UserContextRequest userContextRequest = new UserContextRequest();
			UserContext userContext = new UserContext();
			userContextRequest.setUserContext(userContext);
			userContextRequest.getUserContext().setMsisdn("6212126685");
			
			SubscriberSettingsRequest subscriberSettingsRequest = new SubscriberSettingsRequest();
			SubscriberSetting[] subscriberSettings = new SubscriberSetting[1];
			SubscriberSetting s= new SubscriberSetting();
			s.setId("CF");
			s.setSettingEnabled(false);

			subscriberSettingsRequest.setSubscriberSettings(subscriberSettings);
			
			StatusMessageResponse response = 
					accountSettingsWorkflow.updateUserSettings(userContextRequest, subscriberSettingsRequest);
			
			Assert.assertNotNull(response.getStatusMessage().getStatus());
			
			// success handling assert
			Assert.assertEquals(Integer.valueOf(0), response.getStatusMessage().getStatus());
			
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

}
