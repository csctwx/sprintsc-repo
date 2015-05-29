package com.ericsson.sprint.cac.suc;

import java.util.ArrayList;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ericsson.drutt.ws.client.ServiceLocator;
import com.ericsson.sprint.cac.CommandMetaWs;
import com.ericsson.sprint.cac.CommandResult;
import com.ericsson.sprint.cac.EsbCommandService;
import com.ericsson.sprint.cac.WSExceptionException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:test-regularChannelContext.xml"})
public class TestCommandService {

	@Autowired
	ApplicationContext applicationContext;
	
	@Ignore
	@Test
	public void testExecute() throws WSExceptionException {
		ServiceLocator locator = (ServiceLocator) applicationContext.getBean("ServiceLocator");
		EsbCommandService commandService = locator.getCommandService();
		CommandResult result = commandService.commandRequest("testComamnd", new ArrayList<CommandMetaWs>());
		
	}

}
