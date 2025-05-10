Feature: Inventory and Supplier Management

  Scenario: View current stock levels of ingredients
    Given the system has access to the inventory database
    When the kitchen manager views the inventory dashboard
    Then the current stock levels of all ingredients should be displayed in real time

  Scenario: Suggest restocking when ingredients are low
    Given an ingredient has reached its low stock threshold
    When the system checks inventory levels
    Then the system should suggest restocking that ingredient
    And the kitchen manager should receive a low stock notification

  Scenario: Fetch real-time prices from suppliers
    Given the system is connected to supplier APIs
    When the kitchen manager checks ingredient pricing
    Then the system should display the latest prices from suppliers

  Scenario: Automatically generate purchase orders when stock is critically low
    Given an ingredient is below the critical stock level
    And the system has supplier details for that ingredient
    When the system performs an inventory check
    Then a purchase order should be automatically generated and sent to the supplier
    And the kitchen manager should receive an auto-order notification