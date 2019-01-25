package com.furnitureCompany.drawservice.model;

import javax.persistence.*;

@Entity
@Table(name = "winners")
public class Winner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "winner_Id", unique = true, nullable = false)
    @PrimaryKeyJoinColumn
    private Long winnerId;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(name = "prize_id", nullable = false)
    private Long prizeId;

    public Long getPrizeId() {
        return prizeId;
    }

    public void setPrizeId(Long prizeId) {
        this.prizeId = prizeId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(Long winnerId) {
        this.winnerId = winnerId;
    }
}
