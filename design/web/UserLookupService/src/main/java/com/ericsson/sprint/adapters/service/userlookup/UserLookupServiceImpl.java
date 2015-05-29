package com.ericsson.sprint.adapters.service.userlookup;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import com.drutt.ws.msdp.userlookup.MetaWs;
import com.drutt.ws.msdp.userlookup.UserLookupService;
import com.drutt.ws.msdp.userlookup.WSException_Exception;
import com.ericsson.sprint.adapters.bean.TestBean;
import com.ericsson.sprint.adapters.context.SpringApplicationContext;

@WebService(name = "UserLookupService", targetNamespace = "http://ws.drutt.com/msdp/userlookup")
public class UserLookupServiceImpl implements UserLookupService{
	@Override
	@WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "lookupUser", targetNamespace = "http://ws.drutt.com/msdp/userlookup", className = "com.drutt.ws.msdp.userlookup.LookupUser")
    @ResponseWrapper(localName = "lookupUserResponse", targetNamespace = "http://ws.drutt.com/msdp/userlookup", className = "com.drutt.ws.msdp.userlookup.LookupUserResponse")
    public String lookupUser(
        @WebParam(name = "identifier", targetNamespace = "")
        String identifier,
        @WebParam(name = "meta", targetNamespace = "")
        List<MetaWs> meta)
			throws WSException_Exception {
		TestBean bean = (TestBean) SpringApplicationContext.getBean("testBean");
		System.out.println(bean.getTestBean());
		return null;
	}
}
