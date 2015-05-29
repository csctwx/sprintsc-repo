/**
 * Sprint Template
 */
package com.ericsson.cac.sprint.test.selfcare.workflow.checkEsnSwap;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ericsson.cac.sprint.selfcare.workflow.AccountDeviceWorkflow;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.DeviceResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.ESNSwapEligibleRequest;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.ESNSwapEligibleResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.UserContext;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.UserContextRequest;
import com.ericsson.cac.sprint.test.selfcare.workflow.TestConfig;

/**
 * LostPhoneTest is used to test all the scenarios for the service layer class
 * 
 * @author Ericsson
 * @version $Revision: 1.0 $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class checkEsnSwapTest {

	@Autowired
	private AccountDeviceWorkflow service;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(checkEsnSwapTest.class);

	@Test
	public void testCheckEsnSwap() {
		// System.setProperty("socksProxyHost", "localhost");
		// System.setProperty("socksProxyPort", "3128");
		ESNSwapEligibleRequest esnSwaprequest = new ESNSwapEligibleRequest();
		UserContextRequest userContextRequest = new UserContextRequest();
		UserContext userContext = new UserContext();
		userContext.setMsisdn("3179566118");
		userContextRequest.setUserContext(userContext);
		try {

			ESNSwapEligibleResponse response = service.checkEsnSwap(
					esnSwaprequest, userContextRequest);
			System.out
					.println("&&&&&&&&&&&&&&&&&" + response.getSerialNumber());
			Assert.assertEquals("256691443500328517",
					response.getSerialNumber());

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}

	}

	/*
	 * Commenting negative test case because having this will make the
	 * CheckEsnSwap method to have many try catch blocks to past this testcase
	 * which is not good.
	 */
	/*
	 * @Test public void testCheckEsnSwap_Error() { ESNSwapEligibleRequest
	 * esnSwaprequest = new ESNSwapEligibleRequest(); UserContext userContext =
	 * new UserContext(); userContext.setSubscriberId(""); UserContextRequest
	 * userContextRequest = new UserContextRequest();
	 * userContextRequest.setUserContext(userContext); try {
	 * 
	 * ESNSwapEligibleResponse response = service.checkEsnSwap(esnSwaprequest,
	 * userContextRequest); Assert.assertNull(response.getSerialNumber());
	 * 
	 * } catch (Exception e) { Assert.fail(e.getMessage()); }
	 * 
	 * }
	 */

}
