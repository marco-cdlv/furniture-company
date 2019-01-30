package com.furnitureCompany.purchaseorderservice.services;

import com.furnitureCompany.purchaseorderservice.Repository.PurchaseOrderDetailRepository;
import com.furnitureCompany.purchaseorderservice.clients.ProductRestTemplateClient;
import com.furnitureCompany.purchaseorderservice.model.Product;
import com.furnitureCompany.purchaseorderservice.model.PurchaseOrderDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseOrderDetailService {

    @Autowired
    private PurchaseOrderDetailRepository purchaseOrderDetailRepository;

    @Autowired
    private ProductRestTemplateClient productRestTemplateClient;

    public void addPurchaseOrderDetail(PurchaseOrderDetail purchaseOrderDetail) {
        purchaseOrderDetailRepository.save(purchaseOrderDetail);
    }

    //TODO improve this part
    private int getChanceByModel(String model) {
        if ("XXL".equals(model)) {
            return 4;
        }
        if ("XL".equals(model)) {
            return 3;
        }
        if ("L".equals(model)) {
            return 2;
        }
        return 1;
    }

    public PurchaseOrderDetail getPurchaseOrderDetailById(Long purchaseOrderDetailId) {
        return purchaseOrderDetailRepository.getPurchaseOrderDetailByPurchaseOrderDetailId(purchaseOrderDetailId);
    }

    public List<PurchaseOrderDetail> getPurchaseOrderDetailsByOrderId(Long purchaseOrderId) {
        return purchaseOrderDetailRepository.getPurchaseOrderDetailsByPurchaseOrderId(purchaseOrderId);
    }

    public List<PurchaseOrderDetail> getPurchaseOrderDetailsByOrderIds(List<Long> purchaseOrderIds) {
        return purchaseOrderDetailRepository.findPurchaseOrderDetailsByPurchaseOrderIdIn(purchaseOrderIds);
    }
}
