package com.ericsson.cac.sprint.shop.workflow.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cac.sprint.shop.exceptions.ShopWorkFlowException;


public class CommonUtil {
	
	//** Logger class info *//*
	private static final Logger LOGGER = LoggerFactory
			.getLogger(CommonUtil.class);
	
/*	@Autowired
	private static HeaderHandler headerHandler;
	
	@Autowired
	private static QueryPrepaidSubscriberProxyService queryPrepaidSubscriberProxyService;
	
	public static QuerySubscriberPrepaidInfoResponseType getSubscriberPrepaidInfo (String subscriberId) throws Exception {
		
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
				.querySubscriberPrepaidInfo(
						querySubscriberPrepaidInfoRequest, header);

		msisdn = querySubscriberPrepaidInfoResponseType
				.getResourceInfo().getMdn();
		
		LOGGER.info("MSISDN obtained from QueryPrepaidSubscriberInfo is "
				+ msisdn);
		
		return querySubscriberPrepaidInfoResponseType;
	}*/

	/**
	 * returns the current date in xml calender fromat
	 * */
	
	public static final XMLGregorianCalendar getCurrentDateInFormat(){
		XMLGregorianCalendar xmlDate=null;
		GregorianCalendar calendar=new GregorianCalendar();
		calendar.setTime(new Date());
		try {
			xmlDate=DatatypeFactory.newInstance().
					newXMLGregorianCalendarDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.DAY_OF_MONTH), DatatypeConstants.FIELD_UNDEFINED);
		} catch (DatatypeConfigurationException e) {
			LOGGER.error("Error converting Current Date", e);
		}
		return xmlDate;
	}
	
	public static final XMLGregorianCalendar getCurrentDateInSubmitOrderFormat(){
		XMLGregorianCalendar xmlDate=null;
		GregorianCalendar calendar=new GregorianCalendar();
		calendar.setTime(new Date());
		try {
			xmlDate=DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
		} catch (DatatypeConfigurationException e) {
			LOGGER.error("Error converting Current Date", e);
		}
		return xmlDate;
	}
	public static final boolean isStringNullOfEmpty(String stringValue){
		boolean result=false;
		if(stringValue==null || stringValue.length()==0){
			result=true;
		}
		return result;
	}
	
	public static final XMLGregorianCalendar getCurrentDateInFormat(String stringDate, String inpattern){
		XMLGregorianCalendar xmlDate=null;
		GregorianCalendar calendar=new GregorianCalendar();
		if(!stringDate.startsWith("20")){
			stringDate="20"+stringDate;
		}
		SimpleDateFormat dateFormat= new SimpleDateFormat(inpattern);
		try {
			Date date=dateFormat.parse(stringDate);
			calendar.setTime(date);
			xmlDate=DatatypeFactory.newInstance().
					newXMLGregorianCalendarDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1, DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED);
		} catch (ParseException | DatatypeConfigurationException e1) {
			LOGGER.error("Error in parsing date "+stringDate,e1);
			throw new ShopWorkFlowException(e1);
		}
		calendar.setTime(new Date());
		
		return xmlDate;
	}
	
	public static final XMLGregorianCalendar getDateInFormat(String stringDate, String inpattern){
		XMLGregorianCalendar xmlDate=null;
		GregorianCalendar calendar=new GregorianCalendar();
		
		SimpleDateFormat dateFormat= new SimpleDateFormat(inpattern);
		try {
			Date date=dateFormat.parse(stringDate);
			calendar.setTime(date);
			xmlDate=DatatypeFactory.newInstance().
					newXMLGregorianCalendarDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.DATE),DatatypeConstants.FIELD_UNDEFINED);
			//xmlDate=DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar)
		} catch (ParseException | DatatypeConfigurationException e1) {
			LOGGER.error("Error in parsing date "+stringDate,e1);
			throw new ShopWorkFlowException(e1);
		}
		calendar.setTime(new Date());
		
		return xmlDate;
	}
}
