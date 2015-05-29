package com.ericsson.cac.sprint.test.shop.workflow;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.ClassPathResource;

@Configuration
@ImportResource(value = {
		"classpath:spring-QueryMediaProxyService.xml",
		"classpath:spring-ShopCareWorkFlow.xml",
		"classpath:spring-PromotionManagementProxyService.xml",
		"classpath:spring-QueryCsaProxyService.xml",
		"classpath:spring-TaxCalculationProxyService.xml",
		"classpath:spring-AddressManagementProxyService.xml",
		"classpath:spring-SubscriberManagementProxyService.xml",
		"classpath:spring-EPPService.xml",
		"classpath:spring-EnterprisePaymentProxyService.xml",
		"classpath:spring-EnterpriseWalletProxyService.xml",
		"classpath:spring-OrderFulfillmentProxyService.xml"
})
public class TestConfig {

	@Bean
	public PropertyPlaceholderConfigurer getPropertyPlaceholderConfigurer(){
		PropertyPlaceholderConfigurer pro = new PropertyPlaceholderConfigurer();
		pro.setLocations(new ClassPathResource("/shopservices.properties"));
		return pro;
	}
}