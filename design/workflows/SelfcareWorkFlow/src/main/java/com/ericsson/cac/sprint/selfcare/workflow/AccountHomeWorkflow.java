package com.ericsson.cac.sprint.selfcare.workflow;

import com.ericsson.cac.sprint.selfcare.workflow.datamodel.AccountBalanceResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.StatusMessageResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.SubscriberAccountResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.UserContextRequest;

public interface AccountHomeWorkflow {
	public StatusMessageResponse getStatusMessage(UserContextRequest request);

	public SubscriberAccountResponse getAccount(UserContextRequest request);

	public AccountBalanceResponse getAccountBalance(UserContextRequest request);
}
