/**
 * Sprint Template
 */
package com.ericsson.cac.sprint.test.selfcare.workflow.foundphone;

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
public class FoundPhoneTest {

	@Autowired
	private AccountLoginWorkflow service;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FoundPhoneTest.class);
	@Test
	public void testfoundPhone_Success() {
		//System.setProperty("socksProxyHost", "localhost");
		//System.setProperty("socksProxyPort", "3128");
		UserContext userContext = new UserContext();
		//userContext.setSubscriberId("80068822121");
		userContext.setSubscriberId("12496723121");
		UserContextRequest userContextRequest = new UserContextRequest();
		userContextRequest.setUserContext(userContext);

		try {	
			
			StatusMessageResponse response = service.foundPhone(userContextRequest);
			Assert.assertEquals("0", String.valueOf(response.getStatusMessage().getStatus()));
			
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}

	}	
	
	@Test
	public void testfoundPhone_Failure() {
		//System.setProperty("socksProxyHost", "localhost");
		//System.setProperty("socksProxyPort", "3128");
		UserContext userContext = new UserContext();
		userContext.setSubscriberId("40622562121");//Pass any subscriberId which is Active
		UserContextRequest userContextRequest = new UserContextRequest();
		userContextRequest.setUserContext(userContext);

		try {	
			
			StatusMessageResponse response = service.foundPhone(userContextRequest);
			Assert.assertEquals("2", String.valueOf(response.getStatusMessage().getStatus()));
			
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}

	}	

}
