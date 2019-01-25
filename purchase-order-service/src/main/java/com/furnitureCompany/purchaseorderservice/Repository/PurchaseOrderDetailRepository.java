package com.furnitureCompany.purchaseorderservice.Repository;

import com.furnitureCompany.purchaseorderservice.model.PurchaseOrder;
import com.furnitureCompany.purchaseorderservice.model.PurchaseOrderDetail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseOrderDetailRepository extends CrudRepository<PurchaseOrderDetail, String> {
    PurchaseOrder getPurchaseOrderDetailByPurchaseOrderDetailId(Long purchaseOrderId);
}
