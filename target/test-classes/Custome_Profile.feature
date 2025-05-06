Feature: Customer Profile Management
  As a customer,
  I want to save my dietary preferences and allergies,
  So that I can receive meal suggestions that match my needs and avoid harmful ingredients.

  Scenario: Saving dietary preferences and allergies
    Given the customer has logged into their account
    When they navigate to the preferences page
    And they select "<preference>" as a dietary preference
    And they add "<allergy>" as an allergy
    And they save their preferences
    Then the system should store the preferences successfully
    And future meal suggestions should not include <allergy>

  Scenario: Chef views customer dietary preferences
    Given the chef has access to the customer's profile
    When they view the dietary preferences
    Then they should see that the customer is "<preference>"
    And they should be aware of the allergy to "<allergy>"

  Scenario: Customer reorders a previouo3ws meal
    Given the customer is viewing their past meal orders
    When they click on "Reorder" for a specific meal
    Then the system should add that meal to their current order
    And confirm that the meal has been successfully added

  Scenario: Chef accesses customer order history
    Given the chef has access to the customer's profile
    When they open the order history section
    Then they should see the customer's past meals
    And they should be able to suggest a personalized meal plan based on preferences and history


  Scenario: System admin stores customer order history
    Given the system administrator is logged in
    When a customer completes a new order
    Then the system should store the order in the customer's history
    And confirm the order is stored successfully

  Scenario: System admin retrieves customer order history
    Given the system administrator is logged in
    When they request the order history for a customer
    Then the system should retrieve and display the customer's past meal orders
