package com.ericsson.cac.sprint.test.selfcare.workflow.updateAccount;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ericsson.cac.sprint.selfcare.workflow.AccountSettingsWorkflow;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.Address;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.StatusMessageResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.SubscriberAccount;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.SubscriberAccountRequest;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.UserContext;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.UserContextRequest;
import com.ericsson.cac.sprint.test.selfcare.workflow.TestConfig;
import com.sprint.integration.interfaces.validateaddress.v1.validateaddress.ValidateAddressResponseType;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class UpdateAccountTest {
	@Autowired
	private AccountSettingsWorkflow service;
	private static final Logger LOGGER = LoggerFactory.getLogger(UpdateAccountTest.class);
	private ValidateAddressResponseType validateAddressResponse;
	
	@Test
	public void testUpdateAccount() throws ParseException{
		LOGGER.debug("testing UpdateAccount API...");
		 UserContextRequest userContextRequest = new UserContextRequest();
		 UserContext userContext = new UserContext();
		 userContext.setMsisdn("3179566118");
		 userContextRequest.setUserContext(userContext);
		
		StatusMessageResponse response=new StatusMessageResponse();
		SubscriberAccountRequest request=new SubscriberAccountRequest();
		SubscriberAccount subscriberAccount=new SubscriberAccount();
		Address add=new Address();
		add.setAddress1("2109, Fox Drive");
		add.setAddress2("11th Street");
		add.setCity("Champaign");
		add.setState("IL");
		add.setZipCode("61820");
		
		subscriberAccount.setAccountType("M");
		subscriberAccount.setAddress(add);
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
		String dateInString = "31-08-1982";
		Date date = sdf.parse(dateInString);
		LOGGER.debug("test Birth date:::"+date);
		subscriberAccount.setBirthdate(date);
		
		request.setSubscriberAccount(subscriberAccount);
		
		response=service.updateAccount(request,userContextRequest);
		//Assert.assertNotNull(response.getStatusMessage());
		Assert.assertEquals("0", response.getStatusMessage().getStatus().toString());
		
		
	}
	

}
