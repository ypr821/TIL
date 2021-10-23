# @Autowired

작성일자 : 2021-10-09
내용 : @Autowired의 우선 순위

<br>

<br>

## @Autowired 사용 연습



- 앞서 공부 한 '스프링 삼각형과 설정정보.1 - IoC/DI'의 설명에 사용된 예시로 @Autowired 사용 연습
- type을 우선으로 주입 같은 타입으로 구현한 클래스가 여러 개 일때 bean 태그의 id로 구분해서 매칭한다.

<br><br>

### 1. id 없는 bean tag

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xmlns:context="http://www.springframework.org/schema/context"

  xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
  http://www.springframework.org/schema/context
  http://www.springframework.org/schema/context/spring-context.xsd
">

  <context:annotation-config />

  <bean class="expert004.AmericaTire"></bean>

  <bean id="car" class="expert004.Car"></bean>

</beans>
```



- 사용가능 
- 이유 : type 기준 매칭 
- 같은 타입을 구현한 클래스가 여러 개 있다면 그때 bean 태그의 id로 구분해서 매칭하게 되는 것


<br><br>

### 2. type은 같은 것이 있지만 id가 다를 경우



```java
Car.java 파일 내
	@Autowired Tire tire;
    
expert.xml 파일 내
     <bean id="usaTire" class="expert004.AmericaTire"></bean>
```

- 정상 구동
- type 으로 우선 매칭 한다.


<br><br>

### 3. id없는 두개의 bean태그의 implements한 데이터타입이 같은 경우



```java
Car.java 파일 내
	@Autowired Tire tire;
    
expert.xml 파일 내
     <bean class="expert004.KoreaTire"></bean>
     <bean class="expert004.AmericaTire"></bean>
```

- 에러 - No unique bean
- id 속성 작성하는 습관갖자


<br><br>

### 4. type이 다를 경우

```java
Door.java 추가
    public class Door{ }

Car.java 파일 내
	@Autowired Tire tire;
    
expert.xml 파일 내
     <bean class="expert004.KoreaTire"></bean>
     <bean id="tire" class="expert004.Door"></bean>
```

- KoreaTire 속성 주입
- 역시 type 우선!!!





















