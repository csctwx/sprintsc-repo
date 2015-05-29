package com.ericsson.sprint.msdp.selfcare.controllers.abstracts.settings;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ericsson.cac.sprint.selfcare.workflow.datamodel.BlockedMessagesRequest;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.BlockedMessagesResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.Recipient;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.StatusMessageResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.UserContextRequest;
import com.ericsson.sprint.msdp.selfcare.controllers.abstracts.AbstractBaseController;
import com.ericsson.sprint.msdp.selfcare.controllers.forms.BlockAllMessagesForm;
import com.ericsson.sprint.msdp.selfcare.controllers.forms.BlockMessagesForm;
import com.ericsson.sprint.msdp.selfcare.controllers.forms.UnblockMessagesForm;

public abstract class AbstractMessageSettingsController extends AbstractBaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(AbstractMessageSettingsController.class);

	private static final String BLOCK_ALL_MESSAGES_FORM = "blockAllMessagesForm";
	private static final String UNBLOCK_MESSAGES_FORM = "unblockMessagesForm";
	private static final String BLOCK_MESSAGES_FORM = "blockMessagesForm";

	/**
	 * @param model
	 * @param subscriberId
	 * @param session
	 * @return
	 */
	@RequestMapping(value = PATH_SETTINGS_MESSAGES, method = RequestMethod.GET)
	public String messageSettings(
				Model model,
				@RequestHeader(SUBSCRIBER_ID_KEY) String subscriberId,
				HttpSession session
			) {
		if (logger.isDebugEnabled()) {
			logger.debug("messageSettings(Model, String, HttpSession) - start"); //$NON-NLS-1$
		} 
		
		if (!model.containsAttribute(BLOCK_MESSAGES_FORM)){
			model.addAttribute(BLOCK_MESSAGES_FORM, new BlockMessagesForm());
		}
		if (!model.containsAttribute(UNBLOCK_MESSAGES_FORM)){
			model.addAttribute(UNBLOCK_MESSAGES_FORM, new UnblockMessagesForm());			
		}
		if (!model.containsAttribute(BLOCK_ALL_MESSAGES_FORM)){
			model.addAttribute(BLOCK_ALL_MESSAGES_FORM, new BlockAllMessagesForm());						
		}
		
		model.addAttribute("blockMessagesUrl", "."+ PATH_SETTINGS_MESSAGES_BLOCK_MESSAGES);
		model.addAttribute("unblockMessagesUrl", "."+ PATH_SETTINGS_MESSAGES_UNBLOCK_MESSAGES);
		model.addAttribute("blockAllMessagesUrl", "."+ PATH_SETTINGS_MESSAGES_BLOCK_ALL);
		
		UserContextRequest request = createUserContextRequestWithMdn(subscriberId, session);
		BlockedMessagesResponse response = accountSettings.getBlockedMessages(request);
		model.addAttribute("recipients", response.getRecipients());

		String returnString = resolvePath("myaccount/settings/messages/form");
		if (logger.isDebugEnabled()) {
			logger.debug("messageSettings(Model, String, HttpSession) - end - return value=" + returnString); //$NON-NLS-1$
		}
		return returnString;
	}

	/**
	 * @param form
	 * @return
	 */
	@RequestMapping(value = PATH_SETTINGS_MESSAGES_BLOCK_MESSAGES, method = RequestMethod.POST)
	public String block(
			Model model,
			@RequestHeader(SUBSCRIBER_ID_KEY) String subscriberId,
			@ModelAttribute BlockMessagesForm form,
			HttpSession session
			) {
		if (logger.isDebugEnabled()) {
			logger.debug("block(BlockMessagesForm) - start"); //$NON-NLS-1$
		}

		if (logger.isInfoEnabled()) {
			logger.info("block(Model, String, BlockMessagesForm, HttpSession) - form=" + form); //$NON-NLS-1$
		}
		BlockedMessagesRequest arg1 =  new BlockedMessagesRequest();
		Recipient[] recipients =  new Recipient[1];
		recipients[0] = new Recipient();
		recipients[0].setSender(form.getRecipient());
		
		arg1.setRecipients(recipients );
		
		StatusMessageResponse response = accountSettings.blockMessages(createUserContextRequestWithMdn(subscriberId, session), arg1);
		
		if (logger.isInfoEnabled()) {
			logger.info("block(Model, String, BlockMessagesForm, HttpSession) - response=" + response); //$NON-NLS-1$
		}
		
		model.addAttribute(MESSAGE_KEY, response.getStatusMessage().getReason());

		String returnString = messageSettings(model, subscriberId, session);
		if (logger.isDebugEnabled()) {
			logger.debug("block(BlockMessagesForm) - end - return value=" + returnString); //$NON-NLS-1$
		}
		return returnString;
	}
	
	/**
	 * @param form
	 * @return
	 */
	@RequestMapping(value = PATH_SETTINGS_MESSAGES_UNBLOCK_MESSAGES, method = RequestMethod.POST)
	public String unblock(
			Model model,
			@RequestHeader(SUBSCRIBER_ID_KEY) String subscriberId,
			@ModelAttribute UnblockMessagesForm form,
			HttpSession session
			) {
		if (logger.isDebugEnabled()) {
			logger.debug("unblock(UnblockMessagesForm) - start"); //$NON-NLS-1$
		}

		if (logger.isInfoEnabled()) {
			logger.info("unblock(Model, String, UnblockMessagesForm, HttpSession) - form=" + form); //$NON-NLS-1$
		}
		
		BlockedMessagesRequest arg1 = new BlockedMessagesRequest();
		Recipient[] recipients =  new Recipient[form.getRecipients().size()];
		arg1.setRecipients(recipients );
		StatusMessageResponse response = accountSettings.unblockMessages(createUserContextRequestWithMdn(subscriberId, session), arg1 );
		model.addAttribute(MESSAGE_KEY, response.getStatusMessage().getReason());

		String returnString = messageSettings(model, subscriberId, session);
		if (logger.isDebugEnabled()) {
			logger.debug("unblock(UnblockMessagesForm) - end - return value=" + returnString); //$NON-NLS-1$
		}
		return returnString;
	}

	/**
	 * @return
	 */
	@RequestMapping(value = PATH_SETTINGS_MESSAGES_BLOCK_ALL, method = RequestMethod.POST)
	public String blockAll(
			Model model,
			@RequestHeader(SUBSCRIBER_ID_KEY) String subscriberId,
			@ModelAttribute BlockAllMessagesForm form,
			HttpSession session) {
		
		if (logger.isDebugEnabled()) {
			logger.debug("blockAll(BlockAllMessagesForm) - start"); //$NON-NLS-1$
		}

		if (logger.isInfoEnabled()) {
			logger.info("blockAll(Model, String, BlockAllMessagesForm, HttpSession) - form=" + form); //$NON-NLS-1$
		}
		
		StatusMessageResponse response = accountSettings.blockAllMessages(createUserContextRequestWithMdn(subscriberId, session));
		model.addAttribute(MESSAGE_KEY, response.getStatusMessage().getReason());
		
		String returnString = messageSettings(model, subscriberId, session);
		if (logger.isDebugEnabled()) {
			logger.debug("blockAll(BlockAllMessagesForm) - end - return value=" + returnString); //$NON-NLS-1$
		}
		return returnString;
	}
}
