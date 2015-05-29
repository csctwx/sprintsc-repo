
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
import com.sprint.integration.eai.services.securitymanagementservice.v1.securitymanagementservice.SoapFault;
import com.sprint.integration.interfaces.resendsecurityinfo.v1.resendsecurityinfo.NotificationMethodValueInfo;
import com.sprint.integration.interfaces.resendsecurityinfo.v1.resendsecurityinfo.NotificationMethodnfo;
import com.sprint.integration.interfaces.resendsecurityinfo.v1.resendsecurityinfo.ResendSecurityInfoResponseType;
import com.sprint.integration.interfaces.resendsecurityinfo.v1.resendsecurityinfo.ResendSecurityInfoType;
import com.sprint.integration.interfaces.resendsecurityinfo.v1.resendsecurityinfo.SearchCriteriaType;
import com.sprint.integration.interfaces.updatevoicemailpasscode.v1.updatevoicemailpasscode.UpdateVoiceMailPasscodeResponseType;
import com.sprint.integration.interfaces.updatevoicemailpasscode.v1.updatevoicemailpasscode.UpdateVoiceMailPasscodeType;
import com.sprint.integration.interfaces.validateaccountpin.v1.validateaccountpin.InfoType;
import com.sprint.integration.interfaces.validateaccountpin.v1.validateaccountpin.ValidationErrorInfoType;
import com.sprint.integration.interfaces.validateaccountpin.v1.validateaccountpin.ValidationErrorListType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)

public class SecurityManagementProxyServiceTest{
    private Logger logger = LoggerFactory.getLogger(SecurityManagementProxyServiceTest.class);
    
/*    @Autowired
	private PropertiesConfiguration configuration;*/
    
    @Autowired
    private SecurityManagementProxyService service;

/*    @Autowired
    private SecurityManagementProxyService mockPort;*/
    
	ResendSecurityInfoResponseType resendSecurityInfoResponse = new ResendSecurityInfoResponseType();
	ValidationErrorListType validationErrorListTypeResponse = new ValidationErrorListType();
	UpdateVoiceMailPasscodeResponseType updateVoiceMailPasscodeResponse=null;


    WsMessageHeaderType successHead = new WsMessageHeaderType();
	Holder<WsMessageHeaderType> successHeader = new Holder<WsMessageHeaderType>(
			successHead);
	
    WsMessageHeaderType failHead = new WsMessageHeaderType();
	Holder<WsMessageHeaderType> failureHeader = new Holder<WsMessageHeaderType>(
			failHead);

	@Value("${SecurityManagementService.trackingHeadAppId}")
	private String trackingHeadAppId;
	@Value("${SecurityManagementService.trackingHeadAppUsrId}")
	private String trackingHeadAppUsrId;
	@Value("${SecurityManagementService.trackingHeadConsumerId}")
	private String trackingHeadConsumerId;
	@Value("${SecurityManagementService.trackingHeadTimeToLive}")
	private String trackingHeadTimeToLive;
	@Value("${SecurityManagementService.trackingHeadMessageId}")
	private String trackingHeadMessageId;
	@Value("${SecurityManagementService.trackingHeadConversationId}")
	private String trackingHeadConversationId;
	@Value("${SecurityManagementService.trackingHeadFailAppId}")
	private String trackingHeadFailAppId;
	@Value("${SecurityManagementService.trackingHeadFailAppUsrId}")
	private String trackingHeadFailAppUsrId;
	@Value("${SecurityManagementService.trackingHeadFailConsumerId}")
	private String trackingHeadFailConsumerId;
	@Value("${SecurityManagementService.trackingHeadFailTimeToLive}")
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

       
	NotificationMethodnfo notificationMethodnfo = new NotificationMethodnfo();
	notificationMethodnfo.setNotificationMethod("S");
	NotificationMethodValueInfo notificationMethodValueInfo = new NotificationMethodValueInfo();
	notificationMethodValueInfo.setOther("3179566118");
	notificationMethodnfo.setNotificationMethodValueInfo(notificationMethodValueInfo);
	resendSecurityInfoResponse.setPrimaryMethodInfo(notificationMethodnfo);
	/*when(mockPort.resendSecurityInfo(any(ResendSecurityInfoType.class), eq(successHeader))).thenReturn(resendSecurityInfoResponse);
	
	ErrorDetailsType details = new ErrorDetailsType();
	
	when(
			mockPort.resendSecurityInfo(
					any(ResendSecurityInfoType.class),
					eq(failureHeader))).thenThrow(new SoapFault("test fail", details));
	*/
	
	
	ValidationErrorInfoType validationErrorInfoTypeResponse = new ValidationErrorInfoType();
	validationErrorInfoTypeResponse.setResultCode("0002");
	validationErrorInfoTypeResponse.setResultDescription("pass");
	validationErrorListTypeResponse.getValidationResultsInfo().add(validationErrorInfoTypeResponse);
	
