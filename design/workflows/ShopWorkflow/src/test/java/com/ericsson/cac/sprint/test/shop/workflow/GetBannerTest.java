package com.ericsson.cac.sprint.test.shop.workflow;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ericsson.cac.sprint.shop.workflow.datamodel.AssetRequest;
import com.ericsson.cac.sprint.shop.workflow.datamodel.GetBannerResponse;
import com.ericsson.cac.sprint.shop.workflow.impl.ShopWorkflowImpl;
import com.google.gson.Gson;

/**
 * BlockAllMessagesTest is used to test all the scenarios for the service layer
 * class
 * 
 * @author Ericsson
 * @version $Revision: 1.0 $
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class GetBannerTest {

	@Autowired
	private ShopWorkflowImpl service;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(GetBannerTest.class);

	@Test
	public void testGetBannerTest() {

		try {
			AssetRequest request = new AssetRequest();
			request.setAssetId("A-AFh6XVKM1TW");
			LOGGER.info("Calling get Banner request");
			GetBannerResponse response = service.getBanner(request);
			LOGGER.debug("get Banner response"+response.getBanner().getAssetId());

			System.out.println(new Gson().toJson(response));
			Assert.assertEquals("A-rD86XVKM1TW",response.getBanner().getAssetId());

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testNegativeGetBannerTest() {

		try {
			AssetRequest request = new AssetRequest();
			request.setAssetId("A-rD86XVKM1TW");
			LOGGER.info("Calling get Banner request");
			GetBannerResponse response = service.getBanner(request);
			LOGGER.debug("get Banner response"+response.getBanner().getAssetId());

			Assert.assertNotEquals( "1", response.getBanner().getAssetId());

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}

	}

}
