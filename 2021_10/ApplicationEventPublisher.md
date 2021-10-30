# ApplicationEventPublisher 

<br>

작성일자 : 2021-10-29

내용 : 백기선님의 스프링 프레임워크 핵심 기술 인프런 강의 

<br><br>

## ApplicationEventPublisher 

#### **이벤트 프로그래밍에 필요한 인터페이스** 제공.  [옵저버 패턴]([Observer pattern - Wikipedia](https://en.wikipedia.org/wiki/Observer_pattern)) 구현체. 

<br>

<br>

## ApplicationContext extends [ApplicationEventPublisher]([ApplicationEventPublisher (Spring Framework 5.3.12 API)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/ApplicationEventPublisher.html)) 

- ApplicationContext 가 이벤트를 발생시킨다!
- publishEvent(ApplicationEvent event) 

<br><br>

## 이벤트 만들기 

● ApplicationEvent 상속

● 스프링 4.2 부터는 이 클래스를 상속받지 않아도 이벤트로 사용할 수 있다. 



MyEvent1.java

- ApplicationEvent 상속

```java
package com.inflearnb.applicationeventpublisher;

import org.springframework.context.ApplicationEvent;

public class MyEvent1 extends ApplicationEvent {

  private int data;

  public MyEvent1(Object source) {
    super(source);
  }
  public MyEvent1(Object source,int data) {
    super(source);
    this.data = data;
  }

  public int getData() {
    return data;
  }
}
```



MyEvent.java

- ApplicationEvent 상속 X

```java
package com.inflearnb.applicationeventpublisher;

public class MyEvent{

  private int data;

  private Object source;
/* 
스프링 프레임워크에서 추구하는 방향 => 비 침투성, 클래스 내에 스프링 패키지가 import된 게 없다.

don't invasive, transparent, POJO기반의 프레임워크 코드를 더 유지보수 하기 쉬어진다. 
*/
  public MyEvent(Object source,int data) {
    this.source = source;
    this.data = data;
  }

  public Object getSource() {
    return source;
  }

  public int getData() {
    return data;
  }
}

```



<br><br>

## 이벤트 발생 시키는 방법 

● ApplicationEventPublisher.publishEvent(); 

<br>

AppRunner.java

```java
package com.inflearnb.applicationeventpublisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements ApplicationRunner {

  @Autowired
  ApplicationEventPublisher publisherEvent;
/* ApplicationContext publisherEvent; -> ApplicationContext 사용가능 but 인터페이스 사용 명확하게 하기위해서 ApplicationEventPublisher 으로 사용해봄
*/

  @Override
  public void run(ApplicationArguments args) throws Exception {
    publisherEvent.publishEvent(new MyEvent(this, 1010));
  }
}
```



<br><br>

## 이벤트 처리하는 방법 

● ApplicationListener<이벤트> 구현한 클래스 만들어서 빈으로 등록하기. 

<br>

<br>

MyEventHandler1.java

```java
package com.inflearnb.applicationeventpublisher;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

//@Component
public class MyEventHandler1 implements ApplicationListener<MyEvent1> {

  @Override
  public void onApplicationEvent(MyEvent1 event) {
    System.out.println("이벤트 받았다.데이터는 " + event.getData());
  }
}

```



<br>

● 스프링 4.2 부터는 [@EventListener](https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/context/event/EventListener.html)를 사용해서 빈의 메소드에 사용할 수 있다. 

● 기본적으로는 synchronized.  (순차적으로 처리)

● 순서를 정하고 싶다면 @Order와 함께 사용.

```jav
@EventListener
@Order(Ordered.HIGHEST_PRECEDENCE)//가장 먼저 실행
```

 

<br>

**@EventListener을 사용**

<br>

MyEventHandler.java

