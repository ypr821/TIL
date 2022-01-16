# 인증(Authentication)과 인가(Authorization)

작성일자 : 2022-01-15
<br><br>

<img width="483" alt="인증과 인가" src="https://user-images.githubusercontent.com/56250078/149669922-74d5300c-5b60-4eac-833d-411ea1e12ea7.png">

https://dev.to/caffiendkitten/authentication-vs-authorization-25lc

<br>

## 인증(Authentication)

- 로그인 : 특정 권한을 아이디와 패스워드로 확인한다.

- 예) 건물에 출입이 가능한 사람인지 확인 => 출입증 여부로 출입 가능 여부 판단 <br>
  			출입 가능      : 출입증이 있는 방문자, 직원, 관리자 <br>
  			출입 불가능  : 출입증이 없는 사람 <br>


<br><br>

## 인가(Authorization)

- 한번 인증을 받은 사용자가 이후 여러 기능을 사용할 수 있도록 허가 해준다. <br>
  (= 인증된 사용자에 대한 자원 접근 권한 확인 및 허가, 로그인 유지)

- 예) 접근 할 수 있는 건물의 공간은 어디까지 인가? <br> 
  			건물 관리자 인가?   : 모든 공간에 출입 가능 <br>
  			방문자 인가?            : 허용된 일부 공간만 접근 가능 <br>
  			직원 인가?                : 직원이 속한 소속 팀 층에 접근 가능  <br>







