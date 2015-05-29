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
import com.ericsson.cac.sprint.shop.workflow.datamodel.GetPhoneDetailsRequest;
import com.ericsson.cac.sprint.shop.workflow.datamodel.GetPhoneDetailsResponse;
import com.ericsson.cac.sprint.shop.workflow.datamodel.GetPhoneResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class GetPhoneTest {

	@Autowired
	private ShopWorkflow service;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(GetPhoneTest.class);

	
	
	
	@Test
	public void testGetPhonebyExternalUrl() {

		try {
			LOGGER.info("Calling GetPhones");
			String url="A-Ckb6XVKM1TW";
			String brandId = "SPP";
			GetPhoneDetailsRequest request = new GetPhoneDetailsRequest();
			request.setBrandId(brandId);
			request.setBrandId(url);
			GetPhoneDetailsResponse response = service.getPhoneByExternalUrl(request);
		//	LOGGER.debug("After calling GetPhones"+response.getPhone().getAssetId());
			LOGGER.debug("response phone id="+response.getPhoneDetails().length);
			Assert.assertEquals(1, response.getPhoneDetails().length);

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
	

}
