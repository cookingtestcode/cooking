package model;

import java.util.*;

public class InventoryManager {
    private final Map<String, Integer> inventory;
    private final Map<String, Integer> lowStockThresholds;
    private final Map<String, Integer> criticalStockLevels;
    private final Map<String, Supplier> supplierDetails;
    private final List<String> managerNotifications;

    public static class Supplier {
        private String name;
        private String email;

        public Supplier(String name, String email) {
            this.name = name;
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }
    }

    public InventoryManager() {
        inventory = new HashMap<>();
        lowStockThresholds = new HashMap<>();
        criticalStockLevels = new HashMap<>();
        supplierDetails = new HashMap<>();
        managerNotifications = new ArrayList<>();
        initializeSampleData();
    }

    private void initializeSampleData() {
        inventory.put("Cheese", 10);
        inventory.put("Tomatoes", 10);

        lowStockThresholds.put("Cheese", 5);
        lowStockThresholds.put("Tomatoes", 5);

        criticalStockLevels.put("Cheese", 2);
        criticalStockLevels.put("Tomatoes", 2);

        supplierDetails.put("Cheese", new Supplier("Cheese Supplier Inc.", "cheese@supplier.com"));
        supplierDetails.put("Tomatoes", new Supplier("Tomato World", "tomato@supplier.com"));
    }

    public Map<String, Integer> getInventory() {
        return new HashMap<>(inventory);
    }

    public void setIngredientStock(String ingredient, int quantity) {
        inventory.put(ingredient, quantity);
        if (isRestockSuggested(ingredient)) {
            managerNotifications.add("Low stock alert: " + ingredient + " (Quantity: " + quantity + ")");
        }
    }

    public boolean isRestockSuggested(String ingredient) {
        return inventory.getOrDefault(ingredient, 0) <= lowStockThresholds.getOrDefault(ingredient, 5);
    }

    public boolean isSupplierAPIConnected() {
        return true;
    }

    public String fetchSupplierPrice(String ingredient) {
        return supplierDetails.containsKey(ingredient) ? "$" + (10 + ingredient.length() % 5) + ".00" : null;
    }

    public boolean hasSupplierFor(String ingredient) {
        return supplierDetails.containsKey(ingredient);
    }

    public boolean checkAndAutoOrder(String ingredient) {
        if (inventory.getOrDefault(ingredient, 0) < criticalStockLevels.getOrDefault(ingredient, 2)
                && hasSupplierFor(ingredient)) {
            managerNotifications.add("Auto-order placed for: " + ingredient);
            return true;
        }
        return false;
    }

    public List<String> getManagerNotifications() {
        return new ArrayList<>(managerNotifications);
    }
}