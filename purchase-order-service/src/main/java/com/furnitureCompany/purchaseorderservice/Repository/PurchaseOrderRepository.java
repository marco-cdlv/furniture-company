package com.furnitureCompany.purchaseorderservice.Repository;

import com.furnitureCompany.purchaseorderservice.model.PurchaseOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PurchaseOrderRepository extends CrudRepository<PurchaseOrder, String> {
    PurchaseOrder getPurchaseOrderByPurchaseOrderId(Long purchaseOrderId);
    List<PurchaseOrder> getPurchaseOrdersByCustomerId(Long customerId);
    List<PurchaseOrder> getPurchaseOrdersByPurchaseDateBetween(Date startDate, Date endDate);
}
