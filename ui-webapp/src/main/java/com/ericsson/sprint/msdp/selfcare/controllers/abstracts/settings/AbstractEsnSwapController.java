package com.ericsson.sprint.msdp.selfcare.controllers.abstracts.settings;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ericsson.cac.sprint.selfcare.workflow.datamodel.ESNSwapEligibleRequest;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.ESNSwapEligibleResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.ESNSwapPrepareRequest;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.StatusMessage;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.SwapEsnResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.UserContextRequest;
import com.ericsson.sprint.msdp.selfcare.controllers.abstracts.AbstractBaseController;
import com.ericsson.sprint.msdp.selfcare.controllers.forms.EsnSwapForm;
import com.ericsson.sprint.msdp.selfcare.controllers.forms.validators.EsnSwapFormValidator;

public abstract class AbstractEsnSwapController extends AbstractBaseController {

	private static final Logger logger = Logger.getLogger(AbstractEsnSwapController.class);

	@RequestMapping(value = PATH_SETTINGS_ESN_SWAP, method = RequestMethod.GET)
	public String esnSwap(Model model, @RequestHeader(SUBSCRIBER_ID_KEY) String subscriberId) {
		if (!model.containsAttribute("esnSwapForm")) {
			model.addAttribute("esnSwapForm", new EsnSwapForm());
		}
		return resolvePath("myaccount/settings/esnswap/step1");
	}
	
	@RequestMapping(value = PATH_SETTINGS_ESN_SWAP, method = RequestMethod.POST)
	public String esnSwapReview(Model model, @ModelAttribute EsnSwapForm form,
			@RequestHeader(SUBSCRIBER_ID_KEY) String subscriberId, HttpSession session, BindingResult result) {
		if (logger.isInfoEnabled()) {
			logger.info("esnSwap(Model, EsnSwapForm, String, HttpSession)"); //$NON-NLS-1$
		}

		new EsnSwapFormValidator().validate(form, result);

		if (!result.hasErrors()){
			UserContextRequest request = createUserContextRequestWithMdn(subscriberId, session);
			ESNSwapEligibleRequest paramESNSwapEligibleRequest = new ESNSwapEligibleRequest();
			
			paramESNSwapEligibleRequest.setSerialNumber(form.getSerial());
			ESNSwapEligibleResponse resp = accountDevice.checkEsnSwap(paramESNSwapEligibleRequest, request);
			if (logger.isInfoEnabled()) {
				logger.info("esnSwap(Model, EsnSwapForm, String, HttpSession) - ESNSwapEligibleResponse resp=" + resp); //$NON-NLS-1$
			}
			
			resp.getStatusMessage();
			if (logger.isInfoEnabled()) {
				logger.info("esnSwap(Model, EsnSwapForm, String, HttpSession) - ESNSwapEligibleResponse resp=" + resp); //$NON-NLS-1$
			}
		} else {
			if (logger.isInfoEnabled()) {
				logger.info("esnSwapReview(Model, EsnSwapForm, String, HttpSession, BindingResult) - model=" + model); //$NON-NLS-1$
			}
			return esnSwap(model, subscriberId);
		}
		
		return resolvePath("myaccount/settings/esnswap/step2");
	}
	
	@RequestMapping(value = PATH_SETTINGS_ESN_SWAP_CONFIRM, method = RequestMethod.POST)
	public String esnSwapConfirm(Model model, @ModelAttribute EsnSwapForm form,
			@RequestHeader(SUBSCRIBER_ID_KEY) String subscriberId, HttpSession session) {
		if (logger.isInfoEnabled()) {
			logger.info("esnSwapConfirm(Model, EsnSwapForm, String, HttpSession)"); //$NON-NLS-1$
		}
		String serial = form.getSerial();
		if (logger.isInfoEnabled()) {
			logger.info("esnSwapConfirm(Model, EsnSwapForm, String, HttpSession) - String serial=" + serial); //$NON-NLS-1$
		}

		ESNSwapPrepareRequest req = new ESNSwapPrepareRequest();
		req.setSerialNumber(serial);
		SwapEsnResponse resp = accountDevice.swapESN(req);
		StatusMessage respStatus = resp.getStatus();

		if (logger.isInfoEnabled()) {
			logger.info("esnSwapConfirm(Model, EsnSwapForm, String, HttpSession) - StatusMessage respStatus=" + respStatus.getDescription()); //$NON-NLS-1$
		}
		String returnString = resolvePath("myaccount/settings/esnswap/step3");
		return returnString;
	}
	
}
