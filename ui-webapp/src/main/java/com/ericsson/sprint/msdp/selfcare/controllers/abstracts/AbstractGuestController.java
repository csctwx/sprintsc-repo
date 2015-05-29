package com.ericsson.sprint.msdp.selfcare.controllers.abstracts;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ericsson.cac.sprint.selfcare.workflow.datamodel.ForgotPinRequest;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.SecretQuestionsResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.StatusMessageResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.UserContextRequest;
import com.ericsson.sprint.msdp.selfcare.controllers.forms.ForgotPinForm;
import com.ericsson.sprint.msdp.selfcare.controllers.forms.ForgotPinForm.SecretQuestion;
import com.ericsson.sprint.msdp.selfcare.controllers.forms.SuspendAccountForm;
import com.ericsson.sprint.msdp.selfcare.controllers.forms.validators.ForgotPinFormValidator;

public abstract class AbstractGuestController extends AbstractBaseController {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(AbstractGuestController.class);
	
	/**
	 * 
	 */
	private static final String FORGOT_PIN_FORM = "forgotPinForm";
	
	/**
	 * 
	 */
	private static final String SUSPEND_ACCOUNT_FORM = "suspendAccountForm";
	
	/**
	 * @return
	 */
	@RequestMapping(value = "/guest-header", method = RequestMethod.GET)
	public String guestHeader() {
		return resolvePath("guest-header");
	}

	/**
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return resolvePath("login");
	}

	/**
	 * @param msisdn
	 * @param pin
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@RequestParam("msisdn") String msisdn, @RequestParam("pin") String pin, Model model, 
			HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		
		session.setAttribute("mdn", msisdn);
		model.addAttribute("msisdn", msisdn);
		model.addAttribute("pin", pin);
		return resolvePath("login");
	}

	/**
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/forgot-pin", method = RequestMethod.GET)
	public String forgotPin(Model model, @RequestHeader(SUBSCRIBER_ID_KEY) String subscriberId) {
		if (logger.isDebugEnabled()) {
			logger.debug("forgotPin(Model, String) - start"); 
		}
		
		ForgotPinForm form = new ForgotPinForm();
		if (!model.containsAttribute(FORGOT_PIN_FORM)) {
			model.addAttribute(FORGOT_PIN_FORM, form);
		}
		
		UserContextRequest request = createUserContextRequest(subscriberId);
		SecretQuestionsResponse secretQuestions = accountSettings.getSecretQuestions(request);
		
		List<SecretQuestion> secretQuestionsList = new ArrayList<ForgotPinForm.SecretQuestion>();
		
		String[] questions = secretQuestions.getSecretQuestions().getQuestions();
		String[] codes = secretQuestions.getSecretQuestions().getQuestionCodes();
		
		for (int i = 0; i < questions.length; i++) {
			String question = questions[i];
			String code = codes[i];
			
			SecretQuestion sq = form.new SecretQuestion();
			sq.setCode(code);
			sq.setLabel(question);
			secretQuestionsList.add(sq);
		}
		model.addAttribute("secretQuestionsList", secretQuestionsList);

		return resolvePath("forgot-pin");
	}

	/**
	 * @param model
	 * @param msisdn
	 * @param answer
	 * @param question
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/forgot-pin", method = RequestMethod.POST)
	public String forgotPin(Model model, 
			@ModelAttribute ForgotPinForm form,
			BindingResult result,
			@RequestHeader(SUBSCRIBER_ID_KEY) String subscriberId) {

		if (logger.isDebugEnabled()) {
			logger.debug("forgotPin(Model, ForgotPinForm, String) - start");
		}
		
		new ForgotPinFormValidator().validate(form, result);
		
		if (!result.hasErrors()){
			if (logger.isInfoEnabled()) {
				logger.info("forgotPin(Model, ForgotPinForm, BindingResult, String), no validation error."); //$NON-NLS-1$
			}
			ForgotPinRequest forgotPinRequest = new ForgotPinRequest();
			forgotPinRequest.setMsisdn(form.getMdn());
			forgotPinRequest.setSecretAnswer(form.getSecretAnswer());
			forgotPinRequest.setSecretQuestion(form.getSecretQuestion());
			StatusMessageResponse response = accountLoginWorkflow.sendPin(forgotPinRequest);
			model.addAttribute("message", response.getStatusMessage().getReason());

			if (logger.isInfoEnabled()) {
				logger.info("forgotPin(Model, ForgotPinForm, BindingResult, String) - StatusMessageResponse response=" + response.getStatusMessage().getReason()); //$NON-NLS-1$
			}

			model.addAttribute(FORGOT_PIN_FORM, new ForgotPinForm());
		} else {
			if (logger.isInfoEnabled()) {
				logger.info("forgotPin(Model, ForgotPinForm, BindingResult, String), validation error exists."); //$NON-NLS-1$
			}
		}
		
		String returnString = forgotPin(model, subscriberId);

		if (logger.isDebugEnabled()) {
			logger.debug("forgotPin(Model, SecuritySettingsForm, String) - end - return value=" + returnString); //$NON-NLS-1$
		}
		return returnString;
	}

	/**
	 * @return
	 */
	@RequestMapping(value = "/lost-phone", method = {RequestMethod.GET, RequestMethod.POST})
	public String lostPhone(
			Model model,
			@RequestHeader(value = SUBSCRIBER_ID_KEY, required = false) String subscriberId) {
		if (subscriberId != null && !"sprint".equals(subscriberId)){
			if (logger.isInfoEnabled()) {
				logger.info("lostPhone(Model, String) - subscriberId=" + subscriberId); //$NON-NLS-1$
			}
			
			if (!model.containsAttribute(SUSPEND_ACCOUNT_FORM)){
				model.addAttribute(SUSPEND_ACCOUNT_FORM, new SuspendAccountForm());
			}
			return resolvePath("myaccount/suspend/suspend");
		}
		return resolvePath("lost-phone");
	}

	
	@RequestMapping(value = "/suspend-account", method = RequestMethod.POST)
	public String suspendAccount(
			Model model, 
			@ModelAttribute SuspendAccountForm form,
			@RequestHeader(SUBSCRIBER_ID_KEY) String subscriberId, 
			HttpSession session) {
		
		if (logger.isDebugEnabled()) {
			logger.debug("suspenAccount(Model, SuspendAccountForm, String, HttpSession) - start");
		}

		UserContextRequest request = createUserContextRequestWithMdn(subscriberId, session);
		StatusMessageResponse response = accountLoginWorkflow.lostPhone(request);
		if(response.getStatusMessage().getStatus()!=null && response.getStatusMessage().getStatus() == 0){
			model.addAttribute("title", "Suspended");
			model.addAttribute("message", response.getStatusMessage().getReason());
			model.addAttribute("continueUrl", "portal:my-account-home");
			return resolvePath("generic-success");
		}
		return lostPhone(model, subscriberId);
	}
}
