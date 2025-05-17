package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Menu {
    private Map<String, Double> menuItems; // خريطة تحتوي على أسماء الأطباق والأسعار
    private Map<String, String> itemCategory; // خريطة تحتوي على أسماء الأطباق والفئات (Main, Starter, Dessert)
    private Map<String, String> dietaryTags; // خريطة تحتوي على أسماء الأطباق والوسوم الغذائية (Vegan, Vegetarian)
    private Map<String, List<String>> itemIngredients; // خريطة تحتوي على أسماء الأطباق والمكونات

    public Menu() {
        this.menuItems = new HashMap<>();
        this.itemCategory = new HashMap<>();
        this.dietaryTags = new HashMap<>();
        this.itemIngredients = new HashMap<>();

        // إضافة بعض الأطباق كمثال
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

    public Map<String, Double> getAllMenuItems() {
        return menuItems;
    }
}
