# JWT

작성일자 2022-01-24



## JWT(JSON Web Token)란

- JWT(Json Web Token)란 Json 포맷을 이용하여 사용자에 대한 속성을 저장하는 Web Token이다. 
- JWT는 토큰 자체를 정보로 사용하는 Self-Contained 방식으로 정보를 안전하게 전달한다.

- 서명된 토큰은 그 안에 포함된 클레임의 무결성을 확인할 수 있고 암호화된 토큰은 이러한 클레임을 외부에 숨길 수 있다. 공개/개인 키 쌍을 사용하여 토큰에 서명할 때 서명은 개인 키를 보유하고 있는 당사자만 서명했음을 보장한다.

<br><br>



## JWT 의 구조 

xxxxx.yyyyy.zzzzz

'.'으로 구분하여 Header, Payload, Signature 세부분으로 구성된다. Json 형태인 각 부분은 Base64로 인코딩 되어 표현된다. (참고 Base64는 암호화된 문자열이 아니고, 같은 문자열에 대해 항상 같은 인코딩 문자열을 반환한다.)

<br>

- **Header** - 헤더
  JWT 토큰 type과 사용하는 Signature를 해싱하기 위한 algorithm을 지정하는 두 부분으로 구성된다.

  예를 들면
  { 
  	"alg": "HS256",  "typ": "JWT" 
  }
  그런 다음 이 JSON은 JWT의 첫 번째 부분을 형성하도록 인코딩된 **Base64Url**이다.

  

- **Payload** - 전송되는 데이터를 의미, 담고자하는 데이터가 담긴다. 토큰에서 사용할 정보의 조각들인 **클레임(Claim)**이 담겨 있다. 
  <br>

  **등록된 클레임(Registered Claim)**

  등록된 클레임은 토큰 정보를 표현하기 위해 이미 정해진 종류의 데이터들로, 모두 선택적으로 작성이 가능하며 사용할 것을 권장한다. 또한 JWT를 간결하게 하기 위해 key는 모두 길이 3의 String이다. 여기서 subject로는 unique한 값을 사용하는데, 사용자 이메일을 주로 사용한다.

  - iss: 토큰 발급자(issuer)
  - sub: 토큰 제목(subject)
  - aud: 토큰 대상자(audience)
  - exp: 토큰 만료 시간(expiration), NumericDate 형식으로 되어 있어야 함 ex) 1480849147370
  - nbf: 토큰 활성 날짜(not before), 이 날이 지나기 전의 토큰은 활성화되지 않음
  - iat: 토큰 발급 시간(issued at), 토큰 발급 이후의 경과 시간을 알 수 있음
  - jti: JWT 토큰 식별자(JWT ID), 중복 방지를 위해 사용하며, 일회용 토큰(Access Token) 등에 사용

   <br>

  **공개 클레임(Public Claim)**

  공개 클레임은 사용자 정의 클레임으로, 공개용 정보를 위해 사용된다. 충돌 방지를 위해 URI 포맷을 이용하며, 예시는 아래와 같다.

  ```
  { 
      "https://mangkyu.tistory.com": true
  }
  ```

   <br>

  **비공개 클레임(Private Claim)**

  비공개 클레임은 사용자 정의 클레임으로, 서버와 클라이언트 사이에 임의로 지정한 정보를 저장한다. 아래의 예시와 같다.

  ```
  { 
      "token_type": access 
  }
  ```

  

  <br>

  

- **Signature** - 서명
  서명(Signature)은 BASE64로 인코딩된 헤더(Header)와 페이로드(Payload)의 값을 비밀 키를 이용해 헤더(Header)에서 정의한 알고리즘으로 해싱을 하고, 이 값을 다시 BASE64로 인코딩하여 생성한다.

  

<br>


<img width="400" alt="JWT" src="https://user-images.githubusercontent.com/56250078/150803022-14dfabba-b054-40be-8a45-7d17fc11787d.png">

<br><br>

## JWT의 작동 방식



사용자가 사용자정보를 사용하여 성공적으로 로그인하면 JSON 웹 토큰이 생성되고 사용자에게 HTTP 통신을 통해 전달된다.

생성된 토큰은 HTTP 통신을 할 때 Authorization이라는 key의 value로 사용된다. 일반적으로 value에는 Bearer이 앞에 붙여진다.

