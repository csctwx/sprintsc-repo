package com.ericsson.sprint.msdp.selfcare.controllers.commons;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ericsson.sprint.msdp.selfcare.controllers.abstracts.AbstractBaseController;

@Controller
@RequestMapping(AbstractBaseController.URI_PREFIX_COMMONS)
public class CommonsController extends AbstractBaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CommonsController.class);

	@RequestMapping(value = "/redirect", method = RequestMethod.GET)
	public String redirect(@RequestHeader(value = "GoTo-Location", required = false) String location, Model model, HttpServletResponse response) {
		if (logger.isDebugEnabled()) {
			logger.debug("redirect(String, HttpServletResponse) - start"); //$NON-NLS-1$
		}
		if (logger.isInfoEnabled()) {
			logger.info("redirect(String, HttpServletResponse) - redirecting...  - location=" + location); //$NON-NLS-1$
		}
		model.addAttribute("location", location);
		if (logger.isDebugEnabled()) {
			logger.debug("redirect(String, HttpServletResponse) - end"); //$NON-NLS-1$
		}
		return resolvePath("redirect");
	}

	@Override
	public String getViewPrefix() {
		return VIEW_PREFIX_COMMONS;
	}
}
