package com.copilot.demo.refactoring.case1;

import java.util.Arrays;
import java.util.List;

public class OrderProcessingDemo {
    public static void main(String[] args) {
        // Initialize dependencies
        TaxCalculator taxCalculator = new TaxCalculator();
        DiscountService discountService = new DiscountService();

        // Create OrderProcessor
        OrderProcessor processor = new OrderProcessor(taxCalculator, discountService);

        // Register a customer
        Customer customer = new Customer("CUST001", "John Doe", "john@example.com", "REGULAR");
        processor.registerCustomer(customer);

        // Create sample order items
        OrderItem item1 = new OrderItem("Product1", 100, 2);
        OrderItem item2 = new OrderItem("Product2", 500, 1);
        OrderItem item3 = new OrderItem("Product3", 50, 3);

        // Demonstrate order processing
        List<OrderItem> smallOrder = Arrays.asList(item1, item3);
        List<OrderItem> largeOrder = Arrays.asList(item1, item2, item3);

        System.out.println("Processing small order...");
        processor.processOrder("ORD001", "CUST001", smallOrder);

        System.out.println("\nProcessing large order...");
        processor.processOrder("ORD002", "CUST001", largeOrder);
    }
}
