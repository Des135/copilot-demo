package com.copilot.demo.refactoring.case1;

import java.util.*;
import java.time.LocalDateTime;

/**
 * OrderProcessor class: Poorly designed intentionally, to illustrate basic refactoring techniques.
 */
public class OrderProcessor {
    private static final double TAX_RATE = 0.2;
    private List<Order> orders = new ArrayList<>();
    private Map<String, Customer> customers = new HashMap<>();

    public class Order {
        private String orderId;
        private String customerId;
        private List<OrderItem> items;
        private LocalDateTime orderDate;
        private String status;
        private double totalAmount;

        public Order(String orderId, String customerId) {
            this.orderId = orderId;
            this.customerId = customerId;
            this.items = new ArrayList<>();
            this.orderDate = LocalDateTime.now();
            this.status = "PENDING";
        }

        // Getters and setters
        public String getOrderId() { return orderId; }
        public String getCustomerId() { return customerId; }
        public List<OrderItem> getItems() { return items; }
        public LocalDateTime getOrderDate() { return orderDate; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        public double getTotalAmount() { return totalAmount; }
    }

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

    public class Customer {
        private String customerId;
        private String name;
        private String email;
        private String type;  // "REGULAR" or "PREMIUM"

        public Customer(String customerId, String name, String email, String type) {
            this.customerId = customerId;
            this.name = name;
            this.email = email;
            this.type = type;
        }

        // Getters
        public String getCustomerId() { return customerId; }
        public String getName() { return name; }
        public String getEmail() { return email; }
        public String getType() { return type; }
    }

    // Process new order
    public boolean processOrder(String orderId, String customerId, List<OrderItem> items) {
        // Validate customer
        if (!customers.containsKey(customerId)) {
            System.out.println("Invalid customer ID");
            return false;
        }

        // Create order
        Order order = new Order(orderId, customerId);
        order.items = items;

        // Calculate total
        double total = 0.0;
        for (OrderItem item : items) {
            total += item.price * item.quantity;
        }

        // Apply tax
        total = total * (1 + TAX_RATE);

        // Apply discount based on customer type
        Customer customer = customers.get(customerId);
        if (customer.type.equals("PREMIUM")) {
            total = total * 0.9;  // 10% discount for premium customers
        }

        order.totalAmount = total;
        orders.add(order);

        // Update order status
        if (total > 1000) {
            order.status = "NEEDS_APPROVAL";
        } else {
            order.status = "APPROVED";
        }

        // Send notification
        String message = "Order " + orderId + " processed for customer " + customer.name;
        if (order.status.equals("NEEDS_APPROVAL")) {
            message += " (Needs approval)";
        }
        sendNotification(customer.email, message);

        return true;
    }

    private void sendNotification(String email, String message) {
        // Simulate sending email notification
        System.out.println("Sending email to " + email + ": " + message);
    }
}
