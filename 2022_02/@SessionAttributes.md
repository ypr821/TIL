# @SessionAttributes 
모델 정보를 HTTP 세션에 저장해주는 애노테이션 <br>
● HttpSession을 직접 사용할 수도 있지만 <br>
● 이 애노테이션에 설정한 이름에 해당하는 모델 정보를 자동으로 세션에
넣어준다. 
● @ModelAttribute는 세션에 있는 데이터도 바인딩한다. <br>
● 여러 화면(또는 요청)에서 사용해야 하는 객체를 공유할 때 사용한다. <br>
SessionStatus를 사용해서 세션 처리 완료를 알려줄 수 있다. <br>
● 폼 처리 끝나고 세션을 비울 때 사용한다. <br>


# @SessionAttribute
HTTP 세션에 들어있는 값 참조할 때 사용
● HttpSession을 사용할 때 비해 타입 컨버전을 자동으로 지원하기 때문에 조금
편리함. ● HTTP 세션에 데이터를 넣고 빼고 싶은 경우에는 HttpSession을
사용할 것.
@SessionAttributes와는 다르다.
● @SessionAttributes는 해당 컨트롤러 내에서만 동작.
○ 즉, 해당 컨트롤러 안에서 다루는 특정 모델 객체를 세션에 넣고 공유할 때 사용. ●
@SessionAttribute는 컨트롤러 밖(인터셉터 또는 필터 등)에서 만들어 준 세션 데이터에
접근할 때 사용한다
