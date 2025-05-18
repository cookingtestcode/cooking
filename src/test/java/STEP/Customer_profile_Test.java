package STEP;

import io.cucumber.java.en.*;
import model.CustomerProfile;
import org.junit.Assert;

import java.util.*;

public class Customer_profile_Test {
    private static final CustomerProfile customer = new CustomerProfile();
    private static final Map<String, String> savedPreferences = new HashMap<>();
    private static final Map<String, List<String>> orderHistoryDatabase = new HashMap<>();
    private String dietaryPreference;
    private String allergy;
    private List<String> mealSuggestions = new ArrayList<>();
    private List<String> pastMealOrders = new ArrayList<>();
    private List<String> currentOrder = new ArrayList<>();
    private List<String> personalizedSuggestions;
    private Map<String, Integer> orderTrends;

    @Given("the customer has logged into their account")
    public void customerLoggedIn() {
        System.out.println("Customer logged in.");
    }

    @When("they navigate to the preferences page")
    public void navigateToPreferencesPage() {
        System.out.println("Navigating to preferences page.");
    }

    @And("they select {string} as a dietary preference")
    public void selectDietaryPreference(String preference) {
        customer.setDietaryPreference(preference);
        savedPreferences.put("preference", preference);
        this.dietaryPreference = preference;
        System.out.println("Dietary preference set to: " + preference);
    }

    @And("they add {string} as an allergy")
    public void addAllergy(String allergy) {
        customer.setAllergy(allergy);
        savedPreferences.put("allergy", allergy);
        this.allergy = allergy;
        System.out.println("Allergy added: " + allergy);
    }

    @And("they save their preferences")
    public void savePreferences() {
        List<String> availableMeals = Arrays.asList("Vegan Pasta", "Tomato Soup", "Lentil Salad", "Peanut Curry");
        customer.generateMealSuggestions(availableMeals);
        this.mealSuggestions = new ArrayList<>(customer.getMealSuggestions());
    }

    @Then("the system should store the preferences successfully")
    public void systemStoresPreferences() {
        Assert.assertNotNull(customer.getDietaryPreference());
        Assert.assertNotNull(customer.getAllergy());
    }

    @And("future meal suggestions should not include {string}")
    public void future_meal_suggestions_should_not_include_allergy(String allergy) {
        for (String meal : mealSuggestions) {
            Assert.assertFalse("Meal '" + meal + "' contains allergy: " + allergy,
                    meal.toLowerCase().contains(allergy.toLowerCase()));
        }
    }

    @Given("the chef has access to the customer's profile")
    public void chefAccessToProfile() {
        if (this.pastMealOrders.isEmpty()) {
            this.pastMealOrders = Arrays.asList("Vegan Pasta", "Tofu Bowl", "Tomato Soup");
        }
        Assert.assertTrue("Preferences must exist", !savedPreferences.isEmpty() ||
                customer.getDietaryPreference() != null && customer.getAllergy() != null);
    }

    @When("they view the dietary preferences")
    public void viewDietaryPreferences() {
        System.out.println("Chef sees preferences: " + savedPreferences);
    }

    @Then("they should see that the customer is {string}")
    public void verifyCustomerDietaryPreference(String expected) {
        String actual = getSavedPreference("preference", customer.getDietaryPreference());
        Assert.assertEquals(expected, actual);
    }

    @And("they should be aware of the allergy to {string}")
    public void verifyCustomerAllergy(String expected) {
        String actual = getSavedPreference("allergy", customer.getAllergy());
        Assert.assertEquals(expected, actual);
    }

    @Given("the customer is viewing their past meal orders")
    public void customerViewsPastOrders() {
        this.pastMealOrders = Arrays.asList("Vegan Pasta", "Tofu Bowl", "Tomato Soup");
        customer.addMultipleOrders(this.pastMealOrders);
    }

    @When("they click on {string} for a specific meal")
    public void reorderMeal(String action) {
        if ("Reorder".equalsIgnoreCase(action) && !this.pastMealOrders.isEmpty()) {
            this.currentOrder.add(this.pastMealOrders.get(0));
            customer.addOrder(this.pastMealOrders.get(0));
        }
    }

    @Then("the system should add that meal to their current order")
    public void verifyMealAddedToOrder() {
        Assert.assertEquals(1, this.currentOrder.size());
    }

    @And("confirm that the meal has been successfully added")
    public void confirmMealAdded() {
        Assert.assertNotNull(this.currentOrder.get(0));
        System.out.println("Reordered meal: " + this.currentOrder.get(0));
    }

    @When("they open the order history section")
    public void openOrderHistory() {
        Assert.assertFalse(this.pastMealOrders.isEmpty());
    }

    @Then("they should see the customer's past meals")
    public void displayPastMeals() {
        this.pastMealOrders.forEach(meal -> System.out.println("Past meal: " + meal));
    }

    @And("they should be able to suggest a personalized meal plan based on preferences and history")
    public void suggestPersonalizedMealPlan() {
        personalizedSuggestions = customer.suggestPersonalizedMeals();
        Assert.assertFalse("Personalized suggestions should not be empty", personalizedSuggestions.isEmpty());
        System.out.println("Personalized meal plan: " + personalizedSuggestions);
    }

    @Given("the system administrator is logged in")
    public void adminLoggedIn() {
        System.out.println("Admin logged in.");
    }

    @When("a customer completes a new order")
    public void completeNewOrder() {
        String customerId = "customer123";
        List<String> newOrder = Arrays.asList("Quinoa Bowl", "Avocado Salad");
        orderHistoryDatabase.putIfAbsent(customerId, new ArrayList<>());
        orderHistoryDatabase.get(customerId).addAll(newOrder);
        customer.addMultipleOrders(newOrder);
    }

    @Then("the system should store the order in the customer's history")
    public void verifyOrderStored() {
        String customerId = "customer123";
        Assert.assertTrue(orderHistoryDatabase.containsKey(customerId));
        Assert.assertFalse(orderHistoryDatabase.get(customerId).isEmpty());
        Assert.assertFalse(customer.getOrderHistory().isEmpty());
    }

    @And("confirm the order is stored successfully")
    public void confirmOrderStorage() {
        String customerId = "customer123";
        orderHistoryDatabase.get(customerId).forEach(meal -> System.out.println("Stored meal: " + meal));
    }

    @When("they request the order history for a customer")
    public void requestOrderHistory() {
        Assert.assertTrue(orderHistoryDatabase.containsKey("customer123"));
    }

    @Then("the system should retrieve and display the customer's past meal orders")
    public void retrieveCustomerOrderHistory() {
        orderHistoryDatabase.get("customer123").forEach(meal -> System.out.println("Retrieved meal: " + meal));
    }

    private String getSavedPreference(String key, String fallback) {
        return savedPreferences.getOrDefault(key, fallback);
    }
}