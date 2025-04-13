package com.copilot.demo.refactoring.case1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderProcessorTest {

    private TaxCalculator taxCalculator;
    private DiscountService discountService;
    private OrderProcessor orderProcessor;

    @BeforeEach
    void setUp() {
        taxCalculator = mock(TaxCalculator.class);
        discountService = mock(DiscountService.class);
        orderProcessor = new OrderProcessor(taxCalculator, discountService);
    }

    @Test
    void givenValidCustomerAndItems_whenProcessOrder_thenOrderIsProcessedSuccessfully() {
        // Given
        String orderId = "order123";
        String customerId = "customer1";
        Customer customer = new Customer(customerId, "John Doe", "john.doe@example.com", "REGULAR");
        orderProcessor.registerCustomer(customer);

        List<OrderItem> items = Arrays.asList(
                new OrderItem("product1", 2, 50.0),
                new OrderItem("product2", 1, 100.0)
        );

        when(taxCalculator.calculateTax(200.0)).thenReturn(220.0);
        when(discountService.applyDiscount(220.0, customer)).thenReturn(200.0);

        // When
        boolean result = orderProcessor.processOrder(orderId, customerId, items);

        // Then
        assertTrue(result);
        verify(taxCalculator).calculateTax(200.0);
        verify(discountService).applyDiscount(220.0, customer);
    }

    @Test
    void givenInvalidCustomer_whenProcessOrder_thenOrderIsNotProcessed() {
        // Given
        String orderId = "order123";
        String customerId = "invalidCustomer";
        List<OrderItem> items = Collections.singletonList(new OrderItem("product1", 1, 50.0));

        // When
        boolean result = orderProcessor.processOrder(orderId, customerId, items);

        // Then
        assertFalse(result);
    }

    @Test
    void givenOrderExceedsApprovalThreshold_whenProcessOrder_thenOrderNeedsApproval() {
        // Given
        String orderId = "order123";
        String customerId = "customer1";
        Customer customer = new Customer(customerId, "John Doe", "john.doe@example.com", "REGULAR");
        orderProcessor.registerCustomer(customer);

        List<OrderItem> items = Collections.singletonList(new OrderItem("product1", 10, 150.0));

        when(taxCalculator.calculateTax(1500.0)).thenReturn(1650.0);
        when(discountService.applyDiscount(1650.0, customer)).thenReturn(1600.0);

        // When
        boolean result = orderProcessor.processOrder(orderId, customerId, items);

        // Then
        assertTrue(result);
        Order order = orderProcessor.getOrders().stream()
                .filter(o -> o.getOrderId().equals(orderId))
                .findFirst()
                .orElse(null);

        assertNotNull(order);
        assertEquals(OrderStatus.NEEDS_APPROVAL, order.getStatus());
    }

    @Test
    void givenEmptyItems_whenProcessOrder_thenOrderIsNotProcessed() {
        // Given
        String orderId = "order123";
        String customerId = "customer1";
        Customer customer = new Customer(customerId, "John Doe", "john.doe@example.com", "REGULAR");
        orderProcessor.registerCustomer(customer);

        List<OrderItem> items = Collections.emptyList();

        // When
        boolean result = orderProcessor.processOrder(orderId, customerId, items);

        // Then
        assertFalse(result);
    }

    @Test
    void givenValidOrderId_whenPrintOrderDetails_thenDetailsArePrinted() {
        // Given
        String orderId = "order123";
        String customerId = "customer1";
        Customer customer = new Customer(customerId, "John Doe", "john.doe@example.com", "REGULAR");
        orderProcessor.registerCustomer(customer);

        List<OrderItem> items = Collections.singletonList(new OrderItem("product1", 1, 100.0));

        when(taxCalculator.calculateTax(100.0)).thenReturn(110.0);
        when(discountService.applyDiscount(110.0, customer)).thenReturn(100.0);

        orderProcessor.processOrder(orderId, customerId, items);

        // When & Then
        orderProcessor.printOrderDetails(orderId);
    }
}
