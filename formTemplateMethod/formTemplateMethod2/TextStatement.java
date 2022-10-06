package formTemplateMethod.formTemplateMethod2;

import java.util.Enumeration;

import formTemplateMethod.formTemplateMethod1.Rental;

public class TextStatement extends Statement{
	public String value(Customer customer) {
		Enumeration<Rental> rentals = customer.getRentals().elements();
		String result = customer.getName() + " 고객님의 대여 기록\n";
		while (rentals.hasMoreElements()){
			formTemplateMethod.formTemplateMethod1.Rental each = (Rental) rentals.nextElement();

			result += "\t" + each.getMovie().getTitle()+"\t" + String.valueOf(each.getCharge()) + "\n";
		}

		// 푸터 행 추가?
		result += "누적 대여로: " + String.valueOf(customer.getTotalCharge()) + "\n";

		return result;
	}
}
