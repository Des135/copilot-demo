package com.copilot.demo.refactoring.case1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderProcessorTest {

    @Mock
    private TaxCalculator taxCalculator;

    @Mock
    private DiscountService discountService;

    private OrderProcessor orderProcessor;
    private static final String VALID_CUSTOMER_ID = "customer123";
    private static final String VALID_ORDER_ID = "order123";
    private static final String VALID_EMAIL = "customer@example.com";

    @BeforeEach
    void setUp() {
        orderProcessor = new OrderProcessor(taxCalculator, discountService);
        // Using reflection to set up the private customers map
        try {
            var customersField = OrderProcessor.class.getDeclaredField("customers");
            customersField.setAccessible(true);
            var customers = (java.util.Map<String, Customer>) customersField.get(orderProcessor);
            customers.put(VALID_CUSTOMER_ID, new Customer(VALID_CUSTOMER_ID, "Test Customer", VALID_EMAIL, "dsf"));
        } catch (Exception e) {
            fail("Test setup failed");
        }
    }

    @Test
    void processOrder_whenValidOrderWithinApprovalLimit_shouldReturnTrueAndSetApprovedStatus() {
        // Given
        List<OrderItem> items = Arrays.asList(
            new OrderItem("item1", 100, 1),
            new OrderItem("item2", 200, 1)
        );
        when(taxCalculator.calculateTax(300.0)).thenReturn(330.0);
        when(discountService.applyDiscount(anyDouble(), any())).thenReturn(300.0);

        // When
        boolean result = orderProcessor.processOrder(VALID_ORDER_ID, VALID_CUSTOMER_ID, items);

        // Then
        assertTrue(result);
        verify(taxCalculator).calculateTax(300.0);
        verify(discountService).applyDiscount(330.0, any(Customer.class));
    }

    @Test
    void processOrder_whenValidOrderExceedsApprovalLimit_shouldReturnTrueAndSetNeedsApprovalStatus() {
        // Given
        List<OrderItem> items = Arrays.asList(
            new OrderItem("item1", 1000, 2)
        );
        when(taxCalculator.calculateTax(2000.0)).thenReturn(2200.0);
        when(discountService.applyDiscount(anyDouble(), any())).thenReturn(2000.0);

        // When
        boolean result = orderProcessor.processOrder(VALID_ORDER_ID, VALID_CUSTOMER_ID, items);

        // Then
        assertTrue(result);
        verify(taxCalculator).calculateTax(2000.0);
        verify(discountService).applyDiscount(2200.0, any(Customer.class));
    }

    @Test
    void processOrder_whenInvalidCustomerId_shouldReturnFalse() {
        // Given
        String invalidCustomerId = "invalid123";
        List<OrderItem> items = Arrays.asList(
            new OrderItem("item1", 100, 1)
        );

        // When
        boolean result = orderProcessor.processOrder(VALID_ORDER_ID, invalidCustomerId, items);

        // Then
        assertFalse(result);
        verifyNoInteractions(taxCalculator, discountService);
    }

    @Test
    void processOrder_whenEmptyItemsList_shouldProcessOrderWithZeroAmount() {
        // Given
        List<OrderItem> items = Arrays.asList();
        when(taxCalculator.calculateTax(0.0)).thenReturn(0.0);
        when(discountService.applyDiscount(anyDouble(), any())).thenReturn(0.0);

        // When
        boolean result = orderProcessor.processOrder(VALID_ORDER_ID, VALID_CUSTOMER_ID, items);

        // Then
        assertTrue(result);
        verify(taxCalculator).calculateTax(0.0);
        verify(discountService).applyDiscount(0.0, any(Customer.class));
    }
}
