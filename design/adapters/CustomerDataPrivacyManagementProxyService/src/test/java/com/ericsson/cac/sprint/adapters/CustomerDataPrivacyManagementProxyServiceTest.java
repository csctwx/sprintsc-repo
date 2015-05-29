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
import com.sprint.integration.eai.services.customerdataprivacymanagementservice.v1.customerdataprivacymanagementservice.CustomerDataPrivacyManagementServicePortType;
import com.sprint.integration.eai.services.customerdataprivacymanagementservice.v1.customerdataprivacymanagementservice.SoapFault;
import com.sprint.integration.interfaces.queryuserpermission.v1.queryuserpermission.PermissionInfoType;
import com.sprint.integration.interfaces.queryuserpermission.v1.queryuserpermission.PermissionListType;
import com.sprint.integration.interfaces.queryuserpermission.v1.queryuserpermission.PermissionResultInfoType;
import com.sprint.integration.interfaces.queryuserpermission.v1.queryuserpermission.PermissionResultListType;
import com.sprint.integration.interfaces.queryuserpermission.v1.queryuserpermission.QueryUserPermissionResponseType;
import com.sprint.integration.interfaces.queryuserpermission.v1.queryuserpermission.QueryUserPermissionType;
import com.sprint.integration.interfaces.queryuserpermission.v1.queryuserpermission.UserInfoType;
import com.sprint.integration.interfaces.queryuserpreferencesinfo.v2.queryuserpreferencesinfov2.QueryUserPreferencesInfoV2ResponseType;
import com.sprint.integration.interfaces.queryuserpreferencesinfo.v2.queryuserpreferencesinfov2.QueryUserPreferencesInfoV2Type;
import com.sprint.integration.interfaces.queryuserpreferencesinfo.v2.queryuserpreferencesinfov2.UserIdentityInfoType;
import com.sprint.integration.interfaces.queryuserpreferencesinfo.v2.queryuserpreferencesinfov2.UserPreferenceInfoType;
import com.sprint.integration.interfaces.queryuserpreferencesinfo.v2.queryuserpreferencesinfov2.UserPreferenceListType;
import com.sprint.integration.interfaces.updateuserpermission.v1.updateuserpermission.UpdateUserPermissionResponseType;
import com.sprint.integration.interfaces.updateuserpermission.v1.updateuserpermission.UpdateUserPermissionType;

