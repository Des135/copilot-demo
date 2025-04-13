package com.copilot.demo.refactoring.case1;

public class DiscountService {
    private static final double PREMIUM_DISCOUNT_RATE = 0.1;

    public double applyDiscount(double amount, Customer customer) {
        if (customer.getType().equals("PREMIUM")) {
            return amount * (1 - PREMIUM_DISCOUNT_RATE);
        }
        return amount;
    }
}
