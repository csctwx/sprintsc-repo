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
import com.sprint.integration.interfaces.queryprogramminginstructions.v1.queryprogramminginstructions.InstructionInfoType;
import com.sprint.integration.interfaces.queryprogramminginstructions.v1.queryprogramminginstructions.InstructionListType;
import com.sprint.integration.interfaces.queryprogramminginstructions.v1.queryprogramminginstructions.QueryProgrammingInstructionsRequestType;
import com.sprint.integration.interfaces.queryprogramminginstructions.v1.queryprogramminginstructions.QueryProgrammingInstructionsResponseType;
import com.sprint.integration.interfaces.queryprovisioninginfoservice.v1.queryprovisioninginfoservice.SoapFault;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class QueryProvisioningInfoProxyServiceTest {
	private Logger logger = LoggerFactory
			.getLogger(QueryProvisioningInfoProxyServiceTest.class);

	@Autowired
	private QueryProvisioningInfoProxyService service;

	/*
	 * @Autowired private QueryProvisioningInfoProxyService mockPort;
	 */

	QueryProgrammingInstructionsResponseType queryProgrammingInstructionsResponse = new QueryProgrammingInstructionsResponseType();

	WsMessageHeaderType successHead = new WsMessageHeaderType();
	Holder<WsMessageHeaderType> successHeader = new Holder<WsMessageHeaderType>(
			successHead);

	WsMessageHeaderType failHead = new WsMessageHeaderType();
	Holder<WsMessageHeaderType> failureHeader = new Holder<WsMessageHeaderType>(
			failHead);
	
	@Value("${trackingHeadAppId}")
	private String trackingHeadAppId;
	@Value("${trackingHeadAppUsrId}")
	private String trackingHeadAppUsrId;
	@Value("${trackingHeadConsumerId}")
	private String trackingHeadConsumerId;
	@Value("${trackingHeadTimeToLive}")
	private String trackingHeadTimeToLive;
	@Value("${trackingHeadMessageId}")
	private String trackingHeadMessageId;
	@Value("${trackingHeadConversationId}")
	private String trackingHeadConversationId;
	@Value("${trackingHeadFailAppId}")
	private String trackingHeadFailAppId;
	@Value("${trackingHeadFailAppUsrId}")
	private String trackingHeadFailAppUsrId;
	@Value("${trackingHeadFailConsumerId}")
	private String trackingHeadFailConsumerId;
	@Value("${trackingHeadFailTimeToLive}")
	private String trackingHeadFailTimeToLive;

	@Before
	public void instructMock() throws SoapFault, DatatypeConfigurationException {

		TrackingMessageHeaderType trackingHead = new TrackingMessageHeaderType();
		trackingHead.setApplicationId(trackingHeadAppId);
		trackingHead.setApplicationUserId(trackingHeadAppUsrId);
		trackingHead.setMessageId(trackingHeadMessageId);
		trackingHead.setTimeToLive(new BigInteger(trackingHeadTimeToLive));
		GregorianCalendar c = new GregorianCalendar();
		trackingHead.setMessageDateTimeStamp(DatatypeFactory.newInstance()
				.newXMLGregorianCalendar(c));

		successHead.setTrackingMessageHeader(trackingHead);

		TrackingMessageHeaderType trackingHeadFail = new TrackingMessageHeaderType();
		trackingHeadFail.setApplicationId(trackingHeadFailAppId);
		trackingHeadFail.setApplicationUserId(trackingHeadFailAppUsrId);
		trackingHeadFail.setConsumerId(trackingHeadFailConsumerId);
		trackingHeadFail.setTimeToLive(new BigInteger(trackingHeadFailTimeToLive));
		trackingHeadFail.setMessageDateTimeStamp(DatatypeFactory.newInstance()
				.newXMLGregorianCalendar(c));

		failHead.setTrackingMessageHeader(trackingHead);
		InstructionInfoType instructionInfoType = new InstructionInfoType();
		instructionInfoType.setSequenceNumber(1);
		InstructionListType instructionListType = new InstructionListType();
		instructionListType.getInstructionInfo().add(instructionInfoType);

		queryProgrammingInstructionsResponse.setItemId("REC4S16WHSB");
		queryProgrammingInstructionsResponse
				.setInstructionInfoList(instructionListType);

		/*
		 * when( mockPort.queryProgrammingInstructions(
		 * any(QueryProgrammingInstructionsRequestType.class),
		 * eq(successHeader))).thenReturn(
		 * queryProgrammingInstructionsResponse);
		 * 
		 * // when(mockPort.queryProgrammingInstructions(any(
		 * QueryProgrammingInstructionsRequestType.class), //
		 * eq(successHeader))).thenReturn(queryProgrammingInstructionsResponse);
		 * 
		 * ErrorDetailsType details = new ErrorDetailsType();
		 * 
		 * when( mockPort.queryProgrammingInstructions(
		 * any(QueryProgrammingInstructionsRequestType.class),
		 * eq(failureHeader))).thenThrow( new SoapFault("test fail", details));
		 */
	}

	@Test
	public void testqueryProgrammingInstructions() throws SoapFault {

		QueryProgrammingInstructionsRequestType queryProgrammingInstructionsRequest = new QueryProgrammingInstructionsRequestType();
		queryProgrammingInstructionsRequest.setItemId("REC4S16WHSB");
		queryProgrammingInstructionsRequest.setBrandCode("SPP");

		QueryProgrammingInstructionsResponseType queryProgrammingInstructionsResponse2 = service
				.queryProgrammingInstructions(
						queryProgrammingInstructionsRequest, successHeader);
		Assert.assertEquals("Item Id is not matching",queryProgrammingInstructionsResponse2.getItemId(),
				queryProgrammingInstructionsResponse.getItemId());

	}

}
