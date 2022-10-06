package formTemplateMethod.formTemplateMethod2;

import java.util.Vector;

public class Customer {
	private String name ="푸름";
	private int totalCharge = 10000;

	private Vector rentals = new Vector<>();

	public String getName() {
		return name;
	}

	public Vector getRentals() {
		return rentals;
	}

	// 두 statement 메서드를 하위클래스로 옮기면서 메서드 이름을 더욱 메서드명을 전략에 맞도록 변경함. 클래스로 분리됐기 때문에 value로 메서드명 통일시킴
	// 핵심은 메서드 추출로 두 메서드의 다른 부분을 추출해서 다른 코드를 비슷한 코드와 분리하는 것이다.(내용은 다르지만 시그니처는 같은 메서드를 작성해야함)

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
