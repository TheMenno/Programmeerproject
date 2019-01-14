package com.example.menno_000.programmeerproject;

import java.sql.Timestamp;
import java.util.Date;

public class Food implements java.io.Serializable {
    private int id;
    private int api_id;
    private String product_name;
    private int calories;
    private int amount;
    private Timestamp timestamp;
    private String meal;

    public Food(int api_id, String product_name, int calories, int amount, String meal) {
        this.api_id = api_id;
        this.product_name = product_name;
        this.calories = calories;
        this.amount = amount;
        this.meal = meal;

        Date date = new Date();
        this.timestamp = new Timestamp(date.getTime());
    }

    public int getId() {
        return id;
    }

    public int getApi_id() {
        return api_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public int getCalories() {
        return calories;
    }

    public int getAmount() {
        return amount;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public String getMeal() {
        return meal;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setApi_id(int api_id) {
        this.api_id = api_id;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }
}