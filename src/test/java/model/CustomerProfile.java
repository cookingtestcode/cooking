//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CustomerProfile {
    private String dietaryPreference;
    private String allergy;
    private List<String> mealSuggestions = new ArrayList();
    private List<String> orderHistory = new ArrayList();

    public CustomerProfile() {
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
        if (this.allergy != null) {
            Iterator var2 = availableMeals.iterator();

            while(var2.hasNext()) {
                String meal = (String)var2.next();
                if (!meal.toLowerCase().contains(this.allergy.toLowerCase())) {
                    this.mealSuggestions.add(meal);
                }
            }
        } else {
            this.mealSuggestions.addAll(availableMeals);
        }

    }

    public void addOrder(String meal) {
        if (meal != null && !meal.trim().isEmpty()) {
            this.orderHistory.add(meal);
        }

    }

    public void addMultipleOrders(List<String> meals) {
        if (meals != null) {
            Iterator var2 = meals.iterator();

            while(var2.hasNext()) {
                String meal = (String)var2.next();
                this.addOrder(meal);
            }
        }

    }

    public void clearOrderHistory() {
        this.orderHistory.clear();
    }
}
