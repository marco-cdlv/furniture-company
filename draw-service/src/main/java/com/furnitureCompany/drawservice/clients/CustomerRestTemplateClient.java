package com.furnitureCompany.drawservice.clients;

import com.furnitureCompany.drawservice.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomerRestTemplateClient {

    @Autowired
    RestTemplate restTemplate;

    public List<Customer> getCustomers() {
        ResponseEntity<List> restExchange = restTemplate.exchange(
//                "http://localhost:8081/v1/customers/",
                "http://localhost:5555/customer_service/v1/customers/",
                HttpMethod.GET,
                null, List.class);

        return restExchange.getBody();
    }

    public List<Customer> getCustomersByIds(List<Long> customerIds) {
//        String ids = customerIds.stream().map(id -> id.toString()).reduce(",", String::concat);
        String ids = customerIds.stream().map(id -> id.toString()).collect(Collectors.joining(","));

        ResponseEntity<List> restExchange = restTemplate.exchange(
                "http://localhost:5555/customer_service/v1/customers/ids/{customer_ids}",
                HttpMethod.GET,
                null, List.class, ids);

        return restExchange.getBody();
    }
}
