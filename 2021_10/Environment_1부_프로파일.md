# Environment 1부. 프로파일 

<br>

작성일자 : 2021-10-27

내용 :  백기선님의 스프링 프레임워크 핵심기술 인프런 강의

<br><br>

## 프로파일과 프로퍼티를 다루는 인터페이스. 

ApplicationContext interface는 많은 interface를 구현하고 있다.  

- 예: ***EnvironmentCapable***, ListableBeanFactory, HierarchicalBeanFactory, MessageSource, ApplicationEventPublisher, ResourcePatternResolver

<br>

그 중 ***EnvironmentCapable***을 살펴보자


```java
package org.springframework.core.env;

public interface EnvironmentCapable {
  Environment getEnvironment();
}
```
**getEnvironment()** 를 호출해서 Environment를 가져올 수 있다.

ApplicationContext에 관한 설명을 보면 "Methods inherited from interface org.springframework.core.env.[EnvironmentCapable](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/env/EnvironmentCapable.html)" 라고도 확인할 수 있다.

출처 : https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/ApplicationContext.html

ApplicationContext은 EnvironmentCapable 인터페이스를 상속하기 때문에 Environment 타입의 결과값을 리턴하는 메서드인 getEnvironment()메서드 구현이 강제된다. 

<br><br>

## 프로파일

- 빈들의 그룹, 묶음

- 특정한 환경에서 활성화할 빈을 설정할 수 있다. 

- [Environment](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/env/EnvironmentCapable.html) interface의 역할은 활성화할 프로파일 확인 및 설정

<br>

  

- default라는 이름의 프로파일 : 항상 아무런 프로파일 설정이 없어도 적용되는 프로파일

<br><br>

> 나의 질문 : "Environment" 이 인터페이스는 뭘까?
>
> ```java
> package org.springframework.core.env;
> 
> public interface Environment extends PropertyResolver {
>   String[] getActiveProfiles();
> 
>   String[] getDefaultProfiles();
> 
>   /** @deprecated */
>   @Deprecated
>   boolean acceptsProfiles(String... profiles);
> 
>   boolean acceptsProfiles(Profiles profiles);
> }
> ```
>
> <br>
>
> ## Interface Environment
>
>   - All Superinterfaces:
>
>     [PropertyResolver](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/env/PropertyResolver.html)
>
>   - All Known Subinterfaces:
>
>     [ConfigurableEnvironment](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/env/ConfigurableEnvironment.html), [ConfigurableWebEnvironment](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/context/ConfigurableWebEnvironment.html)
>
>  
>   ------
>   <br>
>
>   ```
>   public interface Environment
>   extends PropertyResolver
>   ```
>  
>   > **Interface representing the environment in which the current application is running. Models two key aspects of the application environment: *profiles* and *properties*. Methods related to property access are exposed via the [`PropertyResolver`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/env/PropertyResolver.html) superinterface.** 
>   >
>   > A ***profile*** is a named, logical group of bean definitions to be registered with the container only if the given profile is *active*. Beans may be assigned to a profile whether defined in XML or via annotations 
>   >
>   > ***Properties*** play an important role in almost all applications, and may originate from a variety of sources: properties files, JVM system properties, system environment variables, JNDI, servlet context parameters, ad-hoc Properties objects, Maps, and so on. 
>
> <br>
>
> 출처 : https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/env/Environment.html
>
> <br><br>
>
>  -  나의 정리 : Interface Environment는 profile과 Properties를 통해 실행중인 환경을 나타내는 인터페이스이다. 위에 선언된 profile관련 메서드 뿐만 아니라 PropertyResolver상속을 통해
> containsProperty(String key), getProperty(String key) 등의 속성관련 메서드로 속성을 확인 할 수 있다.
>
> 





<br><br>

## 프로파일 유즈케이스

- 테스트 환경에서는 A라는 빈을 사용하고, 배포 환경에서는 B라는 빈을 쓰고 싶다. 
- 이 빈은 모니터링 용도니까 테스트할 때는 필요가 없고 배포할 때만 등록이 되면 좋겠다. 

<br><br>

## 프로파일 정의하기

- 클래스에 정의
  - @Configuration @Profile(“test”)
  - @Component @Profile(“test”) 

- 메소드에 정의
  - @Bean @Profile(“test”) 

<br>

> 프로파일을 정의하고 실해해보기
>
> AppRunner.java
>
> ```java
> package profile;
> 
> import java.util.Arrays;
> import org.springframework.beans.factory.annotation.Autowired;
> import org.springframework.boot.ApplicationArguments;
> import org.springframework.boot.ApplicationRunner;
> import org.springframework.context.ApplicationContext;
> import org.springframework.core.env.Environment;
> import org.springframework.stereotype.Component;
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
>   @Override
>   public void run(ApplicationArguments args) throws Exception {
>     //Environment를 가져온다.
>     Environment environment = ctx.getEnvironment();
>     //ActiveProfiles 가져온다.
>     System.out.println(Arrays.toString(environment.getActiveProfiles()));
>     System.out.println(Arrays.toString(environment.getDefaultProfiles()));
>   }
> }
> ```
>
> <br>
>
> TestConfiguration.java
>
> ```java
> package profile;
> 
> import org.springframework.context.annotation.Bean;
> import org.springframework.context.annotation.Configuration;
> import org.springframework.context.annotation.Profile;
> 
> @Configuration
> @Profile("test") //test 파일일때만 빈 설정파일 적용
> public class TestConfiguration {
> 
>   @Bean
>   public BookRepository bookRepository(){
>     return new TestBookRepository();
>   }
> }
> 
> ```
>
> <br>
>
> BookRepository 빈이 생성되지않아서 에러 발생
>
> ```java
> ***************************
> APPLICATION FAILED TO START
> ***************************
> 
> Description:
> 
> Field bookRepository in profile.AppRunner required a bean of type 'profile.BookRepository' that could not be found.
> 
> The injection point has the following annotations:
> 	- @org.springframework.beans.factory.annotation.Autowired(required=true)
> 
> 
> Action:
> 
> Consider defining a bean of type 'profile.BookRepository' in your configuration.
> 
> 
> Process finished with exit code 0
> 
> ```
>
> <br>
>
> 문제 해결 방법 -  프로파일 설정하기



<br>

## 프로파일 설정하기

- -Dspring.profiles.active="test"

  에러 해결 방법 1. 설정 -Dspring.profiles.active="test" 추가한다.

  

 <img width="459" alt="20211028_161443_1" src="https://user-images.githubusercontent.com/56250078/139211073-5ac08103-5964-4a41-a034-999b81d755bf.png">




  <br>

- [@ActiveProfiles]("[ActiveProfiles (Spring Framework 5.3.12 API)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/ActiveProfiles.html)") (테스트용) 

  에러 해결 방법 2. 설정 Actice profiles 에 "test" 추가한다.

  

 <img width="460" alt="20211028_161951" src="https://user-images.githubusercontent.com/56250078/139211104-d42a7d7d-ed03-474c-b5e7-941eea39ca5c.png">

<br>



두 해결 방법의 실행 결과

```java
[test]
[default]
```

<br><br>

## 프로파일 표현식

- ! (not) 
- & (and)
- | (or)

<br><br>

