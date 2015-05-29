package com.ericsson.cac.sprint.adapters;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

import java.math.BigInteger;
import java.util.GregorianCalendar;

import com.sprint.integration.common.errordetailsv2.ErrorDetailsType;
import com.sprint.integration.common.header.wsmessageheader.v2.TrackingMessageHeaderType;
import com.sprint.integration.common.header.wsmessageheader.v2.WsMessageHeaderType;
import com.sprint.integration.eai.services.accountmanagementservice.v1.accountmanagementservice.AccountManagementServicePortType;
import com.sprint.integration.eai.services.accountmanagementservice.v1.accountmanagementservice.SoapFault;
import com.sprint.integration.interfaces.updateaccountdetails.v1.updateaccountdetails.SearchCriteriaType;
import com.sprint.integration.interfaces.updateaccountdetails.v1.updateaccountdetails.UpdateAccountDetailsRequestType;
import com.sprint.integration.interfaces.updateaccountdetails.v1.updateaccountdetails.UpdateAccountDetailsResponseType;

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

import javax.xml.ws.Holder;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.DatatypeConfigurationException;


//import org.apache.commons.configuration.PropertiesConfiguration;
/**
 * Created by esvwxal on 21/01/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class AccountManagementProxyServiceTest {
    private Logger logger = LoggerFactory.getLogger(AccountManagementProxyServiceTest.class);
    
//    @Autowired
//	private PropertiesConfiguration configuration;
    
    @Autowired
    private AccountManagementProxyService service;

    @Autowired
    private AccountManagementServicePortType mockPort;
    
    private UpdateAccountDetailsResponseType updateAccountDetailsResponse1;

    WsMessageHeaderType successHead = new WsMessageHeaderType();
	Holder<WsMessageHeaderType> successHeader = new Holder<WsMessageHeaderType>(
			successHead);
	
    WsMessageHeaderType failHead = new WsMessageHeaderType();
	Holder<WsMessageHeaderType> failureHeader = new Holder<WsMessageHeaderType>(
			failHead);
	
	@Value("${AccountManagementService.trackingHeadAppId}")
	private String trackingHeadAppId;
	@Value("${AccountManagementService.trackingHeadAppUsrId}")
	private String trackingHeadAppUsrId;
	@Value("${AccountManagementService.trackingHeadConsumerId}")
	private String trackingHeadConsumerId;
	@Value("${AccountManagementService.trackingHeadTimeToLive}")
	private String trackingHeadTimeToLive;
	@Value("${AccountManagementService.trackingHeadMessageId}")
	private String trackingHeadMessageId;
	@Value("${AccountManagementService.trackingHeadConversationId}")
	private String trackingHeadConversationId;
	@Value("${AccountManagementService.trackingHeadFailAppId}")
	private String trackingHeadFailAppId;
	@Value("${AccountManagementService.trackingHeadFailAppUsrId}")
	private String trackingHeadFailAppUsrId;
	@Value("${AccountManagementService.trackingHeadFailConsumerId}")
	private String trackingHeadFailConsumerId;
	@Value("${AccountManagementService.trackingHeadFailTimeToLive}")
	private String trackingHeadFailTimeToLive;

    @Before
    public void instructMock() throws SoapFault, DatatypeConfigurationException{

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

	updateAccountDetailsResponse1 = new UpdateAccountDetailsResponseType();
	updateAccountDetailsResponse1.setMessage("successful invocation");

    }


    @Test
    public void testUpdateAccountDetails() throws SoapFault {
    	
    	UpdateAccountDetailsRequestType updateAccountDetailsRequest = new UpdateAccountDetailsRequestType();
    	SearchCriteriaType searchCriteriaType = new SearchCriteriaType();
    	searchCriteriaType.setBan(102222849);
    	//searchCriteriaType.setMdn("1234");
    	updateAccountDetailsRequest.setInfo(searchCriteriaType);    	
    	
    	UpdateAccountDetailsResponseType updateAccountDetailsResponse = service.updateAccountDetails(updateAccountDetailsRequest, successHeader);
    		Assert.assertEquals(updateAccountDetailsResponse1.getMessage(),updateAccountDetailsResponse.getMessage());
    		
        }
    
}
