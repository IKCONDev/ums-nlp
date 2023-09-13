package com.ikn.ums.nlp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
 class UmsNLPApplication {

	public static void main(String[] args) {
		SpringApplication.run(UmsNLPApplication.class, args);
	}
	
	@LoadBalanced
	@Bean
	public RestTemplate createLoadBalancedRestTemplate() {
		return new RestTemplate();
	}

}
