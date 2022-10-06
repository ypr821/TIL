package formTemplateMethod.formTemplateMethod3;

import java.util.Enumeration;

import formTemplateMethod.formTemplateMethod1.Rental;

public class HtmlStatement extends Statement {
	String headerString(Customer customer){
		return "<H1><EM>" + customer.getName() + " 고객님의 대여 기록</EM></H1>\n";
	}

	String eachRentalString(Rental rental){
		return "\t" + rental.getMovie().getTitle()+"\t" + String.valueOf(rental.getCharge()) + "\n";
	}

	String footerString(Customer customer){
		return "<p>누적 대여로: <EM>" + String.valueOf(customer.getTotalCharge()) + " </EM></p>\n";
	}

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
}
