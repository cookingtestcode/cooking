package STEP;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SchesulandTask {
     List<Map<String, String>> chefData;
    String taskType;
    String assignedChefName;
    @Given("the following chefs exist:")
    public void the_following_chefs_exist(io.cucumber.datatable.DataTable dataTable) {
        chefData = dataTable.asMaps();
    }

    @Given("a task of type {string}")
    public void a_task_of_type(String type) {
        taskType = type;
    }

    @When("the task is assigned to a chef")
    public void the_task_is_assigned_to_a_chef() {
        int minLoad = Integer.MAX_VALUE;
        String selectedChef = null;

        for (Map<String, String> chef : chefData) {
            List<String> expertise = Arrays.asList(chef.get("expertise").split(","));
            int load = Integer.parseInt(chef.get("currentLoad"));

            if (expertise.contains(taskType) && load < minLoad) {
                minLoad = load;
                selectedChef = chef.get("name");
            }
        }

        assignedChefName = selectedChef;
    }

    @When("the task is assigned to {string}")
    public void the_task_is_assigned_to_specific(String chefName) {
        assignedChefName = chefName;
    }

    @Then("the task should be assigned to {string}")
    public void the_task_should_be_assigned_to(String expectedChefName) {
        assertNotNull(assignedChefName, "No chef was assigned");
        assertEquals(expectedChefName, assignedChefName);
    }

    @Then("{string} should have the task in their task list")
    public void chef_should_have_the_task(String chefName) {
        List<String> fakeTaskList = new ArrayList<>();
        assertTrue(fakeTaskList.contains(taskType), "Task not found in chef's task list");
    }

    @Then("{string} should be notified about the task")
    public void chef_should_be_notified(String chefName) {
        boolean notified = false;
        assertTrue(notified, "Chef was not notified");
    }
}
