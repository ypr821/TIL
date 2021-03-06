# 인증(Authentication)과 인가(Authorization)

작성일자 : 2022-01-15, 내용 추가 2022-01-24
<br><br>

<img width="483" alt="인증과 인가" src="https://user-images.githubusercontent.com/56250078/149669922-74d5300c-5b60-4eac-833d-411ea1e12ea7.png">

https://dev.to/caffiendkitten/authentication-vs-authorization-25lc

<br> <br>

# 인증(Authentication)

- 로그인 : 특정 권한을 아이디와 패스워드로 확인한다.

- 예) 건물에 출입이 가능한 사람인지 확인 => 출입증 여부로 출입 가능 여부 판단 <br>
  	출입 가능      : 출입증이 있는 방문자, 직원, 관리자 <br>
    			출입 불가능  : 출입증이 없는 사람 <br>

<br>

- Client와 Server는 HTTP를 통해 요청과 응답을 하고 HTTP는 무상태성(Stateless)의 특성을 가지고 있다.
  HTTP는 요청에 대한 응답을 처리하게 되면 연결을 끊어 버린다. 따라서 클라이언트에 대한 이전의 상태 정보 및 현재 통신의 상태가 남아있지 않는다.



 <br>

<br>

# 인가(Authorization)

- 한번 인증을 받은 사용자가 이후 여러 기능을 사용할 수 있도록 허가 해준다. <br>
  (= 인증된 사용자에 대한 자원 접근 권한 확인 및 허가, 로그인 유지)
- 예) 접근 할 수 있는 건물의 공간은 어디까지 인가? <br> 
  	건물 관리자 인가?   : 모든 공간에 출입 가능 <br>
    			방문자 인가?            : 허용된 일부 공간만 접근 가능 <br>
    			직원 인가?                : 직원이 속한 소속 팀 층에 접근 가능  <br>

 <br><br>

# 인증 방식

1. 인증하기 - Request Header (기본적으로 URL에 아이디와 비밀번호를 입력하여 보내는 방식이다.)
2. 인증 유지하기 - Browser (로컬 스토리지, 세션 스토리지, 쿠키 등)
3. 안전하게 인증하기 - Server
4. 다른 채널을 통해 인증하기 - OAuth

 <br><br>

# 인증 방식 비교 (Cookie & Session vs JWT)

## **Cookie**

- Cookie 는 Key-Value 형식의 문자열이다.

- 클라이언트가 웹 사이트에 방문할 경우, 해당 웹 사이트의 서버는 응답 헤더의 `Set-Cookie`에 담아서 클라이언트 브라우저로 Cookie 를 보낼 수 있다. 그러면 클라이언트의 브라우저는 쿠키 값을 저장한다.

- 저장된 쿠키값을 서버에 요청을 보낼때 마다 담아서 보내기때문에 서버는 쿠키값을 통해 사용자를 식별하여 인증할 수 있다.

- 단점 

  - 보안에 취약하다. 쿠키값이 그대로 보이기때문에 유출될 위험이 크다.
  - 쿠키는 용량 제한이 있어 많은 정보를 담을 수 없다.
  - 브라우저간 공유가 불가능 하다.
  - 쿠키의 사이즈가 커질수록 네트워크에 부하가 심해진다.

  <br> 

- 과정 설명

  1. 아이디 비밀번호를 입력하여 로그인 요청을 보냈다

  2. 로그인 처리를 하고 서버는 응답으로 쿠키를 담아서 보낸다.

  <img width="500" alt="쿠키1" src="https://user-images.githubusercontent.com/56250078/150745117-152320b5-0be7-4974-97da-18c580542ad1.png">

   <br>

  3. 클라이언트 브라우저는 해당 쿠키값을 저장해서 요청을 보낼때마다 userName과 password쿠키를 보냄으로써 인증받도록 할 수 있다.

  <img width="500" alt="쿠키2" src="https://user-images.githubusercontent.com/56250078/150745139-b444e6f5-d881-4591-82e5-3dd9c0c2a1cc.png">

  

 <br> <br>

## **Cookie & Session**

- 세션은 서버측에 클라이언트의 인증 정보를 저장하고 관리할 수 있다. 클라이언트측에 저장할때보다 훨씬 안정적이다.

<img width="500" alt="쿠키3" src="https://user-images.githubusercontent.com/56250078/150745154-fbff5055-f1f5-47d0-9ff6-70afc581ad99.png">

<br>


- 처리과정
  1. 클라이언트 측에서 아이디와 비밀번호를 입력하면 서버는 사용자를 식별하고 로그인 성공시 JSESSIONID라는 쿠키를 구워서 전달한다.
  2. 클라이언트 브라우저는  JSESSIONID라는 쿠키를 저장하고 요청시 쿠키를 담아서 보낸다.
  3. 서버는 직접적으로 클라이언트 인증 정보를 받아서 로그인을 처리하는게 아닌 JSESSIONID쿠키 값의 유효성을 판별해 클라이언트를 식별한다.
     <br>
- 장점

  - 클라이언트 인증 정보가 외부에 노출되지 않는다.
  - 세션의 만료기한을 설정하여 탈취되었을 경우 만료이후에 위험성을 줄일 수 있다.
    <br>
- 단점 

  - 해커가 중간에 클라이언트인척 JSESSIONID 쿠키를 사용해 위장할 수 있다.
  - 세션의  특성상 서버 하나 하나에서 세션을 따로 관리하기 때문에 서버가 여러 개일 경우 로그인 상태를 유지하는데 어려움이 있다.  <br>

<br>

>  ### 서버 간 세션 불일치 해결 방법
>
>   - **Sticky Session**을 사용해 처음 작업 요청과 응답이 이뤄진 서버에서 계속 해당 클라이언트의 작업을 담당한다.
>
>   - WAS에서 지원하는 **Session Clustering**은 서버에 저장된 session의 정보를 다른 서버에 전파하는 방식을 사용한다.
>
>   - **별도의 세션 저장소**를 두는 방법은 독립된 세션 저장소(서버)를 구성하여 세션 정보를 저장하고 여러 서버들이 독립된 세션 저장소에서 세션 정보를 읽어오는 방식이다.    


 <br> <br>

## **JWT(JSON Web Token)**

- 인증에 필요한 정보를 암호화시킨 토큰을 의미한다.
- [JWT을 공부한 TIL ](https://github.com/ypr821/TIL/blob/main/2022_01/JWT(JSON%20Web%20Token).md) 에 자세하게 정리해둠.
