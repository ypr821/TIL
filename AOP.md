<br><br>

# 스프링 삼각형과 설정정보.2 - AOP

<br>

작성일자 : 2021-10-10 ~ 2021-10-12

내용 :  스프링 입문을 위한 자바 객체 지향의 원리와 이해 서적 공부 ( 7장  스프링 삼각형과 설정정보.2 - AOP )

<br><br>

## AOP(Aspect-Oriented Programming) - 관점 지향 프로그래밍

<br>

- **AOP(Aspect Oriented Programming)란** 

  **AOP는 새로운 프로그래밍 패러다임이 아니라 OOP(Object Oriented Programming, 객체 지향 프로그래밍)를 돕는 보조적인 기술로, 핵심적인 관심 사항(Core Concern)과 공통 관심 사항(Cross-Cutting Concern)으로 분리시키고 각각을 모듈화 하는 것을 의미한다.**
  <br>

-  **스프링 DI는  의존성에 대한 주입, 스프링AOP는 로직(code) 주입**

- **코드 = 횡단관심사 + 핵심관심사**

  횡단관심사(cross-cutting concern) : 다수의 모듈에 공통적으로 나타나는 부분 = 모듈별로 반복되어 중복해서 나타나는 부분

  핵심관심사(cross-cutting concern) : 횡단관심사 외의 모듈의 고유한 부분 

  <br>

<img width="80%" alt="20211010_200543" src="https://user-images.githubusercontent.com/56250078/136805381-f5914852-0045-4217-8e27-ec1c60770814.png">

<br><br>

-  **AOP(Aspect Oriented Programming)의 장점** 

1. 공통 관심 사항을 핵심 관심사항으로부터 분리시켜 핵심 로직을 깔끔하게 유지할 수 있다.
2. 그에 따라 코드의 가독성, 유지보수성 등을 높일 수 있다.
3. 각각의 모듈에 수정이 필요하면 다른 모듈의 수정 없이 해당 로직만 변경하면 된다.
4. 공통 로직을 적용할 대상을 선택할 수 있다.

출처: https://mangkyu.tistory.com/121 [MangKyu's Diary]

<br>

Spring에서 제공하는 공식문서

> Aspect-oriented Programming (AOP) complements Object-oriented Programming (OOP) by providing another way of thinking about program structure. The key unit of modularity in OOP is the class, whereas in AOP the unit of modularity is the aspect. 
>
> Aspects enable the modularization of concerns (such as transaction management) that cut across multiple types and objects. (Such concerns are often termed “crosscutting” concerns in AOP literature.)
>
> One of the key components of Spring is the AOP framework. While the Spring IoC container does not depend on AOP (meaning you do not need to use AOP if you don’t want to), AOP complements Spring IoC to provide a very capable middleware solution.
>
>
> 출처: https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop

<br>

> 나의 질문 : Aspect가 정확하게 뭘 의미하는 걸까??
>
> 부가 기능 모듈은 기존의 객체지향 설계와 구분되는 새로운 특성이 있어서 Aspect라고 불리기 시작하였다. Aspect는 애플리케이션의 핵심 기능을 담고 있지는 않지만 애플리케이션을 구성하는 한 가지 요소로써 핵심 기능에 부가되어 의미를 갖는 모듈을 의미한다. 그리고 이렇게 애플리케이션의 핵심적인 기능에서 부가적인 기능을 분리하여 독특한 모듈로 만들고 설계하여 개발하는 방법을 AOP(Aspect Oriented Programming, 관점 지향 프로그래밍)라고 부른다.
>
> 출처: https://mangkyu.tistory.com/161 [MangKyu's Diary]

<br>

-  AOP은 단일 책임 원칙( SRP ) 자연스럽게 적용한다.

  <br>

- **로직(코드)을 주입할 수 있는 곳 5가지** 
  1. Around - 메서드 전 후 
  2. Before - 메서드 시작 전 (메서드 시작 직 후)
  3. After - 메서드 종료 후 (메서드 종료 직전)
  4. AfterReturning - 메서드 정상 종료 후 
  5. AfterThrowing - 메서드에서 예외가 발생하면서 종료된 후 

