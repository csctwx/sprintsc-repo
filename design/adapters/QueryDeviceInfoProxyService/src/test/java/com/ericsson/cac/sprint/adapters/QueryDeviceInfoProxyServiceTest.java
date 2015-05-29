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
import com.sprint.integration.eai.services.querydeviceinfoservice.v1.querydeviceinfoservice.Faultmessage2;
import com.sprint.integration.interfaces.querydevicecapabilities.v2.querydevicecapabilitiesv2.QueryDeviceCapabilitiesV2ResponseType;
import com.sprint.integration.interfaces.querydevicecapabilities.v2.querydevicecapabilitiesv2.QueryDeviceCapabilitiesV2Type;
import com.sprint.integration.interfaces.querydeviceinfo.v1.querydeviceinfo.EquipmentInfoType;
import com.sprint.integration.interfaces.querydeviceinfo.v1.querydeviceinfo.EquipmentSerialNumberTypeCodeType;
import com.sprint.integration.interfaces.querydeviceinfo.v1.querydeviceinfo.InfoType;
import com.sprint.integration.interfaces.querydeviceinfo.v1.querydeviceinfo.QueryDeviceInfoResponseType;
import com.sprint.integration.interfaces.querydeviceinfo.v1.querydeviceinfo.QueryDeviceInfoType;
import com.sprint.integration.interfaces.validatedevice.v9.validatedevicev9.DeviceInfoType;
import com.sprint.integration.interfaces.validatedevice.v9.validatedevicev9.ValidateDeviceV9RequestType;
import com.sprint.integration.interfaces.validatedevice.v9.validatedevicev9.ValidateDeviceV9ResponseType;



/**
 * Created by esvwxal on 30/01/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class QueryDeviceInfoProxyServiceTest {
	
    private Logger logger = LoggerFactory.getLogger(QueryDeviceInfoProxyServiceTest.class);
    
    /*@Autowired
	private PropertiesConfiguration configuration;*/
    
    @Autowired
    private QueryDeviceInfoProxyService service;

