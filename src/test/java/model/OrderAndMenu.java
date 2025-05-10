package model;

import java.util.*;

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

    public OrderAndMenu(Menu menu) {
        this.menu = menu;
    }

    public void setCustomerProfile(CustomerProfile customerProfile) {
        this.customerProfile = customerProfile;
    }

    public void assignChef(Chef chef) {
        this.assignedChef = chef;
    }

    public List<String> getRecommendedItems() {
        if (customerProfile == null) {
            return new ArrayList<>(menu.getAllMenuItems().keySet());
        }
        return menu.getFilteredMenu(customerProfile);
    }

    public boolean addToOrder(String itemName) {
        if (menu.getAllMenuItems().containsKey(itemName)) {
            selectedItems.add(itemName);
            if (assignedChef != null) {
                assignedChef.getNotifications().add("New order item: " + itemName);
            }
            return true;
        }
        return false;
    }

    public boolean removeFromOrder(String itemName) {
        return selectedItems.remove(itemName);
    }

    public double calculateTotal() {
        double total = 0;
        for (String item : selectedItems) {
            total += menu.getItemPrice(item);
        }
        return total;
    }

    public Set<String> getCurrentOrder() {
        return new HashSet<>(selectedItems);
    }

    public void confirmOrder() {
        if (customerProfile != null) {
            for (String item : selectedItems) {
                customerProfile.addOrder(item);
            }
        }
        selectedItems.clear();
    }

    public void setAvailableIngredients(String ing1, String ing2, String ing3) {
        availableIngredients.clear();
        availableIngredients.add(ing1);
        availableIngredients.add(ing2);
        availableIngredients.add(ing3);
    }

    public void addIncompatiblePair(String item1, String item2) {
        incompatiblePairs.add(new String[]{item1, item2});
    }

    public boolean isValidMeal(String ing1, String ing2) {
        if (!availableIngredients.contains(ing1) || !availableIngredients.contains(ing2)) {
            return false;
        }
        for (String[] pair : incompatiblePairs) {
            if ((pair[0].equals(ing1) && pair[1].equals(ing2)) ||
                    (pair[0].equals(ing2) && pair[1].equals(ing1))) {
                return false;
            }
        }
        if (customerProfile != null && (customerProfile.hasAllergy(ing1) || customerProfile.hasAllergy(ing2))) {
            return false;
        }
        return true;
    }

    public void setRestrictedIngredient(String ingredient) {
        this.restrictedIngredient = ingredient;
    }

    public void addSubstitution(String original, String substitute) {
        substitutions.put(original, substitute);
    }

    public String suggestSubstitution(String ingredient) {
        return substitutions.getOrDefault(ingredient, null);
    }

    public void applySubstitution(String original, String substitute, Chef chef) {
        if (substitutions.containsKey(original) && chef != null) {
            appliedSubstitutionDetails = original + " -> " + substitute;
            chef.getNotifications().add("Substitution Applied: " + appliedSubstitutionDetails);
        }
    }

    public String getSubstitutionDetails() {
        return appliedSubstitutionDetails;
    }

    public boolean isSubstitutionApplied() {
        return appliedSubstitutionDetails != null;
    }
}