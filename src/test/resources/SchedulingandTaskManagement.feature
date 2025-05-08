Feature: Scheduling and Task Management

  As a kitchen manager,
  I want to assign tasks to chefs based on their workload and expertise,
  So that I can ensure balanced workloads and efficiency.

  As a chef,
  I want to receive notifications about my assigned cooking tasks,
  So that I can prepare meals on time.

  Scenario: Assign a task to a chef with appropriate expertise and the least workload
    Given a task is created
    When I assign the task to a chef based on their expertise and current workload
    Then the chef with the least tasks and required expertise should be assigned the task

  Scenario: Ensure the task is added to the chef's task list
    Given a chef is assigned a task
    When the task is assigned
    Then the chef's task list should include the newly assigned task

  Scenario: Notify the chef about the task assignment
    Given a chef has been assigned a task
    When the task is assigned
    Then the chef should receive a notification about the new task
