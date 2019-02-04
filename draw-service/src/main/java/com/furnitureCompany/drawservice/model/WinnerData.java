package com.furnitureCompany.drawservice.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


public class WinnerData implements Serializable {

    private String winnerFullName;
    private Integer winnerPersonalNumber ;
    private Integer winnerPhoneNumber ;
    private String prizeName;
    private String promotionName;

    public String getWinnerFullName() {
        return winnerFullName;
    }

    public void setWinnerFullName(String winnerFullName) {
        this.winnerFullName = winnerFullName;
    }

    public Integer getWinnerPersonalNumber() {
        return winnerPersonalNumber;
    }

    public void setWinnerPersonalNumber(Integer winnerPersonalNumber) {
        this.winnerPersonalNumber = winnerPersonalNumber;
    }

    public Integer getWinnerPhoneNumber() {
        return winnerPhoneNumber;
    }

    public void setWinnerPhoneNumber(Integer winnerPhoneNumber) {
        this.winnerPhoneNumber = winnerPhoneNumber;
    }

    public String getPrizeName() {
        return prizeName;
    }

    public void setPrizeName(String prizeName) {
        this.prizeName = prizeName;
    }

    public String getPromotionName() {
        return promotionName;
    }

    public void setPromotionName(String promotionName) {
        this.promotionName = promotionName;
    }
}
