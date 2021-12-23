<br><br>

# 스프링 삼각형과 설정정보.1 - IoC/DI

<br>

작성일자 : 2021-10-09

내용 :  스프링 입문을 위한 자바 객체 지향의 원리와 이해 서적 공부 ( 7장  스프링 삼각형과 설정정보.1 - IoC/DI )

<br><br>

## **Ioc/DI - 제어의 역전 / 의존성 주입**

<br>

### **IoC (Inversion of Control)**

IoC에 용어는 90년 중반에 GoF의 디자인패턴에서도 이용어를 사용했다. 즉, IoC는 Spring에서 나온 용어가 아닙니다. IoC에 대한 개념은 굉장히 폭이 넓다.

해석하면 제어의 역전이다. 도대체 어떤 제어를 말하는 것이며 무엇을 역전한다는 말 일까??

의존성 직접 해결 방법(아래에 설명 참고)으로 클래스를 직접 인스턴스하여 의존관계를 나타낼 수 있다. 즉 **개발자가 직접 객체를 제어하여 A객체는 B객체에게 의존하고 있어 라고 클래스를 통해 표현하고 개발자가 직접 표현 한다.** 

**스프링을 통한 @Autowired를 통한 속성에 의존성 주입하는 방법(아래에 설명 참고)은  컨테이너에 의해서 생성한 객체를 사용하기만 한다.**
예를 들어 A라는 객체가 스프링 컨테이너에게 관리되고 있는 Bean이라면 @Autowired 를 통해 객체를 주입받을수 있게 된다.
**개발자가 직접 객체를 관리하지 않고 스프링 컨테이너에서 직접(제어) 객체를 생성하여 해당 객체에 주입 시켜준 것이 제어가 역전이다.**



<br><br>





### 프로그래밍에서 "**Dependency  의존성**"

- 코드에서 두 모듈 간의 연결.

  객체지향언어에서는 두 클래스 간의 관계라고도 말함.  (결합도와 연관이 있을 거 같다)

- 의존성은 'new' 이다.



> 나의 질문 - **Dependency 의존성이 위험한 이유**가 뭘까??
>
> 하나의 모듈이 바뀌면 의존한 다른 모듈까지 변경이 이뤄진다.
> 테스트 가능한 어플을 만들 때 의존성이 있으면 유닛테스트 작성이 어려워 진다.

출처: https://tony-programming.tistory.com/entry/Dependency-의존성-이란 [Tony Programming]



<br>

<br>

### [의존성 직접 해결 방법] 

- **클래스 생성자 내부에 의존하는 클래스를 직접 생성**

<br>

의사코드 예시

```java
운전자가 자동차를 생산한다.
자동차는 내부적으로 타이어를 생산한다.
```



```java
package expert001_01;

public interface Tire {
	String getBrand();
}


public class KoreaTire implements Tire{

	public String getBrand() {
		return "코리아 타이어";
	}
}

public class AmericaTire implements Tire{

	public String getBrand() {
		return "미국 타이어";
	}
}

```



```java
package expert001_01;

public class Car {

	Tire tire;
	
	public Car() {
		//Car가 Tire를 의존하는 부분, new 연산자를 사용하여 객체 생산
        //자동차는 내부적으로 타이어를 생산한다.
        //AmericaTire로 바꾸려면 Car 클래스 수정필요 -> 유연성떨어짐
		tire = new KoreaTire();
	}
	
	public String getTireBrand() {
		return "장착된 타이어 : "+tire.getBrand();
	}
}
```



예시에서는 운전자 클래스 (main로 실행하는 클래스)

```java
package expert001_01;

public class Driver {

	public static void main(String[] args) {
		Car car = new Car();
		System.out.println(car.getTireBrand());

	}
}
```



<br><br>



### **[ DI (Dependency Injection) 의존성 '주입' ]**

