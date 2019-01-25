package com.furnitureCompany.customerservice.events.models;

public class CustomerChangeModel{
    private String type;
    private String action;
    private String customerFullName;


    public  CustomerChangeModel(String type, String action, String customerFullName) {
        super();
        this.type   = type;
        this.action = action;
        this.customerFullName = customerFullName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getCustomerFullName() {
        return customerFullName;
    }

    public void setCustomerFullNam(String customerFullNam) {
        this.customerFullName = customerFullName;
    }
}