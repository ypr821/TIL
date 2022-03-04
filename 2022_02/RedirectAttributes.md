# RedirectAttributes
리다이렉트 할 때 기본적으로 Model에 들어있는 primitive type 데이터는 URI 쿼리 매개변수에
추가된다. <br>
● 스프링 부트에서는 이 기능이 기본적으로 비활성화 되어 있다. <br>
● Ignore-default-model-on-redirect 프로퍼티를 사용해서 활성화 할 수 있다. <br>
원하는 값만 리다이렉트 할 때 전달하고 싶다면 RedirectAttributes에 명시적으로 추가할 수 있다. <br>
리다이렉트 요청을 처리하는 곳에서 쿼리 매개변수를 @RequestParam 또는 @ModelAttribute로
받을 수 있다 <br>
