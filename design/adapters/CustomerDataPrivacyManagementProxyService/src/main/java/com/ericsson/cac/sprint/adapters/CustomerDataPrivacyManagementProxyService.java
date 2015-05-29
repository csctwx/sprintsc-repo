package com.ericsson.cac.sprint.adapters;

import javax.xml.ws.Holder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sprint.integration.common.header.wsmessageheader.v2.WsMessageHeaderType;
import com.sprint.integration.eai.services.customerdataprivacymanagementservice.v1.customerdataprivacymanagementservice.CustomerDataPrivacyManagementService;
import com.sprint.integration.eai.services.customerdataprivacymanagementservice.v1.customerdataprivacymanagementservice.CustomerDataPrivacyManagementServicePortType;
import com.sprint.integration.eai.services.customerdataprivacymanagementservice.v1.customerdataprivacymanagementservice.SoapFault;
import com.sprint.integration.interfaces.queryuserpermission.v1.queryuserpermission.QueryUserPermissionResponseType;
import com.sprint.integration.interfaces.queryuserpermission.v1.queryuserpermission.QueryUserPermissionType;
import com.sprint.integration.interfaces.queryuserpreferencesinfo.v2.queryuserpreferencesinfov2.QueryUserPreferencesInfoV2ResponseType;
import com.sprint.integration.interfaces.queryuserpreferencesinfo.v2.queryuserpreferencesinfov2.QueryUserPreferencesInfoV2Type;
import com.sprint.integration.interfaces.updateuserpermission.v1.updateuserpermission.UpdateUserPermissionResponseType;
import com.sprint.integration.interfaces.updateuserpermission.v1.updateuserpermission.UpdateUserPermissionType;
import com.sprint.integration.interfaces.updateuserpreferencesinfo.v2.updateuserpreferencesinfov2.UpdateUserPreferencesInfoV2ResponseType;
import com.sprint.integration.interfaces.updateuserpreferencesinfo.v2.updateuserpreferencesinfov2.UpdateUserPreferencesInfoV2Type;

@Component
public class CustomerDataPrivacyManagementProxyService extends CustomerDataPrivacyManagementService {
	@Autowired
	private CustomerDataPrivacyManagementServicePortType stub;

	/*
	 * @Autowired private Configuration config;
	 */

	public QueryUserPermissionResponseType queryUserPermission(QueryUserPermissionType queryUserPermissionRequest,
			Holder<WsMessageHeaderType> head) throws SoapFault {
		return stub.queryUserPermission(queryUserPermissionRequest, head);
	}

	public UpdateUserPermissionResponseType updateUserPermission(UpdateUserPermissionType updateUserPermissionRequest,
			Holder<WsMessageHeaderType> head) throws SoapFault {
		return stub.updateUserPermission(updateUserPermissionRequest, head);
	}

	public QueryUserPreferencesInfoV2ResponseType queryUserPreferencesInfoV2(
			QueryUserPreferencesInfoV2Type queryUserPreferencesInfoV2Request, Holder<WsMessageHeaderType> head)
			throws SoapFault {
		return stub.queryUserPreferencesInfoV2(queryUserPreferencesInfoV2Request, head);
	}
	
	public UpdateUserPreferencesInfoV2ResponseType updateUserPreferencesInfoV2(
		UpdateUserPreferencesInfoV2Type updateUserPreferencesInfoRequestV2, Holder<WsMessageHeaderType> head)
		throws SoapFault {
	    	return stub.updateUserPreferencesInfoV2(updateUserPreferencesInfoRequestV2, head);
	}
}
