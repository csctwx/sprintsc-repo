package com.ericsson.sprint.msdp.selfcare.controllers.featurephone.settings;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ericsson.sprint.msdp.selfcare.controllers.abstracts.settings.AbstractEsnSwapController;

@Controller
@RequestMapping(AbstractEsnSwapController.URI_PREFIX_FEATURE_PHONE)
public class FPEsnSwapController extends AbstractEsnSwapController {
	@Override
	public String getViewPrefix() {
		return URI_PREFIX_FEATURE_PHONE;
	}
}