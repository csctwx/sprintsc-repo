/**
 * Sprint Template
 */
package com.ericsson.cac.sprint.test.selfcare.workflow.sendPin;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ericsson.cac.sprint.selfcare.workflow.AccountLoginWorkflow;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.ForgotPinRequest;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.StatusMessageResponse;
import com.ericsson.cac.sprint.test.selfcare.workflow.TestConfig;

/**
 * FoundPhoneTest is used to test all the scenarios for the service layer class
 * 
 * @author Ericsson
 * @version $Revision: 1.0 $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class SendPinTest {

	@Autowired
	private AccountLoginWorkflow service;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(SendPinTest.class);

	@Test
	public void testresendSecurityInfoToUser() {
		try {

			String mdn = "3179566118";
			// HeaderData headerData = new HeaderData();

			// Not Required for adapter call in this use case;

			// headerData.setDevice("iPhone");

			// headerData.setIpAddress("1.2.3.4");

			// /headerData.setSessionInfo("session");

			// headerData.setUrl("");

			// ResendSecurityRequest request = new ResendSecurityRequest(
			// headerData, mdn);

			ForgotPinRequest forgotPinRequest = new ForgotPinRequest();

			forgotPinRequest.setMsisdn(mdn);

			StatusMessageResponse statusMessageResponse = service
					.sendPin(forgotPinRequest);

			System.out.println(statusMessageResponse.getStatusMessage()
					.getStatus());

			// LostPinResponse actualResponse = new LostPinResponse("SUCCESS");

			Assert.assertEquals("0", statusMessageResponse.getStatusMessage()
					.getStatus().toString());

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testresendSecurityInfoToUser_InvalidMDN() {
		try {

			String mdn = "";
			// HeaderData headerData = new HeaderData();

			// Not Required for adapter call in this use case;

			// headerData.setDevice("iPhone");

			// headerData.setIpAddress("1.2.3.4");

			// /headerData.setSessionInfo("session");

			// headerData.setUrl("");

			// ResendSecurityRequest request = new ResendSecurityRequest(
			// headerData, mdn);

			ForgotPinRequest forgotPinRequest = new ForgotPinRequest();

			forgotPinRequest.setMsisdn(mdn);

			StatusMessageResponse statusMessageResponse = service
					.sendPin(forgotPinRequest);

			System.out.println(statusMessageResponse.getStatusMessage()
					.getStatus());

			// LostPinResponse actualResponse = new LostPinResponse("SUCCESS");

			Assert.assertEquals("2", statusMessageResponse.getStatusMessage()
					.getStatus().toString());

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
}
