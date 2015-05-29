package com.ericsson.sprint.msdp.selfcare.controllers.abstracts;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.ericsson.cac.sprint.selfcare.workflow.AccountDeviceWorkflow;
import com.ericsson.cac.sprint.selfcare.workflow.AccountHomeWorkflow;
import com.ericsson.cac.sprint.selfcare.workflow.AccountLoginWorkflow;
import com.ericsson.cac.sprint.selfcare.workflow.AccountServiceWorkflow;
import com.ericsson.cac.sprint.selfcare.workflow.AccountSettingsWorkflow;
import com.ericsson.cac.sprint.selfcare.workflow.AccountWorkflow;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.SubscriberAccount;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.SubscriberAccountResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.UserContext;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.UserContextRequest;

/**
 * @author erosvin
 *
 */
public abstract class AbstractBaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(AbstractBaseController.class);

	/**
	 * Prefix Definitions and Targets
	 */
	public static final String URI_PREFIX_FEATURE_PHONE = "/feature_phone";
	public static final String URI_PREFIX_MOBILE = "/mobile";
	public static final String URI_PREFIX_DESKTOP = "/desktop";
	public static final String URI_PREFIX_COMMONS = "/commons";

	/**
	 * View Prefix Folder for lookup view.
	 */
	public static final String VIEW_PREFIX_FEATURE_PHONE = "mobile";
	public static final String VIEW_PREFIX_MOBILE = "mobile";
	public static final String VIEW_PREFIX_COMMONS = "commons";
	public static final String VIEW_PREFIX_DESKTOP = "desktop";

	/**
	 * Path for responding URI Services.
	 */
	public static final String PATH_SETTINGS_MESSAGES = "/messages-settings";
	public static final String PATH_SETTINGS_MESSAGES_BLOCK_MESSAGES = "/messages-settings-block-messages";
	public static final String PATH_SETTINGS_MESSAGES_UNBLOCK_MESSAGES = "/messages-settings-unblock-messages";
	public static final String PATH_SETTINGS_MESSAGES_BLOCK_ALL = "/messages-settings-block-all";
	public static final String PATH_SETTINGS_MDN_SWAP 			= "/my-account-mdn-swap";
	public static final String PATH_SETTINGS_MDN_SWAP_CONFIRM 	= "/my-account-mdn-swap-confirm";
	public static final String PATH_SETTINGS_ESN_SWAP 					= "/my-account-esn-swap";
	public static final String PATH_SETTINGS_ESN_SWAP_CONFIRM 			= "/my-account-esn-swap-confirm";

	
	/**
	 * Common Constants
	 */
	protected static final String SUBSCRIBER_ID_KEY = "X-DRUTT-PORTAL-USER-ID";
	protected static final String MSISDN_KEY 		= "mdn";
	protected static final String FIRST_NAME_KEY 	= "firstName";
	protected static final String LAST_NAME_KEY 	= "lastName";
	protected static final String MESSAGE_KEY = "message";

	/**
	 * Home Constants
	 */
	protected static final String BASE_PLAN_DESC_KEY 	= "basePlanDescription";
	protected static final String BASE_PLAN_PRICE_KEY 	= "basePlanPrice";
	protected static final String BASE_PLAN_LIST_KEY 	= "basePlanList";
	protected static final String ADDON_LIST_KEY 		= "addOnList";
	protected static final String NEXT_MONTH_CHARGE_KEY = "nextMonthCharges";
	protected static final String CASH_BALANCE_KEY 		= "balance";
	protected static final String DUE_BY_DATE_KEY 		= "dueByDate";
	protected static final String DUE_AMOUNT_KEY 		= "dueAmount";
	protected static final String CURRENCY_KEY 			= "currency";
	
	@Autowired
	protected AccountDeviceWorkflow accountDevice;
	@Autowired
	protected AccountHomeWorkflow accountHome;
	@Autowired
	protected AccountServiceWorkflow accountService;
	@Autowired
	protected AccountSettingsWorkflow accountSettings;
	@Autowired
	protected AccountWorkflow accountWorkflow;
	@Autowired
	protected AccountLoginWorkflow accountLoginWorkflow;


	/**
	 * Define the View Prefix for the Controller.
	 * 
	 * @return
	 */
	public abstract String getViewPrefix();

	/**
	 * Use this method to resolve the view.
	 * 
	 * @param path
	 *            the path to look for after the prefix.
	 * @return full path of the view to display.
	 */
	public String resolvePath(String path) {
		if (logger.isDebugEnabled()) {
			logger.debug("resolvePath(String) - start"); //$NON-NLS-1$
		}

		StringBuilder sb = new StringBuilder();
		sb.append(getViewPrefix());
		sb.append("/");
		sb.append(path);
		String returnString = sb.toString();
		if (logger.isDebugEnabled()) {
			logger.debug("resolvePath(String) - end - return value=" + returnString); //$NON-NLS-1$
		}
		return returnString;
	}

	/**
	 * @param subscriberId
	 * @return
	 */
	protected UserContextRequest createUserContextRequest(String subscriberId) {
		if (logger.isDebugEnabled()) {
			logger.debug("createUserContextRequest(String) - start"); //$NON-NLS-1$
		}

		UserContext userContext = new UserContext();
		userContext.setSubscriberId(subscriberId);
		UserContextRequest request = new UserContextRequest();
		request.setUserContext(userContext);

		if (logger.isDebugEnabled()) {
			logger.debug("createUserContextRequest(String) - end - return value=" + request); //$NON-NLS-1$
		}
		return request;
	}

	/**
	 * @param subscriberId
	 * @param session
	 * @return
	 */
	protected UserContextRequest createUserContextRequestWithMdn(String subscriberId, HttpSession session) {
		if (logger.isDebugEnabled()) {
			logger.debug("createUserContextRequestWithMdn(String, HttpSession) - start"); //$NON-NLS-1$
		}

		UserContextRequest request = createUserContextRequest(subscriberId);

		String msisdn = session.getAttribute(MSISDN_KEY) != null ? session.getAttribute(MSISDN_KEY).toString() : null;
		if (session.getAttribute(MSISDN_KEY) != null) {
			request.getUserContext().setMsisdn(session.getAttribute(MSISDN_KEY).toString());

			if (logger.isInfoEnabled()) {
				logger.info("createUserContextRequestWithMdn(String, HttpSession) - MDN found in session"); //$NON-NLS-1$
			}
		} else {
			if (logger.isInfoEnabled()) {
				logger.info("createUserContextRequestWithMdn(String, HttpSession) - looking for MSISDN in account - subscriberId=" + subscriberId); //$NON-NLS-1$
			}
			SubscriberAccountResponse account = accountHome.getAccount(request);

			if (account != null) {
				if (logger.isInfoEnabled()) {
					logger.info("createUserContextRequestWithMdn(String, HttpSession) - account was found"); //$NON-NLS-1$
				}
				SubscriberAccount subscriberAccount = account.getSubscriberAccount();
				if (subscriberAccount != null) {
					msisdn = account.getSubscriberAccount().getMsisdn();
					if (logger.isInfoEnabled()) {
						logger.info("createUserContextRequestWithMdn(String, HttpSession) - Using - subscriberId=" + subscriberId + ", msisdn=" + msisdn); //$NON-NLS-1$ //$NON-NLS-2$
					}
				} else {
					if (logger.isInfoEnabled()) {
						logger.info("createUserContextRequestWithMdn(String, HttpSession) - subscriber account is null"); //$NON-NLS-1$
					}
				}

				session.setAttribute(MSISDN_KEY, msisdn);
				request.getUserContext().setMsisdn(msisdn);
			} else {
				if (logger.isInfoEnabled()) {
					logger.info("createUserContextRequestWithMdn(String, HttpSession) - account not found!"); //$NON-NLS-1$
				}

				throw new RuntimeException("Couldn't retrieve MDN from Session");
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("createUserContextRequestWithMdn(String, HttpSession) - end - return value=" + request); //$NON-NLS-1$
		}
		return request;
	}

}