# SpEL (스프링 Expression Language) 

<br>

작성일자: 2021-11-07<br>

내용 : 백기선님의 스프링 프레임워크 핵심 기술 인프런 강의

<br>

## [스프링 EL](https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#expressions)이란?

- 객체 그래프를 조회하고 조작하는 기능을 제공한다.
- [Unified EL](https://docs.oracle.com/javaee/5/tutorial/doc/bnahq.html)과 비슷하지만, 메소드 호출을 지원하며, 문자열 템플릿 기능도 제공한다. 
- OGNL, MVEL, JBOss EL 등 자바에서 사용할 수 있는 여러 EL이 있지만, SpEL은 모든 스프링 프로젝트 전반에 걸쳐 사용할 EL로 만들었다. 
- 스프링 3.0 부터 지원. 



## SpEL 구성

- ExpressionParser parser = new SpelExpressionParser()
- StandardEvaluationContext context = new StandardEvaluationContext(bean)
- Expression expression = parser.parseExpression(“SpEL 표현식”) 
- String value = expression.getvalue(context, String.class) 



## 문법

- #{“표현식"}
- ${“프로퍼티"}
- 표현식은 프로퍼티를 가질 수 있지만, 반대는 안 됨.
  - #{${my.data} + 1}
- [레퍼런스 참고](https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#expressions-language-ref) 



## 실제로 어디서 쓰나?

- @Value 애노테이션
- @ConditionalOnExpression 애노테이션
  선택적으로(선별적으로) 빈을 읽어들이고 사용할 수 있는 어노테이션
- [스프링 시큐리티](https://docs.spring.io/spring-security/site/docs/3.0.x/reference/el-access.html)
  - 메소드 시큐리티, @PreAuthorize, @PostAuthorize, @PreFilter, @PostFilter
  - XML 인터셉터 URL 설정
  - ...
- [스프링 데이터](https://spring.io/blog/2014/07/15/spel-support-in-spring-data-jpa-query-definitions)
  - @Query 애노테이션
- [Thymeleaf](https://blog.outsider.ne.kr/997)
- ...





## 사용해보기

```
//#과 같이 쓰면 { } 안을 표현식으로 인식하고 연산후 결과값을 담아준다.
```

```java
package com.inflearnb.spel;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements ApplicationRunner {

  @Value("#{1+1}") //#과 같이 쓰면 { } 안을 표현식으로 인식하고 연산후 결과값을 담아준다.
  int value;

  @Value("#{'hello ' + 'world'}")
  String greeting;

  @Value("#{1 eq 1}")
  boolean trueOrFalse;

  @Value("hello")
  String hello;

  @Value("${my.value}") //properties에 접근할때는 '$' 사용
  int myValue;

  @Value("#{${my.value} eq 100 }")  //'#'표현식 안에 '$'사용 가능
  boolean isMyValue100;

  @Value("#{sample.data}") //빈에 있는 값을 가져와서 찍을 수 있다.
  int sampleDate;

  @Override
  public void run(ApplicationArguments args) throws Exception {

    System.out.println("========================");
    System.out.println(value);
    System.out.println(greeting);
    System.out.println(trueOrFalse);
    System.out.println(hello);
    System.out.println(myValue);
    System.out.println(isMyValue100);
    System.out.println(sampleDate);

    //SpEL 구성하는 클래스 및 메서드 직접 사용해보기
    ExpressionParser parser = new SpelExpressionParser();
    Expression expression = parser.parseExpression("2+100");
    Integer value = expression.getValue(Integer.class); //해당 타입으로 변환할때 컨버전 사용
    System.out.println(value);
  }
}

/* 실행 결과
========================
2
hello world
true
hello
100
true
200
102
*/

```



- 스프링 관련 프레임워크 혹은 api사용할때 #{ } 사용하면 어라? spring expression language사용하는 거구나!!알도록 하자
