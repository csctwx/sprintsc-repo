/**
 * 
 */
package com.ericsson.cac.sprint.adapters;

import javax.xml.ws.Holder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sprint.integration.common.header.wsmessageheader.v2.WsMessageHeaderType;
import com.sprint.integration.eai.services.queryresourceinfoservice.v1.queryresourceinfoservice.QueryResourceInfoPortType;
import com.sprint.integration.eai.services.queryresourceinfoservice.v1.queryresourceinfoservice.QueryResourceInfoService;
import com.sprint.integration.eai.services.queryresourceinfoservice.v1.queryresourceinfoservice.SoapFaultv2;
import com.sprint.integration.interfaces.queryavailablenpacsa.v1.queryavailablenpacsa.QueryAvailableNpaCsaResponseType;
import com.sprint.integration.interfaces.queryavailablenpacsa.v1.queryavailablenpacsa.QueryAvailableNpaCsaType;

/**
 * @author ebabadd
 *
 */
@Component
public class QueryResourceInfoProxyService extends QueryResourceInfoService{
	
	@Autowired
	private QueryResourceInfoPortType stub;
	
	public QueryAvailableNpaCsaResponseType queryAvailableNpaCsa(QueryAvailableNpaCsaType parameters, Holder<WsMessageHeaderType> head) throws SoapFaultv2 {
		return stub.queryAvailableNpaCsa(parameters, head);
	}
	
	
}
