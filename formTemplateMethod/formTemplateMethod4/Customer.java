package formTemplateMethod.formTemplateMethod4;

import java.util.Vector;

public class Customer {
	private String name;
	private int totalCharge;

	private Vector rentals = new Vector<>();

	public String getName() {
		return name;
	}

	public Vector getRentals() {
		return rentals;
	}


	public String statement() {
		return new TextStatement().value(this);
	}

	public String htmlStatement() {
		return new HtmlStatement().value(this);
	}

	public int getTotalCharge() {
		return totalCharge;
	}


	public static void main(String[] args) {
		Customer customer = new Customer();
		System.out.println(customer.statement());
		System.out.println("++++++++++++++++++");
		System.out.println(customer.htmlStatement());
	}
}
