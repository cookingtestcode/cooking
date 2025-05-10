package model;

import java.time.LocalDateTime;
import java.util.*;

public class Main {
    private static Menu restaurantMenu = new Menu();
    private static CustomerProfile customer = new CustomerProfile();
    private static List<Chef> chefs = new ArrayList<>();
    private static OrderAndMenu orderSystem = new OrderAndMenu(restaurantMenu);
    private static InventoryManager inventoryManager = new InventoryManager();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeChefs();
        orderSystem.setCustomerProfile(customer);

        while (true) {
            displayMainMenu();
            int choice = getUserChoice();

            switch (choice) {
                case 1:
                    manageCustomerProfile();
                    break;
                case 2:
                    displayFilteredMenu();
                    break;
                case 3:
                    manageOrder();
                    break;
                case 4:
                    createCustomMeal();
                    break;
                case 5:
                    viewChefNotifications();
                    break;
                case 6:
                    manageInventory();
                    break;
                case 7:
                    rateMenuItem();
                    break;
                case 8:
                    manageSubstitutions();
                    break;
                case 9:
                    viewOrderHistory();
                    break;
                case 10:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void initializeChefs() {
        chefs.add(new Chef("Chef Ali", Arrays.asList("Main", "Dessert"), 0));
        chefs.add(new Chef("Chef Sara", Arrays.asList("Starter", "Main"), 0));
    }

    private static void displayMainMenu() {
        System.out.println("\n=== Restaurant Management System ===");
        System.out.println("1. Manage Customer Profile");
        System.out.println("2. View Menu");
        System.out.println("3. Manage Order");
        System.out.println("4. Create Custom Meal");
        System.out.println("5. View Chef Notifications");
        System.out.println("6. Manage Inventory");
        System.out.println("7. Rate Menu Item");
        System.out.println("8. Manage Substitutions");
        System.out.println("9. View Order History");
        System.out.println("10. Exit");
        System.out.print("Enter your choice: ");
    }

    private static int getUserChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void manageCustomerProfile() {
        System.out.print("Enter customer name: ");
        String name = scanner.nextLine();
        customer.setName(name);

        System.out.print("Enter dietary preference (e.g., Vegetarian, Vegan) or press Enter to skip: ");
        String dietary = scanner.nextLine();
        if (!dietary.isEmpty()) {
            customer.setDietaryPreference(dietary);
        }

        System.out.print("Enter allergy (e.g., Nuts, Dairy) or press Enter to skip: ");
        String allergy = scanner.nextLine();
        if (!allergy.isEmpty()) {
            customer.setAllergy(allergy);
        }

        System.out.println("Customer profile updated.");
    }

    private static void displayFilteredMenu() {
        System.out.println("\n=== Menu for " + customer.getName() + " ===");
        List<String> recommendedItems = orderSystem.getRecommendedItems();
        if (recommendedItems.isEmpty()) {
            System.out.println("No items match the customer's preferences.");
        } else {
            for (String item : recommendedItems) {
                System.out.println("- " + item + " ($" + restaurantMenu.getItemPrice(item) + ")");
                System.out.println("  Ingredients: " + restaurantMenu.getItemIngredients(item));
                System.out.println("  Category: " + restaurantMenu.getItemCategory(item));
                System.out.println("  Dietary: " + restaurantMenu.getDietaryTag(item));
                System.out.println();
            }
        }
    }

    private static void manageOrder() {
        while (true) {
            System.out.println("\n=== Order Management ===");
            System.out.println("1. Add Item to Order");
            System.out.println("2. Remove Item from Order");
            System.out.println("3. View Current Order");
            System.out.println("4. Confirm Order");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = getUserChoice();
            switch (choice) {
                case 1:
                    System.out.print("Enter item name to add: ");
                    String itemToAdd = scanner.nextLine();
                    if (orderSystem.addToOrder(itemToAdd)) {
                        System.out.println(itemToAdd + " added to order.");
                        assignTaskToChef(itemToAdd);
                    } else {
                        System.out.println("Item not found in menu.");
                    }
                    break;
                case 2:
                    System.out.print("Enter item name to remove: ");
                    String itemToRemove = scanner.nextLine();
                    if (orderSystem.removeFromOrder(itemToRemove)) {
                        System.out.println(itemToRemove + " removed from order.");
                    } else {
                        System.out.println("Item not in order.");
                    }
                    break;
                case 3:
                    System.out.println("Current Order: " + orderSystem.getCurrentOrder());
                    System.out.println("Total: $" + orderSystem.calculateTotal());
                    break;
                case 4:
                    orderSystem.confirmOrder();
                    System.out.println("Order confirmed and added to history.");
                    return;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void createCustomMeal() {
        System.out.println("\n=== Create Custom Meal ===");
        System.out.print("Enter meal name: ");
        String mealName = scanner.nextLine();
        System.out.print("Enter category (e.g., Main, Dessert, Starter): ");
        String category = scanner.nextLine();
        System.out.print("Enter dietary tag (e.g., Vegetarian, Vegan) or press Enter to skip: ");
        String dietaryTag = scanner.nextLine();
        if (dietaryTag.isEmpty()) {
            dietaryTag = null;
        }
        System.out.print("Enter price: ");
        double price;
        try {
            price = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid price. Meal creation cancelled.");
            return;
        }

        List<String> ingredients = new ArrayList<>();
        System.out.println("Enter ingredients (one per line, type 'done' to finish):");
        while (true) {
            String ingredient = scanner.nextLine();
            if (ingredient.equalsIgnoreCase("done")) {
                break;
            }
            if (inventoryManager.getInventory().containsKey(ingredient) &&
                    inventoryManager.getInventory().get(ingredient) > 0 &&
                    !customer.hasAllergy(ingredient)) {
                ingredients.add(ingredient);
            } else {
                String substitute = suggestAutoSubstitution(ingredient);
                if (substitute != null) {
                    System.out.println("Ingredient " + ingredient + " unavailable or allergic. Suggested substitute: " + substitute);
                    System.out.print("Use substitute? (y/n): ");
                    if (scanner.nextLine().equalsIgnoreCase("y")) {
                        ingredients.add(substitute);
                    }
                } else {
                    System.out.println("Ingredient " + ingredient + " unavailable or allergic. Skipped.");
                }
            }
        }

        if (ingredients.size() >= 2 && orderSystem.isValidMeal(ingredients.get(0), ingredients.get(1))) {
            restaurantMenu.addMenuItem(mealName, price, ingredients, category, dietaryTag);
            orderSystem.addToOrder(mealName);
            System.out.println("Custom meal " + mealName + " created and added to order.");
            assignTaskToChef(mealName);
        } else {
            System.out.println("Invalid meal: not enough valid ingredients or incompatible ingredients.");
        }
    }

    private static String suggestAutoSubstitution(String ingredient) {
        Map<String, String> possibleSubstitutes = new HashMap<>();
        possibleSubstitutes.put("Cheese", "Vegan Cheese");
        possibleSubstitutes.put("Nuts", "Seeds");
        possibleSubstitutes.put("Milk", "Almond Milk");
        possibleSubstitutes.put("Eggs", "Flaxseed");

        String substitute = possibleSubstitutes.get(ingredient);
        if (substitute != null && inventoryManager.getInventory().containsKey(substitute) &&
                inventoryManager.getInventory().get(substitute) > 0 &&
                !customer.hasAllergy(substitute)) {
            orderSystem.addSubstitution(ingredient, substitute);
            return substitute;
        }
        return null;
    }

    private static void assignTaskToChef(String itemName) {
        String category = restaurantMenu.getItemCategory(itemName);
        Chef selectedChef = null;
        int minWorkload = Integer.MAX_VALUE;

        for (Chef chef : chefs) {
            if (chef.getExpertise().contains(category) && chef.getWorkload() < minWorkload) {
                selectedChef = chef;
                minWorkload = chef.getWorkload();
            }
        }

        if (selectedChef != null) {
            orderSystem.assignChef(selectedChef);
            Task task = new Task("Prepare " + itemName, category, LocalDateTime.now().plusHours(2));
            selectedChef.assignTask(task);
            System.out.println("Task assigned to " + selectedChef.getName() + ": " + task.getName());
        } else {
            System.out.println("No suitable chef available for " + itemName);
        }
    }

    private static void viewChefNotifications() {
        System.out.println("\n=== Chef Notifications ===");
        for (Chef chef : chefs) {
            System.out.println("Notifications for " + chef.getName() + ":");
            List<String> notifications = chef.getNotifications();
            if (notifications.isEmpty()) {
                System.out.println("  No notifications.");
            } else {
                for (String notification : notifications) {
                    System.out.println("  - " + notification);
                }
            }
        }
    }

    private static void manageInventory() {
        System.out.println("\n=== Inventory Management ===");
        System.out.println("Current Inventory: " + inventoryManager.getInventory());
        System.out.println("1. Update Stock");
        System.out.println("2. View Supplier Prices");
        System.out.print("Enter your choice: ");

        int choice = getUserChoice();
        if (choice == 1) {
            System.out.print("Enter ingredient name to update stock: ");
            String ingredient = scanner.nextLine();
            System.out.print("Enter new quantity: ");
            try {
                int quantity = Integer.parseInt(scanner.nextLine());
                inventoryManager.setIngredientStock(ingredient, quantity);
                System.out.println("Stock updated for " + ingredient + ": " + quantity);
                if (inventoryManager.checkAndAutoOrder(ingredient)) {
                    System.out.println("Auto-order placed for " + ingredient + " from supplier: " +
                            inventoryManager.fetchSupplierPrice(ingredient));
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid quantity. Please enter a number.");
            }
        } else if (choice == 2) {
            System.out.print("Enter ingredient name to check supplier price: ");
            String ingredient = scanner.nextLine();
            String price = inventoryManager.fetchSupplierPrice(ingredient);
            if (price != null) {
                System.out.println("Supplier price for " + ingredient + ": " + price);
            } else {
                System.out.println("No supplier available for " + ingredient);
            }
        } else {
            System.out.println("Invalid choice.");
        }
    }

    private static void rateMenuItem() {
        System.out.print("Enter menu item name to rate: ");
        String itemName = scanner.nextLine();
        System.out.print("Enter rating (1-5): ");
        try {
            int rating = Integer.parseInt(scanner.nextLine());
            if (rating >= 1 && rating <= 5) {
                customer.rateMenuItem(itemName, rating);
            } else {
                System.out.println("Rating must be between 1 and 5.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid rating. Please enter a number.");
        }
    }

    private static void manageSubstitutions() {
        System.out.println("\n=== Substitution Management ===");
        System.out.print("Enter original ingredient: ");
        String original = scanner.nextLine();
        System.out.print("Enter substitute ingredient: ");
        String substitute = scanner.nextLine();
        orderSystem.addSubstitution(original, substitute);
        System.out.println("Substitution added: " + original + " -> " + substitute);
        System.out.print("Apply substitution now? (y/n): ");
        if (scanner.nextLine().equalsIgnoreCase("y")) {
            Chef selectedChef = chefs.get(0); // Use first chef for simplicity
            orderSystem.applySubstitution(original, substitute, selectedChef);
            System.out.println("Substitution applied: " + orderSystem.getSubstitutionDetails());
        }
    }

    private static void viewOrderHistory() {
        System.out.println("\n=== Order History for " + customer.getName() + " ===");
        List<String> history = customer.getOrderHistory();
        if (history.isEmpty()) {
            System.out.println("No orders in history.");
        } else {
            for (String order : history) {
                System.out.println("- " + order);
            }
        }
        System.out.println("\nPersonalized Suggestions:");
        List<String> suggestions = customer.suggestPersonalizedMeals();
        if (suggestions.isEmpty()) {
            System.out.println("No suggestions available.");
        } else {
            for (String suggestion : suggestions) {
                System.out.println("- " + suggestion);
            }
        }
    }
}