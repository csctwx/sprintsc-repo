package com.ericsson.cac.sprint.selfcare.workflow.util;

public enum Service {
	
	ACCOUNT_MANAGEMENT_SERVICE("AccountManagementService"),
	QUERY_PLANS_AND_OPTIONS_SERVICE("QueryPlansAndOptionsService"),
	QUERY_PREPAID_SUBSCRIBER_SERVICE("QueryPrepaidSubscriberService"),
	QUERY_DEVICE_INFO_SERVICE("QueryDeviceInfoService"),
	SMS_PREFERENCE_MANAGEMENT_PROXY_SERVICE("SmsPreferenceManagementService"),
	QUERY_SUBSCRIBER_INFO_SERVICE("QuerySubscriberInfoService"),
	SUBSCRIBER_MANAGEMENT_SERVICE("SubscriberManagementService"),
	QUERY_ACCOUNT_INFO_SERVICE("QueryAccountInfoService"),
    SECURITY_MANAGEMENT_SERVICE("SecurityManagementService"),
    ACCOUNT_MANAGEMENT_PROXY_SERVICE("AccountManagementProxyService"),
    QUERY_PREPAID_SUBSCRIBER_PROXY_SERVICE("QueryPrepaidSubscriberService"),
    QUERY_CSA_PROXY_SERVICE("QueryCsaProxyService"),
    CUSTOMER_DATA_PRIVACY_MANAGEMENT_SERVICE("CustomerDataPrivacyManagementService"),
    APPLE_MANAGEMENT_SERVICE("AppleDeviceManagementProxyService"),
    ADDRESS_MANAGEMENT_PROXY_SERVICE("AddressManagementService"),
	//Added for getSecretQuestions 	
	QUERY_REFERENCE_INFO_SERVICE("QueryReferenceInfoService"),
	QUERY_USAGE_SERVICE("QueryUsageService"),
	QUERY_PROVISIONING_INFO_SERVICE("QueryProvisioningInfoService"),
	QUERY_ORDER_FULFILLMENT_SERVICE("QueryOrderFulfillmentService");
	//SECURITY_MANAGEMENT_RESEND_SECURITY("SecurityManagementResendSecurityInfo"),
	
	//suspendSubscriber
	
	//SECURITY_MANAGEMENT_SUSPEND_SUBC("SecurityManagementSuspendSubscriber") ;
	
		
	private String value;

    private Service(String value) {
            this.value = value;
    }

    public String getValue(){
    	return value;
    }
	
}
