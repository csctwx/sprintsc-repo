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
import com.ericsson.cac.sprint.shop.workflow.datamodel.GetFeatureRequest;
import com.ericsson.cac.sprint.shop.workflow.datamodel.GetAccessoryResponse;
import com.ericsson.cac.sprint.shop.workflow.datamodel.GetFeaturesResponse;
import com.google.gson.Gson;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class GetFeaturesTest {
	
	@Autowired
	private ShopWorkflow service;
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(GetFeaturesTest.class);
	
	@Test
	public void testGetFeatures() {

		try {

			AssetRequest request = new AssetRequest();
			request.setAssetId("A-rD86XVKM1TW");
			GetFeatureRequest featuresRequest= new GetFeatureRequest();
			featuresRequest.setPhoneId("A-ykb6XVKM1TW");
			featuresRequest.setBrandId("boost");
			LOGGER.info("Calling GetAccessory");
			GetFeaturesResponse response = service.getFeatures(featuresRequest,true);
			System.out.println(new Gson().toJson(response));
			//LOGGER.debug("After calling GetAccessory "+response.getAccessory().getAssetId());
			
			//Assert.assertEquals("A-rD86XVKM1TW", response.getAccessory().getAssetId());

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
	
	
	

}
