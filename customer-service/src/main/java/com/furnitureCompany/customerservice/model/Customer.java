package com.furnitureCompany.customerservice.model;

import javax.persistence.*;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id", unique = true, nullable = false)
    @PrimaryKeyJoinColumn
    private Long customerId;

    @Column(name = "personal_Id_number", nullable = false)
    private int personalIdNumber;

    @Column(name = "full_name")//, nullable = false)
    private String fullName;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private int phoneNumber;

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

    public int getPersonalIdNumber() {
        return personalIdNumber;
    }

    public void setPersonalIdNumber(int personalIdNumber) {
        this.personalIdNumber = personalIdNumber;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
