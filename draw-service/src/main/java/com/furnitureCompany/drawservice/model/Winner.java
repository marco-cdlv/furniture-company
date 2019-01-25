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

    @Column(name = "participant_id", nullable = false)
    private Long participantId;

    @Column(name = "prize_id", nullable = false)
    private Long prizeId;

    @Column(name = "draw_id", nullable = false)
    private Long drawId;

    public Long getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(Long winnerId) {
        this.winnerId = winnerId;
    }

    public Long getParticipantId() {
        return participantId;
    }

    public void setParticipantId(Long participantId) {
        this.participantId = participantId;
    }

    public Long getPrizeId() {
        return prizeId;
    }

    public void setPrizeId(Long prizeId) {
        this.prizeId = prizeId;
    }

    public Long getDrawId() {
        return drawId;
    }

    public void setDrawId(Long drawId) {
        this.drawId = drawId;
    }
}
