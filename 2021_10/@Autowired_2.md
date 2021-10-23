

# @Autowired 

<br>

작성일자 : 2021-10-23

내용 : 백기선의 스프링 프레임워크 핵심 기술 인프런 강의 공부

<br>

-----

- 필요한 의존 객체의 “타입"에 해당하는 빈을 찾아 주입한다. 

- required: 기본값은 true ( 따라서 못 찾으면 애플리케이션 구동 실패) 

- @Autowired(required = false)  // 의존성 옵셔널로 선택 빈을 못 찾더라도 구동 된다.

  <br>

## 사용할 수 있는 위치

  - 생성자 (스프링 4.3 부터는 생략 가능) 

  - 세터 

  - 필드 

    <br><br>

## 경우의 수

- 해당 타입의 빈이 없는 경우
- 해당 타입의 빈이 한 개인 경우 

- 해당 타입의 빈이 여러 개인 경우
  - 빈 이름으로 시도
    - 같은 이름의 빈 찾으면 해당 빈 사용
    - 같은 이름 못 찾으면 실패 

 <br><br>

## 같은 타입의 빈이 여러개 일 때

- @Primary
- 해당 타입의 빈 모두 주입 받기
- @Qualifier (빈 이름으로 주입) 

<br>

- 같은 타입의 빈이 여러개인데 제대로 설정하지 않은 경우 에러 출력

  Action:
  **Consider marking one of the beans as @Primary, updating the consumer to accept multiple beans, or using @Qualifier to identify the bean that should be consumed**

  @Primary나 @Qualifier사용해서 구체화해라 !!!

```java
***************************
APPLICATION FAILED TO START
***************************
Description:

Parameter 0 of method setBookRepository in com.inflearnb.spring51.BookService required a single bean, but 2 were found:
	- kesunBookRepository: defined in file [C:\YooPuReum\IntelliJ\spring51\spring51\target\classes\com\inflearnb\spring51\KesunBookRepository.class]
	- myBookRepository: defined in file [C:\YooPuReum\IntelliJ\spring51\spring51\target\classes\com\inflearnb\spring51\MyBookRepository.class]

Action:
Consider marking one of the beans as @Primary, updating the consumer to accept multiple beans, or using @Qualifier to identify the bean that should be consumed

Process finished with exit code 0
```

<br>

**@Primary**를 사용해서 마킹해줄 수 있다.

```java
package com.inflearnb.spring51;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class KesunBookRepository implements BookRepository{

}

//@Primary 추가한 뒤 결과 출력 
//class com.inflearnb.spring51.KesunBookRepository

```

<br>

**@Qualifier** 빈의 이름 id 를 주면된다.

```
@Autowired @Qualifier("keesunBookRepository")
```



<br>

**해당 타입의 빈 모두 주입 받는 방법**

List를 사용...

```java
 @Autowired
  List<BookRepository> bookRepositoryList;
```

<br>

```java
  //여러개의 빈을 다 받아라
  @Autowired
  List<BookRepository> bookRepositoryList;

  public void setBookRepository(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }
  public void printBookRepository(){
    this.bookRepositoryList.forEach(System.out::println);
  }

}

//실행결과
//com.inflearnb.spring51.KeesunBookRepository@5a7d016f
//com.inflearnb.spring51.MyBookRepository@39de0148
```



<br><br>

## @Autowire의 동작 원리

- [BeanPostProcessor](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/beans/factory/config/BeanPostProcessor.html)


  - 새로 만든 빈 인스턴스를 수정할 수 있는 라이프 사이클 인터페이스
  - 빈의 인스턴스를 만든 다음에 빈을 Initialization이전, 이후에 부가적인 작업을 할 수있는 라이프사이클 콜백이다.

- [AutowiredAnnotationB](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/beans/factory/annotation/AutowiredAnnotationBeanPostProcessor.html)

  extends BeanPostProcessor

- 스프링이 제공하는 @Autowired와 @Value 애노테이션 그리고 JSR-330의 @Inject 애노테이션을 지원하는 애노테이션 처리기이다.

<br>

>  #### 나의 질문 :  Initialization**이 뭘까?? 빈이 만들어지고 나서 필요한 로직 ???
>
>  - 초기화 빈생성?? (업데이트 예정)
>
>  - **Initialization 방법 1 . @PostConstruct**
>    - 빈이 만들어지고 나서 부가적인 작업
>    - 의존성 주입이 끝난 뒤 실행될 메소드에 적용한다.
>  - **Initialization 방법 2. InitializingBean 인터페이스 사용**
>  - Spring에 기본적으로 제공해주는 `InitializingBean` 인터페이스를 이용해서 초기화 작업을 할 수 있다.
>
>  - 이 인터페이스의 추상메서드 afterPropertiesSet() 만 구현해주면 Spring이 초기화 시점에 알아서 호출해 준다.
>
>
>  ```java
>   @Service
>   public class LifeCycleTest implements InitializingBean {
>  
>     @Override
>     public void afterPropertiesSet() throws Exception {
>       System.out.println("afterPropertiesSet");
>     }
>   }
>  
>  출처 : http://wonwoo.ml/index.php/post/1820
>  ```

<br><br>

- 빈 라이프사이클
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
11. **postProcessBeforeInitialization` methods of BeanPostProcessors**
12. InitializingBean's `afterPropertiesSet`  = @PostConstruct 는 이때 구동
13. a custom init-method definition
14. **postProcessAfterInitialization` methods of BeanPostProcessors**

<br><br>

정리를 해보자

빈 팩토리가 자기에게 등록된 빈 중에 BeanPostProcessor 와 AutowiredAnnotationBeanPostProcessor을 먼저찾고 나머지 일반적인 빈들에게 AutowiredAnnotationBeanPostProcessor에 작성된 로직을 적용해준다. AutowiredAnnotationBeanPostProcessor도 빈으로 등록되어있다
