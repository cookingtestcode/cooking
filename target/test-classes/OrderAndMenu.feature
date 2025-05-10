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