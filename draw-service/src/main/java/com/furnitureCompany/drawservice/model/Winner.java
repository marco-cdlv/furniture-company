package com.furnitureCompany.drawservice.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "winners")
public class Winner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "winner_id", unique = true, nullable = false)
    private Long winnerId;

    @Column(name = "prize_id", nullable = false)
    private Long prizeId;

    @Column(name = "participant_id", nullable = false)
    private Long participantId;

    @Column(name = "promotion_id", nullable = false)
    private Long promotionId;

    @Column(name = "date")
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(Long winnerId) {
        this.winnerId = winnerId;
    }

    public Long getPrizeId() {
        return prizeId;
    }

    public void setPrizeId(Long prizeId) {
        this.prizeId = prizeId;
    }

    public Long getParticipantId() {
        return participantId;
    }

    public void setParticipantId(Long participantId) {
        this.participantId = participantId;
    }

    public Long getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Long promotionId) {
        this.promotionId = promotionId;
    }
}
