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
import com.ericsson.cac.sprint.shop.workflow.datamodel.CompletePurchaseRequest;
import com.ericsson.cac.sprint.shop.workflow.datamodel.CompletePurchaseResponse;
import com.ericsson.cac.sprint.shop.workflow.datamodel.Equipment;
import com.ericsson.cac.sprint.shop.workflow.datamodel.PaymentInfo;
import com.ericsson.cac.sprint.shop.workflow.impl.ShopEppWorkFlowImpl;
import com.ericsson.cac.sprint.test.shop.workflow.TestConfig;
import com.google.gson.Gson;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class DoCompletePurchaseTest {
	
	@Autowired
	private ShopEppWorkFlowImpl service;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DoShippingBillingTest.class);
	
	@Test
	 public void testCompletePurchase() {

	  try {
	   CommonObjectCreator objectCreator=new CommonObjectCreator();
	   PaymentInfo paymentInfo=objectCreator.getPaymentInfo("testDoShippingBilling");
	   AddressInfo billingInfo=objectCreator.getBillingInfo("testDoShippingBilling");
	   Equipment[] equipments =objectCreator.getEquipment("testDoCompletePurchase");
	   CompletePurchaseRequest request= new CompletePurchaseRequest();
	   request.setPaymentInfo(paymentInfo);
	   //request.setBillingInfo(billingInfo);
	   request.setShippingInfo(billingInfo);
	   request.setEquipments(equipments);
	   request.setTransactionId("TOP_249597750_1425924217561");
	   request.setOrderId("KHK_TEST_ORDER_011");
	   request.setBrandId("SPP");
	   LOGGER.info("Calling doShippingBilling");
	   System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
	   System.out.println(new Gson().toJson(request, CompletePurchaseRequest.class));
	   System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
	   System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
	   CompletePurchaseResponse response=service.doCompletePurchase(request);
	
	   Assert.assertEquals(Integer.valueOf(0), response.getStatus());

	  } catch (Exception e) {
	   Assert.fail(e.getMessage());
	  }
	 }

}
