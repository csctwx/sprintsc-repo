package com.ericsson.sprint.msdp.selfcare.controllers.abstracts;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ericsson.cac.sprint.selfcare.workflow.datamodel.AccountBalanceResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.Service;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.SubscriberPlanResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.UserContextRequest;

public abstract class AbstractPaymentsController extends AbstractBaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(AbstractPaymentsController.class);

	/*
	 * SubscriberAccountResponse getAccount(UserContextRequest request)
	 * AccountBalanceResponse getAccountBalance(UserContextRequest request)
	 * SubscriberPlanResponse getPlan(UserContextRequest request)
	 */

	@RequestMapping(value = "/my-account-payments-plan-description", method = RequestMethod.GET)
	public String planDescription(
			Model model,
			@RequestHeader(value = "X-DRUTT-PORTAL-USER-ID") String subscriberId,
			HttpSession session) {

		UserContextRequest req = createUserContextRequestWithMdn(subscriberId,
				session);

		// Query for plan details
		SubscriberPlanResponse planResp = accountService.getPlan(req);

		if (logger.isInfoEnabled()) {
			logger.info("planDescription(Model, String, HttpSession) - SubscriberPlanResponse planResp=" + planResp); //$NON-NLS-1$
		}

		Service[] basePlanList = planResp.getSubscriberPlan().getBaseplan();
		Service basePlan = basePlanList[0];

		model.addAttribute(BASE_PLAN_DESC_KEY, basePlan.getDescription());
		model.addAttribute(BASE_PLAN_PRICE_KEY, basePlan.getRate());
		
		Service[] addonsList = planResp.getSubscriberPlan().getAddOns();

		if (logger.isInfoEnabled()) {
			logger.info("planDescription(Model, String, HttpSession) - Service[] addonsList=" + addonsList); //$NON-NLS-1$
		}

		model.addAttribute(ADDON_LIST_KEY, addonsList);

		// Query for account balance details
		AccountBalanceResponse balanceResp = accountHome.getAccountBalance(req);

		if (logger.isInfoEnabled()) {
			logger.info("planDescription(Model, String, HttpSession) - AccountBalanceResponse balanceResp=" + balanceResp); //$NON-NLS-1$
		}

		model.addAttribute(CASH_BALANCE_KEY, balanceResp.getAccountBalance()
				.getCashBalance());
		model.addAttribute(DUE_BY_DATE_KEY, balanceResp.getAccountBalance()
				.getDueBy());
		model.addAttribute(DUE_AMOUNT_KEY, balanceResp.getAccountBalance()
				.getDueAmount());

		// Query for usage details
		SubscriberPlanResponse planUsageResp = accountService.getPlanUsage(req);

		return resolvePath("myaccount/payments/payments-plan-description");
	}

	@RequestMapping(value = "/my-account-payments-addons", method = RequestMethod.GET)
	public String addons() {
		return resolvePath("myaccount/payments/payments-addons");
	}

	@RequestMapping(value = "/my-account-payments-transaction-history", method = RequestMethod.GET)
	public String transactionHistory() {
		return resolvePath("myaccount/payments/payments-transaction-history");
	}

}
