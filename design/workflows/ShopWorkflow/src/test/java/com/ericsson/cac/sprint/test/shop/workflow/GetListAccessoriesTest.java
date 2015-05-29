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
import com.ericsson.cac.sprint.shop.workflow.datamodel.GetListAccessoriesResponse;
import com.ericsson.cac.sprint.shop.workflow.datamodel.ListAssetRequest;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class GetListAccessoriesTest {
	
	@Autowired
	private ShopWorkflow service;
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(GetListAccessoriesTest.class);


	@Test
	public void testGetListAccessories() {

		try {

			ListAssetRequest request = new ListAssetRequest();
			request.setListId("L-rD86XVKM1TW");
			request.setPageNumber(1);
			request.setPageSize(2);

			LOGGER.info("Calling GetListAccessories");
			GetListAccessoriesResponse response = service.getListAccessories(request);
			LOGGER.debug("After calling GetListAccessories"+response.getAccessories()[0].getAssetId());
			
			Assert.assertEquals("A-XB96XVKM1TW", response.getAccessories()[0].getAssetId());

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
	
	
	@Test
	public void negativetestGetListAccessories() {/*

		try {

			ListAssetRequest request = new ListAssetRequest();
			request.setListId("A-rD86XVKM1TW");
			request.setPageNumber(1);
			request.setPageSize(2);

			LOGGER.info("Calling GetListAccessories");
			GetListAccessoriesResponse response = service.getListAccessories(request);
			LOGGER.debug("After calling GetListAccessories"+response.getAccessories()[0].getAssetId());
			
			Assert.assertNotEquals("1", response.getAccessories()[0].getAssetId());

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	*/}
	
}
