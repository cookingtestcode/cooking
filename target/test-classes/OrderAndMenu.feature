Feature: Order and Menu Customization

  Scenario : Customer creates a custom meal with valid ingredients
    Given the available ingredients are "<available_1>", "<available_2>", "<available_3>"
    When the customer selects ingredients "<selected_1>", "<selected_2>"
    Then the system should accept the meal as valid


  Scenario : Customer selects incompatible ingredients
    Given the incompatible ingredient pair is "<ing_1>" and "<ing_2>"
    When the customer selects ingredients "<ing_1>", "<ing_2>"
    Then the system should reject the meal due to incompatibility



  Scenario : Suggest substitution for restricted ingredient
    Given the ingredient "<restricted>" is restricted for the customer
    And the system has a substitution "<substitute>" for "<restricted>"
    When the customer selects "<restricted>"
    Then the system should suggest "<substitute>"


  Scenario : Notify chef when a substitution is applied
    Given a substitution of "<substitute>" for "<original>" has been applied
    When the meal is finalized
    Then the chef should be notified with the substitution details
