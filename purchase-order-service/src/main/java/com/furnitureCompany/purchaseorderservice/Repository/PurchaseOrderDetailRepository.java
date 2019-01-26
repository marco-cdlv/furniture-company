package com.furnitureCompany.purchaseorderservice.Repository;

import com.furnitureCompany.purchaseorderservice.model.PurchaseOrderDetail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseOrderDetailRepository extends CrudRepository<PurchaseOrderDetail, String> {
    PurchaseOrderDetail getPurchaseOrderDetailByPurchaseOrderDetailId(Long purchaseOrderDetailId);
    List<PurchaseOrderDetail> getPurchaseOrderDetailsByPurchaseOrderId(Long purchaseOrderId);
    List<PurchaseOrderDetail> findPurchaseOrderDetailsByPurchaseOrderIdIn(List<Long> purchaseOrderIds);
}
