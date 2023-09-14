package com.ikn.ums.nlp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication

 public class umsnlpApplication {

	public static void main(String[] args) {
		SpringApplication.run(umsnlpApplication.class, args);
	}
	
	@LoadBalanced
	@Bean
	public RestTemplate createLoadBalancedRestTemplate() {
		return new RestTemplate();
	}

}
