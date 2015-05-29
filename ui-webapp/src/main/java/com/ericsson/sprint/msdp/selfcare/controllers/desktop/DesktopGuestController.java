package com.ericsson.sprint.msdp.selfcare.controllers.desktop;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ericsson.sprint.msdp.selfcare.controllers.abstracts.AbstractBaseController;
import com.ericsson.sprint.msdp.selfcare.controllers.abstracts.AbstractGuestController;

@Controller
@RequestMapping(AbstractBaseController.URI_PREFIX_DESKTOP)
public class DesktopGuestController extends AbstractGuestController {

	@Override
	public String getViewPrefix() {
		return VIEW_PREFIX_DESKTOP;
	}

}
