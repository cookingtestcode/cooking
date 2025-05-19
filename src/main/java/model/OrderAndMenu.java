package model;

import java.util.*;

public class OrderAndMenu {
    private Menu menu;
    private Set<String> selectedItems = new HashSet<>();
    private CustomerProfile customerProfile;
    private Chef assignedChef;

    private Set<String> availableIngredients = new HashSet<>();
    private Set<AbstractMap.SimpleEntry<String, String>> incompatiblePairs = new HashSet<>();
    private Map<String, String> substitutions = new HashMap<>();
    private String restrictedIngredient;
    private String appliedSubstitutionDetails;
    private boolean substitutionApplied = false;

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
        return selectedItems.stream()
                .mapToDouble(menu::getItemPrice)
                .sum();
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

    public void setAvailableIngredients(String... ingredients) {
        availableIngredients.clear();
        availableIngredients.addAll(Arrays.asList(ingredients));
    }

    public void addIncompatiblePair(String item1, String item2) {
        incompatiblePairs.add(new AbstractMap.SimpleEntry<>(item1, item2));
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
        appliedSubstitutionDetails = original + " -> " + substitute;
        substitutionApplied = true;
        if (chef != null) {
            chef.getNotifications().add("Substitution Applied: " + appliedSubstitutionDetails);
        }
    }

    public String getSubstitutionDetails() {
        return appliedSubstitutionDetails;
    }

    public boolean isSubstitutionApplied() {
        return appliedSubstitutionDetails != null && substitutionApplied;
    }

    public boolean isValidMeal(String... ingredients) {
        for (String ing : ingredients) {
            if (!availableIngredients.contains(ing) ||
                    (customerProfile != null && customerProfile.hasAllergy(ing))) {
                return false;
            }
        }

        for (String ing1 : ingredients) {
            for (String ing2 : ingredients) {
                if (!ing1.equals(ing2)) {
                    for (AbstractMap.SimpleEntry<String, String> pair : incompatiblePairs) {
                        if ((pair.getKey().equals(ing1) && pair.getValue().equals(ing2)) ||
                                (pair.getKey().equals(ing2) && pair.getValue().equals(ing1))) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
}
