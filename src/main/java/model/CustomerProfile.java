package model;

import java.util.ArrayList;
import java.util.List;

public class CustomerProfile {
    private String name;
    private String dietaryPreference;
    private String allergy;
    private List<String> orderHistory;

    public CustomerProfile() {
        this.name = "";
        this.dietaryPreference = "";
        this.allergy = "";
        this.orderHistory = new ArrayList<>();
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDietaryPreference() {
        return dietaryPreference;
    }

    public void setDietaryPreference(String dietaryPreference) {
        this.dietaryPreference = dietaryPreference;
    }

    public String getAllergy() {
        return allergy;
    }

    public void setAllergy(String allergy) {
        this.allergy = allergy;
    }

    public boolean hasAllergy(String ingredient) {

        return this.allergy != null && this.allergy.equalsIgnoreCase(ingredient);
    }

    public void addOrder(String item) {
        this.orderHistory.add(item);
    }

    public List<String> getOrderHistory() {
        return this.orderHistory;
    }
}
