package com.sprint.msdp.commandservice.service;

import com.sprint.msdp.commandservice.CommandRequest;
import com.sprint.msdp.commandservice.CommandResponse;
import com.sprint.msdp.commandservice.WSExceptionException;

public interface SprintCommandService {

	CommandResponse commandRequest(CommandRequest parameters) throws WSExceptionException;

}
