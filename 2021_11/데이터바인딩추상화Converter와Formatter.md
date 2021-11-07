# 데이터 바인딩 추상화: Converter와 Formatter 

<br>

작성일자: 2021-11-07<br>
내용 : 백기선님의 스프링 프레임워크 핵심 기술 인프런 강의

<br><br>

> 나의 질문 : 데이터 바인딩은 뭘까?
>
> [데이터 바인딩](https://ko.wikipedia.org/wiki/데이터_바인딩)이란 간단하게 말해서 서로 다른 타입의 데이터를 함께 묶어 동기화하는 기법이다. 
>
> 다시말하면, 프로퍼티 값을 타겟 객체에 할당해주는 것을 의미한다. 예를들어 웹에서 사용자의 입력값은 문자열로 전달된다. 이 문자열을 어플리케이션 도메인 객체의 프로퍼티값으로 동적으로 할당해주는 것을 데이터 바인딩이라고 할 수 있다.

<br><br>

## [Converter](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/convert/converter/Converter.html)

- S 타입을 T 타입으로 변환할 수 있는 매우 일반적인 변환기.

- 상태 정보 없음 == Stateless == 쓰레드세이프 == 빈 등록 가능하긴 함.

- [ConverterRegistry](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/convert/converter/ConverterRegistry.html)에 등록해서 사용 

  ```java
  package com.inflearnb.propertyeditor;
  
  import org.springframework.core.convert.converter.Converter;
  import org.springframework.stereotype.Component;
  
  public class EventConverter {
    //@Component
    public static class StringToEventConverter implements Converter<String,Event>{
  
      @Override
      public Event convert(String source) {
        return new Event(Integer.parseInt(source));
      }
    }
   // @Component
    public static class EventToStringConverter implements Converter<Event,String>{
  
      @Override
      public String convert(Event source) {
        return source.getId().toString();
      }
    }
  }
  
  ```

  <br><br>

  

## [Formatter](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/format/Formatter.html)

- PropertyEditor 대체제

- Object와 String 간의 변환을 담당한다. 

- 문자열을 Locale에 따라 다국화하는 기능도 제공한다. (optional) 

- 쓰레드세이프 == 빈 등록 가능 == 주입도 받을 수 있다.

- [FormatterRegistry](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/format/FormatterRegistry.html)에 등록해서 사용 

  ```java
  public class EventFormatter implements Formatter {
  
    /*  @Autowired
    MessageSource messageSource;*/
      
    @Override
    public Event parse(String text, Locale locale) throws ParseException {
      Event event = new Event();
      int id = Integer.parseInt(text);
      event.setId(id);
      return event;
    }
  
    @Override
    public String print(Event object, Locale locale) {
       //messageSource.getMessage("title",locale ); -> 이런것도 가능하다.  
        
      return object.getId().toString();
    }
  } 
  ```

  

<br><br>

## [ConversionService](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/convert/ConversionService.html)

(이전에는 PropertyEditor에서 DataBinder 를 사용했다면 ) 이제는 ConversionService를 사용하는 것

- 실제 변환 작업은 이 인터페이스를 통해서 쓰레드-세이프하게 사용할 수 있음.
- 스프링 **MVC**, 빈 (value) 설정, SpEL에서 사용한다.
- DefaultFormattingConversionService
  - FormatterRegistry
  - ConversionService
  - 여러 기본 컴버터와 포매터 등록 해 줌. 



<img width="220" alt="20211107_142318_1" src="https://user-images.githubusercontent.com/56250078/140633487-a58dd793-8a23-43e4-8059-9c6e361a0154.png">

<br>설명 

FormatterRegistry는 ConverterRegistry를 구현하고 있어서 FormatterRegistry로 컨버터도 등록할 수 있고 포매터도 등록할 수 있다.

DefaultFormattingConversionService는 FormatterRegistry와 ConversionService 둘다 구현하고 있다.!!!

<br>

AppRunner.java 파일에서 ConversionService 사용해본다.

```java
package com.inflearnb.propertyeditor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements ApplicationRunner {

  @Autowired
  ConversionService conversionService;

  @Override
  public void run(ApplicationArguments args) throws Exception {
   // conversionService.convert();
    System.out.println(conversionService.getClass().toString());

    //등록된 컨버터 보는 방법
    //System.out.println(conversionService);

  }
}

```

<br>

실행결과

```java
class org.springframework.boot.autoconfigure.web.format.WebConversionService
```



<br><br>



## 스프링 부트

- 웹 애플리케이션인 경우에 DefaultFormattingConversionSerivce를 상속하여 만든 WebConversionService를 빈으로 등록해 준다.

- WebConversionService 스프링부트가 제공하는 ConversionService

- Formatter와 Converter 빈을 찾아 자동으로 등록해 준다 

  - WebConfig.java을 지우고 Fomatter나 Converter에 @Conponent를 등록하면 자동으로 등록해준다.

    WebConfig.java

    ```java
    @Configuration
    public class WebConfig  implements WebMvcConfigurer {
    
      @Override
      public void addFormatters(FormatterRegistry registry) {
        //registry.addConverter(new StringToEventConverter());
        registry.addFormatter(new EventFormatter());
      }
    }
    ```

    <br>



참고

```java
package org.springframework.boot.autoconfigure.web.format;
//스프링부트꺼네..

public class WebConversionService extends DefaultFormattingConversionService {
  private static final boolean JSR_354_PRESENT = ClassUtils.isPresent("javax.money.MonetaryAmount", WebConversionService.class.getClassLoader());

  public WebConversionService(DateTimeFormatters dateTimeFormatters) {
    super(false);
    if (dateTimeFormatters.isCustomized()) {
      this.addFormatters(dateTimeFormatters);
    } else {
      addDefaultFormatters(this);
    }

  }

  private void addFormatters(DateTimeFormatters dateTimeFormatters) {
    this.addFormatterForFieldAnnotation(new NumberFormatAnnotationFormatterFactory());
    if (JSR_354_PRESENT) {
      this.addFormatter(new CurrencyUnitFormatter());
      this.addFormatter(new MonetaryAmountFormatter());
      this.addFormatterForFieldAnnotation(new Jsr354NumberFormatAnnotationFormatterFactory());
    }

    this.registerJsr310(dateTimeFormatters);
    this.registerJavaDate(dateTimeFormatters);
  }
    
    ---이하생략
}
```

<br>



<img width="294" alt="20211107_142318_2" src="https://user-images.githubusercontent.com/56250078/140633488-59042dd4-31ab-453f-b586-3f923c1f4902.png">

잘 실행되는구나.... (이전에 작성해둔 controller파일이 있다.)

<br>

```java
package com.inflearnb.propertyeditor;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventController {



  @GetMapping("/event/{event}")
  public String getEvent(@PathVariable Event event){
    System.out.println(event);
    return event.getId().toString();
  }
}

```

<br>

실행 결과 console 창 

Event{id=22, title='null'}

<br><br>
