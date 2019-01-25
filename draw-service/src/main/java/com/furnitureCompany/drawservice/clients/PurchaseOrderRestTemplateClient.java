package com.furnitureCompany.drawservice.clients;

import com.furnitureCompany.drawservice.model.PurchaseOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PurchaseOrderRestTemplateClient {

    private static final Logger logger = LoggerFactory.getLogger(PurchaseOrderRestTemplateClient.class);

    @Autowired
    RestTemplate restTemplate;

    public List<PurchaseOrder> getPurchaseOrdersByCustomerId(Long customerId) {
        ResponseEntity<List> restExchange = restTemplate.exchange(
                "http://localhost:5555/purchase_order_service/v1/purchaseOrders/customer/{customer_id}",
                HttpMethod.GET,
                null, List.class, customerId);

        return restExchange.getBody();
    }

    public List<PurchaseOrder> getPurchaseOrdersBetweenDates(Date startDate, Date endDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, String> parameters = new HashMap<>();

        parameters.put("start_date", simpleDateFormat.format(startDate));
        parameters.put("end_date", simpleDateFormat.format(endDate));

        try {
            ResponseEntity<List> restExchange = restTemplate.exchange(
                    "http://localhost:5555/purchase_order_service/v1/purchaseOrders/{start_date}/{end_date}",
                    HttpMethod.GET,
                    null, List.class, parameters);
            return restExchange.getBody();
        } catch(Exception exception) {
            StringBuilder errorMessage = new StringBuilder("An error occurred trying to get the purchase orders, ");
            errorMessage.append(exception.getMessage());
            logger.error(errorMessage.toString());
        }
        return null;
    }
}
