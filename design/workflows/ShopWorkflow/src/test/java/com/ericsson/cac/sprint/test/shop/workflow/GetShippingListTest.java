/**
 * 
 */
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
import com.ericsson.cac.sprint.shop.workflow.datamodel.GetShippingListRequest;
import com.ericsson.cac.sprint.shop.workflow.datamodel.ListAssetRequest;
import com.ericsson.cac.sprint.shop.workflow.datamodel.ShippingListResponse;
import com.google.gson.Gson;

/**
 * @author eashich
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class GetShippingListTest {

	@Autowired
	private ShopWorkflow service;
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(GetShippingListTest.class);
	
	@Test
	public void testGetShippingList() {
		
		try {

			GetShippingListRequest objGetShippingListRequest = new GetShippingListRequest();
			objGetShippingListRequest.setBrandId("SPP");

			LOGGER.info("Calling GetShippingList");
			ShippingListResponse objShippingListResponse = service.getShippingList(objGetShippingListRequest);
			LOGGER.debug("After calling GetShippingList"+objShippingListResponse.getStatus()+"objShippingListResponse.getStatus"+objShippingListResponse.getDescription()+"objShippingListResponse.getShipping"+objShippingListResponse.getShipping());
			System.out.println(new Gson().toJson(objShippingListResponse));
			Assert.assertEquals(Integer.valueOf(0), objShippingListResponse.getStatus());

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
	

}
