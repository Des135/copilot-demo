package com.copilot.demo.refactoring.case1;

/**
 * Represents an individual item within an order, containing product information,
 * quantity, and price details. Each OrderItem represents a single product line
 * in the order.
 *
 * @author Copilot Demo
 * @version 1.0
 */
public class OrderItem {
    private String productId;
    private int quantity;
    private double price;

    public OrderItem(String productId, int quantity, double price) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters
    public String getProductId() { return productId; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }
}
