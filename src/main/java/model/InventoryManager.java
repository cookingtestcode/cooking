package model;

import java.util.HashMap;
import java.util.Map;

public class InventoryManager {
    private Map<String, Integer> inventory;

    public InventoryManager() {
        this.inventory = new HashMap<>();
    }

    public void setIngredientStock(String ingredient, int quantity) {
        // If the ingredient is already in inventory, update its stock
        if (inventory.containsKey(ingredient)) {
            inventory.put(ingredient, inventory.get(ingredient) + quantity);
        } else {
            // If the ingredient is not in inventory, add it
            inventory.put(ingredient, quantity);
        }
    }

    public Map<String, Integer> getInventory() {
        return inventory;
    }

    public boolean checkStock(String ingredient, int quantity) {
        return inventory.getOrDefault(ingredient, 0) >= quantity;
    }
}
