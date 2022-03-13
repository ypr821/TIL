# @ExceptionHandler

특정 예외가 발생한 요청을 처리하는 핸들러 정의 <br>
- 예외 처리 핸들러 <br>
● 지원하는 메소드 아규먼트 (해당 예외 객체, 핸들러 객체, ...) <br>
● 지원하는 리턴 값 <br>
● REST API의 경우 응답 본문에 에러에 대한 정보를 담아주고, 상태 코드를
설정하려면 ResponseEntity를 주로 사용한다. <br>

<br> <br> 
참고
● https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-ann
-exceptionhandler
