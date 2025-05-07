
package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class OrderAndMenu {
    private List<String> availableIngredients = new ArrayList();
    private Set<String> selectedIngredients = new HashSet();
    private Set<String> incompatibleSet = new HashSet();
    private Map<String, String> substitutions = new HashMap();
    private String restrictedIngredient;
    private String appliedSubstitution;
    private boolean substitutionApplied = false;

    public OrderAndMenu() {
    }

    public void setAvailableIngredients(String... ingredients) {
        this.availableIngredients.clear();
        this.availableIngredients.addAll(Arrays.asList(ingredients));
    }

    public boolean isValidMeal(String... selected) {
        this.selectedIngredients.clear();
        this.selectedIngredients.addAll(Arrays.asList(selected));
        return this.availableIngredients.containsAll(this.selectedIngredients);
    }

    public void setIncompatiblePair(String ing1, String ing2) {
        this.incompatibleSet.clear();
        this.incompatibleSet.add(ing1.toLowerCase());
        this.incompatibleSet.add(ing2.toLowerCase());
    }

    public boolean isIncompatible(String ing1, String ing2) {
        return this.incompatibleSet.contains(ing1.toLowerCase()) && this.incompatibleSet.contains(ing2.toLowerCase());
    }

    public void setRestrictedIngredient(String ingredient) {
        this.restrictedIngredient = ingredient;
    }

    public void addSubstitution(String restricted, String substitute) {
        this.substitutions.put(restricted.toLowerCase(), substitute);
    }

    public String suggestSubstitution(String ingredient) {
        return (String)this.substitutions.get(ingredient.toLowerCase());
    }

    public void applySubstitution(String original, String substitute) {
        this.appliedSubstitution = "Substituted " + original + " with " + substitute;
        this.substitutionApplied = true;
    }

    public boolean isSubstitutionApplied() {
        return this.substitutionApplied;
    }

    public String getSubstitutionDetails() {
        return this.appliedSubstitution;
    }
}
