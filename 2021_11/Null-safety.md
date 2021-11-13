

# Null-safety 

<br>

작성일자 : 2021-11-12

내용 : 백기선님의 스프링 프레임워크 핵심 기술 인프런 강의


<br><br>


## 스프링 프레임워크 5에 추가된 Null 관련 애노테이션

- @NonNull
- @Nullable
- @NonNullApi (패키지 레벨 설정) - 어노테이션이 추가된 해당 패키지 이하 파라미터 리턴은 다 널값 허용 X
- @NonNullFields (패키지 레벨 설정) 
<br><br>


## 목적

- (툴의 지원을 받아) 컴파일 시점에 최대한 NullPointerException을 방지하는 것




<br><br>


```java
package com.inflearnb.inflearnb1112;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class EventService {

  @NonNull
  public String createEevent(@NonNull String name){
    return "hello" + name;
  }
}


package com.inflearnb.inflearnb1112;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements ApplicationRunner {

  @Autowired
  EventService eventService;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    eventService.createEevent("yooooo");
  }
}

```
<br><br>

파라미터랑 리턴값을 null로 바꾸면!!!
<br>


intellij 스프링 null 어노테이션 설정하기

<img width="744" alt="20211112_215049" src="https://user-images.githubusercontent.com/56250078/141613929-988c4e99-8378-48db-bd16-dbddd5bca84b.png">

<img width="441" alt="20211112_215228" src="https://user-images.githubusercontent.com/56250078/141613933-9393b11d-6baf-4d03-a00a-295942b17c9c.png">

<br>

전에는 안떴다. 위 과정의 설정을 하고 나니 아래와 같이 @NotNull 관한 메시지가 뜬다!!

<img width="607" alt="20211112_215252" src="https://user-images.githubusercontent.com/56250078/141613935-ff094af3-4d03-4423-bf89-dcf9a9a250a5.png">

<img width="387" alt="20211112_215415" src="https://user-images.githubusercontent.com/56250078/141613937-9b546c8b-826f-4216-aa78-708e3f596765.png">

