# Java 11

- HTTP Client API를 표준화(Standard) 했습니다.
  jdk 9에서 추가되고 jdk 10에서 업데이트된 java.incubator.http 패키지가 인큐베이터에서 나와 java.net.http패키지로 표준화 했습니다. 구 버전의 HttpUrlConnection을 대체합니다.
  - Non-Blocking request and response 지원합니다. (with CompletableFuture)
  - Backpressure 지원(java.util.concurrent.Flow 패키지를 통해 RX Flow를 구현체에 적용)
  - HTTP/2 지원
  - Factory method 형태로 지원
  - websocket 지원

- Flight Recorder
  JFR은 실행 중인 Java 애플리케이션의 진단 및 프로파일링 데이터를 수집하는 도구다. 주로 실행 중인 Thread, GC Cycles, Locks, Sockets, 메모리 사용량 등에 대한 데이터를 수집합니다.

- core-libs/java.util:collections 새로운 Collection.toArray(IntFunction)
  기본 메소드를 사용하면 컬렉션의 요소를 원하는 런타임 유형의 새로 생성된 배열로 전송할 수 있습니다.
  새 메서드는 `toArray(T[])`배열 인스턴스를 인수로 사용하는 기존 메서드 의 오버로드입니다.

- ZGC: A Scalable Low-Latency Garbage Collector (Experimental) 새로운 GC를 추가했습니다.
  JVM의 GC가 동작할 때 가장 큰 부담은 애플리케이션이 멈추는 Stop-The-World 현상입니다.
  ZGC는 Load barrier와 Colored object pointer를 함께 사용함으로써 이에 대한 부담을 줄여줍니다.

  GC에 관한 공부내용  : https://github.com/ypr821/TIL/blob/main/2021_10/GC(Garbage_Collection).md

- Nest 기반 액세스 제어할 수 있습니다.
  Java SE 11에서 Java Virtual Machine은 클래스 및 인터페이스를 *nest* 라고 하는 새로운 액세스 제어 컨텍스트로 배열하는 것을 지원합니다.
  Java 10 이하의 버전에서는 Nest1과 Nest2가 서로의 private value에 접근할 수 없었습니다. 접근하려면 setAccessible(true)를 호출해야만 했는데,
  Java 11부터는 setAccessible을 하지 않아도 접근할 수 있도록 변경됐습니다.


  ```java
  public class Outer {
      public void outerPublic() {
      }
  
      private void outerPrivate() {
      }
  
      class Inner {
          public void innerPublic() {
              outerPrivate();
          }
      }
  }
  //Inner 클래스에서 outerPrivate() 메서드를 사용할 수 있습니다.
  ```






- 키 암호화 , 전송계층 보완 속성이 추가됐습니다.
  (java11 관련 보안 내용추가 필요)

- Lambda 매개변수에 대한 지역변수 구문에 `var`type으로 매개변수를 선언할 수 있습니다.

- Launch Single-File Source-Code Programs
  Single file 프로그램의 Main 메서드 실행 시 javac를 하지 않고도 바로 실행할 수 있도록 편의성이 향상되었습니다. (편집됨) 
