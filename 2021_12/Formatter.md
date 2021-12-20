# Formatter

작성일자 : 2021-12-21

<br>

> 나의 질문 : **언제 왜 사용하는가?**  => 편리하다.
>
> <br>
>
> 예를 들어, Person이라는 클래스와 SampleController라는 컨트롤러가 있다.
>
> ```java
> package com.example.demo1221;
> 
> import lombok.Getter;
> import lombok.Setter;
> 
> @Setter
> @Getter
> public class Person {
> 
> private String name;
> }
> ```
>
> ```java
> package com.example.demo1221;
> 
> import org.springframework.web.bind.annotation.GetMapping;
> import org.springframework.web.bind.annotation.RequestParam;
> import org.springframework.web.bind.annotation.RestController;
> 
> @RestController
> public class SampleController {
> 
> @GetMapping("/hello")
> public String hello(@RequestParam("name") Person person){
>  return "hello " + person.getName();
> }
> 
> }
> ```
>
> 
>
> http://localhost:8080/hello?name=demoName 으로 URL 요청이 들어오면 해당 컨트롤러의 hello 메소드에서 요청을 처리하게 된다.
>
> request 파라미터로 name이라는 변수에 "demoName"라는 문자열을 값으로 입력받았다. 작성한 SampleController에서는 Formatter를 사용해 Person 이라는 데이터 타입으로 변환해줘야 에러가 생기지 않고 실행될 수 있다.
>
> <br>
>
> ### **왜 Formatter를 사용해서 바로 변환해야 할까??** 
>
> 각각의 데이터를 입력받아서 Person이라는 객체를 생성하고 각 필드에 값을 입력해주는 과정을 줄일 수 있다.



<br>

## Formatter의 메서드 

- Printer: 해당 객체를 (Locale 정보를 참고하여) 문자열로 어떻게 출력할 것인가
- Parser: 어떤 문자열을 (Locale 정보를 참고하여) 객체로 어떻게 변환할 것인가

https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/format/Formatter.html

<br><br>

## Spring 과 Springboot에서 Formatter 적용 방법 

- Springboot에서 자동화해주는 부분 정리

<br>

- ### 포매터 추가하는 방법 1

  - WebMvcConfigurer에 addFormatters(FormatterRegistry)을 사용한다.

  - WebMvcConfigurer :  https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/config/annotation/WebMvcConfigurer.html#addFormatters-org.springframework.format.FormatterRegistry
    <br>

  - 예시 

    ```java
    package com.example.demo1221;
    
    import org.springframework.context.annotation.Configuration;
    import org.springframework.format.FormatterRegistry;
    import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
    
    @Configuration
    public class WebConfig implements WebMvcConfigurer {
    
      @Override
      public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new PersonFormatter());
      }
    }
    
    ```

    ```java
    package com.example.demo1221;
    
    import java.text.ParseException;
    import java.util.Locale;
    import org.springframework.format.Formatter;
    
    public class PersonFormatter implements Formatter<Person> {
    
      @Override
        //Parser: 어떤 문자열을 (Locale 정보를 참고하여) 객체로 어떻게 변환할 것인가
      public Person parse(String text, Locale locale) throws ParseException {
        Person person = new Person();
        person.setName(text);
        return person;
      }
    
      @Override
        //Printer: 해당 객체를 (Locale 정보를 참고하여) 문자열로 어떻게 출력할 것인가
      public String print(Person object, Locale locale) {
        return object.toString();
      }
    }
    ```

<br>

- ### 메소드 정의 포매터 추가하는 방법 2 (Springboot 사용시에만 가능 함)

  - 해당 포매터를 빈으로 등록
    <br>

  - 예시

    Formatter를 @Component 어노테이션을 사용해서 빈으로 등록해준다.
    그리고 WebConfig을 지워준다 .!!! 

    ```java
    package com.example.demo1221;
    
    import java.text.ParseException;
    import java.util.Locale;
    import org.springframework.format.Formatter;
    import org.springframework.stereotype.Component;
    
    @Component
    public class PersonFormatter implements Formatter<Person> {
    
      @Override
      public Person parse(String text, Locale locale) throws ParseException {
        Person person = new Person();
        person.setName(text);
        return person;
      }
    
      @Override
      public String print(Person object, Locale locale) {
        return object.toString();
      }
    }
    
    ```

<br><br>



## Test 코드 작성시 어노테이션사용에 주의가 필요하다.

### 1번 방법 테스트 

```java
package com.example.demo1221;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest
class SampleControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Test
  public void hello() throws Exception {
    this.mockMvc.perform(get("/hello")
            .param("name", "demoName"))
        .andDo(print())
        .andExpect(content().string("hello demoName"));
  }
}
```

​    

<br>

### 2번 방법 테스트

```java
package com.example.demo1221;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class SampleControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Test
  public void hello() throws Exception {
    this.mockMvc.perform(get("/hello")
            .param("name", "demoName"))
        .andDo(print())
        .andExpect(content().string("hello demoName"));
  }
}
```

  <br> 

### 왜 이런 차이가 날까??

2번 방법은 WebConfig을 지우고 Formatter에 @Component을 사용하였는데... 1번 방법을 테스트 할때 사용한 @WebMvcTest은 웹관련 빈만을 등록하고 테스트한다. 일반 @Component는 빈으로 등록이 되지 않는다.

2번 방법으로 테스트할때는 웹관련 빈만을 등록하는 @WebMvcTest을 지우고 @SpringBootTest을 사용해서 전체 빈을 등록해서 테스트에 사용할 수 있도록 한다. 그리고 @AutoConfigureMockMvc을 사용해 MockMvc도 사용할 수 있도록 따로 설정하였다.

​    

​    

​    

​    
