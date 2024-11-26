package com.example.demo.DTO;

public class DeliveryDTO {
    private int deliveryId;
    private int orderId;
    private String description;
    private String status;
    private String date;

    public DeliveryDTO() {}

    public DeliveryDTO(int deliveryId, int orderId, String description, String status, String date) {
        this.deliveryId = deliveryId;
        this.orderId = orderId;
        this.description = description;
        this.status = status;
        this.date = date;
    }

    public int getDeliveryId() {
        return deliveryId;
    }
    public void setDeliveryId(int deliveryId) {
        this.deliveryId = deliveryId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
