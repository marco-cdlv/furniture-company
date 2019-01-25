package com.furnitureCompany.purchaseorderservice.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "purchase_orders")
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_order_id", unique = true, nullable = false)
    private Long purchaseOrderId;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(name = "furniture_id", nullable = false)
    private Long furnitureId;

    @Column(name = "purchase_date", nullable = false)
    private Date purchaseDate;

    @Column(name = "amount", nullable = false)
    private int amount;

    public Long getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(Long purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getFurnitureId() {
        return furnitureId;
    }

    public void setFurnitureId(Long furnitureId) {
        this.furnitureId = furnitureId;
    }
}
