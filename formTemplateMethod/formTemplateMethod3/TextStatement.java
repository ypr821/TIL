package formTemplateMethod.formTemplateMethod3;

import java.util.Enumeration;


public class TextStatement extends Statement {
	String headerString(Customer customer){
		return customer.getName() + " 고객님의 대여 기록 \n";
	}

	String eachRentalString(Rental rental){
		return "\t" + rental.getMovie().getTitle()+"\t" + String.valueOf(rental.getCharge()) + "\n";
	}

	String footerString(Customer customer){
		return "누적 대여로: " + String.valueOf(customer.getTotalCharge()) + "\n";
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
