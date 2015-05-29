package com.ericsson.cac.sprint.test.shop.epp.workflow;



import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ericsson.cac.sprint.shop.workflow.datamodel.AddressInfo;
import com.ericsson.cac.sprint.shop.workflow.datamodel.Equipment;
import com.ericsson.cac.sprint.shop.workflow.datamodel.PaymentInfo;
import com.ericsson.cac.sprint.shop.workflow.datamodel.ShippingAndBillingRequest;
import com.ericsson.cac.sprint.shop.workflow.datamodel.ShippingAndBillingResponse;
import com.ericsson.cac.sprint.shop.workflow.impl.ShopEppWorkFlowImpl;
import com.ericsson.cac.sprint.test.shop.workflow.TestConfig;
import com.google.gson.Gson;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class DoShippingBillingTest {
	
	@Autowired
	private ShopEppWorkFlowImpl service;
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(DoShippingBillingTest.class);
	
	@Test
	public void testDoShippingBilling() {

		try {
			CommonObjectCreator objectCreator=new CommonObjectCreator();
			PaymentInfo paymentInfo=objectCreator.getPaymentInfo("testDoShippingBilling");
			AddressInfo billingInfo=objectCreator.getBillingInfo("testDoShippingBilling");
			Equipment[] equipments =objectCreator.getEquipment("testDoShippingBilling");
			ShippingAndBillingRequest request= new ShippingAndBillingRequest();
			request.setBrandId("SPP");
			request.setPaymentInfo(paymentInfo);
			//request.setBillingInfo(billingInfo);
			request.setEquipments(equipments);			
			request.setShippingInfo(billingInfo);
			LOGGER.info("Calling doShippingBilling");		
			System.out.println(new Gson().toJson(request));
			ShippingAndBillingResponse response = service.doShippingBilling(request);			
			/*for(int i=1;i<=25;i++)
			{
				long number = (long) Math.floor(Math.random() * 9000000000L) + 1000000000L;
				System.out.println("number ::"+ number);
			}*/
			
			System.out.println(new Gson().toJson(response));
			Assert.assertEquals(Integer.valueOf(0), response.getStatus());

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
	
	
	/*@Test
	public void negativetestDoShippingBilling() {

		try {
			CommonObjectCreator objectCreator=new CommonObjectCreator();
			PaymentInfo paymentInfo=objectCreator.getPaymentInfo("testDoShippingBilling");
			AddressInfo billingInfo=objectCreator.getInvalidBillingInfo("testDoShippingBilling");
			Equipment[] equipments =objectCreator.getEquipment("testDoShippingBilling");
			ShippingAndBillingRequest request= new ShippingAndBillingRequest();
			request.setPaymentInfo(paymentInfo);
			request.setBillingInfo(billingInfo);
			request.setEquipments(equipments);			
			
			LOGGER.info("Calling doShippingBilling");		
			
			ShippingAndBillingResponse response = service.doShippingBilling(request);			
			for(int i=1;i<=25;i++)
			{
				long number = (long) Math.floor(Math.random() * 9000000000L) + 1000000000L;
				System.out.println("number ::"+ number);
			}
			//LOGGER.debug("tax trans id ::"+response.getTaxtransactionid());
			Assert.assertEquals(false, response.getAddressValid());

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}*/
}
