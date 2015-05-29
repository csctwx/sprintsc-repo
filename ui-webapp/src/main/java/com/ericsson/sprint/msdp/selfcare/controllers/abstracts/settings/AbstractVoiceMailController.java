package com.ericsson.sprint.msdp.selfcare.controllers.abstracts.settings;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ericsson.cac.sprint.selfcare.workflow.datamodel.StatusMessageResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.UserContextRequest;
import com.ericsson.sprint.msdp.selfcare.controllers.abstracts.AbstractBaseController;
import com.ericsson.sprint.msdp.selfcare.controllers.forms.ResetVMPasswordForm;

public abstract class AbstractVoiceMailController extends AbstractBaseController {

	@RequestMapping(value = "/reset-voice-mail-password", method = RequestMethod.GET)
	public String resetPassword(Model model, @RequestHeader(SUBSCRIBER_ID_KEY) String subscriberId, HttpSession session) {
		
		if (!model.containsAttribute("resetVMPasswordForm")){
			model.addAttribute("resetVMPasswordForm", new ResetVMPasswordForm());
		}
		
		UserContextRequest request = createUserContextRequestWithMdn(subscriberId, session);
		String msisdn = request.getUserContext().getMsisdn();
		model.addAttribute("last7Digits", msisdn.substring(msisdn.length() - 7, msisdn.length()));

		return resolvePath("myaccount/settings/voicemail/reset-password");
	}

	@RequestMapping(value = "/reset-voice-mail-password", method = RequestMethod.POST)
	public String resetPassword(
			Model model, 
			@ModelAttribute ResetVMPasswordForm form,
			@RequestHeader(SUBSCRIBER_ID_KEY) String subscriberId, 
			HttpSession session) {

		StatusMessageResponse response = accountSettings.resetVM(createUserContextRequestWithMdn(subscriberId, session));
		model.addAttribute(MESSAGE_KEY, response.getStatusMessage().getReason());
		
		return resetPassword(model, subscriberId, session);
	}

}
