package formTemplateMethod.formTemplateMethod1;

import java.util.Enumeration;
import java.util.Vector;

public class Customer {
	private String name;
	private int totalCharge;

	private Vector rentals = new Vector<>();

	public String getName() {
		return name;
	}

	public String statement(){
		Enumeration rentals = this.rentals.elements();
		String result = getName() + " 고객님의 대여 기록\n";
		while (rentals.hasMoreElements()){
			Rental each = (Rental) rentals.nextElement();

			result += "\t" + each.getMovie().getTitle()+"\t" + String.valueOf(each.getCharge()) + "\n";
		}

		// 푸터 행 추가?
		result += "누적 대여로: " + String.valueOf(getTotalCharge()) + "\n";

		return result;
	}

	public String htmlStatement(){
		Enumeration rentals = this.rentals.elements();
		String result = "<H1><EM>" + getName() + " 고객님의 대여 기록</EM></H1>\n";
		while (rentals.hasMoreElements()){
			Rental each = (Rental) rentals.nextElement();

			result += "\t" + each.getMovie().getTitle()+"\t" + String.valueOf(each.getCharge()) + "\n";
		}

		// 푸터 행 추가?
		result += "<p>누적 대여로: <EM>" + String.valueOf(getTotalCharge()) + "\n";

		return result;
	}

	private int getTotalCharge() {
		return totalCharge;
	}
}
