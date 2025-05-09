package STEP;

import io.cucumber.java.en.*;
import io.cucumber.java.Before;
import static org.junit.Assert.*;

import model.InventoryManager;

import java.util.Map;

public class InventoryStepDefinitions {

    private InventoryManager inventoryManager;
    private Map<String, Integer> currentInventory;
    private String latestPrice;
    private boolean restockSuggested;
    private boolean purchaseOrderGenerated;

    @Before
    public void setUp() {
        inventoryManager = new InventoryManager();
    }

    // -------------------- Inventory View --------------------

    @Given("the system has access to the inventory database")
    public void givenSystemHasAccessToInventory() {
        assertNotNull("InventoryManager should be initialized", inventoryManager);
    }

    @When("the kitchen manager views the inventory dashboard")
    public void whenManagerViewsInventory() {
        currentInventory = inventoryManager.getInventory();
    }

    @Then("the current stock levels of all ingredients should be displayed in real time")
    public void thenInventoryShouldBeDisplayed() {
        assertNotNull("Inventory data should not be null", currentInventory);
        assertFalse("Inventory should not be empty", currentInventory.isEmpty());
    }

    // -------------------- Low Stock Threshold --------------------

    @Given("an ingredient has reached its low stock threshold")
    public void givenIngredientAtLowStock() {
        inventoryManager.setIngredientStock("Cheese", 4); // Threshold is 5
    }

    @When("the system checks inventory levels")
    public void whenSystemChecksInventory() {
        restockSuggested = inventoryManager.isRestockSuggested("Cheese");
    }

    @Then("the system should suggest restocking that ingredient")
    public void thenRestockShouldBeSuggested() {
        assertTrue("Restocking should be suggested", restockSuggested);
    }

    // -------------------- Supplier Pricing --------------------

    @Given("the system is connected to supplier APIs")
    public void givenSystemConnectedToSuppliers() {
        assertTrue("System should be connected to supplier API", inventoryManager.isSupplierAPIConnected());
    }

    @When("the kitchen manager checks ingredient pricing")
    public void whenManagerChecksPricing() {
        latestPrice = inventoryManager.fetchSupplierPrice("Tomatoes");
    }

    @Then("the system should display the latest prices from suppliers")
    public void thenShowLatestSupplierPrices() {
        assertNotNull("Price should not be null", latestPrice);
        assertTrue("Price should contain $", latestPrice.contains("$"));
    }

    // -------------------- Auto Ordering --------------------

    @Given("an ingredient is below the critical stock level")
    public void givenIngredientIsCriticallyLow() {
        inventoryManager.setIngredientStock("Tomatoes", 1); // Critical is 2
    }

    @And("the system has supplier details for that ingredient")
    public void andSystemHasSupplierDetails() {
        assertTrue("Supplier details must exist", inventoryManager.hasSupplierFor("Tomatoes"));
    }

    @When("the system performs an inventory check")
    public void whenSystemPerformsCheck() {
        purchaseOrderGenerated = inventoryManager.checkAndAutoOrder("Tomatoes");
    }

    @Then("a purchase order should be automatically generated and sent to the supplier")
    public void thenPurchaseOrderShouldBeGenerated() {
        assertTrue("Purchase order should be generated", purchaseOrderGenerated);
    }
}