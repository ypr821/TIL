# 스프링 AOP에서 정확하게 어떤 부분을 자동화 한 걸까?
<br>
작성일자 : 2021-10-20


<br>

우선 스프링을 사용하지 않고 Proxy pattern을 사용해서 코드를 작성함

# 프록시 패턴 구현

<br>

Payment.java  - 인터페이스

```java
package org.springframework.samples.petclinic.owner.proxy;

public interface Payment {

	void pay(int amount);

}

```

<br>

Store.java 

- 생성자 메서드에서 Payment type의 객체를 매개변수로 입력받아 속성에 담고 buySomething()메서드로 payment.pay(); 실행

```java
package org.springframework.samples.petclinic.owner.proxy;

public class Store {

	Payment payment;

	// alt + fn + delete => generate
	public Store(Payment payment) {
		this.payment = payment;
	}

	public void buySomething(int amount) {
		payment.pay(amount);
	}

}

```

<br>

Cash.java - Payment 인터페이스를 구현한 클래스

```java
package org.springframework.samples.petclinic.owner.proxy;

public class Cash implements Payment {

	@Override
	public void pay(int amount) {
		System.out.println(amount + "현금결제");
	}

}

```

<br>

CashPerf.java  -  실행 시간을 측정할 수 있는 proxy class

```java
package org.springframework.samples.petclinic.owner.proxy;

import org.springframework.util.StopWatch;

//CashPerf 실행시간을 측정할 수 있는 proxy class
public class CashPerf implements Payment {

	Payment cash = new Cash();

	@Override
	public void pay(int amount) {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		cash.pay(amount);

		stopWatch.stop();
		System.out.println(stopWatch.prettyPrint());
	}

}

```

<br>

StoreTest.java

```java
package org.springframework.samples.petclinic.owner.proxy;

import org.junit.jupiter.api.Test;

public class StoreTest {

	@Test
	public void testPay() {
		Payment cashPerf = new CashPerf();
		Store store = new Store(cashPerf);
		store.buySomething(1000);
	}
}

/* 실행 결과 
1000현금결제
StopWatch '': running time = 129500 ns
---------------------------------------------
ns         %     Task name
---------------------------------------------
000129500  100%  
*/

```

참고

Payment cashPerf = new CashPerf(); 코드라인에서 CashPerf를 Cash로 수정 후 실행결과

```java
1000현금결제
```

<br><br>

정리를 해보자

기존 구현된 코드에서 proxy 패턴은 적용하는 방법

1. interface를 구현하는 proxy class를 생성한다.

2. 속성으로 interface 타입의 변수에 연결받고자 하는 class의 객체를 생성하여 담는다.

3. 그리고  proxy class 메서드 블록에서 속성으로 선언해둔 객체의 메서드를 호출를 포함하고 필요한 로직을 추가합니다. 



왜 사용하는 가 ?

- 실제로 적용해보니 기존 코드를 수정하지 않고 새로운 코드를 추가할 수 있는 장점을 느꼈다.

아쉬운 점

- 현재 예시에서는 Cash클래스만 있었지만 Payment interface를 구현한 클래스를 더 생성해서 proxy 패턴을 적용하려면 
  proxy class의 필드에서  new Cash( );  생성자 호출 부분을 수정해야 하는 불편함이 있다.  proxy 패턴기반으로 AOP를 적용해서 모듈화 해야 하나 보다.

<br>
----
<br>
# Spring을 사용한 AOP 구현

(anotation 사용)

<br>
OwnerController.java 에서 '@LogExecutionTime' anotation을 붙인 메서드 들의 성능을 측정해본다.

```java
package org.springframework.samples.petclinic.owner;

// import ```생략```

@Controller
class OwnerController {

	private static final String VIEWS_OWNER_CREATE_OR_UPDATE_FORM = "owners/createOrUpdateOwnerForm";

	private final OwnerRepository owners;

	private final VisitRepository visits;

	private final PetRepository petRepository;

	public OwnerController(OwnerRepository clinicService, VisitRepository visits, PetRepository petRepository) {
		this.owners = clinicService;
		this.visits = visits;
		this.petRepository = petRepository;
	}

	@InitBinder
	@LogExecutionTime
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping("/owners/new")
	@LogExecutionTime
	public String initCreationForm(Map<String, Object> model) {
		Owner owner = new Owner();
		model.put("owner", owner);
		return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping("/owners/new")
	@LogExecutionTime
	public String processCreationForm(@Valid Owner owner, BindingResult result) {
		if (result.hasErrors()) {
			return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
		}
		else {
			this.owners.save(owner);
			return "redirect:/owners/" + owner.getId();
		}
	}

	@GetMapping("/owners/find")
	@LogExecutionTime
	public String initFindForm(Map<String, Object> model) {
		model.put("owner", new Owner());
		return "owners/findOwners";
	}

}

```

<br>

LogExecutionTime.java

```java
package org.springframework.samples.petclinic.owner;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)			// 어디에 쓸것인가
@Retention(RetentionPolicy.RUNTIME) // 해당 애노테이션을 언제까지 유지할지 알려주는 어노테이션
public @interface LogExecutionTime {

}

```


<br>
LogAspect.java

```java
package org.springframework.samples.petclinic.owner;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Aspect
public class LogAspect {

	Logger logger = (Logger) LoggerFactory.getLogger(LogAspect.class);

	@Around("@annotation(LogExecutionTime)")
	public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		Object proceed = joinPoint.proceed();
		stopWatch.stop();
		logger.info(stopWatch.prettyPrint());
		return proceed;
	}

}

```
<br>
joinPoint = target

OwnerController는 target 으로 Cash 클래스와 같은 역할을 한다.

Aspect = CashPerf 로 proxy class 이고

**빈으로 등록할때 OwnerController에서 프록시를 생성하고 같은 인터페이스에 상속받고 퍼프를 주입해야하는 과정은 스프링 내부 메커니즘에서 자동으로 처리한다.**
(Store.java, StoreTest.java 의 로직 = 객체 생성 주입)
<br>
<br>
<br>
참고 - 예제로 배우는 스프링 입문 (개정판) 인프런강의 by 백기선님
