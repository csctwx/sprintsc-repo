package com.ericsson.cac.sprint.test.selfcare.workflow.changeNumber;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ericsson.cac.sprint.selfcare.workflow.AccountDeviceWorkflow;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.Address;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.ChangeNumberResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.UserContext;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.UserContextRequest;
import com.ericsson.cac.sprint.test.selfcare.workflow.TestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class ChangeNumberTest {

	@Autowired
	private AccountDeviceWorkflow service;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ChangeNumberTest.class);

	@Test
	public void testChangeNumberSuccess() {
		Address address = new Address();
		address.setZipCode("94610");
		UserContext userContext = new UserContext();
		userContext.setMsisdn("6212126685");
		UserContextRequest userContextRequest = new UserContextRequest();
		userContextRequest.setUserContext(userContext);
		try {

			ChangeNumberResponse response = service.changeNumber(address, userContextRequest);
			Assert.assertNotNull(response);

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}

	}

}