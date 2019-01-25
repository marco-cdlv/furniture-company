package com.furnitureCompany.purchaseorderservice.services;

import com.furnitureCompany.purchaseorderservice.Repository.PurchaseOrderDetailRepository;
import com.furnitureCompany.purchaseorderservice.model.PurchaseOrder;
import com.furnitureCompany.purchaseorderservice.model.PurchaseOrderDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseOrderDetailService {

    @Autowired
    private PurchaseOrderDetailRepository purchaseOrderDetailRepository;

    public void addPurchaseOrderDetail(PurchaseOrderDetail purchaseOrderDetail) {
        purchaseOrderDetailRepository.save(purchaseOrderDetail);
    }

    public PurchaseOrder getPurchaseOrderDetailById(Long purchaseOrderDetailId) {
        return purchaseOrderDetailRepository.getPurchaseOrderDetailByPurchaseOrderDetailId(purchaseOrderDetailId);
    }
}
