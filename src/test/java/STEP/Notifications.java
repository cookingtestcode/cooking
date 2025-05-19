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

public class Notifications {
    private CustomerProfile customer;
    private Chef chef;
    private InventoryManager inventoryManager;
    private NotificationService notificationService;
    private LocalDateTime currentTime;

    public Notifications() {
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

    @Given("a customer has a scheduled meal delivery after two hours")
    public void a_customer_has_a_scheduled_meal_delivery_after_two_hours() {
        customer = new CustomerProfile();
        customer.setName("FutureCustomer");
        LocalDateTime deliveryTime = LocalDateTime.now().plusHours(2);
        customer.scheduleMeal(deliveryTime);
    }


    @Then("the customer should not receive a delivery reminder because it's too early")
    public void the_customer_should_not_receive_a_delivery_reminder_because_its_too_early() {
        boolean result = notificationService.sendDeliveryReminder(customer, customer.getMealDeliveryTime(), LocalDateTime.now());
        assertFalse(result, "Reminder was incorrectly sent before the 1-hour window.");
    }

    @Given("a chef has a cooking task scheduled after two hours")
    public void a_chef_has_a_cooking_task_scheduled_after_two_hours() {
        chef = new Chef("FutureChef", Arrays.asList("Sushi"), 0);
        LocalDateTime taskTime = LocalDateTime.now().plusHours(2);
        chef.scheduleCookingTask(taskTime);
    }


    @Then("the chef should not receive a task notification because it's too early")
    public void the_chef_should_not_receive_a_task_notification_because_its_too_early() {
        boolean result = notificationService.notifyChefOfUpcomingTask(chef, LocalDateTime.now());
        assertFalse(result, "Notification was incorrectly sent before 1 hour window.");
    }


    @Given("an ingredient is available above the minimum threshold")
    public void an_ingredient_is_available_above_the_minimum_threshold() {
        inventoryManager.setIngredientStock("Tomato", 20); // sufficient
    }

    @Then("no alert should be sent to the manager")
    public void no_alert_should_be_sent_to_the_manager() {
        boolean result = notificationService.notifyManagerOfLowStock(inventoryManager);
        assertFalse(result, "Alert was incorrectly sent despite sufficient stock.");
    }

    @When("sending a delivery reminder with null customer")
    public void sending_a_delivery_reminder_with_null_customer() {
        currentTime = LocalDateTime.now();
    }

    @Then("the reminder should not be sent")
    public void the_reminder_should_not_be_sent() {
        boolean result = notificationService.sendDeliveryReminder(null, LocalDateTime.now().plusHours(1), currentTime);
        assertFalse(result, "Reminder should not be sent if input is invalid.");
    }

    @When("sending a delivery reminder with null delivery time")
    public void sending_a_delivery_reminder_with_null_delivery_time() {
        customer = new CustomerProfile();
        customer.setName("NullTimeCustomer");
        currentTime = LocalDateTime.now();
    }


    @When("notifying chef with null value")
    public void notifying_chef_with_null_value() {
        currentTime = LocalDateTime.now();
    }

    @Then("the notification should not be sent")
    public void the_notification_should_not_be_sent() {
        boolean result = notificationService.notifyChefOfUpcomingTask(null, currentTime);
        assertFalse(result, "Notification should not be sent when chef is null.");
    }

    @When("checking inventory with null manager")
    public void checking_inventory_with_null_manager() {
        // no setup needed
    }


    @Then("the alert should not be sent")
    public void the_alert_should_not_be_sent() {
        boolean result = notificationService.notifyManagerOfLowStock(null);
        assertFalse(result, "Alert should not be sent when inventory manager is null.");
    }


    @Given("a chef with no scheduled task")
    public void a_chef_with_no_scheduled_task() {
        chef = new Chef("LazyChef", Arrays.asList("Fusion"), 0);

    }

    @Then("the chef should not receive a task notification")
    public void the_chef_should_not_receive_a_task_notification() {
        boolean result = notificationService.notifyChefOfUpcomingTask(chef, currentTime);
        assertFalse(result, "Chef should not receive notification when no task is scheduled.");
    }

}
