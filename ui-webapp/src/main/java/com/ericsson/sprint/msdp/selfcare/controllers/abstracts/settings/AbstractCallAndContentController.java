package com.ericsson.sprint.msdp.selfcare.controllers.abstracts.settings;

import java.util.Arrays;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ericsson.cac.sprint.selfcare.workflow.datamodel.StatusMessageResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.SubscriberSetting;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.SubscriberSettingsRequest;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.SubscriberSettingsResponse;
import com.ericsson.sprint.msdp.selfcare.controllers.abstracts.AbstractBaseController;
import com.ericsson.sprint.msdp.selfcare.controllers.forms.CallAndContentForm;

public abstract class AbstractCallAndContentController extends AbstractBaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(AbstractCallAndContentController.class);

	@RequestMapping(value = "/my-account-call-and-content", method = RequestMethod.GET)
	public String callAndContent(Model model, @RequestHeader(SUBSCRIBER_ID_KEY) String subscriberId, HttpSession session) {
		if (logger.isDebugEnabled()) {
			logger.debug("callAndContent(Model, String, HttpSession) - start"); //$NON-NLS-1$
		}

		if (!model.containsAttribute("callAndContentForm")){
			SubscriberSettingsResponse response = accountSettings.getUserSettings(createUserContextRequestWithMdn(subscriberId, session));
			SubscriberSetting[] subscriberSettings = response.getSubscriberSettings();

			if (logger.isInfoEnabled()) {
				logger.info("callAndContent(Model, String, HttpSession) - subscriberSettings=" + subscriberSettings); //$NON-NLS-1$
			}
			
			if(subscriberSettings != null){
				for (SubscriberSetting subscriberSetting : subscriberSettings) {
					if (logger.isInfoEnabled()) {
						logger.info("callAndContent(Model, String, HttpSession) - subscriberSetting=" + subscriberSetting); //$NON-NLS-1$
					}
				}
				
				CallAndContentForm form = new CallAndContentForm();
				form.setSubscriberSettings(Arrays.asList(subscriberSettings));
				model.addAttribute("callAndContentForm", form);
			}
		}
		String returnString = resolvePath("myaccount/settings/call-and-content/form");
		if (logger.isDebugEnabled()) {
			logger.debug("callAndContent(Model, String, HttpSession) - end - return value=" + returnString); //$NON-NLS-1$
		}
		return returnString;
	}

	@RequestMapping(value = "/my-account-call-and-content", method = RequestMethod.POST)
	public String callAndContent(Model model, @ModelAttribute CallAndContentForm form, @RequestHeader(SUBSCRIBER_ID_KEY) String subscriberId,
			HttpSession session) {
		if (logger.isDebugEnabled()) {
			logger.debug("callAndContent(Model, CallAndContentForm, String, HttpSession) - start"); //$NON-NLS-1$
		}
		
		SubscriberSettingsRequest arg1 = new SubscriberSettingsRequest();
		form.setSubscriberSettings(form.getSubscriberSettings());
		StatusMessageResponse response = accountSettings.updateUserSettings(createUserContextRequestWithMdn(subscriberId, session), arg1);
		model.addAttribute(MESSAGE_KEY, response.getStatusMessage().getReason());

		String returnString = callAndContent(model, subscriberId, session);
		if (logger.isDebugEnabled()) {
			logger.debug("callAndContent(Model, CallAndContentForm, String, HttpSession) - end - return value=" + returnString); //$NON-NLS-1$
		}
		return returnString;
	}
}
