# GC (Garbage Collection )

<br>

## GC 란?

<br>

- JVM의 Heap 영역에서 사용하지 않는 객체를 삭제하는 프로세스를 말한다.
- Reachable
  참조 되고 있는 객체
- Unreachable
  참조 되지 않는 객체
  GC의 수거 대상
- GC Roots 
  stack 영역의 데이터들
  method 영역의 static 데이터들
  JNI에 의해 생성된 객체들 
  <br><br>

## GC 작동원리

<br>

### 1. Mark and Sweep , Compact

- Mark 
  GC는 GC Root로 부터 모든 변수를 스캔하면서 각각 어떤 객체를 참조하고 있는지 찾아서 마킹한다.
  Reachable 한 객체와 Unreachable한 객체를 구분하는 과정
- Sweep
  Unreachable 객체들을 Heap에서 제거한다.
- Compact
  일부 사용된다. 
  Sweep 후에 분산된 객체들을 Heap의 시작 주소로 모아 메모리가 할당된 부분과 그렇지 않은 부분으로 나눠서 메모리 단편화를 줄인다. 

<br><br>

### 2. Heap의 구조 => GC는 언제 일어날까?

<img width="946" alt="20211015_191348" src="https://user-images.githubusercontent.com/56250078/137476372-961031c5-2a64-4261-a1e5-2b6b03f5155f.png">



<br><br>

### 3. GC가 일어나는 순서

1. Eden 영역에 객체가 꽉 차면 Minor GC 발생! 
2. 살아있는 객체와 사용되지 않는 객체를 marking한다.
3. Reachable한 객체는 Survivor 영역으로 이동한다. 
   (Survivor 0,1 영역의 규칙은 Survivor 0,1영역 둘중에 하나에 객체가 있으면 다른 한쪽은 비어있어야 한다.)
4. Eden에 남아있는 Unreachable한 객체는 삭제된다. (Sweep)
   동시에 살아남은 Reachable한 객체의 age가 증가한다.(Aging)
5. 다시 Eden영역에 객체가 가득 차게 되면 Marking을 하고 Reachable한 객체는 Survivor영역으로 이동한다.
   (이전에 Survivor영역 1에 있었다면 Survivor영역 2로 이동한다. 반대의 경우는 Survivor영역 1로 이동한다)
   그리고 Unreachable한 객체(Survivor 영역 포함)는 삭제된다. 
   
   
6.  객체의 age의 값이 age threshold(특정임계점)에 도달하면 Old Generation으로 이동한다. (Promote )
7. Old Generation영역의 객체가 가득 차면 Major GC가 실행된다.

<br><br>

### 4. stop-the-world

- GC를 실행하기 위해 JVM이 애플리케이션 실행을 멈추는 현상을 의미한다.
  = GC를 실행하는 쓰레드 외의 모든 쓰레드가 작업을 중단한다.

<br><br>

### 5. GC의 종류

- **Serial GC**

  - GC를 처리하는 쓰레드가 1개 ( 싱글 스레드 )
  - 다른 GC에 비해 stop-the-world 시간이 길다.
  - Mark-Compact( Sweep포함 ) 알고리즘 사용
    <br><br>

- **Parallel GC**

  - Java 8의 default GC
  - Young 영역의 GC를 멀티 쓰레드로 수행
  - Serial GC에 비해 stop-the-world 시간 감소
    <br><br>

- **Parallel Old GC**

  - Parallel GC를 개선

  - Old 영역에서도 멀티 쓰레드 방식의 GC 수행

  - Mark-Summary-Compact 알고리즘 사용

    - sweep : 단일 쓰레드가 old 영역 전체를 훑는다.

    - summary : 멀티 쓰레드가 old영역을 분리해서 훑는다.

      <br><br>

- **CMS GC (Concurrent Mark Sweep)**

  - stop-the-world 시간을 줄이기 위해 고안됐다.
  - compact 과정이 없어서 메모리 단편화 문제가 발생할 가능성이 크다.
    
    
  - Initial Mark               : GC Root에서 참조하는 객체들만 우선 식별한다.
  - Concurrent Mark    : 이전 단계에서 식별한 객체들이 참조하는 모든 객체 추적한다.
  - Remark                     : 이전 단계에서 식별한 객체를 다시 추적 추가하거나 참조가 끊긴 객체를 확정한다
  - Concurrent Sweep  : 최종적으로 unreachable 객체들을 삭제한다.
 <img width="458" alt="20211015_194306" src="https://user-images.githubusercontent.com/56250078/137476449-983d5721-44e6-49a7-941b-bc32901c5109.png">

<br><br>

- **G1 GC (Garbage First)**
  - CMS GC의 메모리 단편화 문제를 개선하였다.
  - Java 9 + 의 default GC 이다.
  - Heap을 일정한 크기의 Region으로 나눈다.
  - 전체 Heap이 아닌 Region 단위로 탐색한다.
  - compact 진행한다.
  - marking 을 할때 전체를 탐색하지 않고 Region별로 탐색한다.



<img width="355" alt="20211015_195350" src="https://user-images.githubusercontent.com/56250078/137476462-e03c5502-e4ec-4652-9ae9-6942708ef8b9.png">



<br><br>



참고 : https://www.youtube.com/watch?v=Fe3TVCEJhzo
