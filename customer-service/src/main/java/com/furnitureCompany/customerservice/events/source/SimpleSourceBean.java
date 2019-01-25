package com.furnitureCompany.customerservice.events.source;

import com.furnitureCompany.customerservice.events.models.CustomerChangeModel;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.stream.messaging.Source;
//import org.springframework.messaging.support.MessageBuilder;

@Component
public class SimpleSourceBean {
//    private Source source;
//
//    private static final Logger logger = LoggerFactory.getLogger(SimpleSourceBean.class);
//
//    @Autowired
//    public SimpleSourceBean(Source source){
//        this.source = source;
//    }
//
//    public void publishCustomerChange(String action, String customerFullName){
//       logger.debug("Sending Kafka message {} for customer full name: {}", action, customerFullName);
//        CustomerChangeModel change =  new CustomerChangeModel(
//                CustomerChangeModel.class.getTypeName(),
//                action,
//                customerFullName);
//
//        source.output().send(MessageBuilder.withPayload(change).build());
//    }
}
