Feature: Notifications and Alerts
  As a user of the restaurant management system
  I want to receive timely notifications and alerts
  So that I can stay informed about important events

  Scenario: Customer receives a reminder for an upcoming meal delivery
    Given a customer has a scheduled meal delivery for tomorrow
    When the reminder time is reached
    Then the customer should receive a notification about the upcoming delivery

  Scenario: Chef receives a notification for scheduled cooking tasks
    Given a chef has a cooking task scheduled for today
    When the task is approaching within the next hour
    Then the chef should receive a notification to prepare the meal

  Scenario: Kitchen manager is alerted when an ingredient is low in stock
    Given an ingredient has dropped below the minimum stock threshold
    When the system checks inventory levels
    Then the kitchen manager should receive an alert about the low stock






  Scenario: Customer should not receive reminder because it's too early
    Given a customer has a scheduled meal delivery after two hours
    When the reminder time is reached
    Then the customer should not receive a delivery reminder because it's too early

  Scenario: Chef should not receive notification because task is too far
    Given a chef has a cooking task scheduled after two hours
    When the task is approaching within the next hour
    Then the chef should not receive a task notification because it's too early

  Scenario: Kitchen manager should not receive an alert because stock is sufficient
    Given an ingredient is available above the minimum threshold
    When the system checks inventory levels
    Then no alert should be sent to the manager





  Scenario: No reminder is sent when customer is null
    When sending a delivery reminder with null customer
    Then the reminder should not be sent

  Scenario: No reminder is sent when delivery time is null
    When sending a delivery reminder with null delivery time
    Then the reminder should not be sent

  Scenario: No notification is sent to chef when chef is null
    When notifying chef with null value
    Then the notification should not be sent

  Scenario: No alert is sent when inventory manager is null
    When checking inventory with null manager
    Then the alert should not be sent

  Scenario: No notification is sent to chef when scheduled task is null
    Given a chef with no scheduled task
    When the task is approaching within the next hour
    Then the chef should not receive a task notification
