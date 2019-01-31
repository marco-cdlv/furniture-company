package com.furnitureCompany.drawservice.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "draws")
public class Draw {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "draw_id", unique = true, nullable = false)
    @PrimaryKeyJoinColumn
    private Long drawId;

    @Column(name = "promotion_id")
    private Long promotionId;

    @Column(name = "draw_date")
    private Date drawDate;

    public Long getDrawId() {
        return drawId;
    }

    public void setDrawId(Long drawId) {
        this.drawId = drawId;
    }

    public Date getDrawDate() {
        return drawDate;
    }

    public void setDrawDate(Date drawDate) {
        this.drawDate = drawDate;
    }


    public Long getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Long promotionId) {
        this.promotionId = promotionId;
    }
}
