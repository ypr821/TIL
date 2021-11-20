# HttpSession을 단위 테스트하기 위한 방법

작성일자 : 2021-11-20

내용 : 로그인 기능 구현을 위한 테스트에 필요 !! Mock


<br><br>


로그인 기능을 구현하기위해 단위테스트를 하려다보니 

HttpSession 객체를 활용한 테스트가 필요했다.

방법을 모색해보니 바로 !! **Mock**

 
<br>
 

## Mock이란

실제 객체를 만들어 사용하기에 시간, 비용 등의 Cost가 높거나 혹은 객체 서로간의 의존성이 강해 구현하기 힘들 경우 가짜 객체를 만들어 사용하는 방법이다.


<br>



내 경우 MockHttpSession 을 사용했다.

MockHttpSession 을 사용한 테스트 코드

```java
package com.flab.doorrush.domain.user;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.flab.doorrush.domain.user.api.UserController;
import com.flab.doorrush.domain.user.dao.UserMapper;
import com.flab.doorrush.domain.user.dto.UserDto;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;

@SpringBootTest
public class LoginTest {


  @Autowired
  UserController userController;

  protected MockHttpSession session;


  @Before
  //@Test가 붙은 테스트 메서드가 실행되기 전에 먼저 @Before가 붙은 메서드가 실행되도록 하는 어노테이션
  public void setUp(){
    session = new MockHttpSession();
    session.setAttribute("login","yes");
  }
  @After
  //@Test가 붙은 테스트 메서드가 실행된 후에 @After가 붙은 메서드가 실행되도록 하는 어노테이션
  public void clean(){
    session.clearAttributes();
  }

  @Test
  public void UserControllerTest(){
    userController.login("testID","testPW",session);
    if(session!=null){
      assertThat("yes",is(session.getAttribute("login")));

    }
  }
}
```
<br><br>
**@Test 메서드 실행 전후로 세션을 생성하고 사용 완료한 세션을 삭제하는 과정이 필요하다.**

 
<br>

Mock에 관해서 더 파볼 필요가 있다!

 

 
<br><br><br>
 

참고 블로그

https://shinsunyoung.tistory.com/70
https://www.crocus.co.kr/1555 [Crocus]
