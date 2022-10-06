package formTemplateMethod.formTemplateMethod2;

import java.util.Enumeration;

import formTemplateMethod.formTemplateMethod1.Rental;

public class HtmlStatement extends Statement{
	public String value(Customer customer) {
		Enumeration rentals = customer.getRentals().elements();
		String result = "<H1><EM>" +customer. getName() + " 고객님의 대여 기록</EM></H1>\n";
		while (rentals.hasMoreElements()){
			formTemplateMethod.formTemplateMethod1.Rental each = (Rental) rentals.nextElement();

			result += "\t" + each.getMovie().getTitle()+"\t" + String.valueOf(each.getCharge()) + "\n";
		}

		// 푸터 행 추가?
		result += "<p>누적 대여로: <EM>" + String.valueOf(customer.getTotalCharge()) + " </EM></p>\n";

		return result;
	}
}
