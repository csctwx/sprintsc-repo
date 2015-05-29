package com.ericsson.cac.sprint.selfcare.workflow.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.Holder;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ericsson.cac.sprint.adapters.QueryPrepaidSubscriberProxyService;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.StatusMessage;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.StatusMessageResponse;
import com.sprint.integration.common.header.wsmessageheader.v2.WsMessageHeaderType;
import com.sprint.integration.interfaces.querysubscriberprepaidinfo.v1.querysubscriberprepaidinfo.QuerySubscriberPrepaidInfoResponseType;
import com.sprint.integration.interfaces.querysubscriberprepaidinfo.v1.querysubscriberprepaidinfo.QuerySubscriberPrepaidInfoType;
import com.sprint.integration.interfaces.querysubscriberprepaidinfo.v1.querysubscriberprepaidinfo.SearchCriteriaType;

@Component
public class CommonUtil {

	/** Logger class info */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(CommonUtil.class);

	@Autowired
	private HeaderHandler headerHandler;
	
	@Value("${selfcareworkflow.date.format}")
	private String dateFormat;
	
	@Autowired
	private QueryPrepaidSubscriberProxyService queryPrepaidSubscriberProxyService;
	
	@Value("${successStatusCode}")
	private String successStatusCode;
	
	@Value("${failureStatusCode}")
	private String failureStatusCode;
	
	@Value("${successReason}")
	private String successReason;
	
	@Value("${failureReason}")
	private String failureReason;
	
	@Value("${successDesc}")
	private String successDesc;
	
	@Value("${failureDesc}")
	private String failureDesc;
	
	public QuerySubscriberPrepaidInfoResponseType getSubscriberPrepaidInfo (String subscriberId) throws Exception {
		
		// Fetching MSISDN from SubscriberId by calling
		// QueryPrepaidSubcriberResponse
		//String msisdn = "";
		
		Holder<WsMessageHeaderType> header =  headerHandler.getHeader(Service.QUERY_PREPAID_SUBSCRIBER_SERVICE);
		
		QuerySubscriberPrepaidInfoType querySubscriberPrepaidInfoRequest = new QuerySubscriberPrepaidInfoType();

		SearchCriteriaType searchCriteriaType = new SearchCriteriaType();
		searchCriteriaType.setSubscriberId(subscriberId);
		querySubscriberPrepaidInfoRequest
				.setInfo(searchCriteriaType);

		LOGGER.debug("Invoking QueryPrepaidSubscriberInfp -> querySubscriberPrepaidInfo method");

        QuerySubscriberPrepaidInfoResponseType querySubscriberPrepaidInfoResponseType = queryPrepaidSubscriberProxyService
				.querySubscriberPrepaidInfo(querySubscriberPrepaidInfoRequest,
						header);

		/*
		 * msisdn = querySubscriberPrepaidInfoResponseType
		 * .getResourceInfo().getMdn();
		 */

		/*
		 * LOGGER.info("MSISDN obtained from QueryPrepaidSubscriberInfo is " +
		 * msisdn);
		 */

		return querySubscriberPrepaidInfoResponseType;
	}


    public QuerySubscriberPrepaidInfoResponseType getSubscriberPrepaidInfoWithMsisdn(
			String msisdn) throws Exception {

		// Fetching MSISDN from SubscriberId by calling
		// QueryPrepaidSubcriberResponse
		// String msisdn = "";

		Holder<WsMessageHeaderType> header = headerHandler
				.getHeader(Service.QUERY_PREPAID_SUBSCRIBER_SERVICE);

		QuerySubscriberPrepaidInfoType querySubscriberPrepaidInfoRequest = new QuerySubscriberPrepaidInfoType();

		SearchCriteriaType searchCriteriaType = new SearchCriteriaType();
		searchCriteriaType.setMdn(msisdn);
		querySubscriberPrepaidInfoRequest.setInfo(searchCriteriaType);

		LOGGER.debug("Invoking QueryPrepaidSubscriberInfp -> querySubscriberPrepaidInfo method");

		QuerySubscriberPrepaidInfoResponseType querySubscriberPrepaidInfoResponseType = queryPrepaidSubscriberProxyService
				.querySubscriberPrepaidInfo(querySubscriberPrepaidInfoRequest,
						header);

		/*
		 * msisdn = querySubscriberPrepaidInfoResponseType
		 * .getResourceInfo().getMdn();
		 */

		/*
		 * LOGGER.info("MSISDN obtained from QueryPrepaidSubscriberInfo is " +
		 * msisdn);
		 */

		return querySubscriberPrepaidInfoResponseType;
	}
    
    
    public Date getDateFromXmlGregorianCal(XMLGregorianCalendar calendar) {
		if (calendar == null) {
			return null;
		}
		return calendar.toGregorianCalendar().getTime();
	}

	
	public boolean isNullOrBlank(String value) {
		boolean isError = true;
		
		if(value!=null && !value.isEmpty()) {
			isError = false;
		}
		return isError;
	}
	
	public StatusMessageResponse populateStatusRespMesg(boolean errorFlag, String description) {
		
		StatusMessageResponse statusMessageResponse =  new StatusMessageResponse();
		StatusMessage statusMessage = new StatusMessage();

		if(!errorFlag) {
			AlarmUtil.raiseSnmpAlarm();
			
			LOGGER.debug("Success response");
			statusMessage.setStatus(Integer.valueOf(successStatusCode));
			statusMessage.setDescription(successDesc);	
			
			if(!StringUtils.isBlank(description)) {
				statusMessage.setReason(description);
				statusMessage.setDescription(description);
			} else {
				statusMessage.setReason(successReason);
			}			
		}
		
		else {
			LOGGER.debug("Failure response");
			statusMessage.setStatus(Integer.valueOf(failureStatusCode));
			statusMessage.setDescription(failureDesc);	
			
			if(!StringUtils.isBlank(description)) {
				statusMessage.setReason(failureReason+":" + description);
			} else {
				statusMessage.setReason(failureReason);
			}
			
			LOGGER.error("Error reason -> " + statusMessage.getReason());
		}
		
		statusMessageResponse.setStatusMessage(statusMessage);	
		return statusMessageResponse;
	}

}
