package STEP;

import io.cucumber.java.en.*;
import io.cucumber.java.Before;
import static org.junit.Assert.*;

import model.InventoryManager;
import java.util.Map;

public class InventoryStepDefinitions_Test {
    private InventoryManager inventoryManager;
    private Map<String, Integer> currentInventory;
    private String latestPrice;
    private boolean restockSuggested;
    private boolean purchaseOrderGenerated;
    private String notification;

    @Before
    public void setUp() {
        inventoryManager = new InventoryManager();
    }

    @Given("the system has access to the inventory database")
    public void givenSystemHasAccessToInventory() {
        assertNotNull(inventoryManager);
    }

    @When("the kitchen manager views the inventory dashboard")
    public void whenManagerViewsInventory() {
        currentInventory = inventoryManager.getInventory();
    }

    @Then("the current stock levels of all ingredients should be displayed in real time")
    public void thenInventoryShouldBeDisplayed() {
        assertNotNull(currentInventory);
        assertFalse(currentInventory.isEmpty());
    }

    @Given("an ingredient has reached its low stock threshold")
    public void givenIngredientAtLowStock() {
        inventoryManager.setIngredientStock("Cheese", 4);
    }

    @When("the system checks inventory levels")
    public void whenSystemChecksInventory() {
        restockSuggested = inventoryManager.isRestockSuggested("Cheese");
    }

    @Then("the system should suggest restocking that ingredient")
    public void thenRestockShouldBeSuggested() {
        assertTrue(restockSuggested);
    }

    @Then("the kitchen manager should receive a low stock notification")
    public void managerReceivesNotification() {
        notification = inventoryManager.getManagerNotifications().stream()
                .filter(n -> n.contains("Cheese"))
                .findFirst()
                .orElse(null);
        assertNotNull(notification);
        assertTrue(notification.contains("Low stock alert"));
    }

    @Given("the system is connected to supplier APIs")
    public void givenSystemConnectedToSuppliers() {
        assertTrue(inventoryManager.isSupplierAPIConnected());
    }

    @When("the kitchen manager checks ingredient pricing")
    public void whenManagerChecksPricing() {
        latestPrice = inventoryManager.fetchSupplierPrice("Tomatoes");
    }

    @Then("the system should display the latest prices from suppliers")
    public void thenShowLatestSupplierPrices() {
        assertNotNull(latestPrice);
        assertTrue(latestPrice.contains("$"));
    }

    @Given("an ingredient is below the critical stock level")
    public void givenIngredientIsCriticallyLow() {
        inventoryManager.setIngredientStock("Tomatoes", 1);
    }

    @And("the system has supplier details for that ingredient")
    public void andSystemHasSupplierDetails() {
        assertTrue(inventoryManager.hasSupplierFor("Tomatoes"));
    }

    @When("the system performs an inventory check")
    public void whenSystemPerformsCheck() {
        purchaseOrderGenerated = inventoryManager.checkAndAutoOrder("Tomatoes");
    }

    @Then("a purchase order should be automatically generated and sent to the supplier")
    public void thenPurchaseOrderShouldBeGenerated() {
        assertTrue(purchaseOrderGenerated);
    }

    @Then("the kitchen manager should receive an auto-order notification")
    public void thenManagerReceivesAutoOrderNotification() {
        notification = inventoryManager.getManagerNotifications().stream()
                .filter(n -> n.contains("Auto-order placed"))
                .findFirst()
                .orElse(null);
        assertNotNull("Auto-order notification should exist", notification);
    }
}