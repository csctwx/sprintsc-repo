/**
 * Sprint Template
 */
package com.ericsson.cac.sprint.test.selfcare.workflow.myaccount;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ericsson.cac.sprint.selfcare.workflow.commons.HeaderData;
import com.ericsson.cac.sprint.selfcare.workflow.myaccount.MyAccount;
import com.ericsson.cac.sprint.selfcare.workflow.myaccount.model.MyAccountResponse;
import com.ericsson.cac.sprint.test.selfcare.workflow.TestConfig;


/**
 * MyAccountTest is used to test all the scenarios for the service layer class
 * 
 * @author Ericsson
 * @version $Revision: 1.0 $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Ignore
public class MyAccountTest{

	@Autowired
	private MyAccount service;
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(MyAccountTest.class);
	
	@Test
	public void testQuerySubscriberPrepaidInfo() {
		try {	
			
			HeaderData headerData = new HeaderData(); 
			headerData.setIpAddress("1.2.3.4");
			headerData.setSessionInfo("session");
			headerData.setUrl("");
			
			String mdn = "3179566118";  	
			MyAccountResponse result = service.querySubscriberPrepaidInfo(headerData, mdn); 

			Assert.assertNotNull(result); 		
			Assert.assertNotEquals(null, result.getPrepaidInfoResponseType()); 	
			Assert.assertEquals("102222849", result.getPrepaidInfoResponseType().getBasicInfo().getBan());
			
			/*String mdn = "3179566118";
			HeaderData headerData = new HeaderData();
			
			 // Not Required for adapter call in this use case;
			 
			headerData.setDevice("iPhone");
			headerData.setIp_address("1.2.3.4");
			headerData.setSession_info("session");
			headerData.setUrl("");
			
			
			MyAccountResponse response = service.querySubscriberPrepaidInfo(headerData, mdn);
			PortedNumberIndicatorType mdnType = response.getPrepaidInfoResponseType().getPortingInfo().getMdnType();
			
			//Assert.assertE
			Assert.assertEquals("SUCCESS", "RGL", mdnType.toString());	*/
			
			
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
		
	}
	
	
	@Test
	public void testQueryAccountBasicInfo() {
		try {	
			
			HeaderData headerData = new HeaderData(); 
			headerData.setIpAddress("1.2.3.4");
			headerData.setSessionInfo("session");
			headerData.setUrl("");
			
			String mdn = "3179566118";  	
					
			MyAccountResponse result = service.queryAccountBasicInfo(headerData, mdn);

			Assert.assertNotNull(result); 		
			Assert.assertNotEquals(null, result.getQueryAccountBasicInfoResponse()); 	
			//Assert.assertEquals("102222849", result.getQueryAccountBasicInfoResponse().getBasicInfo().getBan());
			
			
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
		
	}
	
	@Test
	public void testQueryBalanceAndChargesV2() {
		try {	
			
			HeaderData headerData = new HeaderData(); 
			headerData.setIpAddress("1.2.3.4");
			headerData.setSessionInfo("session");
			headerData.setUrl("");
			
			String mdn = "3179566118";  
			MyAccountResponse result = service.queryBalanceAndChargesV2(headerData, mdn);

			Assert.assertNotNull(result); 		
			Assert.assertNotEquals(null, result.getQueryBalanceAndChargesV2ResponseType()); 	
			//Assert.assertEquals("102222849", result.getQueryAccountBasicInfoResponse().getBasicInfo().getBan());
			
			
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
		
	}
		
		@Test
		public void testQueryPrepaidAllowance() {
			try {	
				
				HeaderData headerData = new HeaderData(); 
				headerData.setIpAddress("1.2.3.4");
				headerData.setSessionInfo("session");
				headerData.setUrl("");
				
				String mdn = "3179566118";  
				
				MyAccountResponse result = service.queryPrepaidAllowance(headerData, mdn);

				Assert.assertNotNull(result); 		
				Assert.assertNotEquals(null, result.getQueryPrepaidAllowanceResponseType()); 	
				//Assert.assertEquals("102222849", result.getQueryAccountBasicInfoResponse().getBasicInfo().getBan());
				
				
			} catch (Exception e) {
				Assert.fail(e.getMessage());
			}
			
		}
			
			@Test
			public void testQuerySubscriberServices() {
				try {	
					
					HeaderData headerData = new HeaderData(); 
					headerData.setIpAddress("1.2.3.4");
					headerData.setSessionInfo("session");
					headerData.setUrl("");
					
					String mdn = "3179566118";  
					
					MyAccountResponse result = service.querySubscriberServices(headerData, mdn);

					Assert.assertNotNull(result); 		
					Assert.assertNotEquals(null, result.getQuerySubscriberServicesResponse()); 	
					//Assert.assertEquals("102222849", result.getQueryAccountBasicInfoResponse().getBasicInfo().getBan());
					
					
				} catch (Exception e) {
					Assert.fail(e.getMessage());
				}
		
	}
			
			@Test
			public void testQueryDeviceInfo() {
				try {	
					
					HeaderData headerData = new HeaderData(); 
					headerData.setIpAddress("1.2.3.4");
					headerData.setSessionInfo("session");
					headerData.setUrl("");
					
					String mdn = "3179566118";  
					MyAccountResponse result = service.queryDeviceInfo(headerData, mdn);

					Assert.assertNotNull(result); 		
					Assert.assertNotEquals(null, result.getQueryDeviceInfoResponseType()); 	
					//Assert.assertEquals("102222849", result.getQueryAccountBasicInfoResponse().getBasicInfo().getBan());
					
					
				} catch (Exception e) {
					Assert.fail(e.getMessage());
				}
		
	}
			
			@Test
			public void testQueryPrepaidBalanceAndThresholdInfo() {
				try {	
					
					HeaderData headerData = new HeaderData(); 
					headerData.setIpAddress("1.2.3.4");
					headerData.setSessionInfo("session");
					headerData.setUrl("");
					
					String mdn = "3179566118";  	
					MyAccountResponse result = service.queryPrepaidBalanceAndThresholdInfo(headerData, mdn);

					Assert.assertNotNull(result); 		
					Assert.assertNotEquals(null, result.getQueryPrepaidBalanceAndThresholdInfoResponseType()); 	
					//Assert.assertEquals("102222849", result.getQueryAccountBasicInfoResponse().getBasicInfo().getBan());
					
					
				} catch (Exception e) {
					Assert.fail(e.getMessage());
				}
		
	}
			
			@Test
			public void testQueryAvailableOptions() {
				try {	
					
					HeaderData headerData = new HeaderData(); 
					headerData.setIpAddress("1.2.3.4");
					headerData.setSessionInfo("session");
					headerData.setUrl("");
					
					String mdn = "3179566118";  
					
					MyAccountResponse result = service.queryAvailableOptions(headerData, mdn);

					Assert.assertNotNull(result); 		
					Assert.assertNotEquals(null, result.getQueryAvailableOptionsResponseType()); 	
					//Assert.assertEquals("102222849", result.getQueryAccountBasicInfoResponse().getBasicInfo().getBan());
					
					
				} catch (Exception e) {
					Assert.fail(e.getMessage());
				}
		
	}
			
			@Test
			public void testQueryAvailablePlans() {
				try {	
					
					HeaderData headerData = new HeaderData(); 
					headerData.setIpAddress("1.2.3.4");
					headerData.setSessionInfo("session");
					headerData.setUrl("");
					
					String mdn = "3179566118";  
					
					MyAccountResponse result = service.queryAvailablePlans(headerData, mdn);

					Assert.assertNotNull(result); 		
					Assert.assertNotEquals(null, result.getQueryAvailablePlansResponseType()); 	
					//Assert.assertEquals("102222849", result.getQueryAccountBasicInfoResponse().getBasicInfo().getBan());
					
					
				} catch (Exception e) {
					Assert.fail(e.getMessage());
				}
		
	}
	
	/*@Test 	
	public void testQueryAvailablePlans_1() throws Exception 
	{ 		
		MyAccountImpl fixture = new MyAccountImpl(); 
		fixture.accountResponse = new MyAccountResponse(); 
		fixture.successHead = new WsMessageHeaderType(); 	
		fixture.successHeader = new Holder<>(); 	
		HeaderData data = new HeaderData(); 		
		String mdn = "";  		
		MyAccountResponse result = fixture.queryAvailablePlans(data, mdn); 
		// add additional test code here 	
		Assert.assertNotNull(result); 		
		Assert.assertEquals(null, result.getPrepaidInfoResponseType()); 	
		Assert.assertEquals(null, result.getQueryPrepaidBalanceAndThresholdInfoResponseType()); 
		Assert.assertEquals(null, result.getQueryDeviceInfoResponseType()); 	
		Assert.assertEquals(null, result.getQuerySubscriberServicesResponse());
		Assert.assertEquals(null, result.getQueryPrepaidAllowanceResponseType()); 
		Assert.assertEquals(null, result.getQueryBalanceAndChargesV2ResponseType()); 
		Assert.assertEquals(null, result.getQueryAccountBasicInfoResponse()); 	
		Assert.assertEquals(null, result.getQueryAvailableOptionsResponseType());
		
		}*/
	

}
