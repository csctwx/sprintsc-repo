package com.ericsson.sprint.msdp.selfcare.controllers.mobile.settings;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ericsson.sprint.msdp.selfcare.controllers.abstracts.settings.AbstractMdnSwapController;

@Controller
@RequestMapping(AbstractMdnSwapController.URI_PREFIX_MOBILE)
public class MobileMdnSwapController extends AbstractMdnSwapController {
	@Override
	public String getViewPrefix() {
		return VIEW_PREFIX_MOBILE;
	}
}