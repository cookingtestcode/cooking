package model;

import java.time.LocalDateTime;
import java.util.logging.Logger;

public class NotificationService {
    private static final Logger logger = Logger.getLogger(NotificationService.class.getName());

    public boolean sendDeliveryReminder(CustomerProfile customer, LocalDateTime deliveryTime, LocalDateTime currentTime) {
        if (customer == null || deliveryTime == null || currentTime == null) {
            return false;
        }
        if (currentTime.isAfter(deliveryTime.minusHours(1))) {
            return true;
        } else {
            return false;
        }
    }

    public boolean sendDeliveryReminder(CustomerProfile customer, LocalDateTime deliveryTime) {
        return sendDeliveryReminder(customer, deliveryTime, LocalDateTime.now());
    }

    public boolean notifyChefOfUpcomingTask(Chef chef, LocalDateTime currentTime) {
        if (chef == null || currentTime == null || chef.getScheduledTaskTime() == null) {
            return false;
        }
        if (currentTime.isAfter(chef.getScheduledTaskTime().minusHours(1))) {
            return true;
        } else {
            return false;
        }
    }

    public boolean notifyManagerOfLowStock(InventoryManager inventoryManager) {
        if (inventoryManager == null) {
            return false;
        }
        return inventoryManager.checkLowStockAndNotifyManager();
    }
}