/*    @Autowired
    private QueryDeviceInfoPortType mockPort;
    */
        
    private QueryDeviceInfoResponseType queryDeviceInfoResponse = null;
    private ValidateDeviceV9ResponseType validateDeviceV9Response = null;
    private QueryDeviceCapabilitiesV2ResponseType queryDeviceCapabilitiesV2Response = null;

    WsMessageHeaderType successHead = new WsMessageHeaderType();
	Holder<WsMessageHeaderType> successHeader = new Holder<WsMessageHeaderType>(
			successHead);
	
    WsMessageHeaderType failHead = new WsMessageHeaderType();
	Holder<WsMessageHeaderType> failureHeader = new Holder<WsMessageHeaderType>(
			failHead);

	@Value("${QueryDeviceInfoService.trackingHeadAppId}")
	private String trackingHeadAppId;
	@Value("${QueryDeviceInfoService.trackingHeadAppUsrId}")
	private String trackingHeadAppUsrId;
	@Value("${QueryDeviceInfoService.trackingHeadConsumerId}")
	private String trackingHeadConsumerId;
	@Value("${QueryDeviceInfoService.trackingHeadTimeToLive}")
	private String trackingHeadTimeToLive;
	@Value("${QueryDeviceInfoService.trackingHeadMessageId}")
	private String trackingHeadMessageId;
	@Value("${QueryDeviceInfoService.trackingHeadConversationId}")
	private String trackingHeadConversationId;
	@Value("${QueryDeviceInfoService.trackingHeadFailAppId}")
	private String trackingHeadFailAppId;
	@Value("${QueryDeviceInfoService.trackingHeadFailAppUsrId}")
	private String trackingHeadFailAppUsrId;
	@Value("${QueryDeviceInfoService.trackingHeadFailConsumerId}")
	private String trackingHeadFailConsumerId;
	@Value("${QueryDeviceInfoService.trackingHeadFailTimeToLive}")
	private String trackingHeadFailTimeToLive;
	
    @Before
    public void instructMock() throws Faultmessage2, DatatypeConfigurationException{

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
	
	

	queryDeviceInfoResponse = new QueryDeviceInfoResponseType();
	EquipmentInfoType equipmentInfo = new EquipmentInfoType();
	equipmentInfo.setEquipmentId("256691432408405652");
	equipmentInfo.setItemId("SPHL710ASB");
	equipmentInfo.setSerialType(EquipmentSerialNumberTypeCodeType.E);
	equipmentInfo.setInputSerialType(EquipmentSerialNumberTypeCodeType.E);
	queryDeviceInfoResponse.setEquipmentInfo(equipmentInfo);
	/*when(mockPort.queryDeviceInfo(any(QueryDeviceInfoType.class), eq(successHeader))).thenReturn(queryDeviceInfoResponse);
	when(mockPort.queryDeviceInfo(any(QueryDeviceInfoType.class),eq(failureHeader))).thenThrow(new Faultmessage2("test fail", details));*/
	
	
	validateDeviceV9Response = new ValidateDeviceV9ResponseType();
	validateDeviceV9Response.setDeviceSerialNumber("56789");
/*	when(mockPort.validateDeviceV9(any(ValidateDeviceV9RequestType.class), eq(successHeader))).thenReturn(validateDeviceV9Response);
	when(mockPort.validateDeviceV9(any(ValidateDeviceV9RequestType.class),eq(failureHeader))).thenThrow(new Faultmessage2("test fail", details));*/
	
	queryDeviceCapabilitiesV2Response = new QueryDeviceCapabilitiesV2ResponseType();
	queryDeviceCapabilitiesV2Response.setAreMore(true);
	/*when(mockPort.queryDeviceCapabilitiesV2(any(QueryDeviceCapabilitiesV2Type.class), eq(successHeader))).thenReturn(queryDeviceCapabilitiesV2Response);
	when(mockPort.queryDeviceCapabilitiesV2(any(QueryDeviceCapabilitiesV2Type.class),eq(failureHeader))).thenThrow(new Faultmessage2("test fail", details));*/
	
	
 
	}


    @Test
    public void testQueryDeviceInfo() throws Faultmessage2 {
    	
    	QueryDeviceInfoType queryDeviceInfoRequest = new QueryDeviceInfoType();
    	InfoType infoType = new InfoType();
    	infoType.setEquipmentId("256691432408405652");
    	queryDeviceInfoRequest.setInfo(infoType);
    	
    	   	
    	QueryDeviceInfoResponseType queryDeviceInfoResponse2 = service.queryDeviceInfo(queryDeviceInfoRequest, successHeader);
    	//Assert.assertEquals("Responses are not same", (queryDeviceInfoResponse2.getEquipmentInfo().getEquipmentId().equals(queryDeviceInfoResponse.getEquipmentInfo().getEquipmentId())));
    	//assert queryDeviceInfoResponse2.getEquipmentInfo().getEquipmentId().equals(queryDeviceInfoResponse.getEquipmentInfo().getEquipmentId()) && queryDeviceInfoResponse2.getEquipmentInfo().getItemId().equals(queryDeviceInfoResponse.getEquipmentInfo().getItemId()) : "Response's Equipment ID are not same";
    	Assert.assertEquals("Response's Equipment ID are not same", queryDeviceInfoResponse2.getEquipmentInfo().getItemId(), queryDeviceInfoResponse.getEquipmentInfo().getItemId());
    }
    
    @Test
    public void testValidateDeviceV9() throws Faultmessage2 {
    	
    	ValidateDeviceV9RequestType validateDeviceV9Request = new ValidateDeviceV9RequestType();
    	DeviceInfoType deviceInfo = new DeviceInfoType();
    	deviceInfo.setIccId("1234");
    	validateDeviceV9Request.setDeviceInfo(deviceInfo);
    	
    	ValidateDeviceV9ResponseType validateDeviceV9Response2 = service.validateDeviceV9(validateDeviceV9Request, successHeader);
    	Assert.assertEquals("Responses are not same", validateDeviceV9Response.getDeviceSerialNumber(), validateDeviceV9Response2.getDeviceSerialNumber());
    }
    
    @Test
    public void testQueryDeviceCapabilitiesV2() throws Faultmessage2 {
    	
    	QueryDeviceCapabilitiesV2Type queryDeviceCapabilitiesV2Request = new QueryDeviceCapabilitiesV2Type();
    	com.sprint.integration.interfaces.querydevicecapabilities.v2.querydevicecapabilitiesv2.DeviceInfoType deviceInfo = new com.sprint.integration.interfaces.querydevicecapabilities.v2.querydevicecapabilitiesv2.DeviceInfoType();
    	deviceInfo.setMacId("1234");
    	queryDeviceCapabilitiesV2Request.setPageNumber("3");
    	queryDeviceCapabilitiesV2Request.setDeviceInfo(deviceInfo);
    	
    	QueryDeviceCapabilitiesV2ResponseType queryDeviceCapabilitiesV2Response2 = service.queryDeviceCapabilitiesV2(queryDeviceCapabilitiesV2Request, successHeader);
    	Assert.assertEquals("Responses are not same", queryDeviceCapabilitiesV2Response2.isAreMore(), queryDeviceCapabilitiesV2Response.isAreMore());
    	
    }
}