	/*when(mockPort.validateAccountPin(any(InfoType.class),any(String.class), eq(successHeader))).thenReturn(validationErrorListTypeResponse);
	
	ErrorDetailsType details2 = new ErrorDetailsType();
	
	String pin="1234";
	when(
			mockPort.validateAccountPin(
					any(InfoType.class),any(String.class),
					eq(failureHeader))).thenThrow(new SoapFault("test fail", details2));
*/
    }


    @Test
    public void testresendSecurityInfo() throws SoapFault {
    	
    	ResendSecurityInfoType resendSecurityInfoRequest = new ResendSecurityInfoType();
    	SearchCriteriaType searchCriteriaType = new SearchCriteriaType();
    	//searchCriteriaType.setBan(30);
    	//searchCriteriaType.setMdn("3179566118");
	searchCriteriaType.setSubscriberId("80068822121");
    	resendSecurityInfoRequest.setAccountInfo(searchCriteriaType);    	
    	
    	ResendSecurityInfoResponseType resendSecurityInfoResponse2 = service.resendSecurityInfo(resendSecurityInfoRequest, successHeader);
           // String resultMessage = "abcd";
    		//Assert.assertEquals("Message not same", resendSecurityInfoResponse2.getPrimaryMethodInfo().getNotificationMethod().equals(resendSecurityInfoResponse.getPrimaryMethodInfo().getNotificationMethod()));
    	//assert resendSecurityInfoResponse2.getPrimaryMethodInfo().getNotificationMethod().equals(resendSecurityInfoResponse.getPrimaryMethodInfo().getNotificationMethod()) : "Respnses not same" ;
    	Assert.assertEquals("Respnses not same", "S", resendSecurityInfoResponse2.getPrimaryMethodInfo().getNotificationMethod());
            
        }
    
    @Test
    public void testvalidateAccountPin() throws SoapFault {
    	
    	InfoType infoTypeRequest = new InfoType();
    	infoTypeRequest.setBan("100565543");
    	infoTypeRequest.setSsn("xyz");
    	
    	String pin = "1335";
    	ValidationErrorListType validationErrorResponse = service.validateAccountPin(infoTypeRequest,pin,successHeader);   
    	//Assert.assertEquals("Message not same", validationErrorResponse.getValidationResultsInfo().get(0).getResultCode().equals(validationErrorListTypeResponse.getValidationResultsInfo().get(0).getResultCode()));
    	Assert.assertEquals("Message not same", validationErrorResponse.getValidationResultsInfo().get(0).getResultCode(), validationErrorListTypeResponse.getValidationResultsInfo().get(0).getResultCode());
        }
    
    @Test
    public void testupdateVoiceMailPasscode() throws SoapFault {
    	
    	UpdateVoiceMailPasscodeType updateVoiceMailPasscodeRequest = new UpdateVoiceMailPasscodeType();
    	updateVoiceMailPasscodeRequest.setMdn("3179566118");
    	
    	UpdateVoiceMailPasscodeResponseType updateVoiceMailPasscodeResponse = service.updateVoiceMailPasscode(updateVoiceMailPasscodeRequest,successHeader);   
    	Assert.assertEquals("A", updateVoiceMailPasscodeResponse.getStatusInfo().getStatus());
        }
}
    
