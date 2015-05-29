package com.ericsson.sprint.msdp.selfcare.controllers.mobile;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ericsson.sprint.msdp.selfcare.controllers.abstracts.AbstractDeviceController;

@Controller
@RequestMapping(AbstractDeviceController.URI_PREFIX_MOBILE)
public class MobileDeviceController extends AbstractDeviceController {

	@Override
	public String getViewPrefix() {
		return VIEW_PREFIX_MOBILE;
	}

}
