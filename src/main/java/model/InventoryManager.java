
package model;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

public class InventoryManager {
    private Map<String, Integer> inventory = new HashMap();
    private Map<String, Integer> criticalStockLevels = new HashMap();
    private List<String> managerNotifications = new ArrayList();
    private Map<String, Supplier> supplierDetails = new HashMap();

    public boolean isRestockSuggested(String ingredient) {
        return inventory.getOrDefault(ingredient, 0) < criticalStockLevels.getOrDefault(ingredient, 2);
    }

    public List<String> getManagerNotifications() {
        return new ArrayList(this.managerNotifications);
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
        if ((Integer)this.inventory.getOrDefault(ingredient, 0) < (Integer)this.criticalStockLevels.getOrDefault(ingredient, 2) && this.hasSupplierFor(ingredient)) {
            this.managerNotifications.add("Auto-order placed for: " + ingredient);
            return true;
        } else {
            return false;
        }
    }

    public boolean checkLowStockAndNotifyManager() {
        boolean actionTaken = false; 
        for (Map.Entry<String, Integer> entry : this.inventory.entrySet()) {
            String ingredient = entry.getKey();
            int quantity = entry.getValue();

            if (this.isRestockSuggested(ingredient)) {
                this.managerNotifications.add("Low stock alert: " + ingredient + " (Quantity: " + quantity + ")");
                actionTaken = true; 
            }

            if (this.checkAndAutoOrder(ingredient)) {
                this.managerNotifications.add("Critical stock alert: Auto-order placed for " + ingredient);
                actionTaken = true; 
            }
        }
        return actionTaken; 
    }
}
