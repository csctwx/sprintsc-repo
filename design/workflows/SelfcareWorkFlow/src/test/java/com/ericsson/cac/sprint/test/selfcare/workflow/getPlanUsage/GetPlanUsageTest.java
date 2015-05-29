/**
 * Sprint Template
 */
package com.ericsson.cac.sprint.test.selfcare.workflow.getPlanUsage;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ericsson.cac.sprint.selfcare.workflow.AccountServiceWorkflow;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.PlanUsageResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.SubscriberPlanResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.UserContext;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.UserContextRequest;
import com.ericsson.cac.sprint.test.selfcare.workflow.TestConfig;

/**
 * GetPlanServiceTest is used to test all the scenarios for the service layer
 * class
 * 
 * @author Ericsson
 * @version $Revision: 1.0 $
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class GetPlanUsageTest {

	@Autowired
	private AccountServiceWorkflow service;
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(GetPlanUsageTest.class);

	@Test
	public void testGetPlanUsage() {

		try {

			//String mdn = "6212126687";

			String mdn = "2699030677";

			UserContextRequest request = new UserContextRequest();
			UserContext userContext = new UserContext();
			userContext.setMsisdn(mdn);
			request.setUserContext(userContext);

			System.out.println("Calling GetPlanUsage");
			LOGGER.debug("Calling GetPlanUsage");
			PlanUsageResponse response = service.getPlanUsage(request);
			LOGGER.debug("After calling getPlanUsage");
			System.out.println("After calling getPlanUsage");
			
			
			
/*			for (int i = 0; i < response.getSubscriberPlan().getBaseplan().length; i++) {
				
				System.out.println("BasePlan Name" + response.getSubscriberPlan().getBaseplan()[0].getName());
				System.out.println("BasePlan Description" + response.getSubscriberPlan().getBaseplan()[0].getDescription());
				System.out.println("BasePlan Capacity" + response.getSubscriberPlan().getBaseplan()[0].getCapacity());
				System.out.println("BasePlan Expiry" + response.getSubscriberPlan().getBaseplan()[0].getExpiry());
			}*/
				
			Assert.assertEquals("unlimited", response.getTalkUsage().getCapacity());
	
			// success handling assert
			Assert.assertEquals(Integer.valueOf(0), response.getStatusMessageResponse().getStatusMessage().getStatus());
			
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void testGetPlanUsageError() {

		try {

			String mdn = "";
			UserContextRequest request = new UserContextRequest();
			UserContext userContext = new UserContext();
			userContext.setMsisdn(mdn);
			request.setUserContext(userContext);

			LOGGER.debug("Calling GetPlanUsage");
			PlanUsageResponse response = service.getPlanUsage(request);
			LOGGER.debug("After calling getPlanUsage");

			// error handling assert
			Assert.assertEquals(Integer.valueOf(2), response.getStatusMessageResponse().getStatusMessage().getStatus());
			Assert.assertEquals("NOK", response.getStatusMessageResponse().getStatusMessage().getDescription());

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

}
