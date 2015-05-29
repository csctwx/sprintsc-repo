/**
 * Sprint Template
 */
package com.ericsson.cac.sprint.test.selfcare.workflow.getAccountBalance;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ericsson.cac.sprint.selfcare.workflow.AccountHomeWorkflow;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.AccountBalanceResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.UserContext;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.UserContextRequest;
import com.ericsson.cac.sprint.test.selfcare.workflow.TestConfig;

/**
 * getAccountBalanceServiceTest is used to test all the scenarios for the
 * service layer class
 * 
 * @author Ericsson
 * @version $Revision: 1.0 $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class GetAccountBalanceServiceTest {

	@Autowired
	private AccountHomeWorkflow service;

	@Test
	public void testGetAccountBalSuccess() {
		UserContext userContext = new UserContext();
		userContext.setSubscriberId("40622562121");
		userContext.setMsisdn("3179566118");
		UserContextRequest userContextRequest = new UserContextRequest();
		userContextRequest.setUserContext(userContext);
		try {
			System.out.println("before calling getAccountBalance......");
			AccountBalanceResponse response = service
					.getAccountBalance(userContextRequest);
			System.out.println("after calling getAccountBalance......");
			Assert.assertEquals(Float.valueOf("120.0"), (Float) response
					.getAccountBalance().getCashBalance());
			
			// success handling assert
			Assert.assertEquals(Integer.valueOf(0), response.getStatusMessageResponse().getStatusMessage().getStatus());

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testGetAccountBalError() {
		UserContext userContext = new UserContext();
		userContext.setSubscriberId("40622562121");
		UserContextRequest userContextRequest = new UserContextRequest();
		userContextRequest.setUserContext(null);
		try {
			System.out.println("before calling getAccountBalance......");
			AccountBalanceResponse response = service
					.getAccountBalance(userContextRequest);
			System.out.println("after calling getAccountBalance......");
			Assert.assertEquals(null, response.getAccountBalance());
			
			// error handling assert
			Assert.assertEquals(Integer.valueOf(2), response.getStatusMessageResponse().getStatusMessage().getStatus());
			Assert.assertEquals("NOK", response.getStatusMessageResponse().getStatusMessage().getDescription());

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}

	}

}
