# Validation 추상화 

[org.springframework.validation.Validator]([Validator (Spring Framework 5.3.12 API)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/validation/Validator.html)) 

<br>
작성일자 : 2021-10-31
<br>
내용 : 백기선님의 스프링 프레임워크 핵심 기술 인프런 강의

<br><br>


### Validator 인터페이스:  애플리케이션에서 사용하는 객체 검증용 인터페이스. 





### 특징

- 어떤한 계층과도 관계가 없다. => 모든 계층(웹, 서비스, 데이터)에서 사용해도 좋다. 
- 구현체 중 하나로, JSR-303(Bean Validation 1.0)과 JSR-349(Bean Validation 1.1)을 지원한다. ([LocalValidatorFactoryBean](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/validation/Validator.html))) 
- DataBinder에 들어가 바인딩 할 때 같이 사용되기도 한다. 



## Validator 인터페이스

- boolean **supports**(Class clazz): 어떤 타입의 객체를 검증할 때 사용할 것인지 결정함
- void **validate**(Object obj, Errors e): 실제 검증 로직을 이 안에서 구현
  - 구현할 때 ValidationUtils 사용하며 편리 함. 

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









### 스프링 부트 2.0.5 이상 버전을 사용할 때 

- LocalValidatorFactoryBean 빈으로 자동 등록
- JSR-380(Bean Validation 2.0.1) 구현체로 hibernate-validator 사용. 
- [https://beanvalidation.org/](https://beanvalidation.org/)
  자바 2.0 ?표준스팩  

