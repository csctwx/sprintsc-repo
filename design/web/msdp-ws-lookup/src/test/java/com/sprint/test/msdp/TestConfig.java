package com.sprint.test.msdp;

import java.math.BigInteger;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.ws.Holder;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.ClassPathResource;

import com.sprint.integration.common.header.wsmessageheader.v2.TrackingMessageHeaderType;
import com.sprint.integration.common.header.wsmessageheader.v2.WsMessageHeaderType;
import com.sprint.msdp.commandservice.service.WebappConfig;


public class TestConfig extends WebappConfig {

	@Override
	@Bean
	public PropertyPlaceholderConfigurer getPropertyPlaceholderConfigurer() {
		PropertyPlaceholderConfigurer pro = new PropertyPlaceholderConfigurer();
		pro.setLocation(new ClassPathResource("/config.properties"));
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