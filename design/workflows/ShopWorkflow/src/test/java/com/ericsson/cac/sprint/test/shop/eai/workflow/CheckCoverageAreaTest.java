package com.ericsson.cac.sprint.test.shop.eai.workflow;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ericsson.cac.sprint.shop.workflow.ShopEaiWorkflow;
import com.ericsson.cac.sprint.shop.workflow.datamodel.AddressInfo;
import com.ericsson.cac.sprint.shop.workflow.datamodel.CoverageAreaRequest;
import com.ericsson.cac.sprint.shop.workflow.datamodel.CoverageAreaResponse;
import com.ericsson.cac.sprint.test.shop.workflow.TestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class CheckCoverageAreaTest {
	
	@Autowired
	private ShopEaiWorkflow shopEaiWorkflow;
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(CheckCoverageAreaTest.class);
	
	@Test
	public void testCheckCoverageArea() {

		try {

			CoverageAreaRequest request = new CoverageAreaRequest();
			AddressInfo shippingInfo = new AddressInfo();
			shippingInfo.setZipCode("75024");
			
			request.setShippingInfo(shippingInfo);			

			LOGGER.info("Calling checkCoverageArea");
			CoverageAreaResponse response = shopEaiWorkflow.checkCoverageArea(request);
			LOGGER.debug("After calling checkCoverageArea "+response.getStatus());
			
			Assert.assertEquals(true, response.getCovered());

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
	
	
	
	@Test
	public void negativetestCheckCoverageArea() {

		try {

			CoverageAreaRequest request = new CoverageAreaRequest();
			AddressInfo shippingInfo = new AddressInfo();
			shippingInfo.setZipCode("00000");
			
			request.setShippingInfo(shippingInfo);			

			LOGGER.info("Calling checkCoverageArea");
			CoverageAreaResponse response = shopEaiWorkflow.checkCoverageArea(request);
			LOGGER.debug("After calling checkCoverageArea "+response.getStatus());
			
			Assert.assertEquals(Integer.valueOf(101), response.getStatus());

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
	

}
