package model;

import java.time.LocalDateTime;

public class NotificationService {
    public boolean sendDeliveryReminder(CustomerProfile customer, LocalDateTime deliveryTime, LocalDateTime currentTime) {
        if (customer == null || deliveryTime == null || currentTime == null) {
            System.out.println("Invalid input: customer, deliveryTime, or currentTime is null");
            return false;
        }
        if (currentTime.isAfter(deliveryTime.minusHours(1))) {
            System.out.println("Reminder sent to customer: " + customer.getName() + " about the delivery at " + deliveryTime);
            return true;
        } else {
            System.out.println("Reminder not sent: Current time " + currentTime + " is before reminder window for delivery at " + deliveryTime);
            return false;
        }
    }
    public boolean sendDeliveryReminder(CustomerProfile customer, LocalDateTime deliveryTime) {
        return sendDeliveryReminder(customer, deliveryTime, LocalDateTime.now());
    }

    public boolean notifyChefOfUpcomingTask(Chef chef, LocalDateTime currentTime) {
        if (chef == null || currentTime == null || chef.getScheduledTaskTime() == null) {
            System.out.println("Invalid input: chef, currentTime, or scheduledTaskTime is null");
            return false;
        }
        if (currentTime.isAfter(chef.getScheduledTaskTime().minusHours(1))) {
            System.out.println("Notification sent to chef: " + chef.getName() + " to prepare the meal.");
            return true;
        } else {
            System.out.println("Chef notification not sent: Current time " + currentTime + " is before task window");
            return false;
        }
    }

    public boolean notifyManagerOfLowStock(InventoryManager inventoryManager) {
        if (inventoryManager == null) {
            System.out.println("Invalid input: inventoryManager is null");
            return false;
        }
        return inventoryManager.checkLowStockAndNotifyManager();
    }
}