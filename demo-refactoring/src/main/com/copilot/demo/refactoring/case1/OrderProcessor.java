package com.copilot.demo.refactoring.case1;

import java.util.*;

/**
 * Processes customer orders by handling order creation, validation, pricing calculations,
 * and notification delivery. This class manages the entire order lifecycle including
 * tax calculations, discount applications, and status updates.
 *
 * @author Copilot Demo
 * @version 1.0
 */
public class OrderProcessor {
    private static final double ORDER_APPROVAL_THRESHOLD = 1000.0;

    private final TaxCalculator taxCalculator;
    private final DiscountService discountService;
    private final List<Order> orders = new ArrayList<>();
    private final Map<String, Customer> customers = new HashMap<>();

    /**
     * Constructs a new OrderProcessor with required dependencies.
     *
     * @param taxCalculator    the calculator for determining order tax amounts
     * @param discountService  the service for applying customer discounts
     */
    public OrderProcessor(TaxCalculator taxCalculator, DiscountService discountService) {
        this.taxCalculator = taxCalculator;
        this.discountService = discountService;
    }

    /**
     * Processes a new order for a customer, including validation, price calculation,
     * and notification handling.
     *
     * @param orderId     unique identifier for the order
     * @param customerId  identifier of the customer placing the order
     * @param items      list of items in the order
     * @return boolean indicating whether the order was processed successfully
     */
    public boolean processOrder(String orderId, String customerId, List<OrderItem> items) {
        if (!validateCustomer(customerId)) {
            return false;
        }

        // Check if the items list is empty
        if (items == null || items.isEmpty()) {
            System.out.println("Order items cannot be empty");
            return false;
        }

        Order order = createOrder(orderId, customerId, items);
        Customer customer = customers.get(customerId);
        
        double subtotal = calculateSubtotal(items);
        double totalWithTax = taxCalculator.calculateTax(subtotal);
        double finalTotal = discountService.applyDiscount(totalWithTax, customer);
        
        order.setTotalAmount(finalTotal);
        orders.add(order);
        
        updateOrderStatus(order);
        printOrderDetails(orderId);
        sendOrderNotification(order, customer);

        return true;
    }

    /**
     * Validates if the customer exists in the system.
     *
     * @param customerId identifier of the customer to validate
     * @return boolean indicating whether the customer is valid
     */
    private boolean validateCustomer(String customerId) {
        if (!customers.containsKey(customerId)) {
            System.out.println("Invalid customer ID");
            return false;
        }
        return true;
    }

    /**
     * Creates a new Order instance with the given details.
     *
     * @param orderId    unique identifier for the order
     * @param customerId identifier of the customer placing the order
     * @param items     list of items to include in the order
     * @return newly created Order instance
     */
    private Order createOrder(String orderId, String customerId, List<OrderItem> items) {
        Order order = new Order(orderId, customerId);
        order.setItems(items);
        return order;
    }

    /**
     * Calculates the subtotal of all items in the order before tax and discounts.
     *
     * @param items list of items to calculate subtotal for
     * @return the calculated subtotal
     */
    private double calculateSubtotal(List<OrderItem> items) {
        return items.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }

    /**
     * Updates the order status based on the total amount.
     * Orders over the approval threshold require approval.
     *
     * @param order the order to update status for
     */
    private void updateOrderStatus(Order order) {
        order.setStatus(order.getTotalAmount() > ORDER_APPROVAL_THRESHOLD ? OrderStatus.NEEDS_APPROVAL : OrderStatus.APPROVED);
    }

    /**
     * Prepares and sends an order notification to the customer.
     *
     * @param order    the processed order
     * @param customer the customer who placed the order
     */
    private void sendOrderNotification(Order order, Customer customer) {
        String message = "Order " + order.getOrderId() + " processed for customer " + customer.getName();
        if (order.getStatus() == OrderStatus.NEEDS_APPROVAL) {
            message += " (Needs approval)";
        }
        sendNotification(customer.getEmail(), message);
    }

    /**
     * Sends an email notification to the specified address.
     *
     * @param email   recipient's email address
     * @param message notification message content
     */
    private void sendNotification(String email, String message) {
        // Simulate sending email notification
        System.out.println("Sending email to " + email + ": " + message);
    }

    public void registerCustomer(Customer customer) {
        customers.put(customer.getCustomerId(), customer);
    }

    /**
     * Prints the details of an order, including order ID, customer name, items, and total amount.
     *
     * @param orderId the ID of the order to print details for
     */
    public void printOrderDetails(String orderId) {
        Optional<Order> orderOptional = orders.stream()
                .filter(order -> order.getOrderId().equals(orderId))
                .findFirst();

        if (orderOptional.isEmpty()) {
            System.out.println("Order with ID " + orderId + " not found.");
            return;
        }

        Order order = orderOptional.get();
        Customer customer = customers.get(order.getCustomerId());

        System.out.println("Order Details:");
        System.out.println("Order ID: " + order.getOrderId());
        System.out.println("Customer Name: " + (customer != null ? customer.getName() : "Unknown"));
        System.out.println("Items:");
        for (OrderItem item : order.getItems()) {
            System.out.println("- " + item.getProductId() + " (Quantity: " + item.getQuantity() + ", Price: " + item.getPrice() + ")");
        }
        System.out.println("Total Amount: " + order.getTotalAmount());
    }

    /**
     * Retrieves the list of processed orders.
     *
     * @return list of orders
     */
    public List<Order> getOrders() {
        return new ArrayList<>(orders);
    }
}

