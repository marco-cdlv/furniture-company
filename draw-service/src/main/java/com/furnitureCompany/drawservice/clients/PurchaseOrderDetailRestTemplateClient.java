package com.furnitureCompany.drawservice.clients;

import com.furnitureCompany.drawservice.model.PurchaseOrderDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class PurchaseOrderDetailRestTemplateClient {

    private static final Logger logger = LoggerFactory.getLogger(PurchaseOrderDetailRestTemplateClient.class);

    @Autowired
    RestTemplate restTemplate;

    public List<PurchaseOrderDetail> getPurchaseOrderDetailsByOrderIds(List<Long> orderIds) {
        try {
            ResponseEntity<List> restExchange = restTemplate.exchange(
                    "http://localhost:5555/purchase_order_service/v1/purchaseOrderDetails/orders/{purchase_order_ids}",
                    HttpMethod.GET,
                    null, List.class, orderIds);
            return restExchange.getBody();
        } catch(Exception exception) {
            logger.error("An error occurred trying to get the purchase order details, " + exception.getMessage());
        }
        return null;
    }
}
