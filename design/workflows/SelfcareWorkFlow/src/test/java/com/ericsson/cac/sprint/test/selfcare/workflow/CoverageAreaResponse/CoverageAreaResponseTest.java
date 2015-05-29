package com.ericsson.cac.sprint.test.selfcare.workflow.CoverageAreaResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ericsson.cac.sprint.selfcare.workflow.datamodel.CoverageAreaRequest;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.CoverageAreaResponse;
import com.ericsson.cac.sprint.selfcare.workflow.impl.AccountWorkflowImpl;
import com.ericsson.cac.sprint.test.selfcare.workflow.TestConfig;
import com.sprint.integration.eai.services.smspreferencemanagementservice.v1.smspreferencemanagementservice.SoapFault;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class CoverageAreaResponseTest {
	@Autowired
	private AccountWorkflowImpl service;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CoverageAreaResponseTest.class);
	@Test
	public void CoverageAreaTestSuccess() throws SoapFault{

		try {
			CoverageAreaRequest context = new CoverageAreaRequest();
			context.setZipCode("75023");
			LOGGER.debug("Calling getBlockedMessages");
			
			CoverageAreaResponse coverageareares = service.checkCoverageArea(context);
			LOGGER.debug("After calling CoverageAreaResponse");
			LOGGER.info("After calling  NPA : "+coverageareares.getNpa());
			LOGGER.info("After calling  CSA : "+coverageareares.getCsa());	
			Assert.assertNotNull(coverageareares.getCsa());
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	@Test
	public void CoverageAreaTestFail() throws SoapFault{

		try {
			CoverageAreaRequest context = new CoverageAreaRequest();
			context.setZipCode("");
			LOGGER.debug("Calling getBlockedMessages");
			
			CoverageAreaResponse coverageareares =  service.checkCoverageArea(context);
			LOGGER.debug("After calling CoverageAreaResponse");
//			Assert.assertFalse(false);
			Assert.assertNull(coverageareares.getZipCode());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Test
	public void CoverageAreaTest_Fail() throws SoapFault{

		try {
			CoverageAreaRequest context = new CoverageAreaRequest();
			context.setZipCode("5");
			LOGGER.debug("Calling getBlockedMessages");
			
			CoverageAreaResponse coverageareares =  service.checkCoverageArea(context);
			LOGGER.debug("After calling CoverageAreaResponse");
//			Assert.assertFalse(false);
			Assert.assertNull(coverageareares.getZipCode());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
