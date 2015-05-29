package com.sprint.msdp.commandservice.service;

import java.math.BigInteger;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.ws.Holder;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

import com.sprint.integration.common.header.wsmessageheader.v2.TrackingMessageHeaderType;
import com.sprint.integration.common.header.wsmessageheader.v2.WsMessageHeaderType;

@Configuration
@ImportResource(value = { 
		//"classpath:applicationContext.xml", 
		"classpath:spring-QueryPrepaidSubcriberProxyService.xml",
		"classpath:spring-LoggingManagementProxyService.xml",
		"classpath:spring-QueryDeviceInfoProxyService.xml", 
		"classpath:spring-QueryReferenceInfoProxyService.xml",
		"classpath:spring-QueryAccountInfoProxyService.xml", 
		"classpath:spring-QuerySubscriberInfoProxyService.xml",
		"classpath:spring-SubscriberManagementProxyService.xml", 
		"classpath:spring-SecurityManagementProxyService.xml",
		"classpath:spring-QueryUsageProxyService.xml",
		"classpath:spring-QueryPlansAndOptionsProxyService.xml" 
		})
public class WebappConfig {

	@Bean
	public PropertyPlaceholderConfigurer getPropertyPlaceholderConfigurer() {
		PropertyPlaceholderConfigurer pro = new PropertyPlaceholderConfigurer();
		pro.setLocation(new FileSystemResource("/tmp/selfcare-config.properties"));
		return pro;
	}

	@Bean
	public Holder<WsMessageHeaderType> getSuccessHeader() throws Exception {
		TrackingMessageHeaderType trackingHead = new TrackingMessageHeaderType();
		trackingHead.setApplicationId("KHK");
		trackingHead.setApplicationUserId("KHK");
		trackingHead.setConsumerId("KHK");
		trackingHead.setTimeToLive(new BigInteger("30"));
		trackingHead.setMessageId("KHK-MSGID-0000003");
		trackingHead.setConversationId("KHK-CONVID-0000001");
		GregorianCalendar c = new GregorianCalendar();
		trackingHead.setMessageDateTimeStamp(DatatypeFactory.newInstance().newXMLGregorianCalendar(c));
		WsMessageHeaderType successHead = new WsMessageHeaderType();
		successHead.setTrackingMessageHeader(trackingHead);
		Holder<WsMessageHeaderType> holder = new Holder<WsMessageHeaderType>(successHead);
		return holder;
	}
}