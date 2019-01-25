package com.furnitureCompany.purchaseorderservice.services;

import com.furnitureCompany.purchaseorderservice.Repository.PurchaseOrderRepository;
import com.furnitureCompany.purchaseorderservice.model.PurchaseOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PurchaseOrderService {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    public void addPurchaseOrder(PurchaseOrder purchaseOrder) {
        purchaseOrderRepository.save(purchaseOrder);
    }

    public PurchaseOrder getPurchaseOrderById(Long purchaseOrderId) {
        return purchaseOrderRepository.getPurchaseOrderByPurchaseOrderId(purchaseOrderId);
    }

    public List<PurchaseOrder> getPurchaseOrdersBetweenDates(Date startDate, Date endDate) {
        return purchaseOrderRepository.getPurchaseOrdersByPurchaseDateBetween(startDate, endDate);
    }

    public List<PurchaseOrder> getPurchaseOrdersByCustomerId(Long customerId) {
        return purchaseOrderRepository.getPurchaseOrdersByCustomerId(customerId);
    }

}
