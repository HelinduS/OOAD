package com.example.demo.DTO;

import java.time.LocalDate;

public class OrderDTO {

    private int orderId;
    private int customerId;
    private String date;
    private String description;
    private String status;

    public OrderDTO() {}

    public OrderDTO(int orderId, int customerId,LocalDate date, String description, String status) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.date = date.toString();
        this.description = description;
        this.status = status;
    }



    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
