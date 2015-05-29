package com.ericsson.sprint.ca.log;

import org.jboss.logging.Logger;

public class PmmAuthLogger {

	private static final String LOG_NAME = "ca.pmm.authentication"; 
	private static final Logger log = Logger.getLogger(LOG_NAME);
	private static final int CLASSNAME_LENGHT = 25;
	
	private static PmmAuthLogger instance;
	
	private PmmAuthLogger() {
	}
	
	public static PmmAuthLogger getLogger() {
		if(instance == null) {
			instance = new PmmAuthLogger();
		}
		return instance;
	}
	
	private String format(String name) {
		if(name.length() > CLASSNAME_LENGHT) {
			return name.substring(0, CLASSNAME_LENGHT);
		}
		StringBuffer spaces = new StringBuffer("");
		for(int i = name.length(); i< CLASSNAME_LENGHT; i++) {
			spaces.append(" ");
		}
		return name + spaces.toString();
	}
	
	private String formatMessage(String sid, String classname, String message) {
		return "<" + sid + "> [" + format(classname) + "] " + message;
	}
	
	public void trace(String sid, String classname, String message)
    {
        log.trace(formatMessage(sid, classname, message));
    }

    public void trace(String sid, String classname, String message, Throwable t)
    {
        log.trace(formatMessage(sid, classname, message), t);
    }

    public void debug(String sid, String classname, String message)
    {
    	if ( log.isDebugEnabled()){
    		log.debug(formatMessage(sid, classname, message));
    	}
    }

    public void debug(String sid, String classname, String message, Throwable t)
    {
    	if ( log.isDebugEnabled() ){
    		log.debug(formatMessage(sid, classname, message), t);
    	}
    }
    
    public void info(String sid, String classname, String message)
    {
        log.info(formatMessage(sid, classname, message));
    }

    public void info(String sid, String classname, String message, Throwable t)
    {
        log.info(formatMessage(sid, classname, message), t);
    }

    public void warn(String sid, String classname, String message)
    {
        log.warn(formatMessage(sid, classname, message));
    }

    public void warn(String sid, String classname, String message, Throwable t)
    {
        log.warn(formatMessage(sid, classname, message), t);
    }

    public void error(String sid, String classname, String message)
    {
        log.error(formatMessage(sid, classname, message));
    }

    public void error(String sid, String classname, String message, Throwable t)
    {
        log.error(formatMessage(sid, classname, message), t);
    }

    public void fatal(String sid, String classname, String message)
    {
        log.fatal(formatMessage(sid, classname, message));
    }

    public void fatal(String sid, String classname, String message, Throwable t)
    {
        log.fatal(formatMessage(sid, classname, message), t);
    }
    
}
