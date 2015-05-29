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
import com.ericsson.cac.sprint.shop.workflow.datamodel.GetListPhonesResponse;
import com.ericsson.cac.sprint.shop.workflow.datamodel.GetPhoneGenieListRequest;
import com.ericsson.cac.sprint.shop.workflow.datamodel.GetPhoneListRequest;
import com.ericsson.cac.sprint.shop.workflow.datamodel.ListAssetRequest;
import com.ericsson.cac.sprint.shop.workflow.datamodel.PhoneGenieListResponse;
import com.google.gson.Gson;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class GetPhoneGenieTest {

	@Autowired
	private ShopWorkflow service;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(GetPhoneGenieTest.class);

	
	@Test
	public void testGetPhoneGenie() {

		try {

			LOGGER.info("Calling GetPhoneGeniw");
			GetPhoneGenieListRequest getPhoneListRequest = new GetPhoneGenieListRequest();
			getPhoneListRequest.setBrandId("SPP");
			//getPhoneListRequest.setPageNumber(1);
			PhoneGenieListResponse response = service.getPhoneGenieList(getPhoneListRequest);
			//LOGGER.info("After calling GetPhonesByBrandId - Phone [] zeroth element "+response.getPhones()[0].getPhoneName());
			System.out.println(new Gson().toJson(response));
			Assert.assertEquals(Integer.valueOf(0), response.getStatus());

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
	
}
