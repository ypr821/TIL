# 빈의 스코프 
작성일자 : 2021-10-25 
<br>
내용 : 백기선님의 스프링 프레임워크 핵심 기술 인프런 강의 정리 (예시 코드 및 사진 출처) 

<br>

## 스코프 
스프링이 관리하는 오브젝트, 즉 빈이 생성되고, 존재하고, 적용되는 범위 
출처 : 토비의 스프링3.1 vol.1 서적

### - **싱글톤** 
- Spring 프레임워크에서 기본이 되는 스코프
- 스프링 컨테이너의 시작과 종료까지 1개의 객체로 유지됨

출처: https://mangkyu.tistory.com/117 [MangKyu's Diary]

<br>

프록시 상속 전 Proto = 현재는 싱글톤

<br>

Single.java

```java
package com.inflearnb.spring512;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Single {

	@Autowired
	Proto proto;
    
	public Proto getProto() {
    return proto;
	}
}
```

<br>

Proto.java

```java
@Component
public class Proto {
}
```

<br>

```java
package com.inflearnb.spring512;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements ApplicationRunner {

@Autowired
  Single single;

@Autowired
  Proto proto;

  @Override
  public void run(ApplicationArguments args) throws Exception {
// AppRunner에 받아온 proto
	System.out.println(proto);
// single이 참조하고 있는 proto
    System.out.println(single.getProto()); 
  }
}

```

<br>

```java
//실행결과 
com.inflearnb.spring512.Proto@240447bb
com.inflearnb.spring512.Proto@240447bb

```

<br>

### - **프로토타입**
- 프로토타입 빈의 생성과 의존관계 주입까지만 관여하고 더는 관리하지 않는 스코프
- 요청이 오면 항상 새로운 인스턴스를 생성하여 반환하고 이후에 관리하지 않음
- 프로토타입을 받은 클라이언트가 객체를 관리해야 한다.
<br>
- Request :각각의 요청이 들어오고 나갈때가지 유지되는 스코프
- Session : 세션이 생성되고 종료될 때 까지 유지되는 스코프
- WebSocket 
- application: 웹의 서블릿 컨텍스트와 같은 범위로 유지되는 스코프
- ...

출처: https://mangkyu.tistory.com/117 [MangKyu's Diary]

- ...

  #### 프로토 타입으로 설정해보자.

  Proto.java

  - @Component @Scope("prototype") 설정 추가

  ```java
  package com.inflearnb.spring512;
  
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.context.annotation.Scope;
  import org.springframework.context.annotation.ScopedProxyMode;
  import org.springframework.stereotype.Component;
  
  //빈을 받아 올때마다 새로운 객체를 생성한다.
  @Component @Scope("prototype") 
  public class Proto {
  }
  ```

  <br>

  AppRunner.java 

  ```java
  package com.inflearnb.spring512;
  
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.boot.ApplicationArguments;
  import org.springframework.boot.ApplicationRunner;
  import org.springframework.context.ApplicationContext;
  import org.springframework.stereotype.Component;
  
  @Component
  public class AppRunner implements ApplicationRunner {
  
    @Autowired
    Single single;
  
    @Autowired
    Proto proto;
  
    @Autowired
    ApplicationContext ctx;
  
    @Override
    public void run(ApplicationArguments args) throws Exception {
      System.out.println("proto");
      System.out.println(ctx.getBean(Proto.class));
      System.out.println(ctx.getBean(Proto.class));
      System.out.println(ctx.getBean(Proto.class));
  
      System.out.println("single");
      System.out.println(ctx.getBean(Single.class));
      System.out.println(ctx.getBean(Single.class));
      System.out.println(ctx.getBean(Single.class));
    }
  }
  ```

  <br>

  ```java
  //실행결과
  proto
  com.inflearnb.spring512.Proto@11f272bc
  com.inflearnb.spring512.Proto@22c2e7f1
  com.inflearnb.spring512.Proto@73d3a36f
  single
  com.inflearnb.spring512.Single@240447bb
  com.inflearnb.spring512.Single@240447bb
  com.inflearnb.spring512.Single@240447bb
  ```

  <br> 

## 프로토타입 빈이 싱글톤 빈을 참조하면?

  - 아무 문제 없다.

  - ```java
    //프로토타입은 매번꺼낼때 마다 새로운 인스턴스를 생성하지만 참조된 싱글톤 타입의 빈은 항상 동일 
    package com.inflearnb.spring512;
    
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.context.annotation.Scope;
    import org.springframework.stereotype.Component;
    
    @Component @Scope("prototype") //빈을 받아 올때마다 새로운 객체를 생성한다.
    public class Proto {
      
      @Autowired
      Single single;
    }
    
    ```

    <br>

## 싱글톤 빈이 프로토타입 빈을 참조하면?

