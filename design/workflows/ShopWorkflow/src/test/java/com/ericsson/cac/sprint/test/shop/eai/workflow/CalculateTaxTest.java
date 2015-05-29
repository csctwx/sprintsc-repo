package com.ericsson.cac.sprint.test.shop.eai.workflow;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ericsson.cac.sprint.shop.constants.ShopWorkFlowConstants;
import com.ericsson.cac.sprint.shop.workflow.ShopEaiWorkflow;
import com.ericsson.cac.sprint.shop.workflow.datamodel.AddressInfo;
import com.ericsson.cac.sprint.shop.workflow.datamodel.CalculateTaxRequest;
import com.ericsson.cac.sprint.shop.workflow.datamodel.CalculateTaxResponse;
import com.ericsson.cac.sprint.test.shop.workflow.TestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class CalculateTaxTest {
	
	@Autowired
	private ShopEaiWorkflow shopEaiWorkflow;
	private static final Logger LOGGER = LoggerFactory
			.getLogger(CalculateTaxTest.class);
	
	@Test
	public void testSuccessCalculateTax() {

		try {

			CalculateTaxRequest request = new CalculateTaxRequest();
			request.setLineNumber(1);
			request.setInvoiceNumber("IN12");
			//request.setItemId("789");
			request.setQuantity(1);
			request.setTaxOnAmount(100.00);
			AddressInfo billingInfo= new AddressInfo();
			billingInfo.setZipCode("75024");
			request.setBillingInfo(billingInfo);
			LOGGER.info("Calling GetAccessory");
			CalculateTaxResponse response = shopEaiWorkflow.calculateTax(request);
			LOGGER.debug("After calling calculateTax "+response.getStatus());
			Assert.assertEquals("SUCCESS", response.getDescription());

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	
	@Test
	public void testFailureCalculateTax() {

		try {

			CalculateTaxRequest request = new CalculateTaxRequest();
			request.setLineNumber(1);
			request.setInvoiceNumber("IN12");
			//request.setItemId("789");
			request.setQuantity(1);
			request.setTaxOnAmount(100.00);
			AddressInfo billingInfo= new AddressInfo();
			billingInfo.setZipCode("00000");
			request.setBillingInfo(billingInfo);
			LOGGER.info("Calling GetAccessory");
			CalculateTaxResponse response = shopEaiWorkflow.calculateTax(request);
			LOGGER.debug("After calling calculateTax "+response.getStatus());
			Assert.assertEquals(Integer.valueOf(ShopWorkFlowConstants.WS_ERROR_CODE), response.getStatus());

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
	
}
