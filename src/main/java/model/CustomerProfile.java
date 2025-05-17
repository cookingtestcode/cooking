
package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.time.LocalDateTime;

public class CustomerProfile {
    private String name;
    private String dietaryPreference;
    private String allergy;
    private List<String> mealSuggestions = new ArrayList();
    private List<String> orderHistory = new ArrayList();
    private LocalDateTime scheduledMealTime;

    public CustomerProfile() {}

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
        Iterator var2 = availableMeals.iterator();

        while(var2.hasNext()) {
            String meal = (String)var2.next();
            boolean matchesPreference = this.dietaryPreference == null || meal.toLowerCase().contains(this.dietaryPreference.toLowerCase());
            boolean safeFromAllergy = this.allergy == null || !meal.toLowerCase().contains(this.allergy.toLowerCase());
            if (matchesPreference && safeFromAllergy) {
                this.mealSuggestions.add(meal);
            }
        }
    }

    public List<String> suggestPersonalizedMeals() {
        Map<String, Integer> mealFrequency = new HashMap();
        Iterator var2 = this.orderHistory.iterator();
        while (var2.hasNext()) {
            String meal = (String)var2.next();
            mealFrequency.put(meal, mealFrequency.getOrDefault(meal, 0) + 1);
        }
        List<String> suggestions = new ArrayList();
        for (Map.Entry<String, Integer> entry : mealFrequency.entrySet()) {
            if (entry.getValue() > 1) { 
                suggestions.add(entry.getKey());
            }
        }
        return suggestions;
    }

    public void addMultipleOrders(List<String> orders) {
        this.orderHistory.addAll(orders);
    }
}
