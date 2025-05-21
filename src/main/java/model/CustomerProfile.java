package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CustomerProfile {
    private String name;
    private String dietaryPreference;
    private String allergy;
    private List<String> mealSuggestions = new ArrayList<>();  // تحديد النوع List<String>
    private List<String> orderHistory = new ArrayList<>();  // تحديد النوع List<String>

    public CustomerProfile() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
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
            boolean matchesPreference = (this.dietaryPreference == null || meal.toLowerCase().contains(this.dietaryPreference.toLowerCase()));
            boolean safeFromAllergy = (this.allergy == null || !meal.toLowerCase().contains(this.allergy.toLowerCase()));
            if (matchesPreference && safeFromAllergy) {
                this.mealSuggestions.add(meal);
            }
        }
    }

    public List<String> suggestPersonalizedMeals() {
        Map<String, Integer> mealFrequency = new HashMap<>();  // تحديد النوع Map<String, Integer>

        for (String order : this.orderHistory) {
            mealFrequency.put(order, mealFrequency.getOrDefault(order, 0) + 1);
        }

        List<String> personalizedSuggestions = new ArrayList<>();
        mealFrequency.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(3)
                .forEach(e -> personalizedSuggestions.add(e.getKey()));

        return personalizedSuggestions.isEmpty() ? this.mealSuggestions : personalizedSuggestions;
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

    public boolean hasAllergy(String ingredient) {
        return this.allergy != null && ingredient != null && ingredient.toLowerCase().contains(this.allergy.toLowerCase());
    }

    private LocalDateTime mealDeliveryTime;

    public void scheduleMeal(LocalDateTime deliveryTime) {
        this.mealDeliveryTime = deliveryTime;
    }

    public LocalDateTime getMealDeliveryTime() {
        return mealDeliveryTime;
    }
}
