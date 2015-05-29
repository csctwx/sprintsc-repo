package com.ericsson.cac.sprint.test.shop.workflow;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ericsson.cac.sprint.shop.workflow.ShopWorkflow;
import com.ericsson.cac.sprint.shop.workflow.datamodel.GetListPlansResponse;
import com.ericsson.cac.sprint.shop.workflow.datamodel.ListAssetRequest;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class GetPlansTest {

	@Autowired
	private ShopWorkflow service;
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(GetPlansTest.class);

	@Test
	public void testGetPlans() {

		try {

			ListAssetRequest request = new ListAssetRequest();
			request.setListId("L-rD86XVKM1TW");
			request.setPageNumber(1);
			request.setPageSize(2);


			LOGGER.info("Calling GetPlan");
			GetListPlansResponse response = service.getPlans(request);
			LOGGER.debug("After calling GetPlan"+response.getPlans()[0].getAssetId());
			
			Assert.assertEquals("A-XB96XVKM1TW", response.getPlans()[0].getAssetId());

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
	
	
	@Test
	public void negativetestGetPlans() {/*

		try {

			ListAssetRequest request = new ListAssetRequest();
			request.setListId("A-rD86XVKM1TW");
			request.setPageNumber(1);
			request.setPageSize(2);


			LOGGER.info("Calling GetPlan");
			GetListPlansResponse response = service.getPlans(request);
			LOGGER.debug("After calling GetPlan"+response.getPlans()[0].getAssetId());
			
			Assert.assertNotEquals("1", response.getPlans()[0].getAssetId());

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	
*/}
}

