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