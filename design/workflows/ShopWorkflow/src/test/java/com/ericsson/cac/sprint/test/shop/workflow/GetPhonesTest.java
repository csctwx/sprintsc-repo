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
import com.ericsson.cac.sprint.shop.workflow.datamodel.GetPhoneListRequest;
import com.ericsson.cac.sprint.shop.workflow.datamodel.ListAssetRequest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class GetPhonesTest {

	@Autowired
	private ShopWorkflow service;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(GetPhonesTest.class);

	
	@Test
	public void testGetPhonesByBrandId() {

		try {

			LOGGER.info("Calling GetPhonesByBrandId");
			GetPhoneListRequest getPhoneListRequest = new GetPhoneListRequest();
			getPhoneListRequest.setBrandId("bst");
			getPhoneListRequest.setPageNumber(1);
			GetListPhonesResponse response = service.getPhonesByBrandId(getPhoneListRequest );
			LOGGER.info("After calling GetPhonesByBrandId - Phone [] zeroth element "+response.getPhones()[0].getPhoneName());

			Assert.assertEquals(Integer.valueOf(0), response.getStatus());

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
	
	
	@Test
	public void negativetestGetPhones() {/*

		try {

			ListAssetRequest request = new ListAssetRequest();
			request.setListId("A-rD86XVKM1TW");
			request.setPageNumber(1);
			request.setPageSize(2);

			LOGGER.info("Calling GetPhones");
			GetListPhonesResponse response = service.getPhones(request);
			LOGGER.info("After calling GetPhones");

			Assert.assertNotEquals("1", response.getPhones()[0].getAssetId());

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	*/}

}