- #### 프로토타입 빈이 업데이트가 안된다.

  <br>

  AppRunner.java

  ```java
  package com.inflearnb.spring512;
  
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.boot.ApplicationArguments;
  import org.springframework.boot.ApplicationRunner;
  import org.springframework.context.ApplicationContext;
  import org.springframework.stereotype.Component;
  
  @Component
  public class AppRunner implements ApplicationRunner {
  
    @Autowired
    Single single;
  
    @Autowired
    Proto proto;
  
    @Autowired
    ApplicationContext ctx;
  
    @Override
    public void run(ApplicationArguments args) throws Exception {
      System.out.println("proto");
  
      System.out.println(ctx.getBean(Proto.class));
      System.out.println(ctx.getBean(Proto.class));
      System.out.println(ctx.getBean(Proto.class));
  
      System.out.println("single");
  
      System.out.println(ctx.getBean(Single.class));
      System.out.println(ctx.getBean(Single.class));
      System.out.println(ctx.getBean(Single.class));
  
      System.out.println("proto by single");
  
      System.out.println(ctx.getBean(Single.class).getProto());
      System.out.println(ctx.getBean(Single.class).getProto());
      System.out.println(ctx.getBean(Single.class).getProto());
    }
  }
  
  ```

  <br>

  ```java
  //실행결과
  proto
  com.inflearnb.spring512.Proto@8fcf431
  com.inflearnb.spring512.Proto@64e45c75
  com.inflearnb.spring512.Proto@4298021d
  single
  com.inflearnb.spring512.Single@23307adb
  com.inflearnb.spring512.Single@23307adb
  com.inflearnb.spring512.Single@23307adb
  proto by single
  com.inflearnb.spring512.Proto@4c024ff0
  com.inflearnb.spring512.Proto@4c024ff0
  com.inflearnb.spring512.Proto@4c024ff0
  ```

  

  <br>

- #### 업데이트 할 수 있는 방법

  - **scoped-proxy** 

    Proto.java

    - @Component @Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS) 추가

    ```java
    package com.inflearnb.spring512;
    
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.context.annotation.Scope;
    import org.springframework.context.annotation.ScopedProxyMode;
    import org.springframework.stereotype.Component;
    
    @Component @Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
    //클래스기반의 프록시로 감싸라 : 직접참조가 아닌 프록시를 거치도록 한다.
    //TARGET_CLASS 가 아닌 INTERFACES 였다면 interface를 쓴다 jdk에 있는 interface기반의 프록시를 사용했을 것이다.
    //proxy도 proto를 상속받아서 만들었기때문에 타입은 같다.
    
    public class Proto {
        
      @Autowired
      Single single;
    }
    
    
    ```

    <br>

    ```java
    //실행결과
    proto
    com.inflearnb.spring512.Proto@3c39a97e
    com.inflearnb.spring512.Proto@33ff4b16
    com.inflearnb.spring512.Proto@2ef02c92
    single
    com.inflearnb.spring512.Single@30056b3b
    com.inflearnb.spring512.Single@30056b3b
    com.inflearnb.spring512.Single@30056b3b
    proto by single
    com.inflearnb.spring512.Proto@4106461c
    com.inflearnb.spring512.Proto@8e812d1
    com.inflearnb.spring512.Proto@27d33be2
    ```

    <br>

  - **Object-Provider** 

    - Proto 클래스에 했던 @Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS) 을 지우고 @Component @Scope(value = "prototype")만 설정한다.

      Single.java

      ```
      package com.inflearnb.spring512;
      
      import org.springframework.beans.factory.ObjectProvider;
      import org.springframework.beans.factory.annotation.Autowired;
      import org.springframework.stereotype.Component;
      
      @Component
      public class Single {
      
        @Autowired
        private ObjectProvider<Proto> proto;
      
        public Proto getProto() {
          return proto.getIfAvailable();
        }
      }
      ```

  - Provider (표준) 

<br><br>

프록시 (https://en.wikipedia.org/wiki/Proxy_pattern) 

<img width="188" alt="스프링 프록시와 프로토타입" src="https://user-images.githubusercontent.com/56250078/138672618-6c8bb26b-dda9-465d-b713-6ee28ef73bd0.png">

<br><br>

## 싱글톤 객체 사용시 주의할 점

- 프로퍼티가 공유.
- 멀티스레드 환경에서 Thread-safe 보장 X
- ApplicationContext 초기 구동시 인스턴스 생성

<br><br>


####  주요 내용 정리

- 스프링에서 빈은 default가 싱글톤이다.

- 프로토 타입으로 설정할 수 있다.
  - 설정 방법은 @Scope(value = "prototype") 어노테이션 사용하기

- 싱글톤인 빈 내에 프로토 타입을 참조하고 있으면 참조된 빈은 기본적으로 한번 생성된 빈을 사용하고 새롭게 생성되지 않는다

- 싱글톤 빈 내에 프로토타입의 빈을 가져올 때 마다 새롭게 빈을 생성하는 방법
  - @Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS) 추가
  - ObjectProvider사용하기
<br>
#### 느낀점
- Proxy가 프로토타입의 클래스를 상속받아서 Single클래스가 Proxy를 거쳐갈때 타입에 문제없도록 한 게  신기하다. 빈에대해 더 깊게 알 수 있었다.