<br>

- ####  AOP 예시 코드 설명

- @Aspect는 이 클래스를 이제 AOP에서 사용하겠다는 의미

- @Before은 대상 메서드 실행 전에 이 메서드를 실행하겠다는 의미
  <br>
  
- < aop:aspectj-autoproxy />
  
  * 스프링 프레임워크에게 AOP 프록시를 사용하라고 알려주는 지시자
  * j -> java
  * auto -> 자동
  * proxy -> 프록시패턴 사용하여 횡단 관심사를 핵심 관심사에 주입(프록시를 이용한 간접 호출)
  * **Spring AOP는 프록시 패턴을 사용한다.**
    (참고 - 캐시도 프록시의 한 예다, 버퍼도 일종의 프록시라 볼 수 있다. => 중간에 가로채는 역할 )
    <br>
  
- @Before("execution(*runSomething( ))")의 의미

  - runSomething( ) => Pointcut
  - *runSomething( )가 실행되기 전(@Before)에 public void before을 실행하라는 의미
  - public void before => 횡단 관심사를 실행하는 메서드 

<br><br>

- #### **정리**
  - #### **스프링 AOP는 인터페이스(interface) 기반이다.**
  
  - #### **스프링 AOP는 프록시(proxy) 기반이다.**
  
  - #### **스프링 AOP는 런타임(runtime) 기반이다.**
  
    
    
    > 나의 질문 : 런타임 기반이라는 건 정확하게 어떤 뜻일까?
    
    >  런타임(Runtime)과 컴파일타임(Compiletime)의 차이점은 무엇인가?
    >
    >  런타임(Runtime)과 컴파일타임(Compiletime)은 소프트웨어 프로그램개발의 서로 다른 두 계층의 차이를 설명하기 위한 용어이다. 프로그램을 생성하기 위해 개발자는 첫째로 소스코드를 작성하고 컴파일이라는 과정을 통해 기계어 코드로 변환 되어 실행 가능한 프로그램이 되며, 이러한 편집 과정을 컴파일타임(Compiletime) 이라고 부른다.
    >
    >  컴파일과정을 마친 프로그램은 사용자에 의해 실행되어 지며, 이러한 응용프로그램이 동작되어지는 때를 런타임(Runtime)이라고 부른다.
    >
    >  "런타임"과 "컴파일 타임"이라는 용어는 종종 서로 다른 두 가지 타입의 에러를 나타내기 위해 사용하는 데, 컴파일 타임 에러는 프로그램이 성공적으로 컴파일링되는 것을 방해하는 신택스에러(Syntax error)나 파일참조 오류와 같은 문제를 말하며, 이런 경우 컴파일러는 컴파일 타임 에러를 발생시키고 일반적으로 문제를 일으킨 소스코드 라인을 지시해준다.
    >
    >  만약, 어떤 소스코드가 이미 실행가능한 프로그램으로 컴파일 되었더 라도 이것은 여전히 프로그램의 실행중에 버그를 일으킬 수 있다. 예를 들자면, 예상치 못한 오류 또는 충돌로 동작하지 않을 수 있는데 이렇게 프로그램이 실행 중에 발생하는 형태의 오류를 런타임오류 라고 한다.
    >
    >  
    >
    >  [원문보기](https://pc.net/helpcenter/answers/compile_time_vs_runtime)
    >
    >  출처: https://spaghetti-code.tistory.com/35 [어떻게 짤것인가]
    
    

<br><br>



---



####  용어 정리 



- ####  **Pointcut** - 횡단 관심사를 적용할 타깃 메서드를 선택하는 지시자(메서드 선택 필터)이다.

예) @Before("execution((*runSomething( ))")에서  '*runSomething( )'부분

<br>
"타깃 클래스의 타깃 메서드 지정자"
"Aspect 적용 위치 지정자" 
스프링 AOP만 보자면 Aspect를 메서드에만 적용할 수 있으니 타깃 메서드 지정자라는 말이 틀리지 않다.
그렇지만 AspectJ처럼 스프링 AOP 이전부터 있었고 지금도 유용하게 사용되는 다른 AOP 프레임워크에서는 메서드뿐만 아니라 속성 등에도 Aspect를 적용할 수 있기에 그것까지 고려한다면 Aspect 적용 위치 지정자(지시자)가 맞는 표현이다. Pointcut을 메서드 선정 알고리즘이라고도 한다. 

