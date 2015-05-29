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
import com.sprint.integration.eai.services.loggingmanagementservice.v1.loggingmanagementservice.SoapFault;
import com.sprint.integration.interfaces.queryloginattemptstatus.v1.queryloginattemptstatus.QueryLoginAttemptStatusRequestType;
import com.sprint.integration.interfaces.queryloginattemptstatus.v1.queryloginattemptstatus.QueryLoginAttemptStatusResponseType;
import com.sprint.integration.interfaces.submitloginattempt.v1.submitloginattempt.SubmitLoginAttemptRequestType;
import com.sprint.integration.interfaces.submitloginattempt.v1.submitloginattempt.SubmitLoginAttemptResponseType;
/**
 * Created by esvwxal on 21/01/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:testconfig.xml"})
public class LoggingManagementProxyServiceTest {
    private Logger logger = LoggerFactory.getLogger(LoggingManagementProxyServiceTest.class);
    

    @Autowired
    private LoggingManagementProxyService service;

  /*  @Autowired
    private LoggingManagementServicePortType mockPort;*/
    
    
    private String resultMdn = "3179566118"; 


    WsMessageHeaderType successHead = new WsMessageHeaderType();
	Holder<WsMessageHeaderType> successHeader = new Holder<WsMessageHeaderType>(
			successHead);
	
    WsMessageHeaderType failHead = new WsMessageHeaderType();
	Holder<WsMessageHeaderType> failureHeader = new Holder<WsMessageHeaderType>(
			failHead);

	
	
	
	@Value("${LoggingManagementService.trackingHeadAppId}")
	private String trackingHeadAppId;
	@Value("${LoggingManagementService.trackingHeadAppUsrId}")
	private String trackingHeadAppUsrId;
	@Value("${LoggingManagementService.trackingHeadConsumerId}")
	private String trackingHeadConsumerId;
	@Value("${LoggingManagementService.trackingHeadTimeToLive}")
	private String trackingHeadTimeToLive;
	@Value("${LoggingManagementService.trackingHeadMessageId}")
	private String trackingHeadMessageId;
	@Value("${LoggingManagementService.trackingHeadConversationId}")
	private String trackingHeadConversationId;
	@Value("${LoggingManagementService.trackingHeadFailAppId}")
	private String trackingHeadFailAppId;
	@Value("${LoggingManagementService.trackingHeadFailAppUsrId}")
	private String trackingHeadFailAppUsrId;
	@Value("${LoggingManagementService.trackingHeadFailConsumerId}")
	private String trackingHeadFailConsumerId;
	@Value("${LoggingManagementService.trackingHeadFailTimeToLive}")
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
	trackingHead.setMessageDateTimeStamp(DatatypeFactory.newInstance()
			.newXMLGregorianCalendar(c));

	successHead = new WsMessageHeaderType();
	successHead.setTrackingMessageHeader(trackingHead);
	successHeader = new Holder<WsMessageHeaderType>(successHead);
	
		
	TrackingMessageHeaderType trackingHeadFail = new TrackingMessageHeaderType();
	trackingHeadFail.setApplicationId(trackingHeadAppId);
	trackingHeadFail.setApplicationUserId(trackingHeadAppUsrId);
	trackingHeadFail.setConsumerId(trackingHeadConsumerId);
	trackingHeadFail.setTimeToLive(new BigInteger(trackingHeadTimeToLive));
	trackingHeadFail.setMessageId(trackingHeadMessageId);
	trackingHeadFail.setConversationId(trackingHeadConversationId);
	trackingHeadFail.setMessageDateTimeStamp(DatatypeFactory.newInstance()
			.newXMLGregorianCalendar(c));


	failHead = new WsMessageHeaderType();
	failHead.setTrackingMessageHeader(trackingHeadFail);
	failureHeader = new Holder<WsMessageHeaderType>(failHead);

	/*//Below code is for mockito test
       
	QueryLoginAttemptStatusResponseType queryLoginAttemptStatusResponse = new QueryLoginAttemptStatusResponseType();
	queryLoginAttemptStatusResponse.setMdn("12324");
	queryLoginAttemptStatusResponse.setNumberOfFailedAttemps(new BigInteger("30"));
	
	
	when(mockPort.queryLoginAttemptStatus(any(QueryLoginAttemptStatusRequestType.class), eq(successHeader))).thenReturn(queryLoginAttemptStatusResponse);
	
	ErrorDetailsType details = new ErrorDetailsType();	
	when(
			mockPort.queryLoginAttemptStatus(
					any(QueryLoginAttemptStatusRequestType.class),
					eq(failureHeader))).thenThrow(new SoapFault("test fail", details));

	
	SubmitLoginAttemptResponseType submitLoginAttemptResponse = new SubmitLoginAttemptResponseType();
	submitLoginAttemptResponse.setMdn("1234");
	when(mockPort.submitLoginAttempt(any(SubmitLoginAttemptRequestType.class), eq(successHeader))).thenReturn(submitLoginAttemptResponse);
	
	when(
			mockPort.submitLoginAttempt(
					any(SubmitLoginAttemptRequestType.class),
					eq(failureHeader))).thenThrow(new SoapFault("test fail", details));
*/	
    }

    /*
     * Below code is to test the actual webservice.
     */
    @Test
    public void testQueryLoginAttemptStatus() throws SoapFault {
    	
			QueryLoginAttemptStatusRequestType queryLoginAttemptStatusRequest = new QueryLoginAttemptStatusRequestType();
			queryLoginAttemptStatusRequest.setMdn("3179566118");
            QueryLoginAttemptStatusResponseType queryLoginAttemptStatusResponse = service.queryLoginAttemptStatus(queryLoginAttemptStatusRequest, successHeader);
            Assert.assertEquals("mdn are not eqaul", resultMdn, queryLoginAttemptStatusResponse.getMdn());
            
        }
    
    @Test
    public void testSubmitLoginAttempt() throws SoapFault{
		
			SubmitLoginAttemptRequestType submitLoginAttemptRequest = new SubmitLoginAttemptRequestType();
			submitLoginAttemptRequest.setMdn("3179566118");
			BigInteger numberOfFailedAttempts = new BigInteger("2");
			submitLoginAttemptRequest.setNumberOfFailedAttempts(numberOfFailedAttempts);
			SubmitLoginAttemptResponseType submitLoginAttemptResponse = service.submitLoginAttempt(submitLoginAttemptRequest, successHeader);
			Assert.assertEquals("mdn are not eqaul", resultMdn, submitLoginAttemptResponse.getMdn());
			
	
	}
    
}
