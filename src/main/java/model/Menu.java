
package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Menu {
    private Map<String, Double> menuItems = new HashMap();
    private Map<String, List<String>> itemIngredients = new HashMap();
    private Map<String, String> itemCategories = new HashMap();
    private Map<String, String> dietaryTags = new HashMap();

    public Menu() {
        this.initializeSampleMenu();
    }

    private void initializeSampleMenu() {
        this.addMenuItem("Vegetarian Pizza", 12.99, Arrays.asList("Tomatoes", "Cheese", "Olive Oil", "Flour"), "Main", "Vegetarian");
        this.addMenuItem("Pasta Carbonara", 14.5, Arrays.asList("Pasta", "Cheese", "Eggs", "Milk"), "Main", (String)null);
        this.addMenuItem("Chocolate Cake", 8.99, Arrays.asList("Flour", "Sugar", "Eggs", "Chocolate"), "Dessert", "Vegetarian");
        this.addMenuItem("Nut Salad", 9.75, Arrays.asList("Nuts", "Tomatoes", "Olive Oil"), "Starter", "Vegan");
    }

    public void addMenuItem(String name, double price, List<String> ingredients, String category, String dietaryTag) {
        this.menuItems.put(name, price);
        this.itemIngredients.put(name, ingredients);
        this.itemCategories.put(name, category);
        this.dietaryTags.put(name, dietaryTag);
    }

    public List<String> getFilteredMenu(CustomerProfile customer) {
        List<String> filteredMenu = new ArrayList();
        Iterator var3 = this.menuItems.keySet().iterator();

        while(var3.hasNext()) {
            String item = (String)var3.next();
            boolean matchesDiet = customer.getDietaryPreference() == null || this.dietaryTags.get(item) != null && ((String)this.dietaryTags.get(item)).equalsIgnoreCase(customer.getDietaryPreference());
            boolean safeForAllergy = true;
            if (customer.getAllergy() != null) {
                Iterator var7 = ((List)this.itemIngredients.get(item)).iterator();

                while(var7.hasNext()) {
                    String ingredient = (String)var7.next();
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
        return (Double)this.menuItems.getOrDefault(itemName, 0.0);
    }

    public List<String> getItemIngredients(String itemName) {
        return (List)this.itemIngredients.getOrDefault(itemName, new ArrayList());
    }

    public String getItemCategory(String itemName) {
        return (String)this.itemCategories.get(itemName);
    }

    public String getDietaryTag(String itemName) {
        return (String)this.dietaryTags.get(itemName);
    }

    public Map<String, Double> getAllMenuItems() {
        return new HashMap(this.menuItems);
    }
}
