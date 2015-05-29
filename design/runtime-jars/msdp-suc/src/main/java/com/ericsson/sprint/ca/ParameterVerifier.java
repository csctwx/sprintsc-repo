package com.ericsson.sprint.ca;


import com.drutt.pmm.runtime.command.AbstractCommand;
import com.drutt.pmm.runtime.context.UserExecutionContext;
import com.ericsson.sprint.ca.log.PmmAuthLogger;

public class ParameterVerifier extends AbstractCommand {

	private PmmAuthLogger log = PmmAuthLogger.getLogger();
	
	private String paramName;
	private String paramValue;
	
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	
	public String getParamName() {
		return paramName;
	}
	
	@Override
	public boolean execute(Object arg0) {
		String sid ="", classname ="";
		try {
			UserExecutionContext ctx = (UserExecutionContext) arg0;
			sid = ctx.getRequest().getServletRequest().getSession().getId();
			classname = this.getClass().getSimpleName();
			
			return _execute(ctx);
			
		} catch (Exception e) {
			log.error(sid, classname, "Alfa Parameter Verifier failed. Exception: " + e.getMessage());
		}
		return false;
	}
	
	private boolean _execute(UserExecutionContext ctx) throws Exception {
		String sid = ctx.getRequest().getServletRequest().getSession().getId();
		String classname = this.getClass().getSimpleName();
		
		boolean result = false;
		
		// the value for msisdn hash from Alfa is received as parameter. the name of param is configurable
		String hash;
		try {
			hash = ctx.getRequest().getServletRequest().getParameter(paramName);
			log.debug(sid, classname, "fetched param: " + paramName + "="+ hash);
		} catch (Exception e) {
			log.error(sid, classname, "Unable to fetch the parameter " + paramName);
			throw e;
		}
		
		result = hash != null && !hash.equals("");
		if(paramValue != null && result){
			result = paramValue.equals(hash.toString());
		}
		
		return result;
	}

}
