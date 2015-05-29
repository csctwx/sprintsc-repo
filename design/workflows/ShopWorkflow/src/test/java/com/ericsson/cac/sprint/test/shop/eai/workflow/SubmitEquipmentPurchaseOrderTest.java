package com.ericsson.cac.sprint.test.shop.eai.workflow;

import static org.junit.Assert.assertEquals;

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
import com.ericsson.cac.sprint.shop.workflow.datamodel.EPPPaymentInfo;
import com.ericsson.cac.sprint.shop.workflow.datamodel.Equipment;
import com.ericsson.cac.sprint.shop.workflow.datamodel.Order;
import com.ericsson.cac.sprint.shop.workflow.datamodel.PaymentInfo;
import com.ericsson.cac.sprint.shop.workflow.datamodel.SubmitOrderRequest;
import com.ericsson.cac.sprint.shop.workflow.datamodel.SubmitOrderResponse;
import com.ericsson.cac.sprint.test.shop.epp.workflow.CommonObjectCreator;
import com.ericsson.cac.sprint.test.shop.workflow.TestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class SubmitEquipmentPurchaseOrderTest {

	@Autowired
	private ShopEaiWorkflow shopEaiWorkflow;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ValidateAddressTest.class);

	@Test
	public void testSubmitEquipmentPurchaseOrder() {
		try{
			SubmitOrderRequest request = new SubmitOrderRequest();
			
			/*request.setVendorCode("BHK");
			request.setOrderId("12345");
			request.setAccountType("test");
			request.setAccountSubType("test");
			
			AddressInfo addressShippingInfo = new AddressInfo();		
			addressShippingInfo.setFirstName("test");
			addressShippingInfo.setLastName("test");
			addressShippingInfo.setAddress1("test");
			addressShippingInfo.setAddress2("test");
			addressShippingInfo.setCity("test");
			addressShippingInfo.setState("test");
			addressShippingInfo.setEmail("test@test.com");
			addressShippingInfo.setShippingOption("test");
			//this field is not at impl
			////shippingInfoType.setShippingFee(new java.math.BigDecimal("1000"));
			
			request.setShippingInfo(addressShippingInfo);
			
			AddressInfo addressBillingInfo = new AddressInfo();		
			addressBillingInfo.setFirstName("test");
			addressBillingInfo.setLastName("test");
			addressBillingInfo.setAddress1("test");
			addressBillingInfo.setAddress2("test");
			addressBillingInfo.setCity("test");
			addressBillingInfo.setState("test");
			addressBillingInfo.setEmail("test@test.com");
			
			request.setBillingInfo(addressBillingInfo);
            EPPPaymentInfo ePPPaymentInfo = new EPPPaymentInfo();
            ePPPaymentInfo.setPaymentType("test");
			request.setPaymentType(ePPPaymentInfo);
			
			Order orderLineInfo = new Order();
			Equipment[] equipments = new Equipment[1];
			orderLineInfo.setEquipments(equipments);
			orderLineInfo.setSubTotalAmount(100.00);
			orderLineInfo.setTaxAmount(100.00);
			orderLineInfo.setTotalAmount(100.00);
			
			equipments[0].setOrderLineId("123456");
			equipments[0].setModelId("34521");
			equipments[0].setModelPrice(1000.00);
			equipments[0].setEquipmentTaxAmt(10.00);
			
			orderLineInfo.setEquipments(equipments);
			request.setOrderLineInfo(orderLineInfo);
			*/CommonObjectCreator objectCreator=new CommonObjectCreator();
			   PaymentInfo paymentInfo=objectCreator.getPaymentInfo("testDoShippingBilling");
			   AddressInfo billingInfo=objectCreator.getBillingInfo("testDoShippingBilling");
			   Equipment[] equipments =objectCreator.getEquipment("testDoCompletePurchase");
			  // CalculateTaxResponse calculateTaxResponse=objectCreator.getCalculateTaxResposne();
			   //SubmitOrderRequest request= new SubmitOrderRequest();
			   request.setPaymentInfo(paymentInfo);
			   request.setBillingInfo(billingInfo);
			   request.setShippingInfo(billingInfo);
			   Order order = new Order();
			   order.setEquipments(equipments);
			   order.setTaxAmount(8.00);
			   order.setTotalAmount(108.00);
			   order.setSubTotalAmount(100.00);
			   request.setOrderLineInfo(order);
			   //request.setEquipments(equipments);
			   //request.setTransactionId("TOP_249597750_1425924217561");
			   request.setOrderId("KHK_TEST_ORDER_011");
			   //request.setCalculateTaxResponse(calculateTaxResponse);
			   EPPPaymentInfo ePPPaymentInfo = new EPPPaymentInfo();
	            ePPPaymentInfo.setChannelId("WEB_VMU");
	            ePPPaymentInfo.setActorId("249597750");
	            ePPPaymentInfo.setTokenId("SY1X18RMWN5K6785");
	            ePPPaymentInfo.setTokenType("TOKEN");
	            ePPPaymentInfo.setExternalOrderId("KHK_TEST_ORDER_011");
	            ePPPaymentInfo.setGlobalOrderId("SY1X18RMWN5K6785");
				request.setPaymentType(ePPPaymentInfo);
				request.setAuthorizationCode("tst726");
				request.setEppPaymentId("jHpZvHG4KuxTCr16aiqzipWY6WmW9BAsrIah");
			SubmitOrderResponse submitOrderResponse = shopEaiWorkflow.submitOrder(request);
			
			assertEquals(Integer.valueOf(0),submitOrderResponse.getStatus());
			LOGGER.debug("Success");
		}catch(Exception e){
			Assert.fail(e.getMessage());
		}
	}

}