/**
 * Created by esvwxal on 21/01/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class CustomerDataPrivacyManagementProxyServiceTest {
	private Logger logger = LoggerFactory.getLogger(CustomerDataPrivacyManagementProxyServiceTest.class);

	@Autowired
	private CustomerDataPrivacyManagementProxyService service;

	@Autowired
	private CustomerDataPrivacyManagementServicePortType mockPort;

	WsMessageHeaderType successHead = new WsMessageHeaderType();
	Holder<WsMessageHeaderType> successHeader = new Holder<WsMessageHeaderType>(successHead);

	WsMessageHeaderType failHead = new WsMessageHeaderType();
	Holder<WsMessageHeaderType> failureHeader = new Holder<WsMessageHeaderType>(failHead);

	private QueryUserPermissionResponseType queryUserPermissionResponse = null;
	// private SubPermissionResultListType subPermissionResultList= null;
	private UpdateUserPermissionResponseType updateUserPermissionResponse = null;
	private PermissionResultListType permissionResultList = null;
	private QueryUserPreferencesInfoV2ResponseType queryUserPreferencesInfoV2Response = null;

	@Value("${CustomerDataPrivacyManagementService.trackingHeadAppId}")
	private String trackingHeadAppId;
	@Value("${CustomerDataPrivacyManagementService.trackingHeadAppUsrId}")
	private String trackingHeadAppUsrId;
	@Value("${CustomerDataPrivacyManagementService.trackingHeadConsumerId}")
	private String trackingHeadConsumerId;
	@Value("${CustomerDataPrivacyManagementService.trackingHeadTimeToLive}")
	private String trackingHeadTimeToLive;
	@Value("${CustomerDataPrivacyManagementService.trackingHeadMessageId}")
	private String trackingHeadMessageId;
	@Value("${CustomerDataPrivacyManagementService.trackingHeadConversationId}")
	private String trackingHeadConversationId;
	@Value("${CustomerDataPrivacyManagementService.trackingHeadFailAppId}")
	private String trackingHeadFailAppId;
	@Value("${CustomerDataPrivacyManagementService.trackingHeadFailAppUsrId}")
	private String trackingHeadFailAppUsrId;
	@Value("${CustomerDataPrivacyManagementService.trackingHeadFailConsumerId}")
	private String trackingHeadFailConsumerId;
	@Value("${CustomerDataPrivacyManagementService.trackingHeadFailTimeToLive}")
	private String trackingHeadFailTimeToLive;

	@Before
	public void instructMock() throws SoapFault, DatatypeConfigurationException {

		TrackingMessageHeaderType trackingHead = new TrackingMessageHeaderType();
		trackingHead.setApplicationId(trackingHeadAppId);
		trackingHead.setApplicationUserId(trackingHeadAppUsrId);
		trackingHead.setConsumerId(trackingHeadConsumerId);
		trackingHead.setTimeToLive(new BigInteger(trackingHeadTimeToLive));
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

		queryUserPermissionResponse = new QueryUserPermissionResponseType();

		PermissionResultInfoType permissionResultInfo1 = new PermissionResultInfoType();
		permissionResultInfo1.setDecision("Deny");
		permissionResultInfo1.setPermissionId("MobileActivity");
		permissionResultInfo1.setPermissionOwnerId("3179566118");

		permissionResultList = new PermissionResultListType();
		permissionResultList.getPermissionResult().add(permissionResultInfo1);

		queryUserPermissionResponse.setPermissionResultList(permissionResultList);

		/*
		 * when(mockPort.queryUserPermission(any(QueryUserPermissionType.class),
		 * eq(successHeader))).thenReturn(queryUserPermissionResponse);
		 * 
		 * ErrorDetailsType details = new ErrorDetailsType();
		 * 
		 * when( mockPort.queryUserPermission(
		 * any(QueryUserPermissionType.class), eq(failureHeader))).thenThrow(new
		 * SoapFault("test fail", details));
		 */

		updateUserPermissionResponse = new UpdateUserPermissionResponseType();
		updateUserPermissionResponse.setMessage("abcd");

		/*
		 * when(mockPort.updateUserPermission(any(UpdateUserPermissionType.class)
		 * , eq(successHeader))).thenReturn(updateUserPermissionResponse);
		 * 
		 * when( mockPort.updateUserPermission(
		 * any(UpdateUserPermissionType.class),
		 * eq(failureHeader))).thenThrow(new SoapFault("test fail", details));
		 * 
		 * queryUserPreferencesInfoV2Response = new
		 * QueryUserPreferencesInfoV2ResponseType();
		 * UserPreferenceInfoResponseType userPreferenceInfoResponse = new
		 * UserPreferenceInfoResponseType();
		 * userPreferenceInfoResponse.setContextName("abcde");
		 * userPreferenceInfoResponse.setPreferenceName("ijklm");
		 * 
		 * UserPreferenceListResponseType userPreferenceListResponse = new
		 * UserPreferenceListResponseType();
		 * userPreferenceListResponse.getPreferenceInfo
		 * ().add(userPreferenceInfoResponse);
		 * queryUserPreferencesInfoV2Response
		 * .setUserPreferenceList(userPreferenceListResponse);
		 * 
		 * when(mockPort.queryUserPreferencesInfoV2(any(
		 * QueryUserPreferencesInfoV2Type.class),
		 * eq(successHeader))).thenReturn(queryUserPreferencesInfoV2Response);
		 * when( mockPort.queryUserPreferencesInfoV2(
		 * any(QueryUserPreferencesInfoV2Type.class),
		 * eq(failureHeader))).thenThrow(new SoapFault("test fail", details));
		 */
	}

	@Test
	public void testQueryUserPermission() throws SoapFault {

		QueryUserPermissionType queryUserPermissionRequest = new QueryUserPermissionType();
		UserInfoType userInfo = new UserInfoType();
		userInfo.setUserId("3179566118");
		userInfo.setUserIdType("devicenumber");
		queryUserPermissionRequest.setUserInfo(userInfo);
		queryUserPermissionRequest.setPermissionType("READ");

		PermissionInfoType permissionInfo1 = new PermissionInfoType();
		permissionInfo1.setPermissionId("MobileActivity");
		permissionInfo1.setPermissionOwnerId("3179566118");
		permissionInfo1.setPermissionOwnerType("MDN");

		PermissionListType permissionList = new PermissionListType();
		permissionList.getPermissionInfo().add(permissionInfo1);

		queryUserPermissionRequest.setPermissionList(permissionList);

		QueryUserPermissionResponseType queryUserPermissionResponse2 = service.queryUserPermission(
				queryUserPermissionRequest, successHeader);
		Assert.assertEquals(queryUserPermissionResponse2.getPermissionResultList().getPermissionResult().get(0)
				.getPermissionId(), queryUserPermissionResponse.getPermissionResultList().getPermissionResult().get(0)
				.getPermissionId());

	}

	@Test
	public void testUpdateUserPermission() throws SoapFault, DatatypeConfigurationException {

		UpdateUserPermissionType updateUserPermissionRequest = new UpdateUserPermissionType();
		com.sprint.integration.interfaces.updateuserpermission.v1.updateuserpermission.PermissionInfoType update_permissionInfo1 = new com.sprint.integration.interfaces.updateuserpermission.v1.updateuserpermission.PermissionInfoType();
		update_permissionInfo1.setGrantorId("01234");
		update_permissionInfo1.setGrantorIdType("efgh");
		update_permissionInfo1.setGranteeId("1234");
		update_permissionInfo1.setGranteeIdType("jkl");
		update_permissionInfo1.setPermissionId("lmno");
		update_permissionInfo1.setPermissionType("mnop");
		update_permissionInfo1.setTargetId("5678");
		update_permissionInfo1.setTargetIdType("xyz");
		update_permissionInfo1.setTermsAndConditionsVersion("1.5.6");
		GregorianCalendar c = new GregorianCalendar();
		update_permissionInfo1.setGrantDate((DatatypeFactory.newInstance().newXMLGregorianCalendar(c)));

		// List<com.sprint.integration.interfaces.updateuserpermission.v1.updateuserpermission.PermissionInfoType>
		// update_permissioninfo = new
		// ArrayList<com.sprint.integration.interfaces.updateuserpermission.v1.updateuserpermission.PermissionInfoType>();
		com.sprint.integration.interfaces.updateuserpermission.v1.updateuserpermission.PermissionListType permissionList = new com.sprint.integration.interfaces.updateuserpermission.v1.updateuserpermission.PermissionListType();
		permissionList.getPermissionInfo().add(update_permissionInfo1);
		updateUserPermissionRequest.setPermissionList(permissionList);

		// update_permissioninfo.add(update_permissionInfo1);

		// UpdateUserPermissionSubPermissionListType
		// updateUserPermissionSubPermissionList = new
		// UpdateUserPermissionSubPermissionListType();
		// updateUserPermissionSubPermissionList.setPermissionInfo(update_permissioninfo);

		// updateUserPermissionRequest.setPermissionList(updateUserPermissionSubPermissionList);

		UpdateUserPermissionResponseType updateUserPermissionResponse2 = service.updateUserPermission(
				updateUserPermissionRequest, successHeader);
		updateUserPermissionResponse.setMessage("abcd");
		Assert.assertEquals("Responses are not same",
				updateUserPermissionResponse2.getMessage().equals(updateUserPermissionResponse.getMessage()));

	}

	@Test
	public void testQueryUserPreferencesInfoV2() throws SoapFault {

		QueryUserPreferencesInfoV2Type queryUserPreferencesInfoV2Request = new QueryUserPreferencesInfoV2Type();
		UserIdentityInfoType userIdentityInfo = new UserIdentityInfoType();
		userIdentityInfo.setOwnerId("12345");
		userIdentityInfo.setOwnerType("abcde");
		queryUserPreferencesInfoV2Request.setUserIdentityInfo(userIdentityInfo);

		UserPreferenceListType userPreferenceList = new UserPreferenceListType();
		UserPreferenceInfoType userPreferenceInfo = new UserPreferenceInfoType();
		userPreferenceInfo.setContextName("ghijk");
		userPreferenceInfo.setPreferenceName("mnopq");
		userPreferenceList.getPreferenceInfo().add(userPreferenceInfo);
		queryUserPreferencesInfoV2Request.setUserPreferenceList(userPreferenceList);

		QueryUserPreferencesInfoV2ResponseType queryUserPreferencesInfoV2Response2 = service
				.queryUserPreferencesInfoV2(queryUserPreferencesInfoV2Request, successHeader);
		Assert.assertEquals(
				"Responses are not same",
				queryUserPreferencesInfoV2Response2
						.getUserPreferenceList()
						.getPreferenceInfo()
						.get(0)
						.getContextName()
						.equals(queryUserPreferencesInfoV2Response.getUserPreferenceList().getPreferenceInfo().get(0)
								.getContextName()));
	}

}
