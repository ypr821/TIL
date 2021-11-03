# Collection 궁금 해결
<br>
작성일자 : 2021-10-27
<br>
내용 : Collection 중에서 궁금한 것들 내용 정리

<br><br>
 
 ## **TreeMap 검색 속도 빅오 표기법**

  > A Red-Black tree based [`NavigableMap`](https://docs.oracle.com/javase/8/docs/api/java/util/NavigableMap.html) implementation.
  > The map is sorted according to the [natural ordering](https://docs.oracle.com/javase/8/docs/api/java/lang/Comparable.html) of its keys, or by a [`Comparator`](https://docs.oracle.com/javase/8/docs/api/java/util/Comparator.html) provided at map creation time, depending on which constructor is used.
  > This implementation provides guaranteed log(n) time cost for the `containsKey`

![collection 시간복잡도](https://user-images.githubusercontent.com/56250078/140063270-d233e43a-ac76-463e-8715-e921ae61bd54.png)

사진출처: https://bcp0109.tistory.com/67

<br>
<br>
  

## **HashMap vs HashTable vs ConcurrentHashMap 차이점**
<br>
  

  > HashMap,ConCurrentHashMap,HashTable 이 3가지 클래스는 Map 인터페이스를 구현한 컬렉션이다. 기본적으로 <key,value>구조를 가지고있지만, key,value값의 null 허용여부와 속도,동기화보장 등은 서로서로 조금씩 다르다.
  >
  > 
  >
  > **HashMap**
  >
  > HashMap은 synchronized 키워드가 없기 때문에 동기화가 보장되지 못한다. (싱글 스레드 환경에서 사용하길) 따라서 동기화처리를 하지 않기 때문에 값을 찾는 속도가 상당히 빠르다. 또한 HashTable과 다르게 key,value null값을 허용한다. 즉 속도가 빠르지만, 신뢰성 안정성은 떨어진다고 생각하면 된다.
  >
  > 
  >
  > **ConCurrentHaspMap**
  >
  > HashMap의 멀티스레드 환경에서의 동기화처리로 인한 문제점을 보완한 것이 ConCurrentHashMap이다. 
  >
  > ConcurrentHashMap 클래스의 일부 API 입니다. ConcuurentHashMap에는 Hashtable 과는 다르게 synchronized 키워드가 메소드 전체에 붙어 있지 않습니다. get() 메소드에는 아예 synchronized가 존재하지 않고, put() 메소드에는 중간에 synchronized 키워드가 존재하는 것을 볼 수 있다.
  >
  > https://devlog-wjdrbs96.tistory.com/269
  >
  > 하지만 HashMap과 다르게 key,value에 null을 허용하지 않는다.
  >
  > 
  >
  > **HashTable**
  >
  > HashTable의 메서드는 전부 synchronized 키워드가 붙어있기 때문에 메서드 호출 전 쓰레드간 동기화 락을 통해 멀티 쓰레드 환경에서 data의 무결성을 보장해준다. 또한 key,value값의 null을 허용하지 않는다. 즉 동기화 락때문에 속도는 느리지만, data의 안정성이 높고 신뢰가 높은 컬렉션이다.
  >
  > 
  >
  > 출처 : http://jdm.kr/blog/197

  <br><br>

## **ArrayList vs LinkedList 차이점**

  > - ArrayList는 중복을 허용하고 순서를 유지하며 인덱스로 원소들을 관리한다는 점에서 배열과 상당히 유사하다다. 추가, 삭제를 위해 임시 배열을 생성해 데이터를 복사 하는 방법을 사용 하고 있다.
  >
  >   대량의 자료를 추가/삭제 하는 경우에는 그만큼 데이터의 복사가 많이 일어나게 되어 성능 저하를 일으킬 수 있다. 반면 각 데이터는 인덱스를 가지고 있기 때문에 한번에 참조가 가능해 데이터의 검색에는 유리한 구현체이다.
  >
  > 
  >
  >
  > - LinkedList는 데이터를 저장하는 각 노드가 이전 노드와 다음 노드의 상태만 알고 있는 연결리스트 구조다.
  >
  >   데이터의 추가, 삭제시 불필요한 데이터의 복사가 없어 데이터의 추가, 삭제에 유리합니다. 반면에 데이터의 검색시에는 처음부터 노드를 순회해야 하기 때문에 성능상 불린다.
  >
  > 
  >
  >
  > - 다루고자 하는 데이터의 개수가 변하지 않는 경우와 데이터 처리과정이 순차적이라면 ArrayList가 최상의 선택이겠지만, 데이터 개수의 변경이 많고 순차적인 흐름이 아니라면 LinkedList를 사용하는 효율적이다.
<br><br>
  

## **ArrayList vs HashMap vs HashSet 차이점**

  > 1. **계열들의 특징**
  >    **List** : 수집의 순서가 있으며, 동일한 데이터의 중복 입력이 가능.
  >    순차적으로 대량의 데이터를 엑세스하거나 입력할때 유리한 방식.
  >
  >   **Map** : Key & Value의 형태로 입력이되며, 키값을 입력하면 해당하는 value값을 획득. 수집의 순서를 기억하지 않고, 동일한 데이터를 Key값으로 사용할 수 없다. 
  >   다건의 데이터에서 원하는 특정 데이터에 접근(검색)할 때 유리한 방식. 
  >
  >   **Set** : 중복데이터를 불허하는 것을 제외하고는 큰 특징이 없음.
  >   입력되는 당시의 순서에는 따르지 않으나 순차적인 접근을 위해서는 Iterator로 접근하게 된다.
  >
  > 2. **각 구현 class들의 차이
  >    Map계열 ** 
  >    HashMap은 데이터 입출력이 동기화되지 않고 처리속도가 빠름
  >    HashTable은 모든 입출력이 동기화(토근을 부여받아 순차적으로 객체에 접근)되며 
  >    처리속도는 다소 떨어지게 된다.
  >
  >   **List계열**
  >    Vector가 synchronized(동기화) 되는 반면 
  >    ArrayList는 그렇지 않다.
  >
  > 3. ** ArrayList 와 HashTable(HashMap)의 차이점**
  >    - **ArrayList의 입력방식** 
  >      add(Object o), add(int index, Object o), set(int index, Object o)…etc
  >      \# 데이터를 검색하기 위해서는 처음부터 끝까지 돌거나 사용자가 index를 알아야함
  >      index정보를 알고 있다면 ArrayList가 HashMap보다 빠름
  >    - **HashMap의 입력방식**
  >      pub(Object key, Object value)
  >      key 값을 Object로 갖고 있기에 자바에서 사용가능한 모든 클래스 가능
  >      (복잡한 값을 갖는 Object 도 키로 활용가능)
  >      \# 키 값을 이용해 바로 원하는 정보를 얻어낼 수 있기에 검색능력이 탁월하다.
  >    - **용도의 차이점** 
  >      ArrayList의 경우 단순히 데이터를 입력하고 데이터를 출력하는 용도로
  >      HashMap의 경우 데이터를 캐쉬해서 특정 key값으로 HashMap에 있는 데이터를 검색해서 사용하는 용도로 많이 쓰인다..
  >
  > 출처: https://gyrfalcon.tistory.com/entry/Java-Collection [Minsub's Blog]

<br><br>

## **Hash 충돌**

  > - 서로 다른 값을 가진 key가 해시 테이블의 한 주소에 매핑되는 경우이다.
  > - hashing을 해서 삽입하려 했으나 이미 다른 원소가 자리를 차지 하고 있는 상황.
  >
  > 같은 키 값을 가지는 데이터가 생기는 것은 특정 키의 버켓에 데이터가 집중된다는 뜻
  > 너무 많은 해시 충돌은 해시 테이블의 성능을 떨어뜨림
  >
  > \- 해시함수의 입력값은 무한하지만 출력값의 가짓수는 유한하므로 해시충돌은 반드시 발생 (*비둘기집 원리)
  > \- 클러스터링(Clustering) : 연속된 레코드에 데이터가 몰리는 현상
  > \- 오버플로우(Overflow) : 해시 충돌이 버킷에 할당된 슬롯 수보다 많이 발생하면 더 이상 버킷에 값을 넣을 수 없는 현상

<br><br>
