package com.ericsson.cac.sprint.test.shop.eai.workflow;

import org.apache.log4j.Logger;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ericsson.cac.sprint.shop.workflow.ShopEaiWorkflow;
import com.ericsson.cac.sprint.shop.workflow.datamodel.AddressInfo;
import com.ericsson.cac.sprint.shop.workflow.datamodel.ValidateAddressRequest;
import com.ericsson.cac.sprint.shop.workflow.datamodel.ValidateAddressResponse;
import com.ericsson.cac.sprint.test.shop.workflow.TestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class ValidateAddressTest {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ValidateAddressTest.class);

	@Autowired
	private ShopEaiWorkflow shopEaiWorkflow;
	

	
	
	@Test
	public void testValidateAddress() {

		try {

			ValidateAddressRequest request = new ValidateAddressRequest();
			AddressInfo shippingInfo = new AddressInfo();
			shippingInfo.setAddress1("6300 Legacy Drive");
			shippingInfo.setAddress2(null);
			shippingInfo.setCity("Dallas");
			shippingInfo.setState("Texas");
			shippingInfo.setZipCode("75024");
			request.setShippingInfo(shippingInfo);
			

			logger.info("Calling validateAddress");
			ValidateAddressResponse response = shopEaiWorkflow.validateAddress(request);
			if (logger.isInfoEnabled()) {
				logger.info("testValidateAddress() - ValidateAddressResponse response=" + response);
		}

			logger.debug("After calling validateAddress "+response.getStatus());
			
			Assert.assertEquals(true, response.getAddressExist());

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
		
		
		@Test
		public void negativetestValidateAddress() {

			try {

				ValidateAddressRequest request = new ValidateAddressRequest();
				AddressInfo shippingInfo = new AddressInfo();
				shippingInfo.setAddress1("6300 Legacy Drive");
				shippingInfo.setAddress2(null);
				shippingInfo.setCity("Dallas");
				shippingInfo.setState("Texas");
				shippingInfo.setZipCode("00000");
				request.setShippingInfo(shippingInfo);
				

				logger.info("Calling validateAddress");
				ValidateAddressResponse response = shopEaiWorkflow.validateAddress(request);
				logger.debug("After calling validateAddress "+response.getStatus());
				
				Assert.assertEquals(false, response.getAddressExist());

			} catch (Exception e) {
				Assert.fail(e.getMessage());
			}
	}

}
