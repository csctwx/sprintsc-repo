package com.ericsson.sprint.msdp.selfcare.controllers.mobile.settings;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ericsson.sprint.msdp.selfcare.controllers.abstracts.AbstractBaseController;
import com.ericsson.sprint.msdp.selfcare.controllers.abstracts.settings.AbstractVoiceMailController;

@Controller
@RequestMapping(AbstractBaseController.URI_PREFIX_MOBILE)
public class MobileVoiceMailController extends AbstractVoiceMailController {

	@Override
	public String getViewPrefix() {
		return VIEW_PREFIX_MOBILE;
	}

}
