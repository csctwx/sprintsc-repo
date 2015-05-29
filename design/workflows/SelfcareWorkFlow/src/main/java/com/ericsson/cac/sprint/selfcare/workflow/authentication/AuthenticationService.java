package com.ericsson.cac.sprint.selfcare.workflow.authentication;

import com.ericsson.cac.sprint.selfcare.workflow.authentication.model.LoginRequest;
import com.ericsson.cac.sprint.selfcare.workflow.authentication.model.LoginResponse;

public interface AuthenticationService {
	LoginResponse login(LoginRequest request);
}
