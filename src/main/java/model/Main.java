package model;

import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static Menu restaurantMenu = new Menu();
    private static CustomerProfile customer = new CustomerProfile();
    private static List<Chef> chefs = new ArrayList<>();
    private static OrderAndMenu orderSystem;
    private static InventoryManager inventoryManager;
    private static BillingSystem billingSystem;
    private static Scanner scanner = new Scanner(System.in);

    // تهيئة الكائنات في البلوك الثابت
    static {
        restaurantMenu = new Menu(); // تأكد من تهيئة القائمة
        orderSystem = new OrderAndMenu(restaurantMenu); // تأكد من تهيئة orderSystem
        inventoryManager = new InventoryManager(); // تأكد من تهيئة inventoryManager
        billingSystem = new BillingSystem(orderSystem);  // تأكد من تهيئة billingSystem مع orderSystem
        scanner = new Scanner(System.in); // تأكد من تهيئة scanner
    }

    public static void main(String[] args) {
        initializeChefs();
        orderSystem.setCustomerProfile(customer); // تأكد من تهيئة العلاقة بين العميل والنظام

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
                    processBillingAndReporting();
                    break;
                case 11:
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
        System.out.println("10. Process Billing and Reporting");
        System.out.println("11. Exit");
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
        System.out.print("Enter dietary preference (e.g., Vegetarian, Vegan) or press Enter to skip: ");
        String dietary = scanner.nextLine();
        if (!dietary.isEmpty()) {
            customer.setDietaryPreference(dietary);  // تأكد من تعيين التفضيل
        }

        System.out.print("Enter allergy (e.g., Nuts, Dairy) or press Enter to skip: ");
        String allergy = scanner.nextLine();
        if (!allergy.isEmpty()) {
            customer.setAllergy(allergy);
        }

        System.out.println("Customer profile updated.");
    }

    private static void manageInventory() {
        System.out.println("\n=== Inventory Management ===");
        System.out.println("Current Inventory: " + inventoryManager.getInventory());
        System.out.println("1. Update Stock");
        System.out.println("2. View Supplier Prices");
        System.out.print("Enter your choice: ");
        int choice = getUserChoice();
        String ingredient;
        if (choice == 1) {
            System.out.print("Enter ingredient name to update stock: ");
            ingredient = scanner.nextLine();
            System.out.print("Enter new quantity: ");
            try {
                int quantity = Integer.parseInt(scanner.nextLine());
                inventoryManager.setIngredientStock(ingredient, quantity);
                System.out.println("Stock updated for " + ingredient + ": " + quantity);
                if (inventoryManager.checkAndAutoOrder(ingredient)) {
                    System.out.println("Auto-order placed for " + ingredient + " from supplier: " + inventoryManager.fetchSupplierPrice(ingredient));
                }
            } catch (NumberFormatException var3) {
                System.out.println("Invalid quantity. Please enter a number.");
            }
        } else if (choice == 2) {
            System.out.print("Enter ingredient name to check supplier price: ");
            ingredient = scanner.nextLine();
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

    private static void processBillingAndReporting() {
        System.out.println("\n=== Process Billing and Reporting ===");
        System.out.println("Generating invoices for all completed orders:");
        for (BillingSystem.Invoice invoice : billingSystem.getInvoices()) {
            System.out.println(invoice);
        }

        System.out.println("Generating financial report:");
        BillingSystem.FinancialReport report = billingSystem.generateFinancialReport();
        System.out.println(report);
    }
}
