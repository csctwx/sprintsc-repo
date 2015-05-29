
package com.ericsson.cac.sprint.adapters;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.math.BigInteger;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
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

import com.sprint.integration._public.interfaces.queryorderfulfillmentservice.v1.Faultmessage2;
import com.sprint.integration.common.errordetailsv2.ErrorDetailsType;
import com.sprint.integration.common.header.wsmessageheader.v2.TrackingMessageHeaderType;
import com.sprint.integration.common.header.wsmessageheader.v2.WsMessageHeaderType;
import com.sprint.integration.interfaces.queryaccountorders.v1.queryaccountorders.OrderListType;
import com.sprint.integration.interfaces.queryaccountorders.v1.queryaccountorders.PaginationInfoResponseType;
import com.sprint.integration.interfaces.queryaccountorders.v1.queryaccountorders.QueryAccountOrdersResponseType;
import com.sprint.integration.interfaces.queryaccountorders.v1.queryaccountorders.QueryAccountOrdersType;
import com.sprint.integration.interfaces.queryaccountorders.v1.queryaccountorders.SearchCriteriaType;
import com.sprint.integration.interfaces.queryorderinfo.v1.queryorderinfo.InfoType;
import com.sprint.integration.interfaces.queryorderinfo.v1.queryorderinfo.OrderInfoType;
import com.sprint.integration.interfaces.queryorderinfo.v1.queryorderinfo.QueryOrderInfoResponseType;
import com.sprint.integration.interfaces.queryorderinfo.v1.queryorderinfo.QueryOrderInfoType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class QueryOrderFulfillmentProxyServiceTest{
    private Logger logger = LoggerFactory.getLogger(QueryOrderFulfillmentProxyServiceTest.class);
    
    @Autowired
    private QueryOrderFulfillmentProxyService service;

    @Autowired
    private QueryOrderFulfillmentProxyService mockPort;
    
    private QueryOrderInfoResponseType queryOrderInfoResponse = null;
    private QueryAccountOrdersResponseType queryAccountOrdersResponse = null;


    WsMessageHeaderType successHead = new WsMessageHeaderType();
	Holder<WsMessageHeaderType> successHeader = new Holder<WsMessageHeaderType>(
			successHead);
	
    WsMessageHeaderType failHead = new WsMessageHeaderType();
	Holder<WsMessageHeaderType> failureHeader = new Holder<WsMessageHeaderType>(
			failHead);
	@Value("${QueryOrderFulfillmentService.trackingHeadAppId}")
	private String trackingHeadAppId;
	@Value("${QueryOrderFulfillmentService.trackingHeadAppUsrId}")
	private String trackingHeadAppUsrId;
	@Value("${QueryOrderFulfillmentService.trackingHeadConsumerId}")
	private String trackingHeadConsumerId;
	@Value("${QueryOrderFulfillmentService.trackingHeadTimeToLive}")
	private String trackingHeadTimeToLive;
	@Value("${QueryOrderFulfillmentService.trackingHeadMessageId}")
	private String trackingHeadMessageId;
	@Value("${QueryOrderFulfillmentService.trackingHeadConversationId}")
	private String trackingHeadConversationId;
	@Value("${QueryOrderFulfillmentService.trackingHeadFailAppId}")
	private String trackingHeadFailAppId;
	@Value("${QueryOrderFulfillmentService.trackingHeadFailAppUsrId}")
	private String trackingHeadFailAppUsrId;
	@Value("${QueryOrderFulfillmentService.trackingHeadFailConsumerId}")
	private String trackingHeadFailConsumerId;
	@Value("${QueryOrderFulfillmentService.trackingHeadFailTimeToLive}")
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

	
	queryOrderInfoResponse = new QueryOrderInfoResponseType(); 
	OrderInfoType orderInfoType = new OrderInfoType();
	orderInfoType.setOrderKey("123");
	XMLGregorianCalendar date = null;
	orderInfoType.setOrderDate(date);
	orderInfoType.setOrderStatus("1");
	queryOrderInfoResponse.setOrderInfo(orderInfoType);
	
	queryAccountOrdersResponse = new QueryAccountOrdersResponseType();
	OrderListType orderListType = new OrderListType();
	PaginationInfoResponseType paginationInfoResponseType = new PaginationInfoResponseType();
	BigInteger bigInteger1 = new BigInteger ("123456789");
	paginationInfoResponseType.setPageNumber(bigInteger1);
	orderListType.setPaginationInfo(paginationInfoResponseType);
	queryAccountOrdersResponse.setOrderList(orderListType);
	
	when(mockPort.queryOrderInfoResponseType(any(QueryOrderInfoType.class), eq(successHeader))).thenReturn(queryOrderInfoResponse);
	when(mockPort.queryAccountOrdersResponseType(any(QueryAccountOrdersType.class), eq(successHeader))).thenReturn(queryAccountOrdersResponse);
	
	ErrorDetailsType details = new ErrorDetailsType();
	
	when(
			mockPort.queryOrderInfoResponseType(
					any(QueryOrderInfoType.class),
					eq(failureHeader))).thenThrow(new Faultmessage2("test fail", details));
	
	when(
				mockPort.queryAccountOrdersResponseType(
						any(QueryAccountOrdersType.class),
						eq(failureHeader))).thenThrow(new Faultmessage2("test fail", details));
    }
    



    @Test
    public void testqueryOrderInfoResponseType() throws Faultmessage2 {
    	
    	QueryOrderInfoType queryOrderInfoTypeReq = new QueryOrderInfoType();
    	InfoType infoType = new InfoType();
    	infoType.setVendorOrderKey("123");
    	queryOrderInfoTypeReq.setInfo(infoType);
 	
    	
    	QueryOrderInfoResponseType queryOrderInfoResponse2 = service.queryOrderInfoResponseType(queryOrderInfoTypeReq, successHeader);
    	Assert.assertEquals("Message not same", queryOrderInfoResponse2.getOrderInfo().getOrderKey().equals(queryOrderInfoResponse.getOrderInfo().getOrderKey()));
    				          
        }
    
    @Test
    public void testqueryReferenceSecurityInfo() throws Faultmessage2 {
    	
    	QueryAccountOrdersType queryAccountOrdersReq = new QueryAccountOrdersType();
    	SearchCriteriaType searchCriteriaType = new SearchCriteriaType();
    	searchCriteriaType.setBan(123);
 	
    	
    	QueryAccountOrdersResponseType queryAccountOrdersResponse2 = service.queryAccountOrdersResponseType(queryAccountOrdersReq, successHeader);
    	Assert.assertEquals("Message not same", queryAccountOrdersResponse2.getOrderList().getPaginationInfo().getPageNumber().equals(queryAccountOrdersResponse2.getOrderList().getPaginationInfo().getPageNumber()));
    				          
        }
    
    
}