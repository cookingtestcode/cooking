package model;

import java.util.HashMap;
import java.util.Map;

public class InventoryManager {

    private final Map<String, Integer> inventory;
    private final Map<String, Integer> lowStockThresholds;
    private final Map<String, Integer> criticalStockLevels;
    private final Map<String, String> supplierDetails;

    public InventoryManager() {
        inventory = new HashMap<>();
        lowStockThresholds = new HashMap<>();
        criticalStockLevels = new HashMap<>();
        supplierDetails = new HashMap<>();

        // بيانات أولية تجريبية
        initializeSampleData();
    }

    private void initializeSampleData() {
        inventory.put("Cheese", 10);
        inventory.put("Tomatoes", 10);

        lowStockThresholds.put("Cheese", 5);
        lowStockThresholds.put("Tomatoes", 5);

        criticalStockLevels.put("Cheese", 2);
        criticalStockLevels.put("Tomatoes", 2);

        supplierDetails.put("Cheese", "Cheese Supplier Inc.");
        supplierDetails.put("Tomatoes", "Tomato World");
    }

    public Map<String, Integer> getInventory() {
        return inventory;
    }

    public void setIngredientStock(String ingredient, int quantity) {
        inventory.put(ingredient, quantity);
    }

    public boolean isRestockSuggested(String ingredient) {
        int quantity = inventory.getOrDefault(ingredient, 0);
        int threshold = lowStockThresholds.getOrDefault(ingredient, 5);
        return quantity <= threshold;
    }

    public boolean isSupplierAPIConnected() {
        // تمثيل لاتصال ناجح بـ API المورد
        return true;
    }

    public String fetchSupplierPrice(String ingredient) {
        // تمثيل لسعر تجريبي يعتمد على طول اسم المكون
        return "$" + (2 + ingredient.length()) + ".00";
    }

    public boolean hasSupplierFor(String ingredient) {
        return supplierDetails.containsKey(ingredient);
    }

    public boolean checkAndAutoOrder(String ingredient) {
        int quantity = inventory.getOrDefault(ingredient, 0);
        int critical = criticalStockLevels.getOrDefault(ingredient, 2);
        if (quantity < critical && hasSupplierFor(ingredient)) {
            System.out.println("Purchase order sent to supplier for: " + ingredient);
            return true;
        }
        return false;
    }
}