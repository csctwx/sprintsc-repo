/**
 * Sprint Template
 */
package com.ericsson.cac.sprint.test.selfcare.workflow.swapESN;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ericsson.cac.sprint.selfcare.workflow.AccountDeviceWorkflow;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.ESNSwapPrepareRequest;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.SwapEsnResponse;
import com.ericsson.cac.sprint.test.selfcare.workflow.TestConfig;


/**
 * FoundPhoneTest is used to test all the scenarios for the service layer class 
 *
 * @author Ericsson
 * @version $Revision: 1.0 $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class SwapESNTest {

	@Autowired
	private AccountDeviceWorkflow service;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SwapESNTest.class);
	@Test
	public void testfoundPhone_Success() {

		ESNSwapPrepareRequest esnRequest = new ESNSwapPrepareRequest();
		esnRequest.setSerialNumber("256691465904723784");

		try {	
			
			SwapEsnResponse response = service.swapESN(esnRequest);
			Assert.assertEquals("7605868914", String.valueOf(response.getMsisdn()));
			
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}

	}	
	
	/*@Test
	public void testswapESN_Failure() {

		ESNSwapPrepareRequest esnRequest = new ESNSwapPrepareRequest();
		esnRequest.setSerialNumber("256691465904723784");

		try {	
			
			SwapEsnResponse response = service.swapESN(esnRequest);
			Assert.assertEquals("0", String.valueOf(response.getStatus()));
			
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}

	}*/

}
