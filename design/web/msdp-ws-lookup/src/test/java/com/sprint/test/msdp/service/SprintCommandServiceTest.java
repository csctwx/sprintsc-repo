package com.sprint.test.msdp.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sprint.msdp.commandservice.CommandMetaWs;
import com.sprint.msdp.commandservice.CommandRequest;
import com.sprint.msdp.commandservice.CommandResponse;
import com.sprint.msdp.commandservice.WSExceptionException;
import com.sprint.msdp.commandservice.service.SprintCommandService;
import com.sprint.test.msdp.TestConfig;

/**
 * LostPhoneTest is used to test all the scenarios for the service layer class
 *
 * @author Ericsson
 * @version $Revision: 1.0 $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class SprintCommandServiceTest {
	@Autowired
	private SprintCommandService service;

	@Test
	public void testCommandRequest() {
		CommandRequest parameters = new CommandRequest();
		try {
			CommandMetaWs e = new CommandMetaWs();
			e.setKey("msisdn");
			e.setValue("3179566118");
			parameters.getCommandMeta().add(e);
			e = new CommandMetaWs();
			e.setKey("pin");
			e.setValue("1234");
			parameters.getCommandMeta().add(e);
			CommandResponse commandRequest = service.commandRequest(parameters);
			System.out.println(commandRequest);
		} catch (WSExceptionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
