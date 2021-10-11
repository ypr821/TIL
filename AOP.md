<br><br>

# 스프링 삼각형과 설정정보.2 - AOP

<br>

작성일자 : 2021-10-10

내용 :  스프링 입문을 위한 자바 객체 지향의 원리와 이해 서적 공부 ( 7장  스프링 삼각형과 설정정보.2 - AOP )

<br><br>

## AOP(Aspect-Oriented Programming) - 관점 지향 프로그래밍

<br>

- **AOP(Aspect Oriented Programming)란** 

  **AOP는 새로운 프로그래밍 패러다임이 아니라 OOP(Object Oriented Programming, 객체 지향 프로그래밍)를 돕는 보조적인 기술로, 핵심적인 관심 사항(Core Concern)과 공통 관심 사항(Cross-Cutting Concern)으로 분리시키고 각각을 모듈화 하는 것을 의미한다.**
  <br>

- **스프링 DI는  의존성에 대한 주입, 스프링AOP는 로직(code) 주입**

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
4. 공통 로직을 적용할 대상을 선택할 수 있다

출처: https://mangkyu.tistory.com/121 [MangKyu's Diary]



- 단일 책임 원칙( SRP ) 자연스럽게 적용된다.

- **로직(코드)을 주입할 수 있는 곳 5가지** 
  1. Around
  2. Before - 메서드 시작 전 (메서드 시작 직 후)
  3. After - 메서드 종료 후 (메서드 종료 직전)
  4. AfterReturning - 메서드 정상 종료 후 
  5. AfterThrowing - 메서드에서 예외가 발생하면서 종료된 후 

<br>

- ####  AOP 예시 코드 

- @Aspect는 이 클래스를 이제 AOP에서 사용하겠다는 의미



- @Before은 대상 메서드 실행 전에 이 메서드를 실행하겠다는 의미
  <br>

- **Spring AOP는 프록시 패턴을 사용한다. **
  (참고 - 캐시도 프록시의 한 예다, 버퍼도 일종의 프록시라 볼 수 있다. => 중간에 가로채는 역할 )

- ```java
  <aop:aspectj-autoproxy/>
      
  /*
  * 스프링 프레임워크에게 AOP 프록시를 사용하라고 알려주는 지시자
  * auto가 붙었으니 자동으로!
  * j -> java
  * auto -> 자동
  * proxy -> 프록시패턴 사용하여 횡단 관심사를 핵심 관심사에 주입(프록시를 이용한 간접 호출)
  */   
  
     
  ```



<br><br>

- #### **정리**

  - #### **스프링 AOP는 인터페이스(interface) 기반이다.**

  - #### **스프링 AOP는 프록시(proxy) 기반이다.**

  - #### **스프링 AOP는 런타임(runtime) 기반이다.**

    

* @Before("execution(*runSomething( ))")의 의미
  - runSomething( ) => Pointcut
  - *runSomething( )가 실행되기 전(@Before)에 public void before을 실행하라는 의미
  - public void before => 횡단 관심사를 실행하는 메서드 



####  용어 정리 

<br>

- **Pointcut** : 횡단 관심사를 적용할 타깃 메서드를 선택하는 지시자(메서드 선택 필터)인 것이다.
  <br>"타깃 클래스의 타깃 메서드 지정자"
  "Aspect 적용 위치 지정자" 
  스프링 AOP만 보자면 Aspect를 메서드에만 적용할 수 있으니 타깃 메서드 지정자라는 말이 틀리지 않다.
  그렇지만 AspectJ처럼 스프링 AOP 이전부터 있었고 지금도 유용하게 사용되는 다른 AOP 프레임워크에서는 메서드뿐만 아니라 속성 등에도 Aspect를 적용할 수 있기에 그것들까지 고려한다면 Aspect 적용 위치 지정자(지시자)가 맞는 표현이다. Pointcut을 메서드 선정 알고리즘이라고도 한다. 

  <br>

  [접근제한자패턴] **리턴타입패턴** [패키지&클래스패턴] **메서드이름패턴 (파라미터패턴)** [throws 예외패턴]

필수요소 - 리턴타입패턴, 메서드이름패턴 (파라미터패턴)

<br>
<br>

- **JointPoint** - 연결점, 연결 가능한 지점
  내용 추가예정 !!!



















