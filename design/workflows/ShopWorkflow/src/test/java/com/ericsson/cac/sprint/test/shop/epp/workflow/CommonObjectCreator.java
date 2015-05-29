package com.ericsson.cac.sprint.test.shop.epp.workflow;

import java.math.BigInteger;

import com.ericsson.cac.sprint.shop.workflow.datamodel.AddressInfo;
import com.ericsson.cac.sprint.shop.workflow.datamodel.CalculateTaxResponse;
import com.ericsson.cac.sprint.shop.workflow.datamodel.Equipment;
import com.ericsson.cac.sprint.shop.workflow.datamodel.PaymentInfo;
import com.ericsson.cac.sprint.shop.workflow.datamodel.ShopBanInfo;

public class CommonObjectCreator {
	AddressInfo addressInfo=new AddressInfo();
	PaymentInfo paymentInfo= new PaymentInfo();
	ShopBanInfo banInfo=new ShopBanInfo();
	CalculateTaxResponse calculateTaxResponse=new CalculateTaxResponse();
	public AddressInfo getBillingInfo(){
		addressInfo.setAddress1("Stree1 one");
		addressInfo.setAddress2("line 2");
		addressInfo.setCity("new york");
		addressInfo.setFirstName("Firstname");
		addressInfo.setFirstName("Lastname");
		addressInfo.setState("newyork");
		addressInfo.setZipCode("10001");
		addressInfo.setPhone("2959994089");
		addressInfo.setShippingOption("Ground");
		addressInfo.setEmail("Mitul.Tyagi@ericsson.com");
		return addressInfo;
	}
	
	public PaymentInfo getPaymentInfo(){
		paymentInfo.setCardNumber("1222783498450091");
		paymentInfo.setCardType("CREDIT");
		paymentInfo.setExpirationMonth("06");
		paymentInfo.setExpirationYear("2020");
		paymentInfo.setSecurityCode("201");
		paymentInfo.setPaymentCardType("Visa");
		return paymentInfo;
	}
	
	public PaymentInfo getPaymentInfo(String methodname){
		paymentInfo.setCardNumber("4112344112344113");
		paymentInfo.setCardType("CREDIT");
		paymentInfo.setPaymentCardType("Visa");
		paymentInfo.setExpirationMonth("05");
		paymentInfo.setExpirationYear("17");
		paymentInfo.setSecurityCode("011");
		return paymentInfo;
	}
	
	public AddressInfo getBillingInfo(String methodname){
		//addressInfo.setAddress1("DOUG");
		//addressInfo.setAddress2("CHASE");
		//addressInfo.setCity("3 Main St");
		addressInfo.setFirstName("Mitul");
		addressInfo.setLastName("Tyagi");
		//addressInfo.setState("NY");
		//addressInfo.setZipCode("10453");
		
		addressInfo.setAddress1("6300 Legacy Drive");
		addressInfo.setAddress2(null);
		addressInfo.setCity("Dallas");
		addressInfo.setState("TX");
		addressInfo.setZipCode("75024");
		addressInfo.setShippingOption("Ground");
		addressInfo.setShippingFee("5.0");
		return addressInfo;
	}
	
	
	public AddressInfo getInvalidBillingInfo(String methodname){
		addressInfo.setAddress1("DOUG");
		addressInfo.setAddress2("CHASE");
		addressInfo.setCity("3 Main St");		
		addressInfo.setState("NY");
		addressInfo.setZipCode("10453");		
		return addressInfo;
	}
	
	public Equipment[] getEquipment(String methodname){
		Equipment equipments[] = new Equipment[1];
		Equipment equipment = new Equipment();
		if(methodname.equalsIgnoreCase("testDoCompletePurchase")){
			equipment.setEquipmentTaxAmt(8.00);
			equipment.setTaxTransactionId(new BigInteger("1742596037"));
			equipment.setInvoiceDate("2015-03-27");
			equipment.setTotalAmt(108.00);
		}
		
		
		equipment.setOrderLineId("1");
		//equipment.setModelId("SPHL710TASB");
		equipment.setModelId("SPHL710ASB");
		equipment.setModelPrice(100.00);
		equipment.setAccessoryInd(false);
		equipment.setQuantity(2);
		equipments[0]=equipment;
		return equipments;
	}
	
	public ShopBanInfo getShopBanInfo(){
		banInfo.setBan("BAN");
		banInfo.setCustomerBrand("brandID");
		banInfo.setCustomerSubBrand("subbrand");
		banInfo.setUsgBan(false);
		return banInfo;
	}
	
	public CalculateTaxResponse getCalculateTaxResposne(){
		calculateTaxResponse.setTransactionId(BigInteger.valueOf(10000012));
		calculateTaxResponse.setTaxAmount(8.00);
		calculateTaxResponse.setTotalAmount(108.00);
		calculateTaxResponse.setTaxOnAmount(100.00);
		return calculateTaxResponse;
	}
}
