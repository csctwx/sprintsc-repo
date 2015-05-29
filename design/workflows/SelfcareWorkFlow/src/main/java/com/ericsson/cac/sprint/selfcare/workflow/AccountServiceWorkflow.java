package com.ericsson.cac.sprint.selfcare.workflow;

import com.ericsson.cac.sprint.selfcare.workflow.datamodel.AddOnsRequest;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.AddOnsResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.CartPaymentResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.CartRequest;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.EligibleServicesResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.PlanUsageResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.SearchCriteriaRequest;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.StatusMessageResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.SubscriberPlanResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.TalkHistoryResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.TextHistoryResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.TransactionHistoryResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.UserContextRequest;

public interface AccountServiceWorkflow {

	public SubscriberPlanResponse getPlan(UserContextRequest request);

	public PlanUsageResponse getPlanUsage(UserContextRequest request);

	public EligibleServicesResponse getEligibleServices(
			UserContextRequest request);

	public TalkHistoryResponse getTalkHistory(UserContextRequest request, SearchCriteriaRequest criteria);

	public TextHistoryResponse getTextHistory(UserContextRequest request ,SearchCriteriaRequest criteria);
	
	public StatusMessageResponse updatePlan(CartRequest request);
	
	public AddOnsResponse getEligibleAddOns(AddOnsRequest request);
	
	public CartPaymentResponse restartPlan(UserContextRequest request);
	
	public TransactionHistoryResponse getTransactionHistory(UserContextRequest request, SearchCriteriaRequest criteria);
}
