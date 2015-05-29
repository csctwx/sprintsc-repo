/**
 * Sprint Template
 */
package com.ericsson.cac.sprint.test.selfcare.workflow.getplan;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ericsson.cac.sprint.selfcare.workflow.AccountServiceWorkflow;
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
public class GetPlanServiceTest {

	@Autowired
	private AccountServiceWorkflow service;
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(GetPlanServiceTest.class);

	@Test
	public void testGetPlanSuccess() {

		try {
			UserContextRequest request = new UserContextRequest();
			UserContext userContext = new UserContext();

			// for artf498908
			userContext.setSubscriberId("33211293121");
			//userContext.setSubscriberId("31433282121");
			request.setUserContext(userContext);
			
			System.out.println("Calling getPlan");
			LOGGER.debug("Calling getPlan"+request.getUserContext().getSubscriberId());
			SubscriberPlanResponse response = service.getPlan(request);
			System.out.println("After Calling getPlan");
			LOGGER.debug("After calling getPlan");
			
			System.out.println("BasePlan Description: " +response.getSubscriberPlan().getBaseplan()[0].getDescription());
			
			for (int i = 0; i < response.getSubscriberPlan().getAddOns().length; i++) {
				
				System.out.println("AddOn name: " +response.getSubscriberPlan().getAddOns()[i].getName());
				System.out.println("AddOn description: " +response.getSubscriberPlan().getAddOns()[i].getDescription());
			}
			
			//System.out.println("BasePlan ID: " +response.getSubscriberPlan().getBaseplan()[0].getId());77
			Assert.assertEquals("SPP261FTR", response.getSubscriberPlan().getBaseplan()[0].getId());
			//Assert.assertEquals("SPP552IPH", response.getSubscriberPlan().getBaseplan()[0].getId());
			
            // for artf498908
			Assert.assertEquals("$45 UNL Tlk/Txt/3GBData Plan", response.getSubscriberPlan().getBaseplan()[0].getDescription());

			//Assert.assertEquals("$60/Unltd Min/Text/Data", response.getSubscriberPlan().getBaseplan()[0].getDescription());
			
			// success handling assert
			Assert.assertEquals(Integer.valueOf(0), response.getStatusMessageResponse().getStatusMessage().getStatus());

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void testGetPlanError() {

		try {

			String subId = "";
			UserContextRequest request = new UserContextRequest();
			UserContext userContext = new UserContext();
			userContext.setSubscriberId(subId);
			request.setUserContext(userContext);

			LOGGER.debug("Calling getPlan");
			SubscriberPlanResponse response = service.getPlan(request);
			LOGGER.debug("After calling getPlan");

			Assert.assertEquals(null, response.getSubscriberPlan());
			
			// error handling assert
			Assert.assertEquals(Integer.valueOf(2), response.getStatusMessageResponse().getStatusMessage().getStatus());
			Assert.assertEquals("NOK", response.getStatusMessageResponse().getStatusMessage().getDescription());
			

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

}
