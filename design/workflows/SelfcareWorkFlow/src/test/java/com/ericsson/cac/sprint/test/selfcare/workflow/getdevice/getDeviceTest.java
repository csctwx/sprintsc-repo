/**
 * Sprint Template
 */
package com.ericsson.cac.sprint.test.selfcare.workflow.getdevice;

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
public class getDeviceTest {

	@Autowired
	private AccountDeviceWorkflow service;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(getDeviceTest.class);
	@Test
	public void testgetDevice_Success() {
		//System.setProperty("socksProxyHost", "localhost");
		//System.setProperty("socksProxyPort", "3128");
		UserContext userContext = new UserContext();
		userContext.setSubscriberId("80068822121");
		UserContextRequest userContextRequest = new UserContextRequest();
		userContextRequest.setUserContext(userContext);
		try {	
			
			DeviceResponse response = service.getDevice(userContextRequest);
			System.out.println("***********"+response.getDevice().getName());
			Assert.assertEquals("SPP Samsung Galaxy S III", response.getDevice().getName());
			
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}

	}
	
	@Test
	public void testgetDevice_Error() {
		//System.setProperty("socksProxyHost", "localhost");
		//System.setProperty("socksProxyPort", "3128");
		UserContext userContext = new UserContext();
		userContext.setSubscriberId("");
		UserContextRequest userContextRequest = new UserContextRequest();
		userContextRequest.setUserContext(userContext);
		try {	
			
			DeviceResponse response = service.getDevice(userContextRequest);
			Assert.assertEquals(null, response.getDevice());
			
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}

	}


}
