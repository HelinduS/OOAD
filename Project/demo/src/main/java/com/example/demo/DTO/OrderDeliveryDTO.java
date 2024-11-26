package com.example.demo.DTO;

public class OrderDeliveryDTO {
    private int orderId;
    private String orderDescription;
    private String customerName;
    private int deliveryId;
    private String deliveryDescription;
    private String deliveryStatus;
    private String deliveryDate;

    // Constructor, getters, and setters

    public OrderDeliveryDTO(int orderId, String orderDescription, String customerName,
                            int deliveryId, String deliveryDescription, String deliveryStatus, String deliveryDate) {
        this.orderId = orderId;
        this.orderDescription = orderDescription;
        this.customerName = customerName;
        this.deliveryId = deliveryId;
        this.deliveryDescription = deliveryDescription;
        this.deliveryStatus = deliveryStatus;
        this.deliveryDate = deliveryDate;
    }

    // Getters and setters for all fields

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOrderDescription() {
        return orderDescription;
    }

    public void setOrderDescription(String orderDescription) {
        this.orderDescription = orderDescription;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(int deliveryId) {
        this.deliveryId = deliveryId;
    }

    public String getDeliveryDescription() {
        return deliveryDescription;
    }

    public void setDeliveryDescription(String deliveryDescription) {
        this.deliveryDescription = deliveryDescription;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

}