```java
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

//핸들러는 빈으로 등록되야한다 그래야 스프링이 누구한테 전달해야 하는 지 알 수 잇다
//이벤트는 빈이 아니다.
//가급적 깔끔한 방법으로 스프링 코드 들어 있지 않은 이벤트 클래스와 @만 들어있는 핸들러 클래스 추구

@Component
public class MyEventHandler {

  @EventListener
  @Order(Ordered.HIGHEST_PRECEDENCE) //가장 먼저 실행
  public void onApplicationEvent(MyEvent event) {
    System.out.println(Thread.currentThread());
    System.out.println("이벤트 받았다.데이터는 " + event.getData());

  }
}

```

<br>

AnotherHandler.java

```java
package com.inflearnb.applicationeventpublisher;

import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
public class AnotherHandler {

  @EventListener
  @Order(Ordered.HIGHEST_PRECEDENCE + 2) //가장 먼저에서 +2번째 순서
  public void handler(MyEvent myEvent) {
    System.out.println(Thread.currentThread());
    System.out.println("Another" + myEvent.getData());
  }
}

```

<br>

```java
Thread[restartedMain,5,main]
이벤트 받았다.데이터는 1010
Thread[restartedMain,5,main]
Another1010
```

<br>

● 비동기적으로 실행하고 싶다면 @Async와 함께 사용. 

- 각 핸들러에 Order를 빼고 @Async을 추가해준다.

- @EnableAsync 추가해주기

  ```java
  package com.inflearnb.applicationeventpublisher;
  
  import org.springframework.boot.SpringApplication;
  import org.springframework.boot.autoconfigure.SpringBootApplication;
  import org.springframework.scheduling.annotation.EnableAsync;
  
  @SpringBootApplication
  @EnableAsync
  public class ApplicationEventPublisherApplication {
  
    public static void main(String[] args) {
      SpringApplication.run(ApplicationEventPublisherApplication.class, args);
    }
  
  }
  
  ```

  

  <br>

- 각각의 스레드 풀에서 따로 놀고 스레드 스케줄링에 따라 달려있기 때문에 order가 더 이상 의미가 없어진다.

- 제 각 각 별도의 쓰레드에서 돌았다.

- ```java 
  Thread[task-2,5,main]
  Thread[task-1,5,main]
  Another1010
  이벤트 받았다.데이터는 1010
  ```

  

<br><br>

## 스프링이 제공하는 기본 이벤트 

● ContextRefreshedEvent: ApplicationContext를 초기화 했더나 리프래시 했을 때 발생. 

● ContextStartedEvent: ApplicationContext를 start()하여 라이프사이클 빈들이 시작 신호를 받은 시점에 발생. 

● ContextStoppedEvent: ApplicationContext를 stop()하여 라이프사이클 빈들이 정지 신호를 받은 시점에 발생. 

● ContextClosedEvent: ApplicationContext를 close()하여 싱글톤 빈 소멸되는 시점에 발생. 

● RequestHandledEvent: HTTP 요청을 처리했을 때 발생

<br>

ContextRefreshedEvent,ContextClosedEvent 사용

AnotherHandler.java

```java
package com.inflearnb.applicationeventpublisher;

import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class AnotherHandler {

  @EventListener
  @Async
  public void handle(MyEvent myEvent) {
    System.out.println(Thread.currentThread().toString());
    System.out.println("Another" + myEvent.getData());
  }

  @EventListener
  @Async
  public void handle(ContextRefreshedEvent myEvent) {
    System.out.println(Thread.currentThread().toString());
    System.out.println("ContextRefreshedEvent");
  }

  @EventListener
  @Async
  public void handle(ContextClosedEvent myEvent) {
    System.out.println(Thread.currentThread().toString());
    System.out.println("ContextClosedEvent");
  }
}

```

<br>

```java
//실행결과
Thread[task-1,5,main]
Thread[task-2,5,main]
ContextRefreshedEvent
Another1010
Thread[task-3,5,main]
이벤트 받았다.데이터는 1010
Thread[task-4,5,main]
ContextClosedEvent
```

@Async때문에 조금 뒤죽 박죽이지만 

어플리케이션을 시작하고 이벤트가 실행되니까 가장 먼저 ContextRefreshedEvent가 실행됐고

ContextClosedEvent는 실제 어플리케이션을 stop하니까 실행됐다.

<br>
