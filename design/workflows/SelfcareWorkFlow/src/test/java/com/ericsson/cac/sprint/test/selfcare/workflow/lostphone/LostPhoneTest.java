package com.ericsson.cac.sprint.test.selfcare.workflow.lostphone;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ericsson.cac.sprint.selfcare.workflow.AccountLoginWorkflow;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.StatusMessageResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.UserContext;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.UserContextRequest;
import com.ericsson.cac.sprint.test.selfcare.workflow.TestConfig;

/**
 * FoundPhoneTest is used to test all the scenarios for the service layer class
 * 
 * @author Ericsson
 * @version $Revision: 1.0 $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class LostPhoneTest {

	// @Autowired
	// private LostPinService service;

	@Autowired
	private AccountLoginWorkflow service;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(LostPhoneTest.class);

	@Test
	public void testSuspendSubscriberSuccessWithMsisdn() {
		// System.setProperty("socksProxyHost", "localhost");
		// System.setProperty("socksProxyPort", "3128");
		try {

			UserContextRequest userContextRequest = new UserContextRequest();

			UserContext userContext = new UserContext();

			userContext.setMsisdn("3179566118");

			userContextRequest.setUserContext(userContext);

			// userContext.setSubscriberId(subscriberId);

			// String mdn = "3179566118";
			// HeaderData headerData = new HeaderData();
			// Not Required for adapter call in this use case;
			// headerData.setDevice("iPhone");
			// headerData.setIpAddress("1.2.3.4");
			// headerData.setSessionInfo("session");
			// headerData.setUrl("");
			// LostPhoneResponse response =
			// service.suspendSubscriber(headerData, mdn);
			LOGGER.debug("Calling suspend subscriber");

			StatusMessageResponse statusMessageResponse = service
					.lostPhone(userContextRequest);

			if (null == statusMessageResponse.getStatusMessage()) {

				Assert.assertEquals(null,
						statusMessageResponse.getStatusMessage());
			} else {

				Assert.assertEquals("0", statusMessageResponse
						.getStatusMessage().getStatus().toString());
			}

			LOGGER.debug("response");

			// Assert.assertEquals("0", statusMessageResponse.getStatusMessage()
			// .getStatus());
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testSuspendSubscriberSuccessWithSubscriberId() {
		// System.setProperty("socksProxyHost", "localhost");
		// System.setProperty("socksProxyPort", "3128");
		try {

			UserContextRequest userContextRequest = new UserContextRequest();

			UserContext userContext = new UserContext();

			//userContext.setMsisdn("3179566118");
			userContext.setSubscriberId("40622562121");
			
			userContextRequest.setUserContext(userContext);

			// userContext.setSubscriberId(subscriberId);

			// String mdn = "3179566118";
			// HeaderData headerData = new HeaderData();
			// Not Required for adapter call in this use case;
			// headerData.setDevice("iPhone");
			// headerData.setIpAddress("1.2.3.4");
			// headerData.setSessionInfo("session");
			// headerData.setUrl("");
			// LostPhoneResponse response =
			// service.suspendSubscriber(headerData, mdn);
			LOGGER.debug("Calling suspend subscriber");

			StatusMessageResponse statusMessageResponse = service
					.lostPhone(userContextRequest);

			if (null == statusMessageResponse.getStatusMessage()) {

				Assert.assertEquals(null,
						statusMessageResponse.getStatusMessage());
			} else {

				Assert.assertEquals("0", statusMessageResponse
						.getStatusMessage().getStatus().toString());
			}

			LOGGER.debug("response");

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testSuspendSubscriberFailure() {
		// System.setProperty("socksProxyHost", "localhost");
		// System.setProperty("socksProxyPort", "3128");
		try {

			UserContextRequest userContextRequest = new UserContextRequest();

			UserContext userContext = new UserContext();

			userContext.setMsisdn("");

			userContextRequest.setUserContext(userContext);

			// userContext.setSubscriberId(subscriberId);

			// String mdn = "3179566118";
			// HeaderData headerData = new HeaderData();
			// Not Required for adapter call in this use case;
			// headerData.setDevice("iPhone");
			// headerData.setIpAddress("1.2.3.4");
			// headerData.setSessionInfo("session");
			// headerData.setUrl("");
			// LostPhoneResponse response =
			// service.suspendSubscriber(headerData, mdn);
			LOGGER.debug("Calling suspend subscriber");

			StatusMessageResponse statusMessageResponse = service
					.lostPhone(userContextRequest);

			LOGGER.debug("response");

			Assert.assertEquals("2", statusMessageResponse.getStatusMessage()
					.getStatus().toString());
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
}