> 나의 질문 - **의존성 주입은 뭘까?**
>
> - DI란 외부에서 두 객체 간의 관계를 결정해주는 디자인 패턴
> - 인터페이스를 사이에 둬서 클래스 레벨에서는 의존관계가 고정되지 않도록 한다. 런타임 시에 관계를 다이나믹하게 주입하여 유연성을 확보하고 결합도를 낮출 수 있게 해준다. 
> - 단, 다른 빈을 주입받으려면 자기 자신이 반드시 컨테이너의 빈이여야 한다는 것이다.
>
> 의존성이란 한 객체가 다른 객체를 사용할 때 의존성이 있다고 한다.
> 예를 들어 다음과 같이 Store 객체가 Pencil 객체를 사용하고 있는 경우에 우리는 Store객체가 Pencil 객체에 의존성이 있다고 표현한다. 
>
> 출처: https://mangkyu.tistory.com/150 [MangKyu's Diary] <= 명료하고 정말 이해가 잘되는 좋은 포스팅 감
> 사합니다
>
> 
>
> 참고 - 위키
> 의존성 주입의 의도는 객체의 생성과 사용의 관심을 분리하는 것이다. 이는 가독성과 코드 재사용을 높혀준다. 의존성 주입은 프로그램 디자인이 [결합도](https://ko.wikipedia.org/wiki/결합도)를 [느슨하게](https://en.wikipedia.org/wiki/Loose_coupling)[[6\]](https://ko.wikipedia.org/wiki/의존성_주입#cite_note-6) 되도록하고 [의존관계 역전 원칙](https://ko.wikipedia.org/wiki/의존관계_역전_원칙)과 [단일 책임 원칙](https://ko.wikipedia.org/wiki/단일_책임_원칙)을 따르도록 클라이언트의 생성에 대한 의존성을 클라이언트의 행위로부터 분리하는 것이다.
>
> 





<br><br>

### [ 스프링없이 의존성 주입하기 1 ] - **생성자를 통한 의존성 주입**

- **주입**이란 - 외부에서라는 뜻을 내포, 외부에서 생산된 것을 내부로 넣어주는 것

- 생성자를 통한 의존성 주입의 단점 

  - 한번 타이어를 장착하면 더 이상 타이어를 교체할 방법이 없다.

- **스프링에서 생성자 주입의 장점**

  1. final 키워드 사용가능 객체 불변성 보장O -> 필드,수정자 주입은 final 사용 불가능 객체 불변성 보장X

  2. 순환참조 방지 => 생성자 주입은 빈을 먼저 찾고나서 생성을하여 순환참조가 방지됩니다.
     그러나 필드주입과 수정자주입은 빈을 생성하고 주입하려는 빈을 찾아 주입하는 방식이기때문에 
     순환참조 위험이 있다

     ( 어떻게 방지가 될까?? 생성자 주입을 사용하면 객체 간 순환참조를 하고 있는 경우에 스프링 애플리케이션이 구동되지 않는다.
       컨테이너가 빈을 생성하는 시점에서 객체생성에 사이클관계가 생기기 때문이다! )

     https://yaboong.github.io/spring/2019/08/29/why-field-injection-is-bad/

<br>

의사코드 예시

```java
운전자가 자동차를 생산한다.
운전자가 자동차를 생산하면서 타이어를 장착시킨다.
    
```

<br>

Tire , KoreanTire, AmericaTire는 코드 변화 없다.

```java
package expert001_02;

public class Car {

	Tire tire;
	
    //Car 클래스의 생성자메서드의 매개변수로 Tire인터페이스를 구현한 객체를 받아서 속성에 값을 전달
	public Car(Tire tire) {
	
		this.tire = tire;
	}
	
	public String getTireBrand() {
		return "장착된 타이어 : "+tire.getBrand();
	}
}
```

<br>

```java
package expert001_02;

public class Driver {

	public static void main(String[] args) {
        //운전자가 미국타이어를 장착할지 한국타이어를 장착할지 결정할 수 있다.
		Tire tire = new KoreaTire();
        Car car = new Car(tire);
		System.out.println(car.getTireBrand());
	}
}
```



<br>

Junit test를 사용해서 테스트 코드를 처음 만들어봤다. (재밌다ㅎㅎ)

```java
package expert001_02;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class CarTest {
  @Test
  public void 자동차_코리아타이어_장착_타이어브랜드_테스트() {
    Tire tire = new KoreaTire();
    Car car = new Car(tire);
    assertEquals("장착된 타이어 : 코리아 타이어", car.getTireBrand());
  }
    @Test
    public void 자동차_미국타이어_장착_타이어브랜드_테스트(){
      Tire tire2 = new AmericaTire();
      Car car2 = new Car(tire);
      assertEquals("장착된 타이어 : 미국 타이어", car2.getTireBrand());
    }
}

```

2개의 테스트 실행결과



![20211010_184146](https://user-images.githubusercontent.com/56250078/136690540-10a7d666-59cd-4705-bc29-d8c8e2e1b806.png)



<br><br>

### [ 스프링없이 의존성 주입하기 2 ] - **속성을 통한 의존성 주입**

- 클래스의  get/set 속성 메서드를 사용하여 의존성 주입

- 속성을 통한 의존성 주입의 단점 

  - 수정자 주입을 사용하면, setXxx 메서드를 **public**으로 열어두어야 합니다.
    즉 언제 어디서든 변경이 가능하다는 뜻입니다. 

  

  

<br>

의사코드 예시

```java
운전자가 자동차를 생산한다.
운전자가 자동차를 생산한다.
운전자가 자동차에 타이어를 장착한다.
//운전자가 원할 때 Car의 Tire를 교체할 수 있다.
```

<br>

Tire , KoreanTire, AmericaTire는 코드 변화 없다.

```java
package expert001_03;

public class Car {

  Tire tire;

  public String getTireBrand() {
    return "장착된 타이어 : " + tire.getBrand();
  }

  // get/set 속성 메서드 추가
  public Tire getTire() {
    return tire;
  }

  public void setTire(Tire tire) {
    this.tire = tire;
  }
}

```



<br>

```java
package expert001_03;

public class Driver {

	public static void main(String[] args) {
		Car car = new Car();
		Tire tire = new KoreaTire();
        
        //Car클래스의 set 속성 메서드를 사용하여 의존성 주입
		car.setTire(tire);
		System.out.println(car.getTireBrand());
	}
}


```





<br><br>

### [ 스프링을 통한 의존성 주입하기 1 ] - XML 파일 사용

- **속성을 통한 의존성 주입**

- 클래스의  get/set 속성 메서드를 사용하여 의존성 주입

- 생성자를 통한 의존성 주입의 문제점 - 한번 타이어를 장착하면 더 이상 타이어를 교체할 방법이 없다.

  

- > 나의 질문 - 위 예시 코드를 스프링을 사용했을때 장점은 뭘까??
  >
  > 위 예시의 경우 자동차의 타이어 브랜드를 변경할 때 그 무엇도 재컴파일/재배포하지 않고 XML파일만 수정하면 프로그램의 실행 결과를 바꿀 수 있다.

  

<br>

의사코드 예시

```java
운전자가 종합 쇼핑몰에서 타이어를 구매한다.
운전자가 종합 쇼핑몰에서 자동차를 구매한다.
운전자가 자동차에 타이어를 장착한다.
```

<br>

- 예시에서 종합 쇼핑몰(스프링 프레임 워크)이 새로 추가됐다. -> 기존 코드에서 Driver 클래스 코드만 변경될 예정
- 객체 지향은 현실 지향이다.

```java
package expert002;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class Driver {

  public static void main(String[] args) {
    //스프링을 통한 의존성 주입
    ApplicationContext context = new FileSystemXmlApplicationContext("expert002/expert002.xml");

    //종합 쇼핑몰에서 상품에 해당하는 Car와 Tire를 구매하는 코드이다.
    Car car = context.getBean("car", Car.class);
    Tire tire = context.getBean("tire", Tire.class);

    car.setTire(tire);
    System.out.println(car.getTireBrand());

  }
}

```





- 상품 정보는 XML파일에 있다.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

  <bean id="tire" class="expert002.KoreaTire"></bean>

  <bean id="americaTire" class="expert002.AmericaTire"></bean>

  <bean id="car" class="expert002.Car"></bean>

</beans>
```

- 상품을 등록할 때는 bean 태그를 이용
- bean태그 안에는 id속성과 class속성이 있다. 
  class속성은 어떤 클래스를 통해 생산(인스턴스화)할지 나타낸다



<br><br>



###  [ 스프링을 통한 의존성 주입하기 2 ] - 스프링 설정 파일(XML)에서 속성주입



- XML파일에 새롭게 property 태그 추가 

  =>Driver.java에서  car.setTire(tire) 라고 하던 부분을 XML 파일의 property 태그를 이용해 대체하는 것이다. 



<br>

의사코드 예시

```java
운전자가 종합 쇼핑몰에서 자동차를 구매요청한다.
종합 쇼핑몰은 자동차를 생산한다.
종합 쇼핑몰은 타이어를 생산한다.
종합 쇼핑몰은 자동차에 타이어를 장착한다.
종합 쇼핑몰은 운전자에게 자동차를 전달한다.
```

<br>

- 예시에서 종합 쇼핑몰(스프링 프레임 워크)이 새로 추가됐다. -> 기존 코드에서 Driver 클래스 코드만 변경될 예정
- 객체 지향은 현실 지향이다.

```java
package expert002;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class Driver {

  public static void main(String[] args) {
    //스프링을 통한 의존성 주입
    ApplicationContext context = new FileSystemXmlApplicationContext("expert002/expert002.xml");

    //종합 쇼핑몰에서 상품에 해당하는 Car와 Tire를 구매하는 코드이다.
    Car car = context.getBean("car", Car.class);
      
    //Tire tire = context.getBean("tire", Tire.class);
    //car.setTire(tire);
    System.out.println(car.getTireBrand());

  }
}

```





- 상품 정보는 XML파일에 있다.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
  <bean id="koreaTire" class="expert003.KoreaTire"></bean>

  <bean id="americaTire" class="expert003.AmericaTire"></bean>

  <bean id="car" class="expert003.Car">
    <property name="tire" ref="koreaTire"></property>
  </bean>

</beans>
```

- id가 "car"인 bean 태그 내에 property 태그 추가

  "koreaTire" id에 해당하는 bean에 찾아가서 객체화 하고 그 값을 car객체 내 속성인 tire 객체참조변수에 담는다.
   => 자동차에 타이어를 장착한다.

<br><br>



### [ 스프링을 통한 의존성 주입하기 3 ] - @Autowired를 통한 속성 주입

- XML파일에 property 태그 삭제  

  => Car class의 속성 위에 @Autowired 추가



<br>

의사코드 예시

```java
운전자가 종합 쇼핑몰에서 자동차를 구매요청한다.
종합 쇼핑몰은 자동차를 생산한다.
종합 쇼핑몰은 타이어를 생산한다.
종합 쇼핑몰은 자동차에 타이어를 장착한다.
종합 쇼핑몰은 운전자에게 자동차를 전달한다.
```

( 이전 의사 코드랑 동일하다.)

<br>

```java
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xmlns:context="http://www.springframework.org/schema/context"

  xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
  http://www.springframework.org/schema/context
  http://www.springframework.org/schema/context/spring-context.xsd
">

  <context:annotation-config />

  <bean id="tire" class="expert004.KoreaTire"></bean>

  <bean id="americaTire" class="expert004.AmericaTire"></bean>

  <bean id="car" class="expert004.Car"></bean>

</beans>
```



<br>

```java
// 추가된 코드들

xmlns:context="http://www.springframework.org/schema/context"

http://www.springframework.org/schema/context
http://www.springframework.org/schema/context/spring-context.xsd

<context:annotation-config />
    

// 삭제된 코드 
<property name="tire" ref="koreaTire"></property>
```



<br>

```java
package expert004;

import org.springframework.beans.factory.annotation.Autowired;

public class Car {

  @Autowired
  Tire tire;

  public String getTireBrand() {
    return "장착된 타이어 : " + tire.getBrand();
  }

}
```



나머지 코드 변화 X

<br><br>

### [ 스프링을 통한 의존성 주입하기 4 ] - @Resource를 통한 속성 주입

- 다른 코드 변화없이 @Autowired 대신 @Resource로 수정

  

  <br>

  > 나의 질문 - 왜 @Autowired가 있는 데 @Resource로 수정 할까?
  >
  > - @Autowired는 스프링 어노테이션이다. / 매칭 우선순위가 type이 높고 그 다음  id 순이다.
  > - @Resource는 자바 표준 어노테이션이다. / 매칭 우선순위가 id가 높고 그 다음  type 순이다.
  >   @Resource의 경우 id로 매칭할 빈을 찾지 못한 경우에 type으로 매칭 빈을 찾는다.

  

- @Autowired 와 @Resource 차이점 정리

  |                | @Autowired                                                   | @Resource                    |
  | -------------- | ------------------------------------------------------------ | ---------------------------- |
  | 출처           | 스프링 프레임워크                                            | 표준 자바                    |
  | 소속 패키지    | org.springframework.beans.<br />factory.annotation.Autowired | javax.annotation.Resource    |
  | 빈 검색 방식   | byType먼저, 못 찾으면 byName                                 | byName먼저, 못 찾으면 byType |
  | byName강제하기 | @Autowired<br />@Qualifier("tire1")                          | @Resource(name="tire1")      |

  [**@Autowired와 @Resource 구분**](**https://github.com/ypr821/TIL/blob/main/2021_10/%40Autowired%EC%99%80_%40Resource_%EA%B5%AC%EB%B6%84.md**)

  

  

<br>

