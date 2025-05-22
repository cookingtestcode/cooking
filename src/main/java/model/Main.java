package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Main {
    private static Menu restaurantMenu = new Menu();
    private static CustomerProfile customer = new CustomerProfile();
    private static List<Chef> chefs = new ArrayList<>();
    private static OrderAndMenu orderSystem;
    private static InventoryManager inventoryManager;
    private static BillingSystem billingSystem;
    private static NotificationService notificationService;
    private static Scanner scanner;
    private static Map<String, Integer> itemRatings = new HashMap<>();
    private static final List<String> AVAILABLE_INGREDIENTS = new ArrayList<>(
            Arrays.asList("Cheese", "Tomatoes", "Olive Oil", "Flour", "Pasta", "Eggs",
                    "Milk", "Sugar", "Chocolate", "Nuts", "Vegan Cheese", "Seeds",
                    "Almond Milk", "Flaxseed")
    );
    private static final Map<String, Double> INGREDIENT_PRICES = new HashMap<>();
    private static String userMode = "Customer";
    private static List<String> confirmedOrders = new ArrayList<>(); // New list to track confirmed orders

    static {
        INGREDIENT_PRICES.put("Cheese", 2.0);
        INGREDIENT_PRICES.put("Tomatoes", 1.5);
        INGREDIENT_PRICES.put("Pasta", 2.5);
        INGREDIENT_PRICES.put("fish", 3.0);
        INGREDIENT_PRICES.put("chicken", 4.0);
        INGREDIENT_PRICES.put("meat", 5.0);
    }

    public static void main(String[] args) {
        initializeSystem();
        while (true) {
            selectUserMode();
            if (userMode.equals("Customer")) {
                runCustomerInterface();
            } else if (userMode.equals("Manager")) {
                runManagerInterface();
            } else if (userMode.equals("Exit")) {
                System.out.println("Exiting...");
                scanner.close();
                break;
            }
        }
    }

    private static void initializeSystem() {
        chefs.add(new Chef("Chef Ali", Arrays.asList("Main", "Dessert"), 0));
        chefs.add(new Chef("Chef Sara", Arrays.asList("Starter", "Main"), 0));
        orderSystem = new OrderAndMenu(restaurantMenu);
        orderSystem.setCustomerProfile(customer);
        inventoryManager = new InventoryManager();
        billingSystem = new BillingSystem(orderSystem);
        notificationService = new NotificationService();
        scanner = new Scanner(System.in);

        orderSystem.setAvailableIngredients("Cheese", "Tomatoes", "Olive Oil", "Flour", "Pasta", "Eggs",
                "Milk", "Sugar", "Chocolate", "Nuts", "Vegan Cheese", "Seeds",
                "Almond Milk", "Flaxseed");

        inventoryManager.setIngredientStock("Cheese", 10);
        inventoryManager.setIngredientStock("Tomatoes", 10);
        inventoryManager.setIngredientStock("Pasta", 10);

        orderSystem.addIncompatiblePair("Cheese", "Vegan Cheese");
        orderSystem.addIncompatiblePair("Milk", "Almond Milk");
    }

    private static void selectUserMode() {
        System.out.println("\n=== Welcome to Restaurant Management System ===");
        System.out.println("Select user mode:");
        System.out.println("1. Customer");
        System.out.println("2. Manager");
        System.out.println("3. Exit");
        System.out.print("Enter your choice (1, 2, or 3): ");
        int choice = getUserChoice();
        if (choice == 2) {
            userMode = "Manager";
            System.out.println("Logged in as Manager.");
        } else if (choice == 3) {
            userMode = "Exit";
        } else {
            userMode = "Customer";
            System.out.println("Logged in as Customer.");
        }
    }

    private static int getUserChoice() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // Customer Interface
    private static void runCustomerInterface() {
        while (true) {
            displayCustomerMenu();
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
                    rateMenuItem();
                    break;
                case 6:
                    viewOrderHistory();
                    break;
                case 7:
                    viewCustomerInvoice();
                    break;
                case 8:
                    return; // Return to mode selection
                case 9:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void displayCustomerMenu() {
        System.out.println("\n=== Customer Menu ===");
        System.out.println("1. Manage Profile");
        System.out.println("2. View Menu");
        System.out.println("3. Manage Order");
        System.out.println("4. Create Custom Meal");
        System.out.println("5. Rate Menu Item");
        System.out.println("6. View Order History");
        System.out.println("7. View Your Invoice");
        System.out.println("8. Switch User Mode");
        System.out.println("9. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void manageCustomerProfile() {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }
        customer.setName(name);

        System.out.print("Enter dietary preference (e.g., Vegetarian, Vegan) or press Enter to skip: ");
        String dietary = scanner.nextLine().trim();
        if (!dietary.isEmpty()) {
            customer.setDietaryPreference(dietary);
        }

        System.out.print("Enter allergy (e.g., Nuts, Dairy) or press Enter to skip: ");
        String allergy = scanner.nextLine().trim();
        if (!allergy.isEmpty()) {
            customer.setAllergy(allergy);
        }

        System.out.print("Enter meal delivery time (yyyy-MM-dd HH:mm) or press Enter to skip: ");
        String deliveryTimeInput = scanner.nextLine().trim();
        if (!deliveryTimeInput.isEmpty()) {
            try {
                LocalDateTime deliveryTime = LocalDateTime.parse(deliveryTimeInput,
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                customer.scheduleMeal(deliveryTime);
                System.out.println("Meal delivery scheduled for: " + deliveryTime);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Use yyyy-MM-dd HH:mm (e.g., 2025-05-21 18:00).");
            }
        }

        System.out.println("Profile updated for " + customer.getName() + ".");
    }

    private static void displayFilteredMenu() {
        System.out.println("\n=== Menu for " + (customer.getName() != null ? customer.getName() : "Guest") + " ===");
        List<String> recommendedItems = restaurantMenu.getFilteredMenu(customer);

        if (recommendedItems.isEmpty()) {
            System.out.println("No items match your preferences or allergies.");
        } else {
            System.out.println("Available Menu Items:");
            for (String item : recommendedItems) {
                System.out.printf("- %s ($%.2f)%n", item, restaurantMenu.getItemPrice(item));
                System.out.println("  Ingredients: " + restaurantMenu.getItemIngredients(item));
                System.out.println("  Category: " + restaurantMenu.getItemCategory(item));
                System.out.println("  Dietary: " + (restaurantMenu.getDietaryTag(item) != null ?
                        restaurantMenu.getDietaryTag(item) : "None"));
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
                    String itemToAdd = scanner.nextLine().trim();
                    if (orderSystem.addToOrder(itemToAdd)) {
                        System.out.println(itemToAdd + " added to order.");
                        assignTaskToChef(itemToAdd);
                    } else {
                        System.out.println("Item not found in menu or invalid input.");
                    }
                    break;
                case 2:
                    System.out.print("Enter item name to remove: ");
                    String itemToRemove = scanner.nextLine().trim();
                    if (orderSystem.removeFromOrder(itemToRemove)) {
                        System.out.println(itemToRemove + " removed from order.");
                    } else {
                        System.out.println("Item not in order or invalid input.");
                    }
                    break;
                case 3:
                    System.out.println("Current Order: " + orderSystem.getCurrentOrder());
                    System.out.printf("Total: $%.2f%n", orderSystem.calculateTotal());
                    break;
                case 4:
                    if (orderSystem.getCurrentOrder().isEmpty()) {
                        System.out.println("Order is empty. Add items before confirming.");
                    } else {
                        // Save order items to confirmedOrders before completing
                        List<String> currentOrder = new ArrayList<>(orderSystem.getCurrentOrder());
                        confirmedOrders.addAll(currentOrder);
                        billingSystem.completeOrder();
                        System.out.println("Order confirmed and added to history.");
                        return;
                    }
                    break;
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
        String mealName = scanner.nextLine().trim();
        if (mealName.isEmpty()) {
            System.out.println("Meal name cannot be empty.");
            return;
        }

        Map<String, Integer> inventory = inventoryManager.getInventory();
        List<String> availableIngredients = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            String ingredient = entry.getKey();
            if (entry.getValue() > 0 && !customer.hasAllergy(ingredient)) {
                availableIngredients.add(ingredient);
            }
        }

        if (availableIngredients.isEmpty()) {
            System.out.println("No ingredients available in inventory.");
            return;
        }

        System.out.println("Available ingredients (choose from the list):");
        for (int i = 0; i < availableIngredients.size(); i++) {
            System.out.println((i + 1) + ". " + availableIngredients.get(i) +
                    " ($" + INGREDIENT_PRICES.getOrDefault(availableIngredients.get(i), 2.0) + ")");
        }

        List<String> ingredients = new ArrayList<>();
        System.out.println("Enter ingredients (one per line, type 'done' to finish):");
        while (true) {
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("done")) {
                if (ingredients.size() < 1) {
                    System.out.println("Custom meal requires at least 1 ingredient.");
                    return;
                }
                double price = ingredients.stream()
                        .mapToDouble(ing -> INGREDIENT_PRICES.getOrDefault(ing, 2.0))
                        .sum();
                String category = ingredients.size() >= 2 ? "Main" : "Starter";
                String dietaryTag = customer.getDietaryPreference() != null ?
                        customer.getDietaryPreference() : null;

                if (ingredients.size() == 1 || orderSystem.isValidMeal(ingredients.get(0), ingredients.get(1))) {
                    restaurantMenu.addMenuItem(mealName, price, ingredients, category, dietaryTag);
                    orderSystem.addToOrder(mealName);
                    System.out.println("Custom meal " + mealName + " created with price $" + price +
                            " and added to order.");
                    assignTaskToChef(mealName);
                } else {
                    System.out.println("Invalid meal: incompatible ingredients or allergies detected.");
                }
                return;
            }

            if (availableIngredients.contains(input)) {
                ingredients.add(input);
                System.out.println(input + " added to the meal.");
            } else {
                System.out.println("Invalid ingredient: " + input + ". Please choose from the available ingredients.");
            }
        }
    }

    private static void rateMenuItem() {
        System.out.print("Enter menu item name to rate: ");
        String itemName = scanner.nextLine().trim();
        if (!restaurantMenu.getAllMenuItems().containsKey(itemName)) {
            System.out.println("Item not found in menu.");
            return;
        }
        System.out.print("Enter rating (1-5): ");
        try {
            int rating = Integer.parseInt(scanner.nextLine().trim());
            if (rating >= 1 && rating <= 5) {
                itemRatings.put(itemName, rating);
                System.out.println("Rating recorded for " + itemName + ": " + rating);
            } else {
                System.out.println("Rating must be between 1 and 5.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid rating. Please enter a number.");
        }
    }

    private static void viewOrderHistory() {
        System.out.println("\n=== Order History for " + (customer.getName() != null ? customer.getName() : "Guest") + " ===");
        List<String> history = customer.getOrderHistory();
        if (history.isEmpty()) {
            System.out.println("No orders in history.");
        } else {
            for (String order : history) {
                String ratingInfo = itemRatings.containsKey(order) ? " (Rated: " + itemRatings.get(order) + ")" : "";
                System.out.println("- " + order + ratingInfo + " ($" + restaurantMenu.getItemPrice(order) + ")");
            }
        }

        System.out.println("\nPersonalized Suggestions:");
        customer.generateMealSuggestions(new ArrayList<>(restaurantMenu.getAllMenuItems().keySet()));
        List<String> suggestions = customer.suggestPersonalizedMeals();
        List<String> filteredSuggestions = new ArrayList<>();
        for (String suggestion : suggestions) {
            if (!itemRatings.containsKey(suggestion) || itemRatings.get(suggestion) >= 3) {
                filteredSuggestions.add(suggestion);
            }
        }
        if (filteredSuggestions.isEmpty()) {
            System.out.println("No suggestions available.");
        } else {
            for (String suggestion : filteredSuggestions) {
                System.out.println("- " + suggestion);
            }
        }
    }

    private static void viewCustomerInvoice() {
        System.out.println("\n=== Your Invoice ===");
        if (confirmedOrders.isEmpty()) {
            System.out.println("No invoices found for " + (customer.getName() != null ? customer.getName() : "Guest") + ". Please confirm an order first.");
            return;
        }

        System.out.println("Invoice for " + (customer.getName() != null ? customer.getName() : "Guest") + ":");
        System.out.println("Items:");
        double total = 0.0;
        for (String item : confirmedOrders) {
            if (restaurantMenu.getAllMenuItems().containsKey(item)) {
                double price = restaurantMenu.getItemPrice(item);
                System.out.printf("- %s ($%.2f)%n", item, price);
                total += price;
            }
        }
        System.out.printf("Total: $%.2f%n", total);
    }

    // Manager Interface
    private static void runManagerInterface() {
        while (true) {
            displayManagerMenu();
            int choice = getUserChoice();
            switch (choice) {
                case 1:
                    manageInventory();
                    break;
                case 2:
                    viewChefNotifications();
                    break;
                case 3:
                    manageSubstitutions();
                    break;
                case 4:
                    processBillingAndReporting();
                    break;
                case 5:
                    scheduleNotifications();
                    break;
                case 6:
                    manageMenu();
                    break;
                case 7:
                    return; // Return to mode selection
                case 8:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void displayManagerMenu() {
        System.out.println("\n=== Manager Menu ===");
        System.out.println("1. Manage Inventory");
        System.out.println("2. View Chef Notifications");
        System.out.println("3. Manage Substitutions");
        System.out.println("4. Process Billing and Reporting");
        System.out.println("5. Schedule Notifications");
        System.out.println("6. Manage Menu");
        System.out.println("7. Switch User Mode");
        System.out.println("8. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void manageInventory() {
        System.out.println("\n=== Inventory Management ===");
        System.out.println("Current Inventory: " + inventoryManager.getInventory());
        System.out.println("1. Update Stock");
        System.out.println("2. View Supplier Prices");
        System.out.println("3. Check Low Stock");
        System.out.print("Enter your choice: ");
        int choice = getUserChoice();
        if (choice == 1) {
            System.out.print("Enter ingredient name to update stock: ");
            String ingredient = scanner.nextLine().trim();
            System.out.print("Enter new quantity: ");
            try {
                int quantity = Integer.parseInt(scanner.nextLine().trim());
                if (quantity < 0) {
                    System.out.println("Quantity cannot be negative.");
                    return;
                }
                inventoryManager.setIngredientStock(ingredient, quantity);
                System.out.println("Stock updated for " + ingredient + ": " + quantity);
                if (!AVAILABLE_INGREDIENTS.contains(ingredient)) {
                    AVAILABLE_INGREDIENTS.add(ingredient);
                    System.out.print("Enter supplier price for " + ingredient +
                            " (e.g., 10.00) or press Enter for default ($2.00): ");
                    String priceInput = scanner.nextLine().trim();
                    double price = priceInput.isEmpty() ? 2.0 : Double.parseDouble(priceInput);
                    INGREDIENT_PRICES.put(ingredient, price);
                }
                if (inventoryManager.checkAndAutoOrder(ingredient)) {
                    System.out.println("Auto-order placed for " + ingredient + " from supplier: $" +
                            INGREDIENT_PRICES.getOrDefault(ingredient, 2.0));
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid quantity or price. Please enter a number.");
            }
        } else if (choice == 2) {
            System.out.print("Enter ingredient name to check supplier price: ");
            String ingredient = scanner.nextLine().trim();
            if (inventoryManager.getInventory().containsKey(ingredient)) {
                System.out.println("Supplier price for " + ingredient + ": $" +
                        INGREDIENT_PRICES.getOrDefault(ingredient, 2.0));
            } else {
                System.out.println("Ingredient " + ingredient + " not in inventory.");
            }
        } else if (choice == 3) {
            notificationService.notifyManagerOfLowStock(inventoryManager);
            List<String> notifications = inventoryManager.getManagerNotifications();
            Set<String> uniqueNotifications = new LinkedHashSet<>(notifications);
            System.out.println("Manager Notifications: " + new ArrayList<>(uniqueNotifications));
        } else {
            System.out.println("Invalid choice.");
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

    private static void manageSubstitutions() {
        System.out.println("\n=== Substitution Management ===");
        System.out.print("Enter original ingredient: ");
        String original = scanner.nextLine().trim();
        System.out.print("Enter substitute ingredient: ");
        String substitute = scanner.nextLine().trim();
        if (original.isEmpty() || substitute.isEmpty()) {
            System.out.println("Ingredients cannot be empty.");
            return;
        }
        orderSystem.addSubstitution(original, substitute);
        System.out.println("Substitution added: " + original + " -> " + substitute);
        System.out.print("Apply substitution now? (y/n): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
            Chef selectedChef = chefs.isEmpty() ? null : chefs.get(0);
            if (selectedChef != null) {
                try {
                    orderSystem.applySubstitution(original, substitute, selectedChef);
                    System.out.println("Substitution applied: " + orderSystem.getSubstitutionDetails());
                } catch (IllegalArgumentException | IllegalStateException e) {
                    System.out.println("Error applying substitution: " + e.getMessage());
                }
            } else {
                System.out.println("No chefs available to apply substitution.");
            }
        }
    }

    private static void processBillingAndReporting() {
        System.out.println("\n=== Process Billing and Reporting ===");
        System.out.println("Generating invoices for all completed orders:");
        List<BillingSystem.Invoice> invoices = billingSystem.getInvoices();
        if (invoices.isEmpty()) {
            System.out.println("No invoices available.");
        } else {
            for (BillingSystem.Invoice invoice : invoices) {
                System.out.println(invoice);
            }
        }

        System.out.println("\nGenerating financial report:");
        BillingSystem.FinancialReport report = billingSystem.generateFinancialReport();
        System.out.println(report);
    }

    private static void scheduleNotifications() {
        System.out.println("\n=== Schedule Notifications ===");
        System.out.print("Enter current time for notifications (yyyy-MM-dd HH:mm) or press Enter for now: ");
        String timeInput = scanner.nextLine().trim();
        LocalDateTime currentTime = timeInput.isEmpty() ? LocalDateTime.now() : parseDateTime(timeInput);

        if (currentTime == null) {
            System.out.println("Invalid date format. Notifications cancelled.");
            return;
        }

        if (customer.getMealDeliveryTime() != null) {
            boolean sent = notificationService.sendDeliveryReminder(customer, customer.getMealDeliveryTime(), currentTime);
            if (sent) {
                System.out.println("Customer delivery reminder processed.");
            }
        }

        for (Chef chef : chefs) {
            if (chef.getScheduledTaskTime() != null) {
                boolean sent = notificationService.notifyChefOfUpcomingTask(chef, currentTime);
                if (sent) {
                    System.out.println("Chef " + chef.getName() + " notified of upcoming task.");
                }
            }
        }

        boolean lowStock = notificationService.notifyManagerOfLowStock(inventoryManager);
        if (lowStock) {
            System.out.println("Manager notified of low stock.");
        }
    }

    private static void manageMenu() {
        System.out.println("\n=== Menu Management ===");
        System.out.println("1. Add Menu Item");
        System.out.println("2. Remove Menu Item");
        System.out.println("3. View Menu");
        System.out.print("Enter your choice: ");
        int choice = getUserChoice();
        if (choice == 1) {
            System.out.print("Enter meal name: ");
            String mealName = scanner.nextLine().trim();
            if (mealName.isEmpty()) {
                System.out.println("Meal name cannot be empty.");
                return;
            }
            System.out.print("Enter price: ");
            double price;
            try {
                price = Double.parseDouble(scanner.nextLine().trim());
                if (price <= 0) {
                    System.out.println("Price must be positive.");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid price.");
                return;
            }
            System.out.print("Enter category (e.g., Main, Dessert, Starter): ");
            String category = scanner.nextLine().trim();
            if (category.isEmpty()) {
                System.out.println("Category cannot be empty.");
                return;
            }
            System.out.print("Enter dietary tag (e.g., Vegetarian, Vegan) or press Enter to skip: ");
            String dietaryTag = scanner.nextLine().trim();
            if (dietaryTag.isEmpty()) {
                dietaryTag = null;
            }

            Map<String, Integer> inventory = inventoryManager.getInventory();
            List<String> availableIngredients = new ArrayList<>(inventory.keySet());
            if (availableIngredients.isEmpty()) {
                System.out.println("No ingredients available in inventory.");
                return;
            }

            System.out.println("Available ingredients (choose from the list):");
            for (int i = 0; i < availableIngredients.size(); i++) {
                System.out.println((i + 1) + ". " + availableIngredients.get(i));
            }

            List<String> ingredients = new ArrayList<>();
            System.out.println("Enter ingredients (one per line, type 'done' to finish):");
            while (true) {
                String input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("done")) {
                    if (ingredients.size() < 1) {
                        System.out.println("Menu item requires at least 1 ingredient.");
                        return;
                    }
                    restaurantMenu.addMenuItem(mealName, price, ingredients, category, dietaryTag);
                    System.out.println("Menu item " + mealName + " added.");
                    return;
                }
                if (availableIngredients.contains(input)) {
                    ingredients.add(input);
                    System.out.println(input + " added to the meal.");
                } else {
                    System.out.println("Invalid ingredient: " + input + ". Please choose from the available ingredients.");
                }
            }
        } else if (choice == 2) {
            System.out.print("Enter meal name to remove: ");
            String mealName = scanner.nextLine().trim();
            if (restaurantMenu.getAllMenuItems().containsKey(mealName)) {
                restaurantMenu.addMenuItem(mealName, 0, new ArrayList<>(), null, null); // Assuming this removes by overwriting
                System.out.println("Menu item " + mealName + " removed.");
            } else {
                System.out.println("Menu item " + mealName + " not found.");
            }
        } else if (choice == 3) {
            System.out.println("\n=== Current Menu ===");
            Map<String, Double> menuItems = restaurantMenu.getAllMenuItems();
            if (menuItems.isEmpty()) {
                System.out.println("No items in the menu.");
            } else {
                for (String item : menuItems.keySet()) {
                    System.out.printf("- %s ($%.2f)%n", item, restaurantMenu.getItemPrice(item));
                    System.out.println("  Ingredients: " + restaurantMenu.getItemIngredients(item));
                    System.out.println("  Category: " + restaurantMenu.getItemCategory(item));
                    System.out.println("  Dietary: " + (restaurantMenu.getDietaryTag(item) != null ?
                            restaurantMenu.getDietaryTag(item) : "None"));
                    System.out.println();
                }
            }
        } else {
            System.out.println("Invalid choice.");
        }
    }

    private static String suggestAutoSubstitution(String ingredient) {
        String substitute = orderSystem.suggestSubstitution(ingredient);
        if (substitute != null && inventoryManager.getInventory().containsKey(substitute) &&
                inventoryManager.getInventory().get(substitute) > 0 && !customer.hasAllergy(substitute)) {
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
            selectedChef.scheduleCookingTask(task.getDeadline());
            System.out.println("Task assigned to " + selectedChef.getName() + ": " + task.getName() +
                    " (Due: " + task.getDeadline() + ")");
        } else {
            System.out.println("No suitable chef available for " + itemName);
        }
    }

    private static LocalDateTime parseDateTime(String input) {
        try {
            return LocalDateTime.parse(input, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}
