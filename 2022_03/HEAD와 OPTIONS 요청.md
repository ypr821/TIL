# HEAD와 OPTIONS 요청
처리 <br>
우리가 구현하지 않아도 스프링 웹 MVC에서 자동으로 처리하는 HTTP Method <br>
● HEAD <br>
● OPTIONS <br>
HEAD <br>
● GET 요청과 동일하지만 응답 본문을 받아오지 않고 응답 헤더만 받아온다. <br>
OPTIONS <br>
● 사용할 수 있는 HTTP Method 제공 <br>
● 서버 또는 특정 리소스가 제공하는 기능을 확인할 수 있다. <br>
● 서버는 Allow 응답 헤더에 사용할 수 있는 HTTP Method 목록을 제공해야 한다. <br><br>
참고 <br>
● https://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html <br>
● https://github.com/spring-projects/spring-framework/blob/master/spring-test/src/test/java/ <br>
org/springframework/test/web/servlet/samples/standalone/resultmatchers/HeaderAsserti 
onTests.java <br>
