/**
 * Sprint Template
 */
package com.ericsson.cac.sprint.test.selfcare.workflow.getDeviceInsurance;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ericsson.cac.sprint.selfcare.workflow.AccountDeviceWorkflow;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.DeviceInsuranceResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.DeviceResponse;
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
public class getDeviceInsuranceServiceTest {

	@Autowired
	private AccountDeviceWorkflow service;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(getDeviceInsuranceServiceTest.class);
	@Test
	public void testgetDeviceInsurance_ForUnsuredAccount() {
		UserContext userContext = new UserContext();
		userContext.setSubscriberId("80068822121");
		UserContextRequest userContextRequest = new UserContextRequest();
		userContextRequest.setUserContext(userContext);
		try {	
			
			DeviceInsuranceResponse response = service.getDeviceInsurance(userContextRequest);
			LOGGER.debug("Insured" + response.getDeviceInsurance().getInsured());
			Assert.assertEquals(Boolean.FALSE, response.getDeviceInsurance().getInsured());
			
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}

	}
	
	/*@Test
	 * Pass SubscriberId for a account which is Insured
	public void testgetDeviceInsurance_ForInsuredAccount() {
		UserContext userContext = new UserContext();
		userContext.setSubscriberId("40622562121");
		UserContextRequest userContextRequest = new UserContextRequest();
		userContextRequest.setUserContext(userContext);
		try {	
			
			DeviceInsuranceResponse response = service.getDeviceInsurance(userContextRequest);
			LOGGER.debug("Insured" + response.getDeviceInsurance().getInsured());
			Assert.assertEquals(Boolean.TRUE, response.getDeviceInsurance().getInsured());
			
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}

	}*/

}
