package com.ericsson.sprint.msdp.selfcare.controllers.desktop.settings;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ericsson.sprint.msdp.selfcare.controllers.abstracts.settings.AbstractMdnSwapController;

@Controller
@RequestMapping(AbstractMdnSwapController.URI_PREFIX_DESKTOP)
public class DesktopMdnSwapController extends AbstractMdnSwapController {
	@Override
	public String getViewPrefix() {
		return URI_PREFIX_DESKTOP;
	}
}