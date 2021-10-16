# 빈(bean)
작성일자 : 2021-10-16

내용 : 인프런 예제로 배우는 스프링 입문 강의 
<br><br>

## 빈(bean)이란 ?  
> 스프링 IoC 컨테이너가 관리하는 객체

<br>

### 어떻게 등록하지?

### 1. Component Scanning 을 사용한다.

- **@Component**

  - @Repository  
    - 조금 특이한 형태로 빈으로 등록됨, Repository를 상속받은 interface를 찾아서 구현체를 만들어서 빈에 등록시킨다
  - @Service
  - @Controller
    - @Controller 어노테이션은 @Component라는 메타어노테이션을 사용한 어노테이션이다. 사실상 Component 이다.
  - @Configuration 
    - @Configuration 도 @Component

  <br>

 - Life Cycle Callback 

   - IoC 컨테이너를 만들고 컨테이너에 빈을 등록하는 여러 가지 인터페이스이다.

   - 어노테이션 처리기 : Life Cycle Callback  중 하나로 ComponentScan은 어느 지점 부터 찾을 건 지 알려준다.

     @Component 를 찾아서 객체를 생성하고 빈으로 등록해준다.
     @Component 어노테이션이 붙어있는 클래스를 찾아서 빈으로 등록한다.

   

<img width="574" alt="3" src="https://user-images.githubusercontent.com/56250078/137590620-2a7ae6e6-6f59-442a-995d-0a4be09b52bc.png">

​    @SpringBootApplication 어노테이션을 살펴보면 @ComponentScan 어노테이션이다. 

<img width="692" alt="2" src="https://user-images.githubusercontent.com/56250078/137590616-f4b7348d-f992-4310-a3bc-3393ff5ff2fe.png">



<br><br>

### 2.  또는 직접 일일이 XML이나 자바 설정 파일에 등록한다.

- @Configuration 어노테이션을 사용한 자바 설정 파일에서 @Configuration도 @Component이기 때문에 자동으로 빈이 생성되는데 이때 sampleController메서드가 읽히면서 빈으로 등록된다.

  <img width="478" alt="1" src="https://user-images.githubusercontent.com/56250078/137590589-da908d41-96d9-4d9a-b45f-1ff4ed7117a7.png">

<br>

## 어떻게 꺼내 쓰지?

● **@Autowired 또는 @Inject** 

● **또는 ApplicationContext에서 getBean()으로 직접 꺼낸다. **

<br>

### 특징 

● 오로지 “빈"들만 의존성 주입을 한다.

- 빈(Bean) : ApplicationContext가 알고있고 ApplicationContext 가 만들어서 담고있는 객체이다.

  우리가 직접 new를 사용해서 만든 건 bean이 아니다.



<img width="609" alt="20211016_230551" src="https://user-images.githubusercontent.com/56250078/137590530-79dd503b-0c97-43ad-9351-23d17107b1ad.png">







