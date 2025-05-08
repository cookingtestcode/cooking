package STEP;
import static org.junit.Assert.*;
import java.util.*;
import io.cucumber.java.en.*;
import model.*;

public class SchesulandTask {
    private Task task;
    private Chef assignedChef;
    private List<Chef> chefs = new ArrayList<>();

    @Given("a task is created")
    public void a_task_is_created() {
        task = new Task("Grill Steak", "Grilling");
    }

    @When("I assign the task to a chef based on their expertise and current workload")
    public void i_assign_task_to_chef() {
        chefs.add(new Chef("Ali", List.of("Grilling"), 2));
        chefs.add(new Chef("Sara", List.of("Baking", "Grilling"), 1));
        chefs.add(new Chef("John", List.of("Boiling"), 0));

        assignedChef = chefs.stream()
                .filter(c -> c.getExpertise().contains(task.getType()))
                .min(Comparator.comparingInt(Chef::getWorkload))
                .orElse(null);

        if (assignedChef != null) {
            assignedChef.assignTask(task);
        }
    }

    @Then("the chef with the least tasks and required expertise should be assigned the task")
    public void the_correct_chef_should_be_assigned() {
        assertNotNull(assignedChef);
        assertTrue(assignedChef.getTasks().contains(task));
        assertTrue(assignedChef.getExpertise().contains(task.getType()));
    }

    @Given("a chef is assigned a task")
    public void chef_is_assigned_a_task() {
        assignedChef = new Chef("Khalid", List.of("Grilling"), 0);
        task = new Task("Grill Chicken", "Grilling");
        assignedChef.assignTask(task);
    }
    @When("the task is assigned")
    public void the_task_is_assigned() {
        if (assignedChef != null && task != null && !assignedChef.getTasks().contains(task)) {
            assignedChef.assignTask(task);
        }
    }
    @Then("the chef's task list should include the newly assigned task")
    public void task_should_be_in_chef_task_list() {
        assertTrue(assignedChef.getTasks().contains(task));
    }

    @Given("a chef has been assigned a task")
    public void chef_has_been_assigned_task() {
        chef_is_assigned_a_task();
    }

    @Then("the chef should receive a notification about the new task")
    public void chef_should_receive_notification() {
        assertTrue(assignedChef.getNotifications().contains("New Task Assigned: Grill Chicken"));
    }
}
