package com.ericsson.sprint.msdp.selfcare.controllers.abstracts.settings;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ericsson.cac.sprint.selfcare.workflow.datamodel.Address;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.ChangeNumberResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.CoverageAreaRequest;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.CoverageAreaResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.UserContextRequest;
import com.ericsson.sprint.msdp.selfcare.controllers.abstracts.AbstractBaseController;
import com.ericsson.sprint.msdp.selfcare.controllers.forms.MdnSwapForm;

public abstract class AbstractMdnSwapController extends AbstractBaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(AbstractMdnSwapController.class);

	@RequestMapping(value = PATH_SETTINGS_MDN_SWAP, method = RequestMethod.GET)
	public String mdnSwap(Model model) {
		if (logger.isDebugEnabled()) {
			logger.debug("mdnSwap(Model) - start"); //$NON-NLS-1$
		}

		if (!model.containsAttribute("mdnSwapForm")) {
			model.addAttribute("mdnSwapForm", new MdnSwapForm());
		}
		String returnString = resolvePath("myaccount/settings/mdnswap/step1");
		if (logger.isDebugEnabled()) {
			logger.debug("mdnSwap(Model) - end - return value=" + returnString); //$NON-NLS-1$
		}
		return returnString;
	}

	@RequestMapping(value = PATH_SETTINGS_MDN_SWAP, method = RequestMethod.POST)
	public String mdnSwap(Model model, @ModelAttribute MdnSwapForm form) {
		if (logger.isDebugEnabled()) {
			logger.debug("mdnSwap(Model, MdnSwapForm) - start"); //$NON-NLS-1$
		}

		CoverageAreaRequest request = new CoverageAreaRequest();
		request.setZipCode(form.getZipCode());
		CoverageAreaResponse response = accountWorkflow.checkCoverageArea(request);
		form.setCoverageAreaResponse(response);
		model.addAttribute("mdnSwapForm", form);
		model.addAttribute("mdnConfirmUrl", "." + PATH_SETTINGS_MDN_SWAP_CONFIRM);

		if (logger.isInfoEnabled()) {
			logger.info("mdnSwap(Model, MdnSwapForm) - CoverageAreaResponse.getStatusmessage()=" + response.getStatusmessage()); //$NON-NLS-1$
		}
		
		String returnString = null;
		if(response.getStatusmessage() == 0){
			// Success for check coverage.
			returnString = resolvePath("myaccount/settings/mdnswap/step2");
		}else{
			// Failure for check coverage.
			model.addAttribute("hideForm", Boolean.TRUE);
			returnString = mdnSwap(model);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("mdnSwap(Model, MdnSwapForm) - end - return value=" + returnString); //$NON-NLS-1$
		}
		return returnString;
	}
	
	@RequestMapping(value = PATH_SETTINGS_MDN_SWAP_CONFIRM, method = RequestMethod.POST)
	public String mdnSwapConfirm(Model model, @ModelAttribute MdnSwapForm form,
			@RequestHeader(SUBSCRIBER_ID_KEY) String subscriberId, HttpSession session) {
		if (logger.isDebugEnabled()) {
			logger.debug("mdnSwapConfirm(Model, MdnSwapForm) - start"); //$NON-NLS-1$
		}

		UserContextRequest request = createUserContextRequestWithMdn(subscriberId, session);
		Address adr = new Address();
		adr.setZipCode(form.getZipCode());

		if (logger.isInfoEnabled()) {
			logger.info("mdnSwapConfirm(Model, MdnSwapForm, String, HttpSession) - Address adr=" + adr.getZipCode()); //$NON-NLS-1$
		}
		
		ChangeNumberResponse resp = accountDevice.changeNumber(adr, request);
		if (logger.isInfoEnabled()) {
			logger.info("mdnSwapConfirm(Model, MdnSwapForm, String, HttpSession) - ChangeNumberResponse resp=" + resp); //$NON-NLS-1$
		}
		
		resp.getStatusMessage();

		if (logger.isInfoEnabled()) {
			logger.info("mdnSwapConfirm(Model, MdnSwapForm, String, HttpSession) - ChangeNumberResponse resp=" + resp); //$NON-NLS-1$
		}
		
		if(resp.getMsisdn() != null){
			if (logger.isInfoEnabled()) {
				logger.info("mdnSwapConfirm(Model, MdnSwapForm, String, HttpSession) - String newMsisdn=" + resp.getMsisdn()); //$NON-NLS-1$
			}
			model.addAttribute("newMDN", resp.getMsisdn());
		} else {
			model.addAttribute(MESSAGE_KEY, resp.getStatusMessage());
		}
		
		String returnString = resolvePath("myaccount/settings/mdnswap/step3");
		return returnString;
	}
}