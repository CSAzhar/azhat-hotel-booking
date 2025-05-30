package com.azhar.hotel.security;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configurable
public class CorsConfig {
	
	@Bean
	public WebMvcConfigurer webMvcConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("http://azharhotelbooking.s3-website.eu-north-1.amazonaws.com", "*")
						.allowedMethods("GET", "POST", "PUT", "DELETE")
						.allowedHeaders("*");
						
			}
		};
	}

}
