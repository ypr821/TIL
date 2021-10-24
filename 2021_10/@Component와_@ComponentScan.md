# @Component와 @ComponentScan

<br>
작성일자 : 2021-10-24
<br>
내용 : 백기선의 스프링 프레임워크 핵심 기술 인프런 강의 공부


<br><br>

## @ComponentScan 주요 속성

● 스캔 위치 설정 

● 필터: 어떤 애노테이션을 스캔 할지 또는 하지 않을지 

<br><br>

## @Component 를 가지고 있는 어노테이션 (@Component  : 기본적으로 빈으로 등록된다.)

● @Repository 

● @Service 

● @Controller 

● @Configuration 

<br><br>

### (참고) 단점 

싱글톤인 빈들은 초기에 다 생성한다. 등록해야되는 빈이 많은 경우에 초기 구동시간이 길어질수 있다. 일단 구동후에는 또 다른 빈을 생성하느라 성능을 잡아 먹진 않는다.

프록시나 리플랙션을 사용한 빈 등록은 성능에 영향을 주지만 Function을 사용한 빈 등록 방법은 성능에 영향을 주지 않는다. 이때의 성능은 애플리케이션을 구동할때의 성능이다.
 (백기선님은 이 성능적인 단점은 큰 문제가 되진 않는 다고 하심)

<br>

