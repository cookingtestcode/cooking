Feature: Scheduling and Task Management

  As a kitchen manager,
  I want to assign tasks to chefs based on their workload and expertise,
  So that I can ensure balanced workloads and efficiency.

  Scenario: Assign a task to a chef with matching expertise and least load
    Given a task of type "grill"
    When the task is assigned to a chef
    Then the task should be assigned to "Chef C"

  Scenario: Task should be added to the chef's task list
    Given a task of type "grill"
    When the task is assigned to "Chef A"
    Then "Chef A" should have the task in their task list

  Scenario: Notify the chef after assigning the task
    Given a task of type "grill"
    And the task is assigned to "Chef A"
    Then "Chef A" should be notified about the task