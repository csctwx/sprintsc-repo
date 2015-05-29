package com.ericsson.cac.sprint.adapters;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.ClassPathResource;

@Configuration
@ImportResource("classpath:spring-VoucherManagementProxyService.xml")
public class TestConfig {

	@Bean
	public PropertyPlaceholderConfigurer getPropertyPlaceholderConfigurer(){
		PropertyPlaceholderConfigurer pro = new PropertyPlaceholderConfigurer();
		pro.setLocations(new ClassPathResource("/config.properties"));
		return pro;
	}
}