```
Authorization: Bearer <token>
```



 토큰은 자격 증명이므로 보안 문제를 방지하기 위해 세심한 주의를 기울여야 한다. 일반적으로 토큰을 필요 이상으로 오래 보관해서는 안 된다.





<br><br><br>

## 장점

1. Header와 Payload를 가지고 Signature를 생성하므로 데이터 위변조를 막을 수 있습니다.
2. 인증 정보에 대한 별도의 저장소가 필요없습니다.
3. JWT는 토큰에 대한 기본 정보와 전달할 정보 및 토큰이 검증됬음을 증명하는 서명 등 필요한 모든 정보를 자체적으로 지니고 있습니다.
4. 클라이언트 인증 정보를 저장하는 세션과 다르게, 서버는 무상태가 됩니다.
5. 확장성이 우수합니다.
6. 토큰 기반으로 다른 로그인 시스템에 접근 및 권한 공유가 가능합니다.
7. OAuth의 경우 Facebook, Google 등 소셜 계정을 이용하여 다른 웹서비스에서도 로그인을 할 수 있습니다.
8. 모바일 어플리케이션 환경에서도 잘 동작합니다.

<br>

## 단점

1. 쿠키/세션과 다르게 JWT는 토큰의 길이가 길어, 인증 요청이 많아질수록 네트워크 부하가 심해집니다.
2. Payload 자체는 암호화되지 않기 때문에 유저의 중요한 정보는 담을 수 없습니다.
3. 토큰을 탈취당하면 대처하기 어렵습니다.
4. 토큰은 한 번 발급되면 유효기간이 만료될 때 까지 계속 사용이 가능하기 때문입니다.
5. 특정 사용자의 접속을 강제로 만료하기 어렵지만, 쿠키/세션 기반 인증은 서버 쪽에서 쉽게 세션을 삭제할 수 있습니다.

<br>

<br>



## JWT 사용 보안 전략



### 1. 짧은 만료 기한 설정

토큰의 만료 시간을 짧게 설정하는 방법을 고려할 수 있습니다. 토큰이 탈취되더라도 빠르게 만료되기 때문에 피해를 최소화할 수 있습니다. 그러나 사용자가 자주 로그인해야 하는 불편함이 수반됩니다.

### 2. Sliding Session

글을 작성하는 도중 토큰이 만료가 된다면 Form Submit 요청을 보낼 때 작업이 정상적으로 처리되지 않고, 이전에 작성한 글이 날아가는 등의 불편함이 존재합니다. Sliding Session은 서비스를 지속적으로 이용하는 클라이언트에게 자동으로 토큰 만료 기한을 늘려주는 방법입니다. 글 작성 혹은 결제 등을 시작할 때 새로운 토큰을 발급해줄 수 있습니다. 이를 통해 사용자는 로그인을 자주 할 필요가 없어집니다.

### 3. Refresh Token

클라이언트가 로그인 요청을 보내면 서버는 Access Token 및 그보다 긴 만료 기간을 가진 Refresh Token을 발급하는 전략입니다. 클라이언트는 Access Token이 만료되었을 때 Refresh Token을 사용하여 Access Token의 재발급을 요청합니다. 서버는 DB에 저장된 Refresh Token과 비교하여 유효한 경우 새로운 Access Token을 발급하고, 만료된 경우 사용자에게 로그인을 요구합니다.

해당 전략을 사용하면 Access Token의 만료 기한을 짧게 설정할 수 있으며, 사용자가 자주 로그인할 필요가 없습니다. 또한 서버가 강제로 Refresh Token을 만료시킬 수 있습니다.

그러나 검증을 위해 서버는 Refresh Token을 별도의 storage에 저장해야 합니다. 이는 추가적인 I/O 작업이 발생함을 의미하기 때문에 JWT의 장점(I/O 작업이 필요 없는 빠른 인증 처리)을 완벽하게 누릴 수 없습니다. 클라이언트도 탈취 방지를 위해 Refresh Token을 보안이 유지되는 공간에 저장해야 합니다.<br><br>

<br><br><br>

참고자료

https://jwt.io/introduction <br>
https://mangkyu.tistory.com/56 [MangKyu's Diary]xxxxx.yyyyy.zzzzz <br>
https://tecoble.techcourse.co.kr/post/2021-05-22-cookie-session-jwt/ <br><br>
