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
import com.ericsson.cac.sprint.shop.workflow.datamodel.ValidatePromoCodeRequest;
import com.ericsson.cac.sprint.shop.workflow.datamodel.ValidatePromoCodeResponse;
import com.ericsson.cac.sprint.test.shop.workflow.TestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class ValidatePromoCodeTest {
	
	@Autowired
	private ShopEaiWorkflow shopEaiWorkflow;
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ValidatePromoCodeTest.class);
	
	@Test
	public void testValidatePromoCode() {

		try {

			ValidatePromoCodeRequest request = new ValidatePromoCodeRequest();
			request.setCustomerType("customerType");
			request.setPromoCode("promoCode");

			LOGGER.info("Calling GetAccessory");
			ValidatePromoCodeResponse response = shopEaiWorkflow.validatePromoCode(request);
			LOGGER.debug("After calling GetAccessory "+response.getStatus());
			
			Assert.assertEquals("0", response.getStatus());

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

}
