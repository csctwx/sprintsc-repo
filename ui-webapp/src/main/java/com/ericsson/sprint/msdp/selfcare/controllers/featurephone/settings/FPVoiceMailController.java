package com.ericsson.sprint.msdp.selfcare.controllers.featurephone.settings;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ericsson.sprint.msdp.selfcare.controllers.abstracts.AbstractBaseController;
import com.ericsson.sprint.msdp.selfcare.controllers.abstracts.settings.AbstractVoiceMailController;

@Controller
@RequestMapping(AbstractBaseController.URI_PREFIX_FEATURE_PHONE)
public class FPVoiceMailController extends AbstractVoiceMailController {

	@Override
	public String getViewPrefix() {
		return VIEW_PREFIX_FEATURE_PHONE;
	}

}
