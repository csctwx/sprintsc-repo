package com.ericsson.sprint.msdp.selfcare.controllers.abstracts.settings;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ericsson.cac.sprint.selfcare.workflow.datamodel.SecretQuestionsResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.StatusMessageResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.UpdatePinRequest;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.UserContextRequest;
import com.ericsson.sprint.msdp.selfcare.controllers.abstracts.AbstractBaseController;
import com.ericsson.sprint.msdp.selfcare.controllers.forms.SecuritySettingsForm;
import com.ericsson.sprint.msdp.selfcare.controllers.forms.SecuritySettingsForm.SecretQuestion;
import com.ericsson.sprint.msdp.selfcare.controllers.forms.validators.SecuritySettingsFormValidator;

public abstract class AbstractSecurityController extends AbstractBaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(AbstractSecurityController.class);
	
	/**
	 * 
	 */
	private static final String SECURITY_SETTINGS_FORM = "securitySettingsForm";

	@RequestMapping(value = "/security-settings", method = RequestMethod.GET)
	public String securitySettings(Model model, @RequestHeader(SUBSCRIBER_ID_KEY) String subscriberId) {
		if (logger.isDebugEnabled()) {
			logger.debug("securitySettings(Model, String) - start"); //$NON-NLS-1$
		}
		SecuritySettingsForm form = new SecuritySettingsForm();
		if (!model.containsAttribute(SECURITY_SETTINGS_FORM)) {
			model.addAttribute(SECURITY_SETTINGS_FORM, form);
		}

		UserContextRequest request = createUserContextRequest(subscriberId);
		SecretQuestionsResponse secretQuestions = accountSettings.getSecretQuestions(request);
		
		List<SecretQuestion> secretQuestionsList = new ArrayList<SecuritySettingsForm.SecretQuestion>();
		
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

		String returnString = resolvePath("myaccount/settings/security/form");

		if (logger.isDebugEnabled()) {
			logger.debug("securitySettings(Model, String) - end - return value=" + returnString); //$NON-NLS-1$
		}
		return returnString;
	}

	@RequestMapping(value = "/security-settings", method = RequestMethod.POST)
	public String updateSecuritySettings(
			Model model, 
			@ModelAttribute SecuritySettingsForm form,
			BindingResult result,
			@RequestHeader(SUBSCRIBER_ID_KEY) String subscriberId) {
		if (logger.isDebugEnabled()) {
			logger.debug("updateSecuritySettings(Model, SecuritySettingsForm, String) - start"); //$NON-NLS-1$
		}
		
		new SecuritySettingsFormValidator().validate(form, result);
		
		if (!result.hasErrors()){
			UpdatePinRequest arg1 = new UpdatePinRequest();
			arg1.setPin(form.getPin());
			arg1.setSecretAnswer(form.getSecretAnswer());
			arg1.setSecretQuestion(form.getSecretQuestion());
			StatusMessageResponse response = accountSettings.updatePin(createUserContextRequest(subscriberId), arg1);
			model.addAttribute("message", response.getStatusMessage().getReason());
			model.addAttribute(SECURITY_SETTINGS_FORM, new SecuritySettingsForm());
		}
		
		String returnString = securitySettings(model, subscriberId);

		if (logger.isDebugEnabled()) {
			logger.debug("updateSecuritySettings(Model, SecuritySettingsForm, String) - end - return value=" + returnString); //$NON-NLS-1$
		}
		return returnString;
	}
}
