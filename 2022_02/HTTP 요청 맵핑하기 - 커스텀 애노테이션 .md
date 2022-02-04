#  HTTP 요청 맵핑하기 - 커스텀 애노테이션 

<br><br>

### @RequestMapping 애노테이션을 메타 애노테이션으로 사용하기

- @GetMapping 같은 커스텀한 애노테이션을 만들 수 있다. 

  <br>

### 메타(Meta) 애노테이션

- 애노테이션에 사용할 수 있는 애노테이션

- 스프링이 제공하는 대부분의 애노테이션은 메타 애노테이션으로 사용할 수 있다. 

  <br>

### 조합(Composed) 애노테이션

- 한개 혹은 여러 메타 애노테이션을 조합해서 만든 애노테이션

- 코드를 간결하게 줄일 수 있다. 

- 보다 구체적인 의미를 부여할 수 있다. 

  <br>

### @Retention

- 해당 애노테이션 정보를 언제까지 유지할 것인가.

- Source: 소스 코드까지만 유지(주석처럼). 즉, 컴파일 하면 해당 애노테이션 정보는 사라진다는 이야기.

- Class: 컴파인 한 .class 파일에도 유지. 즉, 런타임 시, 클래스를 메모리로 읽어오면 해당 정보는 사라진다.

- Runtime: 클래스를 메모리에 읽어왔을 때까지 유지! 코드에서 이 정보를 바탕으로 특정 로직을 실행할 수 있다. 

  <br>

### @Target

- 해당 애노테이션을 어디에 사용할 수 있는지 결정한다. 

  <br>

### @Documented

- 해당 애노테이션을 사용한 코드의 문서에 그 애노테이션에 대한 정보를 표기할지 결정한다. 

  <br>

### 메타 애노테이션

- https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#beans meta-annotations
- https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/co re/annotation/AliasFor.htm
