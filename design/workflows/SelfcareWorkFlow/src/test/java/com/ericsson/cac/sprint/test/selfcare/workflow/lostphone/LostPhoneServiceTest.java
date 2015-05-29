/**
 * Sprint Template
 */
package com.ericsson.cac.sprint.test.selfcare.workflow.lostphone;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ericsson.cac.sprint.selfcare.workflow.commons.HeaderData;
import com.ericsson.cac.sprint.selfcare.workflow.lostphone.LostPhoneService;
import com.ericsson.cac.sprint.selfcare.workflow.lostphone.model.RestoreRequest;
import com.ericsson.cac.sprint.selfcare.workflow.lostphone.model.RestoreResponse;
import com.ericsson.cac.sprint.selfcare.workflow.lostphone.model.SuspendRequest;
import com.ericsson.cac.sprint.selfcare.workflow.lostphone.model.SuspendResponse;
import com.ericsson.cac.sprint.test.selfcare.workflow.TestConfig;


/**
 * LostPhoneTest is used to test all the scenarios for the service layer class 
 *
 * @author Ericsson
 * @version $Revision: 1.0 $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class LostPhoneServiceTest {

	@Autowired
	private LostPhoneService service;
	private static final Logger LOGGER = LoggerFactory.getLogger(LostPhoneServiceTest.class);
	@Test
	public void testSuspendSubscriberSuccess() {
		//System.setProperty("socksProxyHost", "localhost");
		//System.setProperty("socksProxyPort", "3128");
		try {	
			String mdn = "3179566118";
			HeaderData headerData = new HeaderData();
			// Not Required for adapter call in this use case;
			headerData.setDevice("iPhone");
			headerData.setIpAddress("1.2.3.4");
			headerData.setSessionInfo("session");
			headerData.setUrl("");
			//LostPhoneResponse response = service.suspendSubscriber(headerData, mdn);
			LOGGER.debug("Calling suspend subscriber");
			SuspendResponse response = service.suspendSubscriber(new SuspendRequest(headerData, mdn));
			LOGGER.debug("response");
			Assert.assertEquals("Can not suspend the subscriber", "SUCCESS", response.getMessage());	
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}

	}
	
	
	@Test
	public void testSuspendSubscriberFailure() {
		//System.setProperty("socksProxyHost", "localhost");
		//System.setProperty("socksProxyPort", "3128");
		try {	
			String mdn = "";
			HeaderData headerData = new HeaderData();
			// Not Required for adapter call in this use case;
			headerData.setDevice("iPhone");
			headerData.setIpAddress("1.2.3.4");
			headerData.setSessionInfo("session");
			headerData.setUrl("");
			//LostPhoneResponse response = service.suspendSubscriber(headerData, mdn);
			LOGGER.debug("Calling suspend subscriber");
			SuspendResponse response = service.suspendSubscriber(new SuspendRequest(headerData, mdn));
			LOGGER.debug("response");
			Assert.assertEquals("Please Enter a valid MDN", response.getMessage());	
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}

	}
	
	
	
	@Test
	public void testRestoreSubscriberSuccess() {
		try {				
			String mdn = "3179566118";
			HeaderData headerData = new HeaderData();
			// Not Required for adapter call in this use case;
			headerData.setDevice("iPhone");
			headerData.setIpAddress("1.2.3.4");
			headerData.setSessionInfo("session");
			headerData.setUrl("");
			//LostPhoneResponse response = service.restoreSubscriber(headerData, mdn);
			RestoreResponse response = service.restoreSubscriber(new RestoreRequest(headerData, mdn));
			LOGGER.debug("response");
			Assert.assertEquals("Can not restore the subscriber", "SUCCESS",response.getMessage());
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}

	}
	
	@Test
	public void testRestoreSubscriberFailure() {
		try {				
			String mdn = " ";
			HeaderData headerData = new HeaderData();
			// Not Required for adapter call in this use case;
			headerData.setDevice("iPhone");
			headerData.setIpAddress("1.2.3.4");
			headerData.setSessionInfo("session");
			headerData.setUrl("");
			//LostPhoneResponse response = service.restoreSubscriber(headerData, mdn);
			RestoreResponse response = service.restoreSubscriber(new RestoreRequest(headerData, mdn));
			LOGGER.debug("response");
			Assert.assertEquals("Please Enter a valid MDN", response.getMessage());
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}

	}
	

}
