
package com.ericsson.cac.sprint.adapters;

import java.math.BigDecimal;
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
import com.sprint.integration.eai.services.vouchermanagementservice.v1.vouchermanagementservice.SoapFault;
import com.sprint.integration.interfaces.managevoucher.v1.managevoucher.ManageVoucherRequestType;
import com.sprint.integration.interfaces.managevoucher.v1.managevoucher.ManageVoucherResponseType;
import com.sprint.integration.interfaces.managevoucher.v1.managevoucher.VoucherIdentifierInfoType;
import com.sprint.integration.interfaces.managevoucher.v1.managevoucher.VoucherInfoType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class VoucherManagementProxyServiceTest{
    private Logger logger = LoggerFactory.getLogger(VoucherManagementProxyServiceTest.class);
    
    @Autowired
    private VoucherManagementProxyService service;

    @Autowired
    private VoucherManagementProxyService mockPort;
    
    private ManageVoucherResponseType manageVoucherResponse = null;


    WsMessageHeaderType successHead = new WsMessageHeaderType();
	Holder<WsMessageHeaderType> successHeader = new Holder<WsMessageHeaderType>(
			successHead);
	
    WsMessageHeaderType failHead = new WsMessageHeaderType();
	Holder<WsMessageHeaderType> failureHeader = new Holder<WsMessageHeaderType>(
			failHead);
	
	@Value("${VoucherManagementService.trackingHeadAppId}")
	private String trackingHeadAppId;
	@Value("${VoucherManagementService.trackingHeadAppUsrId}")
	private String trackingHeadAppUsrId;
	@Value("${VoucherManagementService.trackingHeadConsumerId}")
	private String trackingHeadConsumerId;
	@Value("${VoucherManagementService.trackingHeadTimeToLive}")
	private String trackingHeadTimeToLive;
	@Value("${VoucherManagementService.trackingHeadMessageId}")
	private String trackingHeadMessageId;
	@Value("${VoucherManagementService.trackingHeadConversationId}")
	private String trackingHeadConversationId;
	@Value("${VoucherManagementService.trackingHeadFailAppId}")
	private String trackingHeadFailAppId;
	@Value("${VoucherManagementService.trackingHeadFailAppUsrId}")
	private String trackingHeadFailAppUsrId;
	@Value("${VoucherManagementService.trackingHeadFailConsumerId}")
	private String trackingHeadFailConsumerId;
	@Value("${VoucherManagementService.trackingHeadFailTimeToLive}")
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
	
	manageVoucherResponse = new ManageVoucherResponseType();
	VoucherInfoType voucherInfo = new VoucherInfoType();
	BigDecimal value = new BigDecimal(1);
	voucherInfo.setSerialNumber("123");
	voucherInfo.setPinNumber("123");
	voucherInfo.setVoucherStatus("OK");
	voucherInfo.setVoucherValue(value);
	voucherInfo.setRetailerId("123");
	manageVoucherResponse.setVoucherInfo(voucherInfo);

    }

    @Test
    public void testManageVoucher() throws SoapFault {
    	
    	ManageVoucherRequestType manageVoucherRequestType = new ManageVoucherRequestType();
    	VoucherIdentifierInfoType voucherIdentifierInfo = new VoucherIdentifierInfoType();
    	voucherIdentifierInfo.setPinNumber("123");
    	manageVoucherRequestType.setVoucherIdentifierInfo(voucherIdentifierInfo);
    	manageVoucherRequestType.setActionType("OK");
    	
    	ManageVoucherResponseType manageVoucherResponse2 = service.manageVoucherResp(manageVoucherRequestType, successHeader);
    	Assert.assertEquals("Message not same", manageVoucherResponse2.getVoucherInfo().getPinNumber().equals(manageVoucherResponse.getVoucherInfo().getPinNumber()));
    				          
        }

}