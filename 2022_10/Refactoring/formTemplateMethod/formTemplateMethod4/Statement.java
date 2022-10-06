package formTemplateMethod.formTemplateMethod4;

import java.util.Enumeration;

public abstract class Statement {
	public String value(Customer customer) {
		Enumeration rentals = customer.getRentals().elements();
		String result = headerString(customer);
		while (rentals.hasMoreElements()){
			Rental each = (Rental) rentals.nextElement();

			result += eachRentalString(each);
		}

		// 푸터 행 추가
		result += footerString(customer);

		return result;
	}

	abstract String headerString(Customer customer);
	abstract String eachRentalString(Rental rental);
	abstract String footerString(Customer customer);

}
