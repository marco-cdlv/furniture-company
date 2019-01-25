package com.furnitureCompany.purchaseorderservice.controllers;

import com.furnitureCompany.purchaseorderservice.model.PurchaseOrder;
import com.furnitureCompany.purchaseorderservice.services.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "v1/purchaseOrders")
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @RequestMapping(value = "/{purchase_order_id}", method = RequestMethod.GET)
    public PurchaseOrder getPurchaseOrderById(@PathVariable("purchase_order_id") Long purchaseOrderId) {
        return purchaseOrderService.getPurchaseOrderById(purchaseOrderId);
    }

    @RequestMapping(value = "/customer/{customer_id}", method = RequestMethod.GET)
    public List<PurchaseOrder> getPurchaseOrdersByCustomerId(@PathVariable("customer_id") Long customerId) {
        return purchaseOrderService.getPurchaseOrdersByCustomerId(customerId);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void addPurchaseOrder(@RequestBody PurchaseOrder purchaseOrder) {
        purchaseOrderService.addPurchaseOrder(purchaseOrder);
    }

    @RequestMapping(value = "/{start_date}/{end_date}", method = RequestMethod.GET)
    public List<PurchaseOrder> getPurchaseOrdersBetweenDate(@PathVariable("start_date")@DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                                            @PathVariable("end_date")@DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        return purchaseOrderService.getPurchaseOrdersBetweenDates(startDate, endDate);
    }
}
