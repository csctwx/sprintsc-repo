package com.ericsson.sprint.ca;

import com.drutt.pmm.runtime.context.UserExecutionContext;
import com.drutt.pmm.runtime.ee.integration.GuestUserPolicyImpl;
import com.ericsson.sprint.ca.log.PmmAuthLogger;

public class CustomGuestUserPolicy extends GuestUserPolicyImpl {

	private PmmAuthLogger logger = PmmAuthLogger.getLogger();
	public static final String CLASSNAME = "CustomGuestUserPolicy";
	
	@Override
	public String getName(UserExecutionContext userexecutioncontext) {
		String sid = userexecutioncontext.getRequest().getServletRequest().getSession().getId();
		String brandId = (String) userexecutioncontext.getRequest().getServletRequest().getAttribute(IdentifyBrand.BRAND_ID);
		logger.debug(sid, CLASSNAME, brandId + " is the name of the GuestProfile");
		return brandId;
	}


}
