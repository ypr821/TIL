

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