> ### Function을 사용한 빈 등록
>
> - 모든 빈에 Function을 사용한 빈 등록하는 건 로직이 많이 추가된다. 애초에 ComponentScan이 나온 이유에 해당하는 문제와 직면한다.
>
> ```java
> public static void main(String[] args) {
>  new SpringApplicationBuilder().sources(Demospring51Application.class)   .initializers((ApplicationContextInitializer<GenericApplicationContext>)
> 
>          applicationContext -> {
>            applicationContext.registerBean(MyBean.class);
>          }).run(args);
> }
> ```
>
> 
>
> ```java
> package com.inflearnb.spring51;
> 
> import org.springframework.beans.factory.annotation.Autowired;
> import org.springframework.boot.SpringApplication;
> import org.springframework.boot.autoconfigure.SpringBootApplication;
> import out.MyService;
> 
> @SpringBootApplication
> public class Spring51Application {
> 
> @Autowired
> MyService myService;
> /* Spring51Application과 같은 패키지내가 아닌 다른 패키지에 있는 class를 @Autowired 했다.*/
> 
> public static void main(String[] args) {
> 
>  SpringApplication.run(Spring51Application.class, args);
> }
> }
> ```
>
> 실행결과 에러 출력
>
> ```java
> ***************************
> APPLICATION FAILED TO START
> ***************************
> 
> Description:
> 
> Field myService in com.inflearnb.spring51.Spring51Application required a bean of type 'out.MyService' that could not be found.
> 
> The injection point has the following annotations:
> 	- @org.springframework.beans.factory.annotation.Autowired(required=true)
> 
> 
> Action:
> 
> Consider defining a bean of type 'out.MyService' in your configuration.
> 
> ```
>
> <br>
>
> Spring51Application.java 로가서 직접 빈을 등록하자.
>
> - registerBean()메서드를 사용해서 빈을 직접 등록 할 수 있다. 이럴 경우 MyService에 붙여둔 @Service 어노테이션을 지우면 된다.
>
> ```java
> package com.inflearnb.spring51;
> 
> import org.springframework.beans.factory.annotation.Autowired;
> import org.springframework.boot.SpringApplication;
> import org.springframework.boot.autoconfigure.SpringBootApplication;
> import org.springframework.context.ApplicationContextInitializer;
> import org.springframework.context.ConfigurableApplicationContext;
> import org.springframework.context.support.GenericApplicationContext;
> import out.MyService;
> 
> @SpringBootApplication
> public class Spring51Application {
> 
>   //@Autowired
>   //MyService myService;
> 
>   public static void main(String[] args) {
> 
>     var app = new SpringApplication(Spring51Application.class);
>     app.addInitializers(new ApplicationContextInitializer<GenericApplicationContext>() {
>       @Override
>       public void initialize(GenericApplicationContext ctx) {
>         ctx.registerBean(MyService.class);       
>       }
>     });
>     app.run(args);
>   }
> }
> ```
>
> 
>
> 람다로 코드 정리
>
> ```java
>     var app = new SpringApplication(Spring51Application.class);
>     app.addInitializers((ApplicationContextInitializer<GenericApplicationContext>) ctx -> {
>       ctx.registerBean(MyService.class);
> ```
>
> <br>
>
> 
>
> 메인 메서드에서 ctx.registerBean(MyService.class)가 하나였는데  ApplicationRunner 추가 
>
> ```java
> public static void main(String[] args) {
>     //SpringApplication.run(Spring51Application.class, args);
>     var app = new SpringApplication(Spring51Application.class);
>     app.addInitializers((ApplicationContextInitializer<GenericApplicationContext>) ctx -> {
>       ctx.registerBean(MyService.class);
>       //추가한 부분 
>       ctx.registerBean(ApplicationRunner.class, new Supplier<ApplicationRunner>() {
>         @Override
>         public ApplicationRunner get() {
>           return new ApplicationRunner() {
>             @Override
>             public void run(ApplicationArguments args) throws Exception {
>               System.out.println("Funtional Bean Definition!!");
>             }
>           };
>         }
>       });
>     });
>     app.run(args);
> 
>   }
> 
> ```
>
> 
>
> 람다사용
>
> ```java
> public static void main(String[] args) {
>   //SpringApplication.run(Spring51Application.class, args);
>   var app = new SpringApplication(Spring51Application.class);
>   app.addInitializers((ApplicationContextInitializer<GenericApplicationContext>) ctx -> {
>     ctx.registerBean(MyService.class);
>     ctx.registerBean(ApplicationRunner.class, new Supplier<ApplicationRunner>() {
>       @Override
>       public ApplicationRunner get() {
>         return args1 -> System.out.println("Funtional Bean Definition!!");
>       }
>     });
>   });
>   app.run(args);
> 
> }
> ```
>
> 람다에 람다
>
> ```java
>  public static void main(String[] args) {
>     //SpringApplication.run(Spring51Application.class, args);
>     var app = new SpringApplication(Spring51Application.class);
>     app.addInitializers((ApplicationContextInitializer<GenericApplicationContext>) ctx -> {
>       ctx.registerBean(MyService.class);
>       ctx.registerBean(ApplicationRunner.class, () -> (ApplicationRunner) args1 -> System.out.println("Funtional Bean Definition!!"));
>     });
>     app.run(args);
> 
>   }
> ```
>
> 
>
> ```java 
> //실행결과
> Funtional Bean Definition!!
> com.inflearnb.spring51.KeesunBookRepository@5cea88ef
> com.inflearnb.spring51.MyBookRepository@75e8690c
> 
> ```
>
> <br>
>
> 빈 두개를 Funtional하게 등록했다.

<br><br>

#### ComponentScan 을 더 살펴보자 (스캔은 어디서 부터 시작되는 걸까?)

ComponentScan.class 파일의 일부분 이다.

```java
ackage org.springframework.context.annotation;

import 생략;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Repeatable(ComponentScans.class)
public @interface ComponentScan {
  @AliasFor("basePackages")
  String[] value() default {};

  @AliasFor("value")
  String[] basePackages() default {};

  Class<?>[] basePackageClasses() default {};

  Class<? extends BeanNameGenerator> nameGenerator() default BeanNameGenerator.class;

  Class<? extends ScopeMetadataResolver> scopeResolver() default AnnotationScopeMetadataResolver.class;

  ScopedProxyMode scopedProxy() default ScopedProxyMode.DEFAULT;

  String resourcePattern() default "**/*.class";

  boolean useDefaultFilters() default true;

  ComponentScan.Filter[] includeFilters() default {};

  ComponentScan.Filter[] excludeFilters() default {};
    
	```중간 생략```
}

```

