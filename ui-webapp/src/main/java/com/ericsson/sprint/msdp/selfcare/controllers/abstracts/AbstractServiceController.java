package com.ericsson.sprint.msdp.selfcare.controllers.abstracts;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public abstract class AbstractServiceController extends AbstractBaseController {

	@RequestMapping(value = "/service-and-usage", method = RequestMethod.GET)
	public String serviceAndUsage() {
		return resolvePath("myaccount/service/service-and-usage");
	}
}
