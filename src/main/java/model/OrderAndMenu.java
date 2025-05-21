package model;

import java.util.*;

public class OrderAndMenu {
    private Menu menu;
    private CustomerProfile customerProfile;
    private Chef assignedChef;
    private Map<String, Set<String>> incompatiblePairs = new HashMap<>();
    private String appliedSubstitutionDetails;
    private boolean substitutionApplied = false;
    private Set<String> selectedItems = Collections.synchronizedSet(new HashSet<>());
    private Set<String> availableIngredients = Collections.synchronizedSet(new HashSet<>());
    private Map<String, String> substitutions = Collections.synchronizedMap(new HashMap<>());
    private String restrictedIngredient;

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
        if (itemName == null || itemName.trim().isEmpty()) {
            return false;
        }

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
        incompatiblePairs.computeIfAbsent(item1, k -> new HashSet<>()).add(item2);
        incompatiblePairs.computeIfAbsent(item2, k -> new HashSet<>()).add(item1);
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
        if (original == null || substitute == null) {
            throw new IllegalArgumentException("Original and substitute ingredients cannot be null");
        }
        this.appliedSubstitutionDetails = original + " -> " + substitute;
        this.substitutionApplied = true;

        if (chef == null) {
            throw new IllegalStateException("Chef must be assigned for substitution to be applied");
        }
        chef.getNotifications().add("Substitution Applied: " + this.appliedSubstitutionDetails);
    }

    public String getSubstitutionDetails() {
        return appliedSubstitutionDetails;
    }

    public boolean isSubstitutionApplied() {
        return appliedSubstitutionDetails != null && substitutionApplied;
    }
    public boolean isValidMeal(String ing1, String ing2) {
        if (ing1 == null || ing2 == null) {
            return false;
        }
        if (!this.availableIngredients.contains(ing1) || !this.availableIngredients.contains(ing2)) {
            return false;
        }

        for (Map.Entry<String, Set<String>> entry : incompatiblePairs.entrySet()) {
            if (entry.getValue().contains(ing1) && entry.getValue().contains(ing2)) {
                return false;
            }
        }


        if (this.customerProfile != null &&
                (this.customerProfile.hasAllergy(ing1) || this.customerProfile.hasAllergy(ing2))) {
            return false;
        }
        return true;
    }


}
