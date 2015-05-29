package com.sprint.msdp.commandservice;
 
import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.sprint.msdp.commandservice.service.SprintCommandService;
 
@WebService(endpointInterface = "com.sprint.msdp.commandservice.EsbCommandService")
public class EsbCommandServiceImpl  extends SpringBeanAutowiringSupport implements EsbCommandService   {

	@Autowired
	private SprintCommandService sprintCommandService;
	
	@Override
	public CommandResponse commandRequest(CommandRequest parameters)
			throws WSExceptionException {
		return sprintCommandService.commandRequest(parameters);
	}
}