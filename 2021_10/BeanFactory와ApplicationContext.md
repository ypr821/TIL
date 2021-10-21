
# BeanFactory와ApplicationContext
<br>
작성일자 : 2021-10-21<br>
내용 : 스프링 프레임워크 핵심 기술(인프런 강의)

<br><br>
## Interface BeanFactory

<br>

- IoC 컨테이너의 최상위 interface이고 가장 핵심 interface이다.

- 빈을 생성하고 의존관계를 설정하는 기능을 담당하는 가장 기본적인 IoC 컨테이너이자 클래스를 말한다.

<br>

The point of this approach is that the BeanFactory is a central registry of application components, and centralizes configuration of application components 

참고 : [BeanFactory (Spring Framework 5.0.8.RELEASE API)](https://docs.spring.io/spring-framework/docs/5.0.8.RELEASE/javadoc-api/org/springframework/beans/factory/BeanFactory.html)

<br>

Inversion of Control: 의존 관계 주입(Dependency Injection)이라고도 하며, 어떤 객체가 사용하는 의존 객체를 직접 만들어 사용하는게 아니라, 주입 받아 사용하는 방법을 말 함. 

### **스프링 IoC 컨테이너**

● BeanFactory 

- 애플리케이션 컴포넌트의 중앙 저장소.

● 빈 설정 소스로 부터 빈 정의를 읽어들이고, 빈을 구성하고 제공한다. 

### **빈** 

●스프링 IoC 컨테이너가 관리 하는 객체.

● 장점
- 의존성 관리
- 스코프 
- 싱글톤: 하나
- 프로포토타입: 매번 다른 객체

○ 라이프사이클 인터페이스

<br>

> 나의 질문 : 왜 BookRepository, BookService 는 빈에 등록할까?
>
> 의존성주입을 하고 싶으면 빈이 되어야 한다.
> 빈의 scope BookService 인스턴스는 오직 하나만 사용된다 => 싱글톤으로 객체를 관리하고싶다.

<br>

빈에 등록되는 애들은 

아무 어노테이션을 주지 않으면 기본적으로 싱글톤 타입으로 들어감

프로토타입은 매번 다른 객체 사용하는 것

<br>

BeanFactory 의 life cycle

Bean factory implementations should support the standard bean lifecycle interfaces as far as possible. The full set of initialization methods and their standard order is:

1. BeanNameAware's `setBeanName`
2. BeanClassLoaderAware's `setBeanClassLoader`
3. BeanFactoryAware's `setBeanFactory`
4. EnvironmentAware's `setEnvironment`
5. EmbeddedValueResolverAware's `setEmbeddedValueResolver`
6. ResourceLoaderAware's `setResourceLoader` (only applicable when running in an application context)
7. ApplicationEventPublisherAware's `setApplicationEventPublisher` (only applicable when running in an application context)
8. MessageSourceAware's `setMessageSource` (only applicable when running in an application context)
9. ApplicationContextAware's `setApplicationContext` (only applicable when running in an application context)
10. ServletContextAware's `setServletContext` (only applicable when running in a web application context)
11. `postProcessBeforeInitialization` methods of BeanPostProcessors
12. InitializingBean's `afterPropertiesSet`
13. a custom init-method definition
14. `postProcessAfterInitialization` methods of BeanPostProcessors

On shutdown of a bean factory, the following lifecycle methods apply:

1. `postProcessBeforeDestruction` methods of DestructionAwareBeanPostProcessors
2. DisposableBean's `destroy`
3. a custom destroy-method definition

<br>

BookServiceTest.java

```java
package com.example.inflearnb.book;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class BookServiceTest {

  @Mock
  BookRepository bookRepository;

  @Test
  public void save(){
    Book book = new Book();

    when(bookRepository.save(book)).thenReturn(book);
     /* 이 때 bookrepository를 빈으로 등록해서 의존성 주입이 가능하게 만들었다.
     * "의존성 주입을 사용하면 단위 테스트가 용이해진다"라는 정보를 전달하고자 설명한 예제이다.
     * 의존성 주입을 사용하도록 객체를 구성하면, 의존성을 느슨하게 관리할 수 있고, 덤으로 테스트하기 편리해진다
     * */

    BookService bookService = new BookService((bookRepository));
    Book result = bookService.save(book);

    assertThat(book.getCreated()).isNotNull();
    assertThat(book.getBookStatus()).isEqualTo(BookStatus.DRAFT);
    assertThat(result).isNotNull();

  }
}

```

<br><br>

## Interface ApplicationContext

<br>

- Central interface to provide configuration for an application. This is read-only while the application is running, but may be reloaded if the implementation supports this.

  ```java 
  ApplicationContext는 BeanFactory를 구현하고 있어 BeanFactory의 확장된 버전이라고 생각하면 좋다.
  ```

- ApplicationContext는 BeanFactory를 구현하고 있어 BeanFactory의 확장된 버전이다.

All Superinterfaces:

[ApplicationEventPublisher](https://docs.spring.io/spring-framework/docs/5.0.8.RELEASE/javadoc-api/org/springframework/context/ApplicationEventPublisher.html), [BeanFactory](https://docs.spring.io/spring-framework/docs/5.0.8.RELEASE/javadoc-api/org/springframework/beans/factory/BeanFactory.html), [EnvironmentCapable](https://docs.spring.io/spring-framework/docs/5.0.8.RELEASE/javadoc-api/org/springframework/core/env/EnvironmentCapable.html), [HierarchicalBeanFactory](https://docs.spring.io/spring-framework/docs/5.0.8.RELEASE/javadoc-api/org/springframework/beans/factory/HierarchicalBeanFactory.html), [ListableBeanFactory](https://docs.spring.io/spring-framework/docs/5.0.8.RELEASE/javadoc-api/org/springframework/beans/factory/ListableBeanFactory.html), [MessageSource](https://docs.spring.io/spring-framework/docs/5.0.8.RELEASE/javadoc-api/org/springframework/context/MessageSource.html), [ResourceLoader](https://docs.spring.io/spring-framework/docs/5.0.8.RELEASE/javadoc-api/org/springframework/core/io/ResourceLoader.html), [ResourcePatternResolver](https://docs.spring.io/spring-framework/docs/5.0.8.RELEASE/javadoc-api/org/springframework/core/io/support/ResourcePatternResolver.html)

참고 : [ApplicationContext (Spring Framework 5.0.8.RELEASE API)](https://docs.spring.io/spring-framework/docs/5.0.8.RELEASE/javadoc-api/org/springframework/context/ApplicationContext.html)



<br><br>



application.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xmlns:context="http://www.springframework.org/schema/context"
  xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd">
<!--
  <bean id="bookService" class="com.example.inflearnb2.BookService">
    <property name="bookRepository" ref="bookRepository" />
  </bean>

  <bean id="bookRepository" class="com.example.inflearnb2.BookRepository"/>
-->
  <context:component-scan base-package="com.example.inflearnb2"></context:component-scan>

</beans>
```

<br>

-----



InflearnB2Application.java 

- 빈을 직접 가져와서 쓴다.(xml설정파일 대신 java config 파일 사용)

```java
package com.example.inflearnb2;

import java.util.Arrays;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class InflearnB2Application2 {

  public static void main(String[] args) {
    ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
	//ApplicationContext context = new ClassPathXmlApplicationContext("application.xml");
    String [] beanDefinitionNames = context.getBeanDefinitionNames();
    System.out.println(Arrays.toString(beanDefinitionNames));
    BookService bookService =  (BookService) context.getBean("bookService");
    System.out.println(bookService.bookRepository != null);
  }

}

```

<br>

ApplicationConfig.java 

- 빈을 하나하나 입력했다가@Configuration, @ComponentScan 어노테이션으로 깔끔하게 코드 줄어들었다.

```java
package com.example.inflearnb2;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = InflearnB2Application.class)
//InflearnB2Application.class 부터 컴포넌트 스캐닝을 해랏
public class ApplicationConfig {

    /*
      @Bean
      public BookRepository bookRepository(){
      return new BookRepository();
      }

      @Bean
      public  BookService bookService(BookRepository bookRepository){
      BookService bookService = new BookService();
      //bookService.setBookRepository(bookRepository());
      //bookService.setBookRepository(bookRepository); //setter 사용하기 때문에 @Autowired로       의존성 자동 주입가능
      return bookService;
      }
    */
    
    
}

```

<br>

#### 두 java 파일이 아래 java파일 하나로 줄어들었다.

<br>

InflearnB2Application.java

```java
package com.example.inflearnb2;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@ComponentScan 붙어있고, @SpringBootConfiguration -> @Configuration 붙어있다
public class InflearnB2Application {

  public static void main(String[] args) {

  }
}
```

<br>

@SpringBootApplication 을 살펴보면 

@Configuration와 @ComponentScan을 담고 있다.



<img width="362" alt="ApplicationContext1" src="https://user-images.githubusercontent.com/56250078/138296794-ca2733da-2b64-4665-a547-d73222403fd1.png">

<img width="331" alt="ApplicationContext2" src="https://user-images.githubusercontent.com/56250078/138296802-caddc26e-27c3-4daf-ad83-878b0a7e60f6.png">



<br><br>







## BeanFactory와 ApplicationContext 차이점

- **BeanFactroy는 pre-loading(사전로딩) 방식**이라는 것이다.

pre-loading(사전로딩) 방식이라는 것은 Bean이 호출되기도 전에 모든 Bean을 인스턴스화 시키는 것으로 만일 등록된 Bean이 많을 경우 BeanFactory를 사용할 경우 ApplicationContext 보다 속도가 느릴 수 있다.

- 반면에 **ApplicationContext는 lazy-loading(지연로딩) 방식**이다.
해당 Bean에 대한 설정 등을 스프링 컨테이너에서 이미 로드했을지라도 Bean 자체가 인스턴스화 되지 않고, 해당 Bean을 호출할 때야 인스턴스와 되어 많은 Bean이 등록되어 있는 시스템일수록 강점을 보인다.



참고 : https://pamyferret.tistory.com/24



차이점이 잘 정리된 블로그

https://beststar-1.tistory.com/39

















