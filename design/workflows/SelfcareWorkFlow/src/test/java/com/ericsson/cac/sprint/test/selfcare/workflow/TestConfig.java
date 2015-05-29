package com.ericsson.cac.sprint.test.selfcare.workflow;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.ClassPathResource;

@Configuration
@ImportResource(value = {
		 "classpath:spring-CustomerDataPrivacyManagementProxyService.xml",
        "classpath:spring-SecurityManagementProxyService.xml",
        "classpath:spring-QueryPlansAndOptionsProxyService.xml",
        "classpath:spring-QueryResourceInfoProxyService.xml",
        "classpath:spring-QueryCsaProxyService.xml",
       //"classpath:spring-EnterprisePaymentProxyService.xml",
        "classpath:spring-VoucherManagementProxyService.xml",
        "classpath:spring-LoggingManagementProxyService.xml",
        "classpath:spring-QueryAccountInfoProxyService.xml",
        "classpath:spring-QueryPrepaidSubcriberProxyService.xml",
        "classpath:spring-TaxCalculationProxyService.xml",
        "classpath:spring-QueryOrderFulfillmentProxyService.xml",
        "classpath:spring-AccountManagementProxyService.xml",
        "classpath:spring-QueryDeviceInfoProxyService.xml",
        "classpath:spring-QueryUsageProxyService.xml",
        "classpath:spring-PrepaidAccountManagementProxyService.xml",
        "classpath:spring-SubscriberManagementProxyService.xml",
        "classpath:spring-QueryBillingSystemProxyService.xml",
        "classpath:spring-QueryReferenceInfoProxyService.xml",
        "classpath:spring-QueryResourceInfoProxyService.xml",
        "classpath:spring-SmsPreferenceManagementProxyService.xml",
        "classpath:spring-PromotionManagementProxyService.xml",
       //"classpath:spring-EnterpriseWalletProxyService.xml",
        "classpath:spring-AddressManagementProxyService.xml",
       "classpath:spring-OrderFulfillmentProxyService.xml",
       //"classpath:spring-UserProfileProxyService.xml",
        "classpath:spring-QuerySubscriberInfoProxyService.xml",
        "classpath:spring-AppleDeviceManagementProxyService.xml",
        "classpath:spring-OfferManagementProxyService.xml",
        "classpath:spring-QueryProvisioningInfoService.xml",
        "classpath:spring-SelfCareWorkFlow.xml"	
})
public class TestConfig {

	@Bean
	public PropertyPlaceholderConfigurer getPropertyPlaceholderConfigurer(){
		PropertyPlaceholderConfigurer pro = new PropertyPlaceholderConfigurer();
		pro.setLocations(new ClassPathResource("/config.properties"));
		return pro;
	}
}
