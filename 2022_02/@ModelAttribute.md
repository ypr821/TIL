# @ModelAttribute
● 여러 곳에 있는 단순 타입 데이터를 복합 타입 객체로 받아오거나 해당 객체를 새로 만들 때
사용할 수 있다.
● 여러 곳? URI 패스, 요청 매개변수, 세션 등
● 생략 가능
값을 바인딩 할 수 없는 경우에는?
● BindException 발생 400 에러
바인딩 에러를 직접 다루고 싶은 경우
● BindingResult 타입의 아규먼트를 바로 오른쪽에 추가한다.
바인딩 이후에 검증 작업을 추가로 하고 싶은 경우
● @Valid 또는 @Validated 애노테이션을 사용한다.
참고
● https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-ann
-modelattrib-method-args
