package com.furnitureCompany.drawservice;

import com.furnitureCompany.drawservice.events.models.CustomerChangeModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
//import org.springframework.cloud.stream.annotation.EnableBinding;
//import org.springframework.cloud.stream.annotation.StreamListener;
//import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
//@EnableBinding(Sink.class)
//@EnableCircuitBreaker
public class DrawServiceApplication {

	//@LoadBalanced
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	//private static final Logger logger = LoggerFactory.getLogger(DrawServiceApplication.class);

//	@StreamListener(Sink.INPUT)
//    public void loggerSink(CustomerChangeModel customerChange) {
//        logger.debug("Received an event for customer full name {}", customerChange.getCustomerFullName());
//    }

	public static void main(String[] args) {
		SpringApplication.run(DrawServiceApplication.class, args);
	}

}

