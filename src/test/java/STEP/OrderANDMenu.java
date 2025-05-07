
package STEP;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.OrderAndMenu;
import org.junit.Assert;

public class OrderANDMenu {
    OrderAndMenu orderManager = new OrderAndMenu();
    String suggestedSubstitution;
    String originalIngredient;
    String substituteIngredient;
    boolean mealIsValid;

    public OrderANDMenu() {
    }

    @Given("the available ingredients are {string}, {string}, {string}")
    public void the_available_ingredients_are(String ing1, String ing2, String ing3) {
        this.orderManager.setAvailableIngredients(new String[]{ing1, ing2, ing3});
    }

    @When("the customer selects ingredients {string}, {string}")
    public void the_customer_selects_ingredients(String ing1, String ing2) {
        this.mealIsValid = this.orderManager.isValidMeal(new String[]{ing1, ing2});
    }

    @Then("the system should accept the meal as valid")
    public void the_system_should_accept_the_meal_as_valid() {
        Assert.assertTrue(this.mealIsValid);
        System.out.println("Meal accepted.");
    }

    @Given("the incompatible ingredient pair is {string} and {string}")
    public void the_incompatible_ingredient_pair_is(String ing1, String ing2) {
        this.orderManager.setIncompatiblePair(ing1, ing2);
    }

    @Then("the system should reject the meal due to incompatibility")
    public void the_system_should_reject_the_meal_due_to_incompatibility() {
        Assert.assertFalse(this.mealIsValid);
    }

    @Given("the ingredient {string} is restricted for the customer")
    public void the_ingredient_is_restricted(String ingredient) {
        this.orderManager.setRestrictedIngredient(ingredient);
    }

    @And("the system has a substitution {string} for {string}")
    public void the_system_has_a_substitution(String substitute, String original) {
        this.orderManager.addSubstitution(original, substitute);
        this.originalIngredient = original;
        this.substituteIngredient = substitute;
    }

    @When("the customer selects {string}")
    public void the_customer_selects(String ingredient) {
        this.suggestedSubstitution = this.orderManager.suggestSubstitution(ingredient);
    }

    @Then("the system should suggest {string}")
    public void the_system_should_suggest(String expectedSubstitute) {
        Assert.assertEquals(expectedSubstitute, this.suggestedSubstitution);
    }

    @Given("a substitution of {string} for {string} has been applied")
    public void a_substitution_of_has_been_applied(String substitute, String original) {
        this.orderManager.applySubstitution(original, substitute);
    }

    @When("the meal is finalized")
    public void the_meal_is_finalized() {
        Assert.assertTrue(this.orderManager.isSubstitutionApplied());
    }

    @Then("the chef should be notified with the substitution details")
    public void the_chef_should_be_notified() {
        Assert.assertNotNull(this.orderManager.getSubstitutionDetails());
        System.out.println("Chef notified: " + this.orderManager.getSubstitutionDetails());
    }
}
