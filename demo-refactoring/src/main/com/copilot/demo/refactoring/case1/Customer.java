package com.copilot.demo.refactoring.case1;

/**
 * Represents a customer in the system with their basic information and type.
 * Customers can be either REGULAR or PREMIUM type, which affects their discount eligibility.
 *
 * @author Copilot Demo
 * @version 1.0
 */
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
