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

import com.sprint.integration.common.errordetailsv2.ErrorDetailsType;
import com.sprint.integration.common.header.wsmessageheader.v2.TrackingMessageHeaderType;
import com.sprint.integration.common.header.wsmessageheader.v2.WsMessageHeaderType;
import com.sprint.integration.eai.services.queryresourceinfoservice.v1.queryresourceinfoservice.QueryResourceInfoPortType;
import com.sprint.integration.eai.services.queryresourceinfoservice.v1.queryresourceinfoservice.SoapFaultv2;
import com.sprint.integration.interfaces.queryavailablenpacsa.v1.queryavailablenpacsa.NpaCsaInfoListType;
import com.sprint.integration.interfaces.queryavailablenpacsa.v1.queryavailablenpacsa.NpaCsaInfoType;
import com.sprint.integration.interfaces.queryavailablenpacsa.v1.queryavailablenpacsa.NpaCsaQueryInfoType;
import com.sprint.integration.interfaces.queryavailablenpacsa.v1.queryavailablenpacsa.QueryAvailableNpaCsaResponseType;
import com.sprint.integration.interfaces.queryavailablenpacsa.v1.queryavailablenpacsa.QueryAvailableNpaCsaType;

/**
 * @author ebabadd
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class QueryResourceInfoProxyServiceTest {
    private Logger logger = LoggerFactory.getLogger(QueryResourceInfoProxyServiceTest.class);
    @Autowired
    private QueryResourceInfoProxyService service;
    @Autowired
    private QueryResourceInfoPortType mockPort;
    @Autowired
    QueryAvailableNpaCsaResponseType response;
    
    WsMessageHeaderType successHead = new WsMessageHeaderType();
	Holder<WsMessageHeaderType> successHeader = new Holder<WsMessageHeaderType>(
			successHead);
	
	WsMessageHeaderType failHead = new WsMessageHeaderType();
	Holder<WsMessageHeaderType> failureHeader = new Holder<WsMessageHeaderType>(
			failHead);
	
	@Value("${QueryResourceInfoService.trackingHeadAppId}")
	private String trackingHeadAppId;
	@Value("${QueryResourceInfoService.trackingHeadAppUsrId}")
	private String trackingHeadAppUsrId;
	@Value("${QueryResourceInfoService.trackingHeadConsumerId}")
	private String trackingHeadConsumerId;
	@Value("${QueryResourceInfoService.trackingHeadTimeToLive}")
	private String trackingHeadTimeToLive;
	@Value("${QueryResourceInfoService.trackingHeadMessageId}")
	private String trackingHeadMessageId;
	@Value("${QueryResourceInfoService.trackingHeadConversationId}")
	private String trackingHeadConversationId;
	@Value("${QueryResourceInfoService.trackingHeadFailAppId}")
	private String trackingHeadFailAppId;
	@Value("${QueryResourceInfoService.trackingHeadFailAppUsrId}")
	private String trackingHeadFailAppUsrId;
	@Value("${QueryResourceInfoService.trackingHeadFailConsumerId}")
	private String trackingHeadFailConsumerId;
	@Value("${QueryResourceInfoService.trackingHeadFailTimeToLive}")
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
		ErrorDetailsType details = new ErrorDetailsType();
        
		response= new QueryAvailableNpaCsaResponseType();
		
		NpaCsaInfoListType npaCsaList=new NpaCsaInfoListType();
		NpaCsaInfoType npaCsa=new NpaCsaInfoType();
		npaCsa.setNpa("npaValue");

		npaCsaList.getNpaCsaInfo().add(npaCsa);
		response.setNpaCsaInfoList(npaCsaList);
           
    }

    @Test
    public void testQueryAvailableNpaCsa() throws SoapFaultv2 {
    	QueryAvailableNpaCsaType request=new QueryAvailableNpaCsaType();
    	NpaCsaQueryInfoType npaCsaQueryInfo=new NpaCsaQueryInfoType();
    	
    	npaCsaQueryInfo.setZipCode("12345");
    	request.setNpaCsaQueryInfo(npaCsaQueryInfo);
			
    	QueryAvailableNpaCsaResponseType response2 = service.queryAvailableNpaCsa(request, successHeader);
    	System.out.println("Serviceee"+service+"RESPonse2:"+response2);
       Assert.assertEquals("Responses are not same", response2.getNpaCsaInfoList().getNpaCsaInfo().get(0).getNpa().equals(response.getNpaCsaInfoList().getNpaCsaInfo().get(0).getNpa()));
        
    }
	
}
