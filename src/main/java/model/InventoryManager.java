package model;

import java.util.HashMap;
import java.util.Map;

public class InventoryManager {
    private Map<String, Integer> inventory;

    public InventoryManager() {
        this.inventory = new HashMap<>();
    }

    public void setIngredientStock(String ingredient, int quantity) {
        if (inventory.containsKey(ingredient)) {
            inventory.put(ingredient, inventory.get(ingredient) + quantity);
        } else {
            inventory.put(ingredient, quantity);
        }
    }

    public Map<String, Integer> getInventory() {
        return inventory;
    }

    public boolean checkStock(String ingredient, int quantity) {
        return inventory.getOrDefault(ingredient, 0) >= quantity;
    }

    public boolean checkLowStockAndNotifyManager() {
        boolean isLowStock = false;

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            if (entry.getValue() < 5) {
                isLowStock = true;
                System.out.println("Low stock alert for: " + entry.getKey() + " - Only " + entry.getValue() + " left.");
            }
        }

        if (isLowStock) {
            System.out.println("Notifying manager about low stock.");
            return true;
        }

        System.out.println("All items have sufficient stock.");
        return false;
    }
}
