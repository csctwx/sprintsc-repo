package com.ericsson.sprint.msdp.selfcare.controllers.mobile.usage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ericsson.sprint.msdp.selfcare.controllers.abstracts.AbstractBaseController;
import com.ericsson.sprint.msdp.selfcare.controllers.abstracts.usage.AbstractUsageHistoryController;

@Controller
@RequestMapping(AbstractBaseController.URI_PREFIX_MOBILE)
public class MobileUsageHistoryController extends AbstractUsageHistoryController {

	@Override
	public String getViewPrefix() {
		return VIEW_PREFIX_MOBILE;
	}

}
