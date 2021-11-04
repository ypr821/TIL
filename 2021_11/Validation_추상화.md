# Validation 추상화 

[org.springframework.validation.Validator]([Validator (Spring Framework 5.3.12 API)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/validation/Validator.html)) 

<br>
작성일자 : 2021-10-31
<br>
내용 : 백기선님의 스프링 프레임워크 핵심 기술 인프런 강의

<br><br>

### 애플리케이션에서 사용하는 객체 검증용 인터페이스. 

> 나의 질문 : 이 검증 인터페이스는 언제 사용하나?
>
> 유효성 검사 할때 사용한다.

<br>

### 특징

- 어떤한 계층과도 관계가 없다. => 모든 계층(웹, 서비스, 데이터)에서 사용해도 좋다. 
- 구현체 중 하나로, JSR-303(Bean Validation 1.0)과 JSR-349(Bean Validation 1.1)을 지원한다. ([LocalValidatorFactoryBean](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/validation/Validator.html))) 
- DataBinder에 들어가 바인딩 할 때 같이 사용되기도 한다. 

<br><br>

## Validator 인터페이스

- boolean **supports**(Class clazz): 어떤 타입의 객체를 검증할 때 사용할 것인지 결정한다.
- void **validate**(Object obj, Errors e): 실제 검증 로직을 이 안에서 구현한다.
  - 구현할 때 ValidationUtils 사용하며 편리. 

```java
public class UserLoginValidator implements Validator {

    private static final int MINIMUM_PASSWORD_LENGTH = 6;

    public boolean supports(Class clazz) {
       return UserLogin.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, Errors errors) {
       ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "field.required");
       ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "field.required");
       UserLogin login = (UserLogin) target;
       if (login.getPassword() != null
             && login.getPassword().trim().length() < MINIMUM_PASSWORD_LENGTH) {
          errors.rejectValue("password", "field.min.length",
                new Object[]{Integer.valueOf(MINIMUM_PASSWORD_LENGTH)},
                "The password must be at least [" + MINIMUM_PASSWORD_LENGTH + "] characters in length.");
       }
    }
 }
```

<br>

AppRunner.java

```java
package com.inflearnb.resource;

import java.util.Arrays;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

@Component
public class AppRunner implements ApplicationRunner {

  @Override
  public void run(ApplicationArguments args) throws Exception {
    Event event = new Event();
    EventValidator eventValidator = new EventValidator();
    Errors errors = new BeanPropertyBindingResult(event, "event"); 
    //어떤 타겟을 검사할것인가, 그 이름
    //BeanPropertyBindingResult를 기본구현체로 사용
    //spring mvc를 사용하면 BeanPropertyBindingResult 자동으로 생성해서 넘겨주기 때문에 직접사용하는 경우는 드물다.
    //해당 Errors 인터페이스는 자주 볼 수 있다.

    eventValidator.validate(event,errors);
    System.out.println(errors.hasErrors());
    errors.getAllErrors().forEach(e -> {
      System.out.println("====error code====");
      Arrays.stream(e.getCodes()).forEach(System.out::println);
      System.out.println(e.getDefaultMessage());
    });
  }
}
```

<br>

EventValidator.java

```java
package com.inflearnb.resource;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class EventValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return Event.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title","notempty","Empty title is now allowed.");

 /*   ValidationUtils 사용하지 않고 직접 검증하는 로직을 구현하는 방법
    Event event = (Event) target;
    if(event.getTitle() == null){
      errors.reject("errorCode","defaultMessage");
      errors.rejectValue("field","errorCode","defaultMessage");
    }*/
  }
}

```

<br>

실행결과

```java
true
====error code====
notempty.event.title
notempty.title
notempty.java.lang.String
notempty
Empty title is now allowed.
```

<br><br>





### 스프링 부트 2.0.5 이상 버전을 사용할 때 

- LocalValidatorFactoryBean 빈으로 자동 등록해준다. 그래서 Validator를 그냥 사용할 수 있다.
- JSR-380(Bean Validation 2.0.1) 구현체로 hibernate-validator 사용. 
- [https://beanvalidation.org/](https://beanvalidation.org/)
  자바 2.0 ?표준스팩  

<br>

```java
class org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport$NoOpValidator
```

예상한 결과는 LocalValidatorFactoryBean 였는데 음...... 궁금해서 찾아보니..

스프링 부트 2.3부터 validator 관련 의존성이 기본 의존성에서 빠지면서 예상한 결과와 다른 값을 출력받게 됐다. 

<br>

validation 의존성을 추가한다.

```xml
    <!-- https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-validation -->
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>

```

<br>

실행결과

```java
class org.springframework.validation.beanvalidation.LocalValidatorFactoryBean

```

<br><br>

=====

AppRunner.java 

@Autowired
  Validator validator; 추가!!

```java
package com.inflearnb.resource;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class AppRunner implements ApplicationRunner {

  @Autowired
  Validator validator;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    System.out.println(validator.getClass());
    Event event = new Event();
    //error를 만들 셋팅
    event.setLimit(-1);
    event.setEmail("aaa2");
    Errors errors = new BeanPropertyBindingResult(event, "event");
    //(어떤 타겟을 검사할 것 인가, 그 타겟 이름)


    validator.validate(event,errors);
    System.out.println(errors.hasErrors());
    errors.getAllErrors().forEach(e -> {
      System.out.println("====error code====");
      Arrays.stream(e.getCodes()).forEach(System.out::println);
      System.out.println(e.getDefaultMessage());
    });
  }
}


```



<br>

Event.java 에 어노테이션을 추가해서 validation 기능을 사용할 수 있도록 했다.

```java
package com.inflearnb.resource;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Event {

  Integer id;

  @NotEmpty
  String title;

  @NotNull @Min(0)
  Integer limit;

  @Email
  String email;

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Integer getLimit() {
    return limit;
  }

  public void setLimit(Integer limit) {
    this.limit = limit;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Integer getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }
}

```

<br>

실행결과

```java
class org.springframework.validation.beanvalidation.LocalValidatorFactoryBean
true
====error code====
Min.event.limit
Min.limit
Min.java.lang.Integer
Min
0 이상이어야 합니다
====error code====
NotEmpty.event.title
NotEmpty.title
NotEmpty.java.lang.String
NotEmpty
비어 있을 수 없습니다
====error code====
Email.event.email
Email.email
Email.java.lang.String
Email
올바른 형식의 이메일 주소여야 합니다

```

default메시지는 validator 인터페이스가 자동 생성 해준다.

오 신기하다.

<br><br>



