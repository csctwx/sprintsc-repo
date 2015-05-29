package com.ericsson.sprint.msdp.selfcare.controllers.abstracts;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ericsson.cac.sprint.selfcare.workflow.datamodel.AccountBalance;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.AccountBalanceResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.Service;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.StatusMessageResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.SubscriberAccount;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.SubscriberAccountResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.SubscriberPlanResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.UserContextRequest;

public abstract class AbstractMyAccountController
		extends
			AbstractBaseController {

	private static final String ACCOUNT_SUSPENDED_FOUND_PHONE = "/account-suspended-found-phone";
	private static final String ACCOUNT_SUSPENDED_SWAP_PHONE = "/account-suspended-swap-phone";
	private static final String ACCOUNT_SUSPENDED_SHOP_PHONE = "/account-suspended-shop-phone";

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(AbstractMyAccountController.class);

	@RequestMapping(value = "/my-account-header", method = RequestMethod.GET)
	public String header(Model model,
			@RequestHeader(SUBSCRIBER_ID_KEY) String subscriberId,
			HttpSession session) {
		if (logger.isDebugEnabled()) {
			logger.debug("header(Model, String, HttpSession) - start"); //$NON-NLS-1$
		}

		UserContextRequest request = createUserContextRequest(subscriberId);
		SubscriberAccountResponse resp = accountHome.getAccount(request);
		SubscriberAccount account = resp.getSubscriberAccount();

		if (account != null) {
			model.addAttribute(MSISDN_KEY, account.getMsisdn());
			model.addAttribute(FIRST_NAME_KEY, account.getFirstName());
			model.addAttribute(LAST_NAME_KEY, account.getLastName());
		}

		String returnString = resolvePath("myaccount/header");
		if (logger.isDebugEnabled()) {
			logger.debug("header(Model, String, HttpSession) - end"); //$NON-NLS-1$
		}
		return returnString;
	}

	@RequestMapping(value = "/account-usage", method = RequestMethod.GET)
	public String usage(Model model,
			@RequestHeader(SUBSCRIBER_ID_KEY) String subscriberId,
			HttpSession session) {

		if (logger.isDebugEnabled()) {
			logger.debug("usage() - start"); //$NON-NLS-1$
		}

		UserContextRequest req = createUserContextRequestWithMdn(subscriberId,
				session);
		SubscriberPlanResponse resp = accountService.getPlanUsage(req);

		if (logger.isInfoEnabled()) {
			logger.info("usage(Model, String, HttpSession) - SubscriberPlanResponse resp=" + resp); //$NON-NLS-1$
		}

		Service[] basePlanResp = resp.getSubscriberPlan().getBaseplan();
		if (logger.isInfoEnabled()) {
			logger.info("usage(Model, String, HttpSession) - Service[] getBaseplan=" + basePlanResp); //$NON-NLS-1$
		}
		model.addAttribute(BASE_PLAN_LIST_KEY, resp.getSubscriberPlan().getBaseplan());

		for (Service s : basePlanResp) {
			if (logger.isInfoEnabled()) {
				logger.info("usage(Model, String, HttpSession) - baseplan s usage=" + s.getUsage()); //$NON-NLS-1$
			}

			if (logger.isInfoEnabled()) {
				logger.info("usage(Model, String, HttpSession) - baseplan s capacity=" + s.getCapacity()); //$NON-NLS-1$
			}
			
			if (logger.isInfoEnabled()) {
				logger.info("usage(Model, String, HttpSession) - baseplan s type=" + s.getType()); //$NON-NLS-1$
			}
			
			if (logger.isInfoEnabled()) {
				logger.info("usage(Model, String, HttpSession) - baseplan s desc=" + s.getDescription()); //$NON-NLS-1$
			}
			
			if (logger.isInfoEnabled()) {
				logger.info("usage(Model, String, HttpSession) - baseplan s type=" + s.getName()); //$NON-NLS-1$
			}

			if (logger.isInfoEnabled()) {
				logger.info("usage(Model, String, HttpSession) - baseplan s unit=" + s.getUnit()); //$NON-NLS-1$
			}
		}

		
		
		Service[] addOns = resp.getSubscriberPlan().getAddOns();
		if (logger.isInfoEnabled()) {
			logger.info("usage(Model, String, HttpSession) - Service[] addOns=" + addOns); //$NON-NLS-1$
		}
		
		String returnString = resolvePath("myaccount/usage");
		if (logger.isDebugEnabled()) {
			logger.debug("usage() - end"); //$NON-NLS-1$
		}

		return returnString;
	}

	@RequestMapping(value = "/account-balance", method = RequestMethod.GET)
	public String balance(Model model,
			@RequestHeader(SUBSCRIBER_ID_KEY) String subscriberId,
			HttpSession session) {
		if (logger.isDebugEnabled()) {
			logger.debug("balance(Model, String, HttpSession) - start"); //$NON-NLS-1$
		}

		UserContextRequest request = createUserContextRequest(subscriberId);
		AccountBalanceResponse resp = accountHome.getAccountBalance(request);
		AccountBalance accountBalance = resp.getAccountBalance();

		if (logger.isInfoEnabled()) {
			logger.info("balance(Model, String, HttpSession) - accountBalance=" + accountBalance); //$NON-NLS-1$
		}

		if (accountBalance != null) {

			logger.info("balance(Model, String, HttpSession) - inside accountBalance=" + accountBalance); //$NON-NLS-1$

			model.addAttribute(NEXT_MONTH_CHARGE_KEY, resp.getAccountBalance()
					.getNextMonthCharges());
			if (logger.isInfoEnabled()) {
				logger.info("balance(Model, String, HttpSession) - accountext month charge=" + resp.getAccountBalance().getNextMonthCharges()); //$NON-NLS-1$
			}

			model.addAttribute(CASH_BALANCE_KEY, resp.getAccountBalance()
					.getCashBalance());
			if (logger.isInfoEnabled()) {
				logger.info("balance(Model, String, HttpSession) - balance=" + resp.getAccountBalance().getCashBalance()); //$NON-NLS-1$
			}

			model.addAttribute(DUE_BY_DATE_KEY, resp.getAccountBalance().getDueBy());
			if (logger.isInfoEnabled()) {
				logger.info("balance(Model, String, HttpSession) - due by=" + resp.getAccountBalance().getDueBy()); //$NON-NLS-1$
			}

			model.addAttribute(DUE_AMOUNT_KEY, resp.getAccountBalance()
					.getDueAmount());
			if (logger.isInfoEnabled()) {
				logger.info("balance(Model, String, HttpSession) - due amount=" + resp.getAccountBalance().getDueAmount()); //$NON-NLS-1$
			}

			model.addAttribute(MESSAGE_KEY, resp.getAccountBalance()
					.getAccountStatus());
			if (logger.isInfoEnabled()) {
				logger.info("balance(Model, String, HttpSession) - status=" + resp.getAccountBalance().getAccountStatus()); //$NON-NLS-1$
			}

			model.addAttribute(CURRENCY_KEY, resp.getAccountBalance()
					.getCurrency());
			if (logger.isInfoEnabled()) {
				logger.info("balance(Model, String, HttpSession) - curreny=" + resp.getAccountBalance().getCurrency()); //$NON-NLS-1$
			}
		}
		String returnString = resolvePath("myaccount/balance");
		if (logger.isDebugEnabled()) {
			logger.debug("balance(Model, String, HttpSession) - end"); //$NON-NLS-1$
		}
		return returnString;
	}

	@RequestMapping(value = "/account-plan", method = RequestMethod.GET)
	public String plan(
			Model model,
			@RequestHeader(value = "X-DRUTT-PORTAL-USER-ID") String subscriberId,
			HttpSession session) {
		if (logger.isDebugEnabled()) {
			logger.debug("plan(Model, String) - start"); //$NON-NLS-1$
		}

		UserContextRequest req = createUserContextRequestWithMdn(subscriberId,
				session);
		SubscriberPlanResponse resp = accountService.getPlan(req);

		if (logger.isInfoEnabled()) {
			logger.info("plan(Model, String, HttpSession) - SubscriberPlanResponse resp=" + resp); //$NON-NLS-1$
		}

		Service[] basePlanList;
		Service basePlan;
		if (resp.getSubscriberPlan().getBaseplan() != null) {
			basePlanList = resp.getSubscriberPlan().getBaseplan();
			basePlan = basePlanList[0];

			if (logger.isInfoEnabled()) {
				logger.info("plan(Model, String, HttpSession) - Service basePlan=" + basePlan); //$NON-NLS-1$
			}

			model.addAttribute(BASE_PLAN_DESC_KEY, basePlan.getDescription());
		}

		Service[] addOnsList;
		if (resp.getSubscriberPlan().getAddOns() != null) {
			addOnsList = resp.getSubscriberPlan().getAddOns();
			if (logger.isInfoEnabled()) {
				logger.info("plan(Model, String, HttpSession) - Service[] addOnsList=" + addOnsList); //$NON-NLS-1$
			}

			model.addAttribute(ADDON_LIST_KEY, addOnsList);
			model.addAttribute("addOnName", addOnsList[1].getName());
		}

		if (logger.isDebugEnabled()) {
			logger.debug("plan(Model, String) - end"); //$NON-NLS-1$
		}

		return resolvePath("myaccount/plan");
	}

	/**
	 * @return
	 */
	@RequestMapping(value = "/account-suspended", method = RequestMethod.GET)
	public String suspended(Model model) {
		model.addAttribute("foundPhoneUrl", "." + ACCOUNT_SUSPENDED_FOUND_PHONE);
		model.addAttribute("swapPhoneUrl", "." + ACCOUNT_SUSPENDED_FOUND_PHONE);
		model.addAttribute("shopPhoneUrl", "." + ACCOUNT_SUSPENDED_FOUND_PHONE);
		return resolvePath("myaccount/suspended");
	}

	@RequestMapping(value = "/account-curing", method = RequestMethod.GET)
	public String curing() {
		if (logger.isDebugEnabled()) {
			logger.debug("curing() - start"); //$NON-NLS-1$
		}

		String returnString = resolvePath("myaccount/curing");
		if (logger.isDebugEnabled()) {
			logger.debug("curing() - end"); //$NON-NLS-1$
		}
		return returnString;
	}

	/**
	 * @return
	 */
	@RequestMapping(value = ACCOUNT_SUSPENDED_FOUND_PHONE, method = RequestMethod.GET)
	public String foundPhone(Model model,
			@RequestHeader(SUBSCRIBER_ID_KEY) String subscriberId,
			HttpSession session) {
		UserContextRequest request = createUserContextRequestWithMdn(
				subscriberId, session);
		StatusMessageResponse res = accountLoginWorkflow.foundPhone(request);
		model.addAttribute("title", "Unsuspend");
		model.addAttribute("message", res.getStatusMessage().getReason());
		model.addAttribute("continueUrl", "portal:my-account-home");
		return resolvePath("generic-success");
	}

	/**
	 * @return
	 */
	@RequestMapping(value = ACCOUNT_SUSPENDED_SWAP_PHONE, method = RequestMethod.GET)
	public String swapPhone(Model model,
			@RequestHeader(SUBSCRIBER_ID_KEY) String subscriberId,
			HttpSession session) {
		model.addAttribute("title", "Unsuspend");
		model.addAttribute("message", "Swap Phone");
		model.addAttribute("continueUrl", "portal:my-account-home");
		return resolvePath("generic-success");
	}

	/**
	 * @return
	 */
	@RequestMapping(value = ACCOUNT_SUSPENDED_SHOP_PHONE, method = RequestMethod.GET)
	public String shopPhone(Model model,
			@RequestHeader(SUBSCRIBER_ID_KEY) String subscriberId,
			HttpSession session) {
		model.addAttribute("title", "Unsuspend");
		model.addAttribute("message", "Shop Phone");
		model.addAttribute("continueUrl", "portal:my-account-home");
		return resolvePath("generic-success");
	}

}
