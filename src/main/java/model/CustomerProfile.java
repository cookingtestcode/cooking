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

    // Getter and Setter methods
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

    public void addOrder(String item) {
        this.orderHistory.add(item);
    }

    public List<String> getOrderHistory() {
        return this.orderHistory;
    }
    package model;

import java.util.ArrayList;
import java.util.List;

public class CustomerProfile {
    private String name;
    private String dietaryPreference;
    private String allergy;
    private List<String> orderHistory;  // حفظ تاريخ الطلبات

    public CustomerProfile() {
        this.name = "";
        this.dietaryPreference = "";
        this.allergy = "";
        this.orderHistory = new ArrayList<>();  // تهيئة تاريخ الطلبات
    }

    // Getter and Setter methods
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

    public void addOrder(String item) {
        this.orderHistory.add(item);
    }

    public List<String> getOrderHistory() {
        return this.orderHistory;
    }
}
}
