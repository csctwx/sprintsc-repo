package com.ericsson.cac.sprint.selfcare.workflow.lostpin;

import com.ericsson.cac.sprint.selfcare.workflow.lostpin.model.AccountSecurityRequest;
import com.ericsson.cac.sprint.selfcare.workflow.lostpin.model.AccountSecurityResponse;
import com.ericsson.cac.sprint.selfcare.workflow.lostpin.model.BrandCodeRequest;
import com.ericsson.cac.sprint.selfcare.workflow.lostpin.model.BrandCodeResponse;
import com.ericsson.cac.sprint.selfcare.workflow.lostpin.model.LoginAttemptRequest;
import com.ericsson.cac.sprint.selfcare.workflow.lostpin.model.LoginAttemptResponse;
import com.ericsson.cac.sprint.selfcare.workflow.lostpin.model.ResendSecurityRequest;
import com.ericsson.cac.sprint.selfcare.workflow.lostpin.model.ResendSecurityResponse;
import com.ericsson.cac.sprint.selfcare.workflow.lostpin.model.SecurityRequest;
import com.ericsson.cac.sprint.selfcare.workflow.lostpin.model.SecurityResponse;

public interface LostPinService {

	public SecurityResponse fetchSecurityQuestionsForLostPin(
			SecurityRequest securityRequest);

	public ResendSecurityResponse resendSecurityInfoToUser(
			ResendSecurityRequest resendSecurityRequest);

	public AccountSecurityResponse validateSecurityAnswers(
			AccountSecurityRequest accountSecurityRequest,
			LoginAttemptResponse loginAttemptResponse);

	public BrandCodeResponse validateBrandCode(BrandCodeRequest brandCodeRequest);

	public LoginAttemptResponse loginAttemptStatus(
			LoginAttemptRequest loginAttemptRequest);

}
