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
import com.ericsson.cac.sprint.shop.workflow.datamodel.AssetRequest;
import com.ericsson.cac.sprint.shop.workflow.datamodel.GetPlanResponse;
import com.ericsson.cac.sprint.test.shop.workflow.TestConfig;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class GetPlanTest {

	@Autowired
	private ShopWorkflow service;
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(GetPlanTest.class);

	@Test
	public void testGetPlan() {

		try {

			AssetRequest request = new AssetRequest();
			request.setAssetId("A-rD86XVKM1TW");

			LOGGER.info("Calling GetPlan");
			GetPlanResponse response = service.getPlan(request);
			LOGGER.debug("After calling GetPlan"+response.getPlan().getAssetId());
			
			Assert.assertEquals("A-rD86XVKM1TW", response.getPlan().getAssetId());

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
	
	
	@Test
	public void negativetestGetPlan() {

		try {

			AssetRequest request = new AssetRequest();
			request.setAssetId("A-rD86XVKM1TW");

			LOGGER.info("Calling GetPlan");
			GetPlanResponse response = service.getPlan(request);
			LOGGER.debug("After calling GetPlan"+response.getPlan().getAssetId());
			Assert.assertNotEquals("1", response.getPlan().getAssetId());

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
	
}

