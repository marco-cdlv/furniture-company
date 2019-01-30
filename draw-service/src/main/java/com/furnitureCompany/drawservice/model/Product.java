package com.furnitureCompany.drawservice.model;


import java.io.Serializable;

public class Product implements Serializable {

    private Long id;
    private String name;
    private String model;
    private int amount;
    private String color;
    private int numberChances;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getNumberChances() {
        return numberChances;
    }

    public void setNumberChances(int numberChances) {
        this.numberChances = numberChances;
    }
}
