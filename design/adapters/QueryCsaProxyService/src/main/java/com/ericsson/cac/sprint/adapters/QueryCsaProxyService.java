/**
 * 
 */
package com.ericsson.cac.sprint.adapters;

import javax.xml.ws.Holder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sprint.integration.common.header.wsmessageheader.v2.WsMessageHeaderType;
import com.sprint.integration.eai.services.querycsa.v1.querycsa.Faultmessage;
import com.sprint.integration.eai.services.querycsa.v1.querycsa.Faultmessage2;
import com.sprint.integration.eai.services.querycsa.v1.querycsa.QueryCsaPortType;
import com.sprint.integration.eai.services.querycsa.v1.querycsa.QueryCsaService;
import com.sprint.integration.interfaces.querycsa.v1.querycsaenvelope.QueryCsaReply;
import com.sprint.integration.interfaces.querycsa.v1.querycsaenvelope.QueryCsaRequest;
import com.sprint.integration.interfaces.querycsa.v2.querycsav2.QueryCsaV2ResponseType;
import com.sprint.integration.interfaces.querycsa.v2.querycsav2.QueryCsaV2Type;
import com.sprint.integration.interfaces.querylocationinfo.v1.querylocationinfo.QueryLocationInfoRequestType;
import com.sprint.integration.interfaces.querylocationinfo.v1.querylocationinfo.QueryLocationInfoResponseType;

/**
 * @author ebabadd
 *
 */
@Component
public class QueryCsaProxyService extends QueryCsaService {
	
	@Autowired
	private QueryCsaPortType stub;
	
	public QueryCsaReply queryCsa(QueryCsaRequest body,Holder<WsMessageHeaderType> head) throws Faultmessage{
		return stub.queryCsa(body, head);
	}
	
	public QueryCsaV2ResponseType queryCsaV2(QueryCsaV2Type body,Holder<WsMessageHeaderType> head) throws Faultmessage2{
		return stub.queryCsaV2(body, head);
	}
	
	public QueryLocationInfoResponseType queryLocationInfo(QueryLocationInfoRequestType body,Holder<WsMessageHeaderType> head) throws Faultmessage{
		return stub.queryLocationInfo(body, head);
	}

}
