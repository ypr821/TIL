# Multi Thread 환경에서 동시성 제어하는 방법



## Process

- 특정작업을 수행하는 소프트웨어 프로그램

- 프로그램이 실행되어 메모리나 CPU 같은 자원을 할당받으면 이를 프로세스라고 부른다.

- 스레드는 프로세스를 구성하는 하나의 단위이다.
  (하나의 프로세스에는 여러 스레드가 작동할 수 있다.)

- 프로세스는 독자적인 메모리를 할당받아서 서로다른 프로세스 끼리는 서로의 메모리를 공유하지 않는다.



## Thread 

- 작업의 한 단위이다.

- 프로세스 내부에 있는 여러 스레드 들은 서로 같은 프로세스 내부에 존재하고 있기 때문에 같은 자원을 공유하여 사용할 수 있다.
- 같은 자원을 공유하여 사용한다는 점 때문에 멀티스레드 환경에서 문제가 발생할 수 있다.

<br><br>



## 동시성 제어 방법 1 : Lock - synchronized



<br><br>

## 동시성 제어 방법 2 : Volatile



<br><br>

## 동시성 제어 방법 3 : Atomic



<br>

<br>

## synchronized, volatile, AtomicInteger 비교

## synchronized

- 여러 쓰레드가 write하는 상황에 적합
- 가시성 문제해결 : synchronized블락 진입전 후에 메인 메모리와 CPU 캐시 메모리의 값을 동기화 하기 때문에 문제가 없도록 처리

## volatile

- 하나의 쓰레드만 read&write하고 나머지 쓰레드가 read하는 상황에 적합
- 가시성 문제해결 : CPU 캐시 사용 막음 → 메모리에 접근해서 실제 값을 읽어오도록 설정
  - Read : CPU cache에 저장된 값X , Main Memory에서 읽는다.
  - Write : Main Memory에 까지 작성

## AtomicIntger

- 여러 쓰레드가 read&write를 병행
- 가시성 문제해결 : CAS알고리즘
  - **현재 쓰레드에 저장된 값과 메인 메모리에 저장된 값을 비교 → 일치시 새로운 값으로 교체, 불일치시 실패하고 재시도**





참조 : 

https://velog.io/@been/%EC%9E%90%EB%B0%94Multi-Thread%ED%99%98%EA%B2%BD%EC%97%90%EC%84%9C-%EB%8F%99%EC%8B%9C%EC%84%B1-%EC%A0%9C%EC%96%B4%EB%A5%BC-%ED%95%98%EB%8A%94-%EB%B0%A9%EB%B2%95
