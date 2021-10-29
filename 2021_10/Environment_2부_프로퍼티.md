

# Environment 2부. 프로퍼티 

<br>

작성일자 ; 2021-10-28

내용 : 백기선님의 스프링 프레임워크 핵심 기술 인프런 강의 

<br><br>

## 프로퍼티

- 다양한 방법으로 정의할 수 있는 설정값

- Environment의 역할은 프로퍼티 소스 설정 및 프로퍼티 값 가져오기 

- key-value 형태

- 계층형???

<br>

 > 나의 질문 : 프로퍼티에 값을 저장하는 이유는?
  >
  > -  Java단에서 자주 쓰는 URL이나 문자열을 프로퍼티로 저장해서 써먹으려고 저장한다. <br>
  >   출처: https://yodurumi.tistory.com/171 [알이즈웰]
  >
  > - 애플리케이션에서 설정에 관련된 상수는 소스코드와 별도로 관리하는 것이 기본이다. 
  >   설정과 관련된 값을 소스코드 내부에서 관리하면.. 값이 바뀔 때마다 매번 컴파일을 해야해서 번거롭기 때문이다. 
  >   Spring Boot는 프로젝트 설정에 쓰이는 내용 또는 각종 상수를 .yml 이나 .properties 파일에 저장할 수 있게 지원한다.
  >
  >   Spring Boot에서 .properties 파일의 값을 가져올 때는 @Value 라는 어노테이션을 쓴다. 이것을 쓰면 Bean 객체가 초기화 될때 @Value에 지정된 값을 해당 프로퍼티에 초기화 한다. <br>
  >   출처: https://preamtree.tistory.com/158 [Preamtree의 행복로그]

  <br>
  <br><br>

## 프로퍼티에는 우선 순위가 있다.

- StandardServletEnvironment의 우선순위

  - ServletConfig 매개변수

  - ServletContext 매개변수

  - JNDI (java:comp/env/) 

  - JVM 시스템 프로퍼티 (-Dkey=”value”)

  - JVM 시스템 환경 변수 (운영 체제 환경 변수) 

    <br><br>

## @PropertySource

- **Environment를 통해 프로퍼티 추가하는 방법**

  - **방법 1 - VM options 에 설정 추가**

<img width="458" alt="20211028_223324" src="https://user-images.githubusercontent.com/56250078/139396807-d2d79833-9c8e-42b6-b000-ec2b4ae29ea3.png">

  <br>

  - **방법 2 - properties 파일 생성후 설정 추가**
<img width="148" alt="20211028_224333" src="https://user-images.githubusercontent.com/56250078/139396865-e7f8d545-3c1a-489a-a151-4bc338044b1f.png">

    <br>

    #### +

    **Spring512Application.java 파일에 @PropertySource("classpath:/app.properties") 추가** 하여 properties파일을 놓겠다!! 하면 Environment 에서 가져다 쓸 수 있게 된다,

    ```java
    package profile;
    
    import org.springframework.boot.SpringApplication;
    import org.springframework.boot.autoconfigure.SpringBootApplication;
    import org.springframework.context.annotation.PropertySource;
    import org.springframework.context.annotation.PropertySources;
    
    @SpringBootApplication
    @PropertySource("classpath:/app.properties")
    public class Spring512Application {
    
      public static void main(String[] args) {
        SpringApplication.run(Spring512Application.class, args);
      }
    
    }
    
    ```

    

- AppRunner.java 

  ```java
  package profile;
  
  import java.util.Arrays;
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.boot.ApplicationArguments;
  import org.springframework.boot.ApplicationRunner;
  import org.springframework.context.ApplicationContext;
  import org.springframework.core.env.Environment;
  import org.springframework.stereotype.Component;
  
  @Component
  public class AppRunner implements ApplicationRunner {
  
    @Autowired
    ApplicationContext ctx;
  
    @Autowired
    BookRepository bookRepository;
  
    @Override
    public void run(ApplicationArguments args) throws Exception {
      //Environment를 가져온다.
      Environment environment = ctx.getEnvironment();
  
      System.out.println(environment.getProperty("app.name"));
      System.out.println(environment.getProperty("app.about"))
    }
  }
  //실행결과 
  /* spring5
     spring */
  ```



- <br>

- properties 파일 app.about 키를 app.name 으로 바꿔서 실행해보기
<img width="155" alt="20211028_22434" src="https://user-images.githubusercontent.com/56250078/139396910-d79645fa-bc8a-4ae9-baa7-f086f6feb6d3.png">

  ```java
  //실행결과 
  /* spring5
     null */
  ```

  

  참고 스프링부트 @Value 사용하기

  > ```java
  > @Value("${app.name}")
  > String appName;
  > ```
  >
  > 추가하고 
  >
  > 확인할 수 있는 코드 
  >
  > ```java
  > System.out.println(appName);
  > ```
  >
  > 추가 하고 실행 해본다 
  >
  > ```java
  > package profile;
  > 
  > import 생략
  > 
  > @Component
  > public class AppRunner implements ApplicationRunner {
  > 
  >   @Autowired
  >   ApplicationContext ctx;
  > 
  >   @Autowired
  >   BookRepository bookRepository;
  > 
  >   @Value("${app.name}")
  >   String appName;
  > 
  >   @Override
  >   public void run(ApplicationArguments args) throws Exception {
  >     //Environment를 가져온다.
  >     Environment environment = ctx.getEnvironment();
  > 
  >     System.out.println(environment.getProperty("app.name"));
  >     System.out.println(appName);
  >   }
  > }
  > ```

  <br><br>

## 스프링 부트의 외부 설정 참고

- 기본 프로퍼티 소스 지원 (application.properties)
- 프로파일까지 고려한 계층형 프로퍼티 우선 순위 제공

<br><br>

