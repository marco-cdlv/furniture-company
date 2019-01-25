package com.furnitureCompany.purchaseorderservice.controllers;

import com.furnitureCompany.purchaseorderservice.model.PurchaseOrder;
import com.furnitureCompany.purchaseorderservice.model.PurchaseOrderDetail;
import com.furnitureCompany.purchaseorderservice.services.PurchaseOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "v1/purchaseOrderDetails")
public class PurchaseOrderDetailController {

    @Autowired
    private PurchaseOrderDetailService purchaseOrderDetailService;

    @RequestMapping(value = "/{purchase_order_detail_id}", method = RequestMethod.GET)
    public PurchaseOrder getPurchaseOrderDetailById(@PathVariable("purchase_order_detail_id") Long purchaseOrderDetailId) {
        return purchaseOrderDetailService.getPurchaseOrderDetailById(purchaseOrderDetailId);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void addPurchaseOrder(@RequestBody PurchaseOrderDetail purchaseOrderDetail) {
        purchaseOrderDetailService.addPurchaseOrderDetail(purchaseOrderDetail);
    }
}
