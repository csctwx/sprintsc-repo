package com.ericsson.drutt.ws.client;

//import static com.ericsson.drutt.alfa.common.config.CAConfiguration.CONFIG_MSDP_WS;

import java.net.URL;

import javax.xml.ws.BindingProvider;

import com.ericsson.sprint.cac.EsbCommandService;
import com.ericsson.sprint.cac.EsbCommandServiceService;

//import com.ericsson.drutt.alfa.common.config.CAConfiguration;
//import com.ericsson.drutt.alfa.common.config.ReloadListener;


public class ServiceLocator  {
//	private static Logger log  = Logger.getLogger(ServiceLocator.class);
   
    private EsbCommandServiceService esbCommandService = null;   
    private static ServiceLocator instance = null;
    private static ThreadLocal serviceCache = new ThreadLocal();
    
    private String wsUser;
    private String wsPassword;
    private URL url;
    
    public void setWsUser(String wsUser) {
		this.wsUser = wsUser;
		serviceCache.remove();
	}

	public void setWsPassword(String wsPassword) {
		this.wsPassword = wsPassword;
		serviceCache.remove();
	}

	public void setUrl(URL url) {
		this.url = url;
		esbCommandService = null;
		serviceCache.remove();
	}

    
    public ServiceLocator(){
        
    }
    
    private EsbCommandServiceService getService(){
    	if(esbCommandService == null){
    		esbCommandService = (EsbCommandServiceService) WebServiceUtil.createService(EsbCommandServiceService.class, url);
    	}
    	
    	return esbCommandService;
    }
   
    public EsbCommandService getCommandService(){
    	EsbCommandService commandService;
    	commandService = (EsbCommandService) serviceCache.get();
    	if(commandService == null){
    		commandService = createCommandService();
    	}
    	
    	return commandService;
    }

	private EsbCommandService createCommandService() {
		EsbCommandService port = getService().getEsbCommandServicePort();
		if((port instanceof BindingProvider) && wsUser != null)
        {
            BindingProvider bp = (BindingProvider)port;
            bp.getRequestContext().put("javax.xml.ws.security.auth.username", wsUser);
            bp.getRequestContext().put("javax.xml.ws.security.auth.password", wsPassword);
        }
        return port;
	}
    
    
    
}


