# copilot-demo
Demo repository for showcasing Copilot functionality

## Purpose
This project demonstrates the functionality of Copilot by showcasing a refactored Java-based order processing system. It includes features like tax calculation, discount application, and order lifecycle management.

## Code/Module Structure
- **`demo-refactoring`**: Contains the refactored code for the order processing system.
    - `OrderProcessor.java`: Handles order processing, validation, and notifications.
    - `Customer.java`: Represents customer details.
    - `OrderProcessingDemo.java`: Demonstrates the usage of the order processing system.
    - `TaxCalculator.java` and `DiscountService.java`: Provide tax and discount calculations.

## Key Features
- Modular and testable design using dependency injection.
- Clear separation of concerns for better maintainability.
- Support for customer registration and order processing.
- Tax and discount calculations based on customer type and order details.
- Notifications for processed orders.

## Setup Instructions
1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/copilot-demo.git
   cd copilot-demo