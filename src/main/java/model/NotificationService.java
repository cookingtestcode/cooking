package model;

import java.time.LocalDateTime;
import java.util.logging.Logger;

public class NotificationService {
    // Logger for logging messages
    private static final Logger logger = Logger.getLogger(NotificationService.class.getName());

    // Method to send delivery reminder to the customer
    public boolean sendDeliveryReminder(CustomerProfile customer, LocalDateTime deliveryTime, LocalDateTime currentTime) {
        if (customer == null || deliveryTime == null || currentTime == null) {
            logger.warning("Invalid input: customer, deliveryTime, or currentTime is null");
            return false;
        }
        if (currentTime.isAfter(deliveryTime.minusHours(1))) {
            logger.info("Reminder sent to customer: " + customer.getName() + " about the delivery at " + deliveryTime);
            return true;
        } else {
            logger.info("Reminder not sent: Current time " + currentTime + " is before reminder window for delivery at " + deliveryTime);
            return false;
        }
    }

    // Method to notify the chef about upcoming tasks
    public boolean notifyChefOfUpcomingTask(Chef chef, LocalDateTime currentTime) {
        if (chef == null || currentTime == null || chef.getScheduledTaskTime() == null) {
            logger.warning("Invalid input: chef, currentTime, or scheduledTaskTime is null");
            return false;
        }
        if (currentTime.isAfter(chef.getScheduledTaskTime().minusHours(1))) {
            logger.info("Notification sent to chef: " + chef.getName() + " to prepare the meal.");
            return true;
        } else {
            logger.info("Chef notification not sent: Current time " + currentTime + " is before task window");
            return false;
        }
    }

    // Method to notify the manager of low stock
    public boolean notifyManagerOfLowStock(InventoryManager inventoryManager) {
        if (inventoryManager == null) {
            logger.warning("Invalid input: inventoryManager is null");
            return false;
        }
        return inventoryManager.checkLowStockAndNotifyManager();
    }
}
