package com.ericsson.sprint.msdp.selfcare.controllers.featurephone.settings;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ericsson.sprint.msdp.selfcare.controllers.abstracts.settings.AbstractMdnSwapController;

@Controller
@RequestMapping(AbstractMdnSwapController.URI_PREFIX_FEATURE_PHONE)
public class FPMdnSwapController extends AbstractMdnSwapController {
	@Override
	public String getViewPrefix() {
		return URI_PREFIX_FEATURE_PHONE;
	}
}