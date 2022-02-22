# Redis 와 Memcached 의 차이



<br>

### Redis와 Memcached 모두 In Memory, key-value 방식의 NoSQL입니다.

<br>

|               | Redis                                                  | Memcached                                                    |
| ------------- | ------------------------------------------------------ | ------------------------------------------------------------ |
| 저장소        | In Memory Storage                                      |                                                              |
| 저장 방식     | Key-Value                                              |                                                              |
| 데이터 타입   | String, Set, Sorted Set, Hash, List                    | String                                                       |
| 데이터 저장   | Memory, Disk                                           | Only Memory                                                  |
| 메모리 재사용 | 메모리 재사용 하지 않음(명시적으로만 데이터 삭제 가능) | 메모리 부족시 LRU 알고리즘을 이용하여 데이터 삭제 후 메모리 재사용 |
| 스레드        | Single Thread                                          | Multi Thread                                                 |
| 캐싱 용량     | Key, Value 모두 512MB                                  | Key name 250 byte,  Value 1MB                                |



<br><br>

## **Redis** 


1. **다양한 자료구조를 지원한다.**
   String만 지원하는 Memcached에 비해 Redis는 더욱 다양한 자료구조를 지원하여 더 다양한 타입의 자료를 저장할 수 있다. 
   기본적으로 String, Bitmap, Hash, List, Set, Sorted Set을 제공한다. (강력한 장점 => 확장성 UP)


2. 데이터 복구가 가능하다.
   프로세스의 돌발 종료, 서버 종료 등 돌발 상황에서 Memcached는 모든 데이터가 유실되지만, 
     Redis는 데이터를 Disk에도 저장하기 때문에 메모리에서 유실된 데이터를 복구할 수 있습니다. 
     다만, 메모리는 별도의  설정이 필요하다

3.  Single Thread를 사용하기 때문에 scale out 하였을때 thread safe 할 수 있다.

 <br><br>

## **Memcached** 


1. 멀티스레드를 아키텍처를 지원한다.
   Single Thread인 Redis에 비해 Memcached는 Multi Thread를 지원하기 때문에 서버 Scale up에 유리하다.


2. Redis에 비해 적은 메모리를 요구한다.
   HTML과 같은 정적인 데이터를 캐싱하는 것에는 Memcached가 유리하다.
   Redis는 Copy&Write 방식을 사용하기 때문에 실제 사용하는 메모리보다 더 많은 메모리를 요구한다.





<br><br>




--------------------------------------------

- 왜 Spring Data Redis인가?

  NoSQL 스토리지 시스템은 수평적 확장성과 속도를 위해 기존 RDBMS에 대한 대안을 제공한다. 

  Spring Data Redis(SDR) 프레임워크는 Spring의 우수한 인프라 지원을 통해 저장소와 상호 작용하는 데 필요한 중복 작업 및 상용구 코드를 제거하여 Redis 키-값 저장소를 사용하는 Spring 애플리케이션을 쉽게 작성할 수 있도록 한다.

- 레디스 지원

  Spring Data에서 지원하는 키-값 저장소 중 하나는 Redis 입니다. Redis 프로젝트 홈 페이지를 인용하면:

>  Redis는 고급 키-값 저장소입니다. memcached와 유사하지만 데이터 세트는 휘발성이 아니며 값은 memcached에서와 마찬가지로 문자열일 수 있지만 목록, 세트 및 순서가 지정된 세트도 가능합니다. 이 모든 데이터 유형은 요소 푸시/팝, 요소 추가/제거, 서버 측 통합, 교차, 집합 간의 차이 등을 수행하는 원자적 작업으로 조작할 수 있습니다. Redis는 다양한 종류의 정렬 기능을 지원합니다.
> Spring Data Redis는 Spring 애플리케이션에서 Redis에 대한 쉬운 구성 및 액세스를 제공합니다. 상점과 상호 작용하기 위한 저수준 및 고수준 추상화를 모두 제공하여 사용자를 인프라 문제에서 해방시킵니다.
>
> 
>
> One of the key-value stores supported by Spring Data is Redis. To quote the Redis project home page:
>
> Redis is an advanced key-value store. It is similar to memcached but the dataset is not volatile, and values can be strings, exactly like in memcached, but also lists, sets, and ordered sets. All this data types can be manipulated with atomic operations to push/pop elements, add/remove elements, perform server side union, intersection, difference between sets, and so forth. Redis supports different kind of sorting abilities.
> Spring Data Redis provides easy configuration and access to Redis from Spring applications. It offers both low-level and high-level abstractions for interacting with the store, freeing the user from infrastructural concerns.

<br><br>

참고 사이트

https://docs.spring.io/spring-data/data-redis/docs/current/reference/html/#reference

https://junghyungil.tistory.com/165
https://brownbears.tistory.com/43

https://deveric.tistory.com/65
