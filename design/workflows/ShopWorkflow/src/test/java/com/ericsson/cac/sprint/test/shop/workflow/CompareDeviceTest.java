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
import com.ericsson.cac.sprint.shop.workflow.datamodel.CompareDeviceRequest;
import com.ericsson.cac.sprint.shop.workflow.datamodel.GetCompareDeviceResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class CompareDeviceTest {

	@Autowired
	private ShopWorkflow service;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(CompareDeviceTest.class);

	@Test
	public void testCompareDevice() {

		try {
				CompareDeviceRequest request= new CompareDeviceRequest();
				request.setExternalId1("A-rD86XVKM1TW");
				request.setExternalId2("A-rD86XVKM1TW");
				request.setExternalId3("A-rD86XVKM1TW");
				request.setExternalId4("A-rD86XVKM1TW");
				
				GetCompareDeviceResponse response= service.getCompareDeviceResponse(request);
				Assert.assertEquals(Integer.valueOf(0), response.getStatus());
				LOGGER.debug("Success");
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
	
	
	@Test
	public void negativetestCompareDevice() {

		try {
			CompareDeviceRequest request= new CompareDeviceRequest();
			request.setExternalId1("extId1");
			GetCompareDeviceResponse response= service.getCompareDeviceResponse(request);
			Assert.assertEquals(Integer.valueOf(101), response.getStatus());
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

}
