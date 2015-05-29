package com.ericsson.sprint.msdp.selfcare.controllers.desktop;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ericsson.sprint.msdp.selfcare.controllers.abstracts.AbstractBaseController;
import com.ericsson.sprint.msdp.selfcare.controllers.abstracts.AbstractPaymentsController;

@Controller
@RequestMapping(AbstractBaseController.URI_PREFIX_DESKTOP)
public class DesktopPaymentsController extends AbstractPaymentsController {

	@Override
	public String getViewPrefix() {
		return VIEW_PREFIX_DESKTOP;
	}

}
