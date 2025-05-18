package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class OrderAndMenu {
    private Menu menu;
    private Set<String> selectedItems = new HashSet<>();
    private CustomerProfile customerProfile;
    private Chef assignedChef;
    private Set<String> availableIngredients = new HashSet<>();
    private Set<String[]> incompatiblePairs = new HashSet<>();
    private Map<String, String> substitutions = new HashMap<>();
    private String restrictedIngredient;
    private String appliedSubstitutionDetails;
    private boolean substitutionApplied = false;
    private String substitutionDetails;

    public OrderAndMenu(Menu menu) {
        this.menu = menu;
    }

    public void setCustomerProfile(CustomerProfile customerProfile) {
        this.customerProfile = customerProfile;
    }

    public CustomerProfile getCustomerProfile() {
        return this.customerProfile;
    }

    public void assignChef(Chef chef) {
        this.assignedChef = chef;
    }

    public List<String> getRecommendedItems() {
        return (List)(this.customerProfile == null ? new ArrayList(this.menu.getAllMenuItems().keySet()) : this.menu.getFilteredMenu(this.customerProfile));
    }

    public boolean addToOrder(String itemName) {
        if (this.menu.getAllMenuItems().containsKey(itemName)) {
            this.selectedItems.add(itemName);
            if (this.assignedChef != null) {
                this.assignedChef.getNotifications().add("New order item: " + itemName);
            }
            return true;
        }
        return false;
    }

    public boolean removeFromOrder(String itemName) {
        return this.selectedItems.remove(itemName);
    }

    public double calculateTotal() {
        double total = 0.0;
        for (String item : this.selectedItems) {
            total += this.menu.getItemPrice(item);
        }
        return total;
    }

    public Set<String> getCurrentOrder() {
        return new HashSet<>(this.selectedItems);
    }

    public void confirmOrder() {
        if (this.customerProfile != null) {
            for (String item : this.selectedItems) {
                this.customerProfile.addOrder(item);
            }
        }
        this.selectedItems.clear();
    }

    public void setAvailableIngredients(String ing1, String ing2, String ing3) {
        this.availableIngredients.clear();
        this.availableIngredients.add(ing1);
        this.availableIngredients.add(ing2);
        this.availableIngredients.add(ing3);
    }

    public void addIncompatiblePair(String item1, String item2) {
        this.incompatiblePairs.add(new String[]{item1, item2});
    }

    public boolean isValidMeal(String ing1, String ing2) {
        if (this.availableIngredients.contains(ing1) && this.availableIngredients.contains(ing2)) {
            for (String[] pair : this.incompatiblePairs) {
                if ((pair[0].equals(ing1) && pair[1].equals(ing2)) || (pair[0].equals(ing2) && pair[1].equals(ing1))) {
                    return false;
                }
            }
            if (this.customerProfile != null && (this.customerProfile.hasAllergy(ing1) || this.customerProfile.hasAllergy(ing2))) {
                return false;
            }
            return true;
        }
        return false;
    }

    public void setRestrictedIngredient(String ingredient) {
        this.restrictedIngredient = ingredient;
    }

    public void addSubstitution(String original, String substitute) {
        this.substitutions.put(original, substitute);
    }

    public String suggestSubstitution(String ingredient) {
        return this.substitutions.getOrDefault(ingredient, null);
    }

    public void applySubstitution(String original, String substitute, Chef chef) {
        this.appliedSubstitutionDetails = original + " -> " + substitute;
        this.substitutionApplied = true;
        if (chef != null) {
            chef.getNotifications().add("Substitution Applied: " + this.appliedSubstitutionDetails);
        }
    }

    public String getSubstitutionDetails() {
        return this.appliedSubstitutionDetails;
    }

    public boolean isSubstitutionApplied() {
        return this.appliedSubstitutionDetails != null && this.substitutionApplied;
    }
}