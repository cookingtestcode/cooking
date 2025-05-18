package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryManager {
    private final Map<String, Integer> inventory = new HashMap<>();
    private final Map<String, Integer> lowStockThresholds = new HashMap<>();
    private final Map<String, Integer> criticalStockLevels = new HashMap<>();
    private final Map<String, Supplier> supplierDetails = new HashMap<>();
    private final List<String> managerNotifications = new ArrayList<>();

    public InventoryManager() {
        this.initializeSampleData();
    }

    private void initializeSampleData() {
        this.inventory.put("Cheese", 10);
        this.inventory.put("Tomatoes", 10);
        this.lowStockThresholds.put("Cheese", 5);
        this.lowStockThresholds.put("Tomatoes", 5);
        this.criticalStockLevels.put("Cheese", 2);
        this.criticalStockLevels.put("Tomatoes", 2);
        this.supplierDetails.put("Cheese", new Supplier("Cheese Supplier Inc.", "cheese@supplier.com"));
        this.supplierDetails.put("Tomatoes", new Supplier("Tomato World", "tomato@supplier.com"));
    }

    public boolean checkLowStockAndNotifyManager() {
        boolean lowStockDetected = false;
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            String ingredient = entry.getKey();
            int quantity = entry.getValue();
            int threshold = lowStockThresholds.getOrDefault(ingredient, 5);
            if (quantity <= threshold) {
                managerNotifications.add("Low stock alert: " + ingredient + " (Quantity: " + quantity + ")");
                lowStockDetected = true;
                System.out.println("Low stock detected for " + ingredient + ": Quantity = " + quantity + ", Threshold = " + threshold);
            }
        }
        if (!lowStockDetected) {
            System.out.println("No ingredients below low stock threshold.");
        }
        return lowStockDetected;
    }

    public Map<String, Integer> getInventory() {
        return new HashMap<>(this.inventory);
    }

    public void setIngredientStock(String ingredient, int quantity) {
        this.inventory.put(ingredient, quantity);
        if (this.isRestockSuggested(ingredient)) {
            this.managerNotifications.add("Low stock alert: " + ingredient + " (Quantity: " + quantity + ")");
        }
    }

    public boolean isRestockSuggested(String ingredient) {
        return this.inventory.getOrDefault(ingredient, 0) <= this.lowStockThresholds.getOrDefault(ingredient, 5);
    }

    public boolean isSupplierAPIConnected() {
        return true;
    }

    public String fetchSupplierPrice(String ingredient) {
        return this.supplierDetails.containsKey(ingredient) ? "$" + (10 + ingredient.length() % 5) + ".00" : null;
    }

    public boolean hasSupplierFor(String ingredient) {
        return this.supplierDetails.containsKey(ingredient);
    }

    public boolean checkAndAutoOrder(String ingredient) {
        if (this.inventory.getOrDefault(ingredient, 0) < this.criticalStockLevels.getOrDefault(ingredient, 2) && this.hasSupplierFor(ingredient)) {
            this.managerNotifications.add("Auto-order placed for: " + ingredient);
            return true;
        } else {
            return false;
        }
    }

    public List<String> getManagerNotifications() {
        return new ArrayList<>(this.managerNotifications);
    }

    public static class Supplier {
        private String name;
        private String email;

        public Supplier(String name, String email) {
            this.name = name;
            this.email = email;
        }

        public String getName() {
            return this.name;
        }

        public String getEmail() {
            return this.email;
        }
    }
}