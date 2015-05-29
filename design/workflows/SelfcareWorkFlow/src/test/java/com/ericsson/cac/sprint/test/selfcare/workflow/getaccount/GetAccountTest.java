package com.ericsson.cac.sprint.test.selfcare.workflow.getaccount;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ericsson.cac.sprint.selfcare.workflow.AccountHomeWorkflow;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.SubscriberAccountResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.UserContext;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.UserContextRequest;
import com.ericsson.cac.sprint.test.selfcare.workflow.TestConfig;

/**
 * GetAccountTest is used to test all the scenarios for the service layer class
 * 
 * @author Ericsson
 * @version $Revision: 1.0 $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class GetAccountTest {

	@Autowired
	private AccountHomeWorkflow service;

	@Test
	public void testGetAccountSubscriberIdNullError() {

		UserContextRequest userContextRequest = new UserContextRequest();
		UserContext userContext = new UserContext();
		userContext.setSubscriberId("");
		userContextRequest.setUserContext(userContext);

		try {

			System.out.println("Before executing getAccount service");
			SubscriberAccountResponse response = service
					.getAccount(userContextRequest);
			System.out.println("After executing getAccount service");

			Assert.assertEquals(null, response.getSubscriberAccount());
			
			// error handling assert
			Assert.assertEquals(Integer.valueOf(2), response.getStatusMessageResponse().getStatusMessage().getStatus());
			Assert.assertEquals("NOK", response.getStatusMessageResponse().getStatusMessage().getDescription());

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void testGetAccountSuccess() {
		
		UserContextRequest userContextRequest = new UserContextRequest();
		UserContext userContext = new UserContext();
		userContext.setSubscriberId("80068822121");
		userContextRequest.setUserContext(userContext);

		try {

			System.out.println("Before executing testGetAccountSuccess service");
			SubscriberAccountResponse response = service
					.getAccount(userContextRequest);
			System.out.println("After executing testGetAccountSuccess service");

			System.out.println("getSubscriberAccount object:" +response.getSubscriberAccount());
			System.out.println("Account Type: " +response.getSubscriberAccount().getAccountType());
			System.out.println("Account Status: " +response.getSubscriberAccount().getAccountStatus());
			
			Assert.assertEquals("prepaid", 
					response.getSubscriberAccount().getAccountType().toLowerCase());
			Assert.assertEquals("O", 
					response.getSubscriberAccount().getAccountStatus());
			
			// success handling assert
			Assert.assertEquals(Integer.valueOf(0), response.getStatusMessageResponse().getStatusMessage().getStatus());


		} catch (Exception e) {
			System.out.println(e.getMessage());
			Assert.fail(e.getMessage());
		}
	}
	
}
