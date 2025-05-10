Feature: Billing and Financial Reporting

  Scenario: Generate invoice after customer order
    Given the customer has completed an order
    When the system processes the payment
    Then the customer should receive a detailed invoice
    And the invoice should be saved in the system

  Scenario: Generate financial report for admin
    Given the system has multiple completed orders
    When the admin requests a financial report
    Then the system should generate a report showing total revenue and order count