package com.copilot.demo.refactoring.case1;

/**
 * Enumeration of possible order statuses in the system.
 * Orders can be in one of three states:
 * - PENDING: Initial state of a new order
 * - NEEDS_APPROVAL: Order requires approval due to high value
 * - APPROVED: Order has been approved and is ready for processing
 *
 * @author Copilot Demo
 * @version 1.0
 */
public enum OrderStatus {
    PENDING,
    NEEDS_APPROVAL,
    APPROVED
}

