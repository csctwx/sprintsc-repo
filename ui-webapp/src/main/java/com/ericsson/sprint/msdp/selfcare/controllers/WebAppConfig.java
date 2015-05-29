package com.ericsson.sprint.msdp.selfcare.controllers;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class WebAppConfig {

	@Bean
	public static PropertyPlaceholderConfigurer getPropertyPlaceholderConfigurer(){
		PropertyPlaceholderConfigurer pro = new PropertyPlaceholderConfigurer();
		pro.setLocation(new FileSystemResource("/opt/drutt/ca/adapt/conf/selfcare-config.properties"));
		return pro;
	}
}