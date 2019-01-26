package com.furnitureCompany.purchaseorderservice.controllers;

import com.furnitureCompany.purchaseorderservice.model.PurchaseOrder;
import com.furnitureCompany.purchaseorderservice.model.PurchaseOrderDetail;
import com.furnitureCompany.purchaseorderservice.services.PurchaseOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "v1/purchaseOrderDetails")
public class PurchaseOrderDetailController {

    @Autowired
    private PurchaseOrderDetailService purchaseOrderDetailService;

    @RequestMapping(value = "/{purchase_order_detail_id}", method = RequestMethod.GET)
    public PurchaseOrderDetail getPurchaseOrderDetailById(@PathVariable("purchase_order_detail_id") Long purchaseOrderDetailId) {
        return purchaseOrderDetailService.getPurchaseOrderDetailById(purchaseOrderDetailId);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void addPurchaseOrder(@RequestBody PurchaseOrderDetail purchaseOrderDetail) {
        purchaseOrderDetailService.addPurchaseOrderDetail(purchaseOrderDetail);
    }

    @RequestMapping(value = "/order/{purchase_order_id}", method = RequestMethod.GET)
    public List<PurchaseOrderDetail> getPurchaseOrderDetailByOrderId(@PathVariable("purchase_order_id") Long purchaseOrderId) {
        return purchaseOrderDetailService.getPurchaseOrderDetailsByOrderId(purchaseOrderId);
    }

    @RequestMapping(value = "/orders/{purchase_order_ids}", method = RequestMethod.GET)
    public List<PurchaseOrderDetail> getPurchaseOrderDetailByOrderIds(@PathVariable("purchase_order_ids") List<Long> purchaseOrderIds) {
        return purchaseOrderDetailService.getPurchaseOrderDetailsByOrderIds(purchaseOrderIds);
    }
}