<br>

[접근제한자패턴] **리턴타입패턴** [패키지&클래스패턴] **메서드이름패턴 (파라미터패턴)** [throws 예외패턴]

필수요소 - 리턴타입패턴, 메서드이름패턴 (파라미터패턴)

<br>
<br>

=====================================================================================================



- #### **JointPoint** - 연결점, 연결 가능한 지점
  
  Pointcut은 JointPoint의 부분 집합이다. 앞에서 스프링 AOP는 인터페이스를 기반으로 한다고 설명했다.
  
  인터페이스는 추상메서드의 집합체이다. 
  
  (삼단 논법 결론 => 스프링 AOP는 메서드에만 적용 가능하다는 결론에 도달하게 된다.)
  
  
  Pointcut의 후보가 되는 모든 메서드들이  JointPoint, 즉 Aspect 적용이 가능한 지점이 된다.
  
  JointPoint란 "Aspect 적용이 가능한 모든 지점을 말한다." 
  
  => Aspect 적용할 수 있는 지점 중 일부가 Pointcut이 되므로 Pointcut은 JoinPoint의 부분집합인 셈이다.



- 스프링에서 AOP의 JoinPoint란 스프링 프레임워크가 관리하는 빈의 모든 메서드에 해당한다. ( 광의의 JoinPoint 이다)



MyAspect.java

```java
package aop002;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.JoinPoint;


@Aspect

public class MyAspect {

  @Before("execution( * runSomething())")
  public void before(JoinPoint joinPoint) {
    System.out.println("얼굴 인식 확인 : 문을 개방하라");
  }
}


```

​	

JoinPoint joinPoint 실체 찾기.



Start.java

```java
package aop002;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Start {

  public static void main(String[] args) {
    ApplicationContext context = new ClassPathXmlApplicationContext("aop002.xml", Start.class);

    Person romeo = context.getBean("boy", Person.class);
    Person juliet = context.getBean("girl", Person.class);

    romeo.runSomething();
    //이 시점에서 JoinPoint는 romeo 객체의 runSomething()
      
    juliet.runSomething();
    //이 시점에서 JoinPoint는 juliet 객체의 runSomething()

  }
}

```



**정리 
광의의 JoinPoint란 Aspect적용이 가능한 모든 지점이다.
협의의 JoinPoint란 호출된 객체의 메서드이다.**



- #### Advice 

  - Advice는 Pointcut에 적용할 로직, 즉 메서드를 의미하는데, 여기에 더해 언제라는 개념까지 포함한다.
  - Advice란 **Pointcut에 언제, 무엇을 적용할지 정의한 메서드**이다. 
  - 위의 예시 코드에서 Pointcut이 시작되기 전 (@Before)에 before( ) 메서드를 실행하라고 돼있음을 확인할 수 있다.
    

- #### Aspect

  -  <u>Aspect는 여러 개의 Advice와 여러 개의 Pointcut의 결합체를 의미 </u>하는 용어다.
  - Aspect = Advice들 + Pointcut들
  - Advice란 Pointcut에 [ 언제(When), 무엇(What) ]을 의미한다. 
    Pointcut은 [ 어디에(Where) ]를 의미한다.
    
    결국 Aspect 는 When + Where + What (언제, 어디에, 무엇을)이 된다.
    
  - 예제 코드를 해석해보면 Pointcut인 public void aop002.Boy.runSomething( ) 메서드가 시작되기 전(@Before)에 before( ) 메서드를 실행하라고 돼 있다.
    

- #### Advisor
  - 어디서, 언제, 무엇을?
  - Advisor = <u>한 개</u>의 Advice + <u>한 개</u>의 Pointcut
  - 스프링  AOP에서만 사용하는 용어
  - Aspect가 나오고 나서는 쓰지 말라고 권고하는 기능이다.



