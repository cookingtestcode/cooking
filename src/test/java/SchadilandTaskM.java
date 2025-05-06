import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SchadilandTaskM {
    @Given("a task of type {string}")
    public void aTaskOfType(String arg0) {
    }

    @When("the task is assigned to a chef")
    public void theTaskIsAssignedToAChef() {
    }

    @Then("the task should be assigned to {string}")
    public void theTaskShouldBeAssignedTo(String arg0) {
    }

    @When("the task is assigned to {string}")
    public void theTaskIsAssignedTo(String arg0) {
    }

    @Then("{string} should have the task in their task list")
    public void shouldHaveTheTaskInTheirTaskList(String arg0) {
    }

    @Then("{string} should be notified about the task")
    public void shouldBeNotifiedAboutTheTask(String arg0) {
    }
}
