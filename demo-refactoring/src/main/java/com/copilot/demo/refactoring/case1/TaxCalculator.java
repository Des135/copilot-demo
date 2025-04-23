package com.copilot.demo.refactoring.case1;

/**
 * Handles tax calculations for orders using a fixed tax rate.
 * Provides functionality to calculate the total amount including tax.
 *
 * @author Copilot Demo
 * @version 1.0
 */
public class TaxCalculator {
    private static final double TAX_RATE = 0.2;

    public double calculateTax(double amount) {
        return amount * (1 + TAX_RATE);
    }
}

