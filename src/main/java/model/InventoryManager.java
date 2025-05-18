package model;

import java.util.ArrayList;
import java.util.List;

public class InventoryManager {

    public boolean isRestockSuggested(String ingredient) {
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
            this.managerNotifications.add("Auto-order placed for: " + ingredient);
            return true;
        } else {
            return false;
        }
    }



        }
        }

        }
    }
}