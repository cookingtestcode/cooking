Feature: Task Scheduling and Assignment

  Scenario: Assign task to suitable chef
    Given a task is created with deadline
    When I assign the task to a chef based on their expertise, current workload, and deadline
    Then the chef with the least tasks and required expertise should be assigned the task

  Scenario: Verify task assignment to chef
    Given a chef is assigned a task with deadline
    When the task is assigned
    Then the chef's task list should include the newly assigned task with deadline

  Scenario: Chef receives task notification
    Given a chef has been assigned a task
    Then the chef should receive a notification about the new task with deadline