
package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class BillingSystem {
    private final List<Invoice> invoices;
    private final OrderAndMenu orderSystem;

    public BillingSystem(OrderAndMenu orderSystem) {
        this.orderSystem = orderSystem;
        this.invoices = new ArrayList();
    }

    public void completeOrder() {
        if (!this.orderSystem.getCurrentOrder().isEmpty()) {
            Invoice invoice = this.generateInvoice();
            this.invoices.add(invoice);
            this.orderSystem.confirmOrder();
        }

    }

    public Invoice generateInvoice() {
        Set<String> items = this.orderSystem.getCurrentOrder();
        double total = this.orderSystem.calculateTotal();
        String customerName = this.orderSystem.getCustomerProfile() != null ? this.orderSystem.getCustomerProfile().getName() : "Guest";
        return new Invoice(customerName, new HashSet(items), total);
    }

    public List<Invoice> getInvoices() {
        return new ArrayList(this.invoices);
    }

    public FinancialReport generateFinancialReport() {
        double totalRevenue = this.invoices.stream().mapToDouble(Invoice::getTotal).sum();
        int orderCount = this.invoices.size();
        return new FinancialReport(totalRevenue, orderCount);
    }

    public static class Invoice {
        private final String customerName;
        private final Set<String> items;
        private final double total;
        private final String invoiceId;

        public Invoice(String customerName, Set<String> items, double total) {
            this.customerName = customerName;
            this.items = new HashSet(items);
            this.total = total;
            this.invoiceId = UUID.randomUUID().toString();
        }

        public String getCustomerName() {
            return this.customerName;
        }

        public Set<String> getItems() {
            return new HashSet(this.items);
        }

        public double getTotal() {
            return this.total;
        }

        public String getInvoiceId() {
            return this.invoiceId;
        }

        public String toString() {
            String var10000 = this.invoiceId;
            return "Invoice ID: " + var10000 + "\nCustomer: " + this.customerName + "\nItems: " + String.valueOf(this.items) + "\nTotal: $" + String.format("%.2f", this.total);
        }
    }

    public static class FinancialReport {
        private final double totalRevenue;
        private final int orderCount;

        public FinancialReport(double totalRevenue, int orderCount) {
            this.totalRevenue = totalRevenue;
            this.orderCount = orderCount;
        }

        public double getTotalRevenue() {
            return this.totalRevenue;
        }

        public int getOrderCount() {
            return this.orderCount;
        }

        public String toString() {
            String var10000 = String.format("%.2f", this.totalRevenue);
            return "Financial Report:\nTotal Revenue: $" + var10000 + "\nOrder Count: " + this.orderCount;
        }
    }
}
