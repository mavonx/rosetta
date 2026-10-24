package org.rosetta.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Order {
    private int id;
    private int userId;
    private String status;
    private BigDecimal totalAmount;
    private LocalDateTime orderDate;

    public Order() {}

    public Order(int id, int userId, String status, BigDecimal totalAmount, LocalDateTime orderDate) {
        this.id = id;
        this.userId = userId;
        this.status = status;
        this.totalAmount = totalAmount;
        this.orderDate = orderDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getStatus() {
        return status;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    @Override
    public String toString() {
        return "Order(id: " + id + ", userId: " + userId + ", status: " + status + ", totalAmount: " + totalAmount + ", orderDate: " + orderDate + ")";
    }
}
