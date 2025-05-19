
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
    private List<String> mealSuggestions = new ArrayList();
    private List<String> orderHistory = new ArrayList();

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

        while(var2.hasNext()) {
            String order = (String)var2.next();
            mealFrequency.put(order, (Integer)mealFrequency.getOrDefault(order, 0) + 1);
        }

        List<String> personalizedSuggestions = new ArrayList();
        mealFrequency.entrySet().stream().sorted((e1, e2) -> {
            return ((Integer)e2.getValue()).compareTo((Integer)e1.getValue());
        }).limit(3L).forEach((e) -> {
            personalizedSuggestions.add((String)e.getKey());
        });
        return (List)(personalizedSuggestions.isEmpty() ? this.mealSuggestions : personalizedSuggestions);
    }

   /* public Map<String, Integer> analyzeOrderTrends() {
        Map<String, Integer> trends = new HashMap();
        Iterator var2 = this.orderHistory.iterator();

        while(var2.hasNext()) {
            String order = (String)var2.next();
            trends.put(order, (Integer)trends.getOrDefault(order, 0) + 1);
        }

        return trends;
    }*/

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

   /* public void clearOrderHistory() {
        this.orderHistory.clear();
    }*/

   /* public List<String> getRecommendedItems(Menu menu) {
        return menu.getFilteredMenu(this);
    }
*/
    public boolean hasAllergy(String ingredient) {
        return this.allergy != null && ingredient != null && ingredient.toLowerCase().contains(this.allergy.toLowerCase());
    }

    /*public void rateMenuItem(String itemName, int rating) {
        System.out.println("Customer " + this.name + " rated " + itemName + " with " + rating + " stars");
    }*/
    private LocalDateTime mealDeliveryTime;

    public void scheduleMeal(LocalDateTime deliveryTime) {
        this.mealDeliveryTime = deliveryTime;
    }

    public LocalDateTime getMealDeliveryTime() {
        return mealDeliveryTime;
    }
}
