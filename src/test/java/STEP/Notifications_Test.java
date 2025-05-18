package STEP;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.Chef;
import model.CustomerProfile;
import model.InventoryManager;
import model.NotificationService;
import java.time.LocalDateTime;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class Notifications_Test {
    private CustomerProfile customer;
    private Chef chef;
    private InventoryManager inventoryManager;
    private NotificationService notificationService;
    private LocalDateTime currentTime;

    public Notifications_Test() {
        this.notificationService = new NotificationService();
        this.inventoryManager = new InventoryManager();
    }
    @Given("a customer has a scheduled meal delivery for tomorrow")
    public void a_customer_has_a_scheduled_meal_delivery_for_tomorrow() {
        customer = new CustomerProfile();
        customer.setName("TestCustomer");
        LocalDateTime deliveryTime = LocalDateTime.now().plusMinutes(30);
        customer.scheduleMeal(deliveryTime);
    }

    @When("the reminder time is reached")
    public void the_reminder_time_is_reached() {
        currentTime = LocalDateTime.now();
    }

    @Then("the customer should receive a notification about the upcoming delivery")
    public void the_customer_should_receive_a_notification_about_the_upcoming_delivery() {
        boolean result = notificationService.sendDeliveryReminder(customer, customer.getMealDeliveryTime(), currentTime);
        assertTrue(result, "Customer did not receive the delivery reminder.");
    }
    @Given("a chef has a cooking task scheduled for today")
    public void a_chef_has_a_cooking_task_scheduled_for_today() {
        chef = new Chef("Chef Ali", Arrays.asList("Italian"), 0);

        LocalDateTime taskTime = LocalDateTime.now().plusMinutes(30);
        chef.scheduleCookingTask(taskTime);
    }

    @When("the task is approaching within the next hour")
    public void the_task_is_approaching_within_the_next_hour() {

        currentTime = LocalDateTime.now();
    }

    @Then("the chef should receive a notification to prepare the meal")
    public void the_chef_should_receive_a_notification_to_prepare_the_meal() {
        boolean result = notificationService.notifyChefOfUpcomingTask(chef, currentTime);
        assertTrue(result, "Chef did not receive the task notification.");
    }

    @Given("an ingredient has dropped below the minimum stock threshold")
    public void an_ingredient_has_dropped_below_the_minimum_stock_threshold() {

        inventoryManager.setIngredientStock("Cheese", 2);
    }
    @Then("the kitchen manager should receive an alert about the low stock")
    public void the_kitchen_manager_should_receive_an_alert_about_the_low_stock() {
        boolean result = notificationService.notifyManagerOfLowStock(inventoryManager);
        assertTrue(result, "Manager did not receive a low stock alert.");
        assertFalse(inventoryManager.getManagerNotifications().isEmpty(), "No notifications found in manager notifications list.");
        assertTrue(inventoryManager.getManagerNotifications().contains("Low stock alert: Cheese (Quantity: 2)"),
                "Expected low stock notification for Cheese not found.");
    }
}