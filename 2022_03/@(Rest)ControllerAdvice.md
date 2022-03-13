# @(Rest)ControllerAdvice
- 전역 컨트롤러 <br>
예외 처리, 바인딩 설정, 모델 객체를 모든 컨트롤러 전반에 걸쳐 적용하고 싶은 경우에 사용한다. <br>
● @ExceptionHandler <br>
● @InitBinder <br>
● @ModelAttributes <br>
적용할 범위를 지정할 수도 있다. <br>
● 특정 애노테이션을 가지고 있는 컨트롤러에만 적용하기 <br>
● 특정 패키지 이하의 컨트롤러에만 적용하기 <br>
● 특정 클래스 타입에만 적용하기 <br>
<br>
참고 <br>
● https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-ann
-controller-advice
