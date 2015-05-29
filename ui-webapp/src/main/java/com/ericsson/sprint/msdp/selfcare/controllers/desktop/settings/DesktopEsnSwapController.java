package com.ericsson.sprint.msdp.selfcare.controllers.desktop.settings;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ericsson.sprint.msdp.selfcare.controllers.abstracts.settings.AbstractEsnSwapController;

@Controller
@RequestMapping(AbstractEsnSwapController.URI_PREFIX_DESKTOP)
public class DesktopEsnSwapController extends AbstractEsnSwapController {
	@Override
	public String getViewPrefix() {
		return VIEW_PREFIX_DESKTOP;
	}
}