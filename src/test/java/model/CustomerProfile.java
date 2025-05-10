package model;

import java.util.*;

public class CustomerProfile {
    private String name;
    private String dietaryPreference;
    private String allergy;
    private List<String> mealSuggestions = new ArrayList<>();
    private List<String> orderHistory = new ArrayList<>();

    public CustomerProfile() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDietaryPreference(String dietaryPreference) {
        this.dietaryPreference = dietaryPreference;
    }

    public void setAllergy(String allergy) {
        this.allergy = allergy;
    }

    public String getDietaryPreference() {
        return this.dietaryPreference;
    }

    public String getAllergy() {
        return this.allergy;
    }

    public List<String> getMealSuggestions() {
        return this.mealSuggestions;
    }

    public List<String> getOrderHistory() {
        return this.orderHistory;
    }

    public void generateMealSuggestions(List<String> availableMeals) {
        this.mealSuggestions.clear();
        for (String meal : availableMeals) {
            boolean matchesPreference = dietaryPreference == null || meal.toLowerCase().contains(dietaryPreference.toLowerCase());
            boolean safeFromAllergy = allergy == null || !meal.toLowerCase().contains(allergy.toLowerCase());
            if (matchesPreference && safeFromAllergy) {
                this.mealSuggestions.add(meal);
            }
        }
    }

    public List<String> suggestPersonalizedMeals() {
        Map<String, Integer> mealFrequency = new HashMap<>();
        for (String order : orderHistory) {
            mealFrequency.put(order, mealFrequency.getOrDefault(order, 0) + 1);
        }
        List<String> personalizedSuggestions = new ArrayList<>();
        mealFrequency.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(3)
                .forEach(e -> personalizedSuggestions.add(e.getKey()));
        return personalizedSuggestions.isEmpty() ? mealSuggestions : personalizedSuggestions;
    }

    public Map<String, Integer> analyzeOrderTrends() {
        Map<String, Integer> trends = new HashMap<>();
        for (String order : orderHistory) {
            trends.put(order, trends.getOrDefault(order, 0) + 1);
        }
        return trends;
    }

    public void addOrder(String meal) {
        if (meal != null && !meal.trim().isEmpty()) {
            this.orderHistory.add(meal);
        }
    }

    public void addMultipleOrders(List<String> meals) {
        if (meals != null) {
            for (String meal : meals) {
                this.addOrder(meal);
            }
        }
    }

    public void clearOrderHistory() {
        this.orderHistory.clear();
    }

    public List<String> getRecommendedItems(Menu menu) {
        return menu.getFilteredMenu(this);
    }
    public boolean hasAllergy(String ingredient) {
        return allergy != null && ingredient != null && ingredient.toLowerCase().contains(allergy.toLowerCase());
    }
    public void rateMenuItem(String itemName, int rating) {

        System.out.println("Customer " + name + " rated " + itemName + " with " + rating + " stars");
    }
}