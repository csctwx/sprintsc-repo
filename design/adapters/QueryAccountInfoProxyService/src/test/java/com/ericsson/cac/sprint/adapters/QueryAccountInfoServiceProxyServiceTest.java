package com.ericsson.cac.sprint.adapters;

import java.math.BigInteger;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.ws.Holder;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sprint.integration.common.header.wsmessageheader.v2.TrackingMessageHeaderType;
import com.sprint.integration.common.header.wsmessageheader.v2.WsMessageHeaderType;
import com.sprint.integration.interfaces.queryaccountbasicinfo.v1.queryaccountbasicinfo.QueryAccountBasicInfo;
import com.sprint.integration.interfaces.queryaccountbasicinfo.v1.queryaccountbasicinfo.QueryAccountBasicInfoResponse;
import com.sprint.integration.interfaces.queryaccountinfoservice.v1.queryaccountinfoservice.SoapFaultv2;
import com.sprint.integration.interfaces.queryaccountsecurityinfo.v1.queryaccountsecurityinfo.QueryAccountSecurityInfo;
import com.sprint.integration.interfaces.queryaccountsecurityinfo.v1.queryaccountsecurityinfo.QueryAccountSecurityInfoResponse;

/**
 * Created by echasis on 20/01/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class QueryAccountInfoServiceProxyServiceTest {
    private Logger logger = LoggerFactory.getLogger(QueryAccountInfoServiceProxyServiceTest.class);
    
    @Autowired
    private QueryAccountInfoServiceProxyService service;

   /* @Autowired
    private QueryAccountInfoPortType mockPort;*/
    
   /* @Autowired
    private PropertiesConfiguration configuration;*/
    
    private QueryAccountBasicInfoResponse queryAccountBasicInfoResponse = null;
    private QueryAccountSecurityInfoResponse queryAccountSecurityInfoResponse = null;
    
    WsMessageHeaderType successHead = null;
	Holder<WsMessageHeaderType> successHeader = null;
	
	WsMessageHeaderType failHead = null;
	Holder<WsMessageHeaderType> failureHeader = null;
	
	
	@Value("${QueryAccountInfoService.trackingHeadAppId}")
	private String trackingHeadAppId;
	@Value("${QueryAccountInfoService.trackingHeadAppUsrId}")
	private String trackingHeadAppUsrId;
	@Value("${QueryAccountInfoService.trackingHeadConsumerId}")
	private String trackingHeadConsumerId;
	@Value("${QueryAccountInfoService.trackingHeadTimeToLive}")
	private String trackingHeadTimeToLive;
	@Value("${QueryAccountInfoService.trackingHeadMessageId}")
	private String trackingHeadMessageId;
	@Value("${QueryAccountInfoService.trackingHeadConversationId}")
	private String trackingHeadConversationId;
	@Value("${QueryAccountInfoService.trackingHeadFailAppId}")
	private String trackingHeadFailAppId;
	@Value("${QueryAccountInfoService.trackingHeadFailAppUsrId}")
	private String trackingHeadFailAppUsrId;
	@Value("${QueryAccountInfoService.trackingHeadFailConsumerId}")
	private String trackingHeadFailConsumerId;
	@Value("${QueryAccountInfoService.trackingHeadFailTimeToLive}")
	private String trackingHeadFailTimeToLive;


    @Before
    public void instructMock() throws SoapFaultv2, DatatypeConfigurationException {
    	
    	TrackingMessageHeaderType trackingHead = new TrackingMessageHeaderType();
		trackingHead.setApplicationId(trackingHeadAppId);
		trackingHead.setApplicationUserId(trackingHeadAppUsrId);
		trackingHead.setConsumerId(trackingHeadConsumerId);
		trackingHead.setTimeToLive(new BigInteger(trackingHeadTimeToLive));
		trackingHead.setMessageId(trackingHeadMessageId);
		trackingHead.setConversationId(trackingHeadConversationId);
		GregorianCalendar c = new GregorianCalendar();
		trackingHead.setMessageDateTimeStamp(DatatypeFactory.newInstance().newXMLGregorianCalendar(c));
		successHead = new WsMessageHeaderType();
		successHead.setTrackingMessageHeader(trackingHead);
		successHeader = new Holder<WsMessageHeaderType>(successHead);
		
		TrackingMessageHeaderType trackingHeadFail = new TrackingMessageHeaderType();
		trackingHeadFail.setApplicationId(trackingHeadFailAppId);
		trackingHeadFail.setApplicationUserId(trackingHeadFailAppUsrId);
		trackingHeadFail.setConsumerId(trackingHeadFailConsumerId);
		trackingHeadFail.setTimeToLive(new BigInteger(trackingHeadFailTimeToLive));
		trackingHeadFail.setMessageDateTimeStamp(DatatypeFactory.newInstance().newXMLGregorianCalendar(c));

		failHead = new WsMessageHeaderType();
		failHead.setTrackingMessageHeader(trackingHead);
		failureHeader = new Holder<WsMessageHeaderType>(failHead);
	
	
    }

    @Test
    public void testQueryAccountSecurityInfo() throws SoapFaultv2 {
        	QueryAccountSecurityInfo queryAccountSecurityInfoRequest = new QueryAccountSecurityInfo();
			com.sprint.integration.interfaces.queryaccountsecurityinfo.v1.queryaccountsecurityinfo.SearchCriteria searchCriteria = new com.sprint.integration.interfaces.queryaccountsecurityinfo.v1.queryaccountsecurityinfo.SearchCriteria();
			searchCriteria.setBan("100565543");
			queryAccountSecurityInfoRequest.setInfo(searchCriteria);
			
            QueryAccountSecurityInfoResponse queryAccountSecurityInfoResponse2 = service.queryAccountSecurityInfo(queryAccountSecurityInfoRequest, successHeader);
            Assert.assertEquals("123456", queryAccountSecurityInfoResponse2.getSecurityInfo().getPin());
        
    }
    
    @Test
    public void testQueryAccountBasicInfo() {
		try {
			QueryAccountBasicInfo queryAccountBasicInfoRequest = new QueryAccountBasicInfo();
			com.sprint.integration.interfaces.queryaccountbasicinfo.v1.queryaccountbasicinfo.SearchCriteria searchCriteria = new com.sprint.integration.interfaces.queryaccountbasicinfo.v1.queryaccountbasicinfo.SearchCriteria();
			searchCriteria.setBan(new Integer("100565543"));
			queryAccountBasicInfoRequest.setInfo(searchCriteria);
			
			QueryAccountBasicInfoResponse queryAccountBasicInfoResponse2 = service.queryAccountBasicInfo(queryAccountBasicInfoRequest, successHeader);
			Assert.assertNotNull("123456", queryAccountBasicInfoResponse2);
			
		} catch(SoapFaultv2 e) {
		}
	}
	
}
