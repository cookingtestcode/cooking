Feature: Order and Menu Customization

  Background:
    Given the order system is initialized

  Scenario: Customer creates a custom meal with valid ingredients
    Given the available ingredients are "Tomato", "Lettuce", "Chicken"
    When the customer selects ingredients "Tomato", "Chicken"
    Then the system should accept the meal as valid

  Scenario: Customer selects incompatible ingredients
    Given the incompatible ingredient pairs are "dairy" and "fish", "meat" and "cheese"
    When the customer selects ingredients "dairy", "fish"
    Then the system should reject the meal due to incompatibility

  Scenario: Customer selects ingredients with allergy restriction
    Given the customer has an allergy to "nuts"
    And the available ingredients are "Tomato", "Lettuce", "nuts"
    When the customer selects ingredients "Tomato", "nuts"
    Then the system should reject the meal due to allergy

  Scenario: Suggest substitution for restricted ingredient
    Given the ingredient "dairy" is restricted for the customer
    And the system has a substitution "almond milk" for "dairy"
    When the customer selects "dairy"
    Then the system should suggest "almond milk"

  Scenario: Notify chef when a substitution is applied
    Given a substitution of "almond milk" for "dairy" has been applied
    When the meal is finalized
    Then the chef should be notified with the substitution details


  Scenario: Customer selects an unavailable ingredient
    Given the available ingredients are "Tomato", "Lettuce", "Chicken"
    When the customer selects ingredients "Tomato", "Beef"
    Then the system should reject the meal due to unavailable ingredient

  Scenario: Customer does not select any ingredients
    When the customer selects no ingredients
    Then the system should reject the meal due to missing selection



  Scenario: Customer selects a restricted ingredient without substitution
    Given the ingredient "meat" is restricted for the customer
    When the customer selects "meat"
    Then the system should not suggest a substitution


  Scenario: Apply multiple substitutions and notify chef
    Given the ingredient "milk" is restricted for the customer
    And the system has a substitution "soy milk" for "milk"
    And the ingredient "cheese" is restricted for the customer
    And the system has a substitution "vegan cheese" for "cheese"
    When the customer selects "milk" and "cheese"
    And both substitutions are applied
    Then the chef should be notified with all substitution details


  Scenario: Customer adds and removes an order item
    Given the order system is initialized
    And the menu has items "Burger" priced 5.0 and "Fries" priced 2.5
    When the customer adds "Burger" to the order
    Then the current order should contain "Burger"
    When the customer removes "Burger" from the order
    Then the current order should not contain "Burger"

  Scenario: Calculate total order price
    Given the order system is initialized
    And the menu has items "Burger" priced 5.0 and "Fries" priced 2.5
    When the customer adds "Burger" to the order
    And the customer adds "Fries" to the order
    Then the total order price should be 7.5


