package com.ericsson.sprint.msdp.selfcare.controllers.abstracts.usage;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ericsson.sprint.msdp.selfcare.controllers.abstracts.AbstractBaseController;

public abstract class AbstractUsageController extends AbstractBaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(AbstractUsageController.class);

	@RequestMapping(value = "/usage-summary", method = { RequestMethod.GET, RequestMethod.POST })
	public String summary(Model model, @RequestHeader(SUBSCRIBER_ID_KEY) String subscriberId, HttpSession session) {
		if (logger.isDebugEnabled()) {
			logger.debug("summary(Model, String, HttpSession) - start"); //$NON-NLS-1$
		}

		String returnString = resolvePath("myaccount/usage/summary");

		if (logger.isDebugEnabled()) {
			logger.debug("summary(Model, String, HttpSession) - end - return value=" + returnString); //$NON-NLS-1$
		}
		return returnString;
	}

}
