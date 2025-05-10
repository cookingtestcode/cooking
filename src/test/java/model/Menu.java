package model;

import java.util.*;

public class Menu {
    private Map<String, Double> menuItems; // اسم الوجبة والسعر
    private Map<String, List<String>> itemIngredients; // مكونات كل وجبة
    private Map<String, String> itemCategories; // تصنيف الوجبة (مقبلات، رئيسية، حلويات...)
    private Map<String, String> dietaryTags; // وسوم غذائية (نباتي، خالي من الجلوتين...)

    public Menu() {
        menuItems = new HashMap<>();
        itemIngredients = new HashMap<>();
        itemCategories = new HashMap<>();
        dietaryTags = new HashMap<>();
        initializeSampleMenu();
    }

    private void initializeSampleMenu() {

        addMenuItem("Vegetarian Pizza", 12.99,
                Arrays.asList("Tomatoes", "Cheese", "Olive Oil", "Flour"),
                "Main", "Vegetarian");

        addMenuItem("Pasta Carbonara", 14.50,
                Arrays.asList("Pasta", "Cheese", "Eggs", "Milk"),
                "Main", null);

        addMenuItem("Chocolate Cake", 8.99,
                Arrays.asList("Flour", "Sugar", "Eggs", "Chocolate"),
                "Dessert", "Vegetarian");

        addMenuItem("Nut Salad", 9.75,
                Arrays.asList("Nuts", "Tomatoes", "Olive Oil"),
                "Starter", "Vegan");
    }

    public void addMenuItem(String name, double price, List<String> ingredients,
                            String category, String dietaryTag) {
        menuItems.put(name, price);
        itemIngredients.put(name, ingredients);
        itemCategories.put(name, category);
        dietaryTags.put(name, dietaryTag);
    }

    public List<String> getFilteredMenu(CustomerProfile customer) {
        List<String> filteredMenu = new ArrayList<>();

        for (String item : menuItems.keySet()) {
            boolean matchesDiet = customer.getDietaryPreference() == null ||
                    (dietaryTags.get(item) != null &&
                            dietaryTags.get(item).equalsIgnoreCase(customer.getDietaryPreference()));

            boolean safeForAllergy = true;
            if (customer.getAllergy() != null) {
                for (String ingredient : itemIngredients.get(item)) {
                    if (ingredient.toLowerCase().contains(customer.getAllergy().toLowerCase())) {
                        safeForAllergy = false;
                        break;
                    }
                }
            }

            if (matchesDiet && safeForAllergy) {
                filteredMenu.add(item);
            }
        }

        return filteredMenu;
    }

    public double getItemPrice(String itemName) {
        return menuItems.getOrDefault(itemName, 0.0);
    }

    public List<String> getItemIngredients(String itemName) {
        return itemIngredients.getOrDefault(itemName, new ArrayList<>());
    }

    public String getItemCategory(String itemName) {
        return itemCategories.get(itemName);
    }

    public String getDietaryTag(String itemName) {
        return dietaryTags.get(itemName);
    }

    public Map<String, Double> getAllMenuItems() {
        return new HashMap<>(menuItems);
    }
}