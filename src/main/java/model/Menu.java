package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Menu {
    private Map<String, Double> menuItems;
    private Map<String, String> itemCategory;
    private Map<String, String> dietaryTags;
    private Map<String, List<String>> itemIngredients;

    public Menu() {
        this.menuItems = new HashMap<>();
        this.itemCategory = new HashMap<>();
        this.dietaryTags = new HashMap<>();
        this.itemIngredients = new HashMap<>();

        addMenuItem("Vegan Burger", 10.99, List.of("Lettuce", "Tomato", "Vegan Cheese"), "Main", "Vegan");
        addMenuItem("Vegetarian Salad", 8.99, List.of("Lettuce", "Tomato", "Olives", "Cucumber"), "Starter", "Vegetarian");
    }

    public void addMenuItem(String name, double price, List<String> ingredients, String category, String dietaryTag) {
        menuItems.put(name, price);
        itemCategory.put(name, category);
        dietaryTags.put(name, dietaryTag);
        itemIngredients.put(name, ingredients);
    }

    public double getItemPrice(String item) {
        return menuItems.getOrDefault(item, 0.0);
    }

    public String getItemCategory(String item) {
        return itemCategory.getOrDefault(item, "Unknown");
    }

    public String getDietaryTag(String item) {
        return dietaryTags.getOrDefault(item, "None");
    }

    public List<String> getItemIngredients(String item) {
        return itemIngredients.getOrDefault(item, new ArrayList<>());
    }

    public List<String> getMenuItems() {
        return new ArrayList<>(menuItems.keySet());
    }

    public List<String> getFilteredMenu(CustomerProfile customerProfile) {
        List<String> filteredItems = new ArrayList<>();
        String dietaryPreference = customerProfile.getDietaryPreference();
        for (String item : menuItems.keySet()) {
            String itemDietaryTag = getDietaryTag(item);
            if (dietaryPreference.equalsIgnoreCase(itemDietaryTag)) {
                filteredItems.add(item);
            }
        }
        return filteredItems;
    }

    public Map<String, Double> getAllMenuItems() {
        return menuItems;
    }
}
