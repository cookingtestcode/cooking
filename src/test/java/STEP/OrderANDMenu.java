package STEP;

import io.cucumber.java.en.*;
import model.*;
import org.junit.Assert;
import java.util.List;

public class OrderANDMenu {
    private OrderAndMenu orderManager;
    private CustomerProfile customerProfile;
    private Chef chef;
    private String suggestedSubstitution;
    private boolean mealIsValid;

    @Given("the order system is initialized")
    public void initializeOrderSystem() {
        orderManager = new OrderAndMenu(new Menu());
        customerProfile = new CustomerProfile();
        chef = new Chef("Ali", List.of("Grilling"), 0);
        orderManager.setCustomerProfile(customerProfile);
        orderManager.assignChef(chef);
    }

    @Given("the available ingredients are {string}, {string}, {string}")
    public void setAvailableIngredients(String ing1, String ing2, String ing3) {
        orderManager.setAvailableIngredients(ing1, ing2, ing3);
    }

    @Given("the customer has an allergy to {string}")
    public void setCustomerAllergy(String allergy) {
        customerProfile.setAllergy(allergy);
    }

    @Given("the incompatible ingredient pairs are {string} and {string}, {string} and {string}")
    public void setIncompatiblePairs(String pair1a, String pair1b, String pair2a, String pair2b) {
        orderManager.addIncompatiblePair(pair1a, pair1b);
        orderManager.addIncompatiblePair(pair2a, pair2b);
    }

    @Given("the ingredient {string} is restricted for the customer")
    public void setRestrictedIngredient(String ingredient) {
        orderManager.setRestrictedIngredient(ingredient);
    }

    @Given("the system has a substitution {string} for {string}")
    public void addSubstitution(String substitute, String original) {
        orderManager.addSubstitution(original, substitute);
    }

    @Given("a substitution of {string} for {string} has been applied")
    public void applySubstitution(String substitute, String original) {
        orderManager.addSubstitution(original, substitute);
        orderManager.applySubstitution(original, substitute, chef);
    }

    @When("the customer selects ingredients {string}, {string}")
    public void validateSelectedMeal(String ing1, String ing2) {
        mealIsValid = orderManager.isValidMeal(ing1, ing2);
    }

    @When("the customer selects {string}")
    public void suggestSubstitutionForIngredient(String ingredient) {
        suggestedSubstitution = orderManager.suggestSubstitution(ingredient);
    }

    @When("the meal is finalized")
    public void checkSubstitutionApplied() {
        Assert.assertTrue("Substitution should be applied", orderManager.isSubstitutionApplied());
    }

    @Then("the system should accept the meal as valid")
    public void verifyMealIsValid() {
        Assert.assertTrue("Meal should be valid", mealIsValid);
    }

    @Then("the system should reject the meal due to incompatibility")
    public void verifyMealIsInvalidDueToIncompatibility() {
        Assert.assertFalse("Meal should be invalid due to incompatibility", mealIsValid);
    }

    @Then("the system should reject the meal due to allergy")
    public void verifyMealIsInvalidDueToAllergy() {
        Assert.assertFalse("Meal should be invalid due to allergy", mealIsValid);
    }

    @Then("the system should reject the meal due to invalid ingredients")
    public void verifyMealIsInvalidDueToInvalidIngredients() {
        Assert.assertFalse("Meal should be invalid due to invalid ingredients", mealIsValid);
    }

    @Then("the system should suggest {string}")
    public void verifySuggestedSubstitution(String expectedSubstitute) {
        Assert.assertEquals("Suggested substitution should match", expectedSubstitute, suggestedSubstitution);
    }

    @Then("the chef should be notified with the substitution details")
    public void verifyChefNotification() {
        String substitutionDetails = orderManager.getSubstitutionDetails(); 
        Assert.assertNotNull("Substitution details should not be null", substitutionDetails);

        String expectedNotification = "Substitution Applied: " + substitutionDetails;
        Assert.assertTrue("Chef should receive substitution notification",
                chef.getNotifications().contains(expectedNotification));
    }

    @Then("the system should not suggest any substitution")
    public void verifyNoSubstitutionSuggested() {
        Assert.assertNull("No substitution should be suggested", suggestedSubstitution);
    }
}
