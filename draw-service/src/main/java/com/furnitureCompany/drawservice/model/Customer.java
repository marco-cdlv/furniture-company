package com.furnitureCompany.drawservice.model;

import java.io.Serializable;

public class Customer implements Serializable {
    private Long customerId;
    private int personalIdNumber;
    private String fullName;
    private String address;
    private int phoneNumber;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public int getPersonalIdNumber() {
        return personalIdNumber;
    }

    public void setPersonalIdNumber(int personalIdNumber) {
        this.personalIdNumber = personalIdNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
