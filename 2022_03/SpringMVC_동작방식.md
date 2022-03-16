# Spring MVC 동작방식

요청 => Dispather Servlet => Handler Mapping => 요청에 맞는 controller 와 method 정보 파악 => Dispather Servlet가 Handler Adapter에게 실행 위임 
=> Controller의 Method 실행 => Controller는 뷰에 전달할 객체 정보를 Model에 저장 => Dispather Servlet이 View name 을 View Resolver에 전달하여 View 객체를 얻는다 
=> 해당 View 객체에 화면 표시 의뢰 + Model 에서 정보 가져와서 화면 표시 처리
