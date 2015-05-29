package com.ericsson.cac.sprint.selfcare.workflow;

import com.ericsson.cac.sprint.selfcare.workflow.datamodel.ForgotPinRequest;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.LoginRequest;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.StatusMessageResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.UserContextRequest;

public interface AccountLoginWorkflow {

	public StatusMessageResponse sendPin(ForgotPinRequest request);

	public StatusMessageResponse login(LoginRequest request);

	public StatusMessageResponse lostPhone(UserContextRequest request);

	public StatusMessageResponse foundPhone(UserContextRequest request);
}
