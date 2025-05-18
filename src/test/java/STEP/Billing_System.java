
package STEP;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.BillingSystem;
import model.CustomerProfile;
import model.Menu;
import model.OrderAndMenu;
import org.junit.Assert;

public class Billing_System {
    private BillingSystem billingSystem;
    private OrderAndMenu orderSystem;
    private Menu menu = new Menu();
    private CustomerProfile customer = new CustomerProfile();

    public Billing_System() {
        this.orderSystem = new OrderAndMenu(this.menu);
        this.orderSystem.setCustomerProfile(this.customer);
        this.billingSystem = new BillingSystem(this.orderSystem);
    }

    @Given("the customer has completed an order")
    public void theCustomerHasCompletedAnOrder() {
        this.customer.setName("Test Customer");
        this.orderSystem.addToOrder("Vegetarian Pizza");
        this.orderSystem.addToOrder("Chocolate Cake");
        this.billingSystem.completeOrder();
    }

    @When("the system processes the payment")
    public void theSystemProcessesThePayment() {
        Assert.assertTrue("Order should be cleared after completion", this.orderSystem.getCurrentOrder().isEmpty());
    }

    @Then("the customer should receive a detailed invoice")
    public void theCustomerShouldReceiveADetailedInvoice() {
        BillingSystem.Invoice invoice = (BillingSystem.Invoice)this.billingSystem.getInvoices().get(0);
        Assert.assertNotNull("Invoice should be generated", invoice);
        Assert.assertEquals("Customer name should match", "Test Customer", invoice.getCustomerName());
        Assert.assertTrue("Invoice should contain Vegetarian Pizza", invoice.getItems().contains("Vegetarian Pizza"));
        Assert.assertTrue("Invoice should contain Chocolate Cake", invoice.getItems().contains("Chocolate Cake"));
        Assert.assertEquals("Invoice total should match", 21.98, invoice.getTotal(), 0.01);
    }

    @And("the invoice should be saved in the system")
    public void theInvoiceShouldBeSavedInTheSystem() {
        Assert.assertEquals("Invoice should be saved in the system", 1L, (long)this.billingSystem.getInvoices().size());
    }

    @Given("the system has multiple completed orders")
    public void theSystemHasMultipleCompletedOrders() {
        this.customer.setName("Customer 1");
        this.orderSystem.addToOrder("Vegetarian Pizza");
        this.billingSystem.completeOrder();
        this.orderSystem.addToOrder("Pasta Carbonara");
        this.orderSystem.addToOrder("Nut Salad");
        this.billingSystem.completeOrder();
        this.orderSystem.addToOrder("Chocolate Cake");
        this.billingSystem.completeOrder();
    }

    @When("the admin requests a financial report")
    public void theAdminRequestsAFinancialReport() {
        this.billingSystem.generateFinancialReport();
    }

    @Then("the system should generate a report showing total revenue and order count")
    public void theSystemShouldGenerateAReportShowingTotalRevenueAndOrderCount() {
        BillingSystem.FinancialReport report = this.billingSystem.generateFinancialReport();
        Assert.assertNotNull("Financial report should be generated", report);
        Assert.assertEquals("Total revenue should match", 46.23, report.getTotalRevenue(), 0.01);
        Assert.assertEquals("Order count should match", 3L, (long)report.getOrderCount());
    }
}
