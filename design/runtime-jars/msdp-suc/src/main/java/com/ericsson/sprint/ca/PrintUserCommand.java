package com.ericsson.sprint.ca;

import com.drutt.pmm.runtime.ee.portal.PortalCommand;
import com.drutt.pmm.runtime.portal.PortalExecutionContext;
import com.drutt.pmm.userprofile.core.UserContext;
import com.ericsson.sprint.ca.log.PmmAuthLogger;

public class PrintUserCommand extends PortalCommand {

	private PmmAuthLogger logger = PmmAuthLogger.getLogger();
	private final String CLASSNAME = "PrintUserCommand";
	@Override
	public boolean execute(PortalExecutionContext ctx) {
		String sessionId = ctx.getRequest().getServletRequest().getSession().getId();
		StringBuilder sb = new StringBuilder("UserContext->");
		UserContext userContext = ctx.getUserContext();
		sb.append("msisdn:" + userContext.getName()).append("-");
		sb.append("name:"+userContext.getName()).append("-");
		sb.append("Metas:"+userContext.getUserMeta().toString());
		
		logger.debug(sessionId, CLASSNAME, sb.toString());
		return true;
	}

}