<br>

ComponentScan.class 파일중 아래 부분을 살펴보면 ComponentScan의 **스캔 시작**을 알 수 있다.

```java
@AliasFor("basePackages")
  String[] value() default {};
//문자열로 return -> type safe 하지 않는다.
  @AliasFor("value")
  String[] basePackages() default {};

//위의 메서드들보다 type safe하다.
  Class<?>[] basePackageClasses() default {};
```

<br>

**basePackageClasses( )** 로 값이 전달된 클래스를 기준으로 컴포넌트 스캔을 시작한다.

```java
package com.inflearnb.spring51;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(
    //basePackages = "com.inflearnb.spring51"
    basePackageClasses = TestConfiguration.class
    
)
public class TestConfiguration {

}

//출처  : https://atoz-develop.tistory.com/entry/Spring-Component-Scan%EA%B3%BC-Function%EC%9D%84-%EC%82%AC%EC%9A%A9%ED%95%9C-%EB%B9%88-%EB%93%B1%EB%A1%9D-%EB%B0%A9%EB%B2%95 블로그 참조
```



basePackages()나 basePackageClasses()를 설정하지 않으면 기본값은 @ComponentScan을 붙이고 있는 @Configuration 부터 컴포넌트 스캔을 시작한다.

Application 클래스가 시작 지점이 된다. 



>  내 코드의 경우 @SpringBootApplication를 붙이고 있는 Spring51Application 클래스가 시작 지점이다.
>
> @SpringBootApplication는@ComponentScan와  @SpringBootConfiguration를 담고있고
>  @SpringBootConfiguration은 @Configuration을 담고 있다.



그리고  Application 클래스가 담겨있는 패키지 내의 모든 패키지 및 클래스를 컴포턴트 스캔한다. 

해당 패키지 밖은 컴포넌트 스캔이 안된다.

<br>

#### ComponentScan은 스캔의 범위를 지정하는 중요한 기능을 한다.

빈을 찾을 수 없고 빈 주입이 잘 안되는 에러가 뜨면 

어디서 어떻게 ComponentScans 이 되는 지 ComponentScans의 범위를 잘 따져봐야 한다.



<br><br>

참고

SpringBootApplication.class 파일을 살펴보면 

```java
package org.springframework.boot.autoconfigure;

	```생략```

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(
  excludeFilters = {@Filter(
  type = FilterType.CUSTOM,
  classes = {TypeExcludeFilter.class}
), @Filter(
  type = FilterType.CUSTOM,
  classes = {AutoConfigurationExcludeFilter.class}
)}
)
public @interface SpringBootApplication {
	```중간 생략```
}
```



#### ComponentScan에서 Filter를 사용해서 걸러낸다.

```java
@ComponentScan(
  excludeFilters = {@Filter(
  type = FilterType.CUSTOM,
  classes = {TypeExcludeFilter.class}
), @Filter(
  type = FilterType.CUSTOM,
  classes = {AutoConfigurationExcludeFilter.class}
)}
)
```



<br><br>



## 동작 원리 

● @ComponentScan은 스캔할 패키지와 애노테이션에 대한 정보이다.

● 실제 스캐닝은 **ConfigurationClassPostProcessor**라는 **BeanFactoryPostProcessor**에 의해 처리 된다.

- BeanFactoryPostProcessor 인터페이스를 구현한 ConfigurationClassPostProcessor 
- 빈들을 만들기 이전에 적용해준다.
- 다른빈들을 등록하기 전에 컴포넌트 스캔을 하고 빈을 등록한다.



<br><br>

## 주요 포인트

1. 컴포넌트 스캔의 역할 

2. 가장 중요한 속성 2가지 

3. 스캔 대상들

4. 펑셔널한 빈등록방법
