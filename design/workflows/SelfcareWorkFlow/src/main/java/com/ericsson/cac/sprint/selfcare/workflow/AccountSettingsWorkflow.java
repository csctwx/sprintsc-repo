package com.ericsson.cac.sprint.selfcare.workflow;

import com.ericsson.cac.sprint.selfcare.workflow.datamodel.BlockedMessagesRequest;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.BlockedMessagesResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.SecretQuestionsResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.StatusMessageResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.SubscriberAccountRequest;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.SubscriberSettingsRequest;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.SubscriberSettingsResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.TsCsResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.UpdatePinRequest;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.UserContextRequest;

public interface AccountSettingsWorkflow {

	public SecretQuestionsResponse getSecretQuestions(UserContextRequest request);

	public StatusMessageResponse updateAccount(SubscriberAccountRequest request,UserContextRequest context);

	public TsCsResponse getTsCs(UserContextRequest request);

	public BlockedMessagesResponse getBlockedMessages(UserContextRequest request);

	public StatusMessageResponse blockMessages(UserContextRequest userContextRequest, BlockedMessagesRequest blockMessagesRequest);

	public StatusMessageResponse unblockMessages(UserContextRequest userContextRequest,BlockedMessagesRequest blockedMessagesRequest);

	public StatusMessageResponse blockAllMessages(UserContextRequest request);

	public StatusMessageResponse resetVM(UserContextRequest request);

	public StatusMessageResponse updatePin(UserContextRequest request, UpdatePinRequest updateRequest);

	public SubscriberSettingsResponse getUserSettings(UserContextRequest request);

	public StatusMessageResponse updateUserSettings(UserContextRequest userContextRequest, SubscriberSettingsRequest request);

}
