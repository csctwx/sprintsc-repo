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
import com.ericsson.cac.sprint.shop.workflow.datamodel.EquipmentAvailabilityRequest;
import com.ericsson.cac.sprint.shop.workflow.datamodel.EquipmentAvailabilityResponse;
import com.ericsson.cac.sprint.shop.workflow.datamodel.GetPlanResponse;
import com.ericsson.cac.sprint.test.shop.workflow.TestConfig;
import com.google.gson.Gson;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class GetEquipmentListTest {

	@Autowired
	private ShopWorkflow service;
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(GetEquipmentListTest.class);

	@Test
	public void testGetPlan() {

		try {

			EquipmentAvailabilityRequest request = new EquipmentAvailabilityRequest();
			request.setBrandId("SPP");
			request.setModelId("REC4S16BLSB1");
			request.setIspreorder(true);

			LOGGER.info("Calling getEquipmentAvailability");
			EquipmentAvailabilityResponse response = service.getEquipmentAvailability(request);
			LOGGER.debug("After calling getEquipmentAvailability"+response.getEquipmentAvailability());
			System.out.println(new Gson().toJson(response));
			Assert.assertEquals(false, response.getEquipmentAvailability().getPreOrderIndicator());

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
	
	
	@Test
	public void negativetestGetPlan() {

		try {

			EquipmentAvailabilityRequest request = new EquipmentAvailabilityRequest();
			request.setBrandId("SPP");
			request.setModelId("REC4S16BLSB1");
			request.setIspreorder(true);

			LOGGER.info("Calling getEquipmentAvailability");
			EquipmentAvailabilityResponse response = service.getEquipmentAvailability(request);
			LOGGER.debug("After calling getEquipmentAvailability"+response.getEquipmentAvailability());
			System.out.println(new Gson().toJson(response));
			Assert.assertNotEquals(true, response.getEquipmentAvailability().getPreOrderIndicator());

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
	
}

