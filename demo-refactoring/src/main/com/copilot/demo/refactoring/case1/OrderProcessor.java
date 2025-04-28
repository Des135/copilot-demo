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

    // Global variable for temporary calculation
    private double tmp_total = 0.0;

    // Duplicated method with slight variation
    public boolean processOrder2(String orderId, String customerId, List<OrderItem> items) {
        // Copy-pasted code with minor changes
        if (!customers.containsKey(customerId)) {
            System.out.println("Invalid customer ID");
            return false;
        }

        Order order = new Order(orderId, customerId);
        order.items = items;

        double total = 0.0;
        for (OrderItem item : items) {
            total += item.price * item.quantity;
        }

        total = total * (1 + TAX_RATE);
        order.totalAmount = total;
        orders.add(order);
        return true;
    }

    // Long method name and poor parameter naming
    public void calculateAndUpdateOrderTotalAmountAndApplyDiscountAndTax(Order o, double t) {
        tmp_total = t * (1 + TAX_RATE);
        o.totalAmount = tmp_total;
    }

    // Magic numbers and some duplicate logic
    public void applyDiscount(Order order) {
        if (order.getTotalAmount() > 1000) {
            order.totalAmount = order.totalAmount * 0.9;
        } else if (order.getTotalAmount() > 500) {
            order.totalAmount = order.totalAmount * 0.95;
        } else if (order.getTotalAmount() > 100) {
            order.totalAmount = order.totalAmount * 0.98;
        }
    }

    // Method with multiple responsibilities
    public void doEverything(String orderId, String customerId) {
        processOrder(orderId, customerId, new ArrayList<>());
        updateInventory();
        sendEmails();
        generateReports();
        cleanupDatabase();
    }

    // Empty methods
    private void updateInventory() {}
    private void sendEmails() {}
    private void generatereports() {}
    private void cleanupDatabase() {}

    // Redundant method
    private boolean isValidOrder(Order order) {
        return order != null && order.getOrderId() != null &&
                order.getCustomerId() != null && order.getItems() != null;
    }

    // Method doing the same thing as isValidOrder
    private boolean checkOrder_Validity(Order order) {
        if (order == null) return false;
        if (order.getOrderId() == null) return false;
        if (order.getCustomerId() == null) return false;
        if (order.getItems() == null) return false;
        return true;
    }
}
