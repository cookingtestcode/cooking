package model;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static Menu restaurantMenu;
    private static CustomerProfile customer;
    private static InventoryManager inventoryManager;
    private static Scanner scanner;

    static {
        restaurantMenu = new Menu();
        customer = new CustomerProfile();
        inventoryManager = new InventoryManager();
        scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
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
                    manageInventory();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void displayMainMenu() {
        System.out.println("\n=== Restaurant Management System ===");
        System.out.println("1. Manage Customer Profile");
        System.out.println("2. View Menu");
        System.out.println("3. Manage Inventory");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");
    }

    private static int getUserChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException var1) {
            return -1;
        }
    }

    private static void manageCustomerProfile() {
        System.out.print("Enter customer name: ");
        String name = scanner.nextLine();
        customer.setName(name);

        System.out.print("Enter dietary preference (e.g., Vegetarian, Vegan): ");
        String dietary = scanner.nextLine();
        customer.setDietaryPreference(dietary);

        System.out.print("Enter allergy (e.g., Nuts, Dairy): ");
        String allergy = scanner.nextLine();
        customer.setAllergy(allergy);

        System.out.println("Customer profile updated.");
    }

    private static void displayFilteredMenu() {
        System.out.println("\n=== Menu for " + customer.getName() + " ===");
        List<String> recommendedItems = getRecommendedItems();
        if (recommendedItems.isEmpty()) {
            System.out.println("No items match the customer's preferences.");
        } else {
            for (String item : recommendedItems) {
                System.out.println("- " + item + " ($" + restaurantMenu.getItemPrice(item) + ")");
                System.out.println("  Ingredients: " + String.join(", ", restaurantMenu.getItemIngredients(item)));
                System.out.println("  Category: " + restaurantMenu.getItemCategory(item));
                System.out.println("  Dietary: " + restaurantMenu.getDietaryTag(item));
                System.out.println();
            }
        }
    }

    private static List<String> getRecommendedItems() {
        // This can be extended to filter by dietary preference
        return restaurantMenu.getMenuItems();  // Just return all items for now
    }

    private static void manageInventory() {
        System.out.println("\n=== Inventory Management ===");
        System.out.println("Current Inventory: " + inventoryManager.getInventory());
        System.out.println("1. Update Stock");
        System.out.println("2. View Supplier Prices");
        System.out.print("Enter your choice: ");
        int choice = getUserChoice();
        if (choice == 1) {
            updateInventory();
        } else {
            System.out.println("Invalid choice.");
        }
    }

    private static void updateInventory() {
        System.out.print("Enter ingredient name to update stock: ");
        String ingredient = scanner.nextLine();

        System.out.print("Enter new quantity: ");
        int quantity = Integer.parseInt(scanner.nextLine());

        inventoryManager.setIngredientStock(ingredient, quantity);
        System.out.println("Stock updated for " + ingredient + ": " + quantity);
    }
}
