# 트랜잭션의 격리 수준(isolation Level)
작성일자 : 2022-01-10
<br><br>

### 트랜잭션 isolation level (격리 수준) 은 무엇이고, 왜 있어야 할까~?

- 동시에 여러 트랜잭션이 처리될 때 특정 트랜잭션이 다른 트랜잭션에서 변경하는 데이터 값 확인 가능 여부를 level에 따라 결정하는 것 입니다.
- 트랜잭션 isolation level 이 필요한 이유는 동시에 여러 트랜잭션이 처리될 때 예측할 수 없는 데이터 변화 문제를 최소화 할 수 있습니다. 또한 트랜잭션 isolation level을 통해 데이터의 일관성을 확보할 수 있습니다.

<br><br>

### Spring 과 MySQL 기본 값은 각각 무엇일까~?

- MySQL 의 경우 REPEATABLE READ입니다.
  https://dev.mysql.com/doc/refman/8.0/en/set-transaction.html

- Spring에는 defalult 이렇게 나와있어서 찾아보니 "Use the default isolation level of the underlying datastore. All other levels correspond to the JDBC isolation levels." 라고 나와있어서 REPEATABLE READ 로 이해하고 있습니다.

  <img width="460" alt="20220110_154953_1" src="https://user-images.githubusercontent.com/56250078/148730121-86c3fc33-e085-4f70-8d90-51a3ca6a8c4c.png">


<br><br>


### 트랜잭션 격리 수준(Isolation Level)의 종류

- Isolation level 조정은 동시성이 증가되는데 반해 데이터 무결성에 문제가 발생할 수 있고, 데이터의 무결성을 유지하는 데 반해 동시성이 떨어질 수 있다.
- 레벨이 높아질수록 비용이 높아진다.

| 옵션             | 설명                                                         |
| ---------------- | ------------------------------------------------------------ |
| READ_UNCOMMITTED | 트랜잭션에서 commit 되지 않은 다른 트랜잭션에서 읽는 것을 허용한다. Dirty Read가 발생한다. |
| READ_COMMITTED   | 트랜잭션에서 **commit 되어 확정된 데이터만**을 읽는 것을 허용한다. Oracle 기본 격리 수준이다. Non-Repeatable-Read 부정합이 발생할 수 있음 |
| REPEATABLE READ  | **트랜잭션 시작 전 커밋된 내용만** 조회 가능하다. MySQL InnoDB 기본 격리 수준이다. 한 트랜잭션 내에서 수 차례 SELECT 수행하더라도 동일한 값이 읽혀지는 것을 보장한다. Phantom Read 부정합이 발생할 수 있다. |
| SERIALIZABLE     | 모든 작업을 하나의 트랜잭션에 처리하는 것과 같은 높은 고립수준을 제공하는데, 이로인해 동시성 처리 효율은 매우 떨어진다. |

<br><br>

### READ_COMMITTED 와 REPEATABLE READ 차이점

- 그림에서 확인해보면 트랜잭션 A가 커밋하고 난 이후에 두 level의 차이점이 생긴다. 

- **READ_COMMITTED**의 경우 트랜잭션 A가 커밋이 완료하면 바뀐 데이터인 Busan을 사용하게 된다. 데이터의 일관성이 달라진다. 이부분에서 Non-Repeatable Read 현상이 발생한다.
<br>

- **REPEATABLE READ**은 트랜잭션이 커밋을 완료하여도 자신의 이전 commit완료 된 데이터만을 사용하기 때문에 데이터의 일관성을 유지해준다. 
<br>

- 신기한 점은 REPEATABLE READ level의 트랜잭션이 구동하고 있는 도중에 트랜잭션A가 시작되고 실제 DB 데이터를 변경해도 임시로 저장해둔 이전 데이터 값을 REPEATABLE READ level의 트랜잭션 조회 결과로 반환해준다.

<br>

<img width="960" alt="20220110_154953_2" src="https://user-images.githubusercontent.com/56250078/148730140-d384c438-dc55-4a0f-8be8-e27f7345375c.png">


<br><br>


### 낮은 단계의 트랜잭션 격리 수준(Isolation Level) 이용시 발생하는 현상

<img width="1036" alt="20220110_162441" src="https://user-images.githubusercontent.com/56250078/148730535-476bd148-84e2-4433-ace5-f2fbdd69b8d1.png">

<br>

#### 1. Dirty Read

- 커밋되지 않은 수정 중인 데이터를 다른 트랜잭션에서 읽을 수 있도록 허용할 때 발생하는 현상
- 어떤 트랜잭션에서 아직 실행이 끝난지 않은 다른 트랜잭션에 의한 변경 사항을 보게 되는 되는 경우
<br>

#### 2. Non-Repeatable Read

- 한 트랜잭션에서 같은 쿼리를 두 번 수행할 때, 두 쿼리의 결과가 상이하게 나타나는 비 일관성 현상
- 한 트랜잭션이 수행중일 때 다른 트랜잭션이 값을 수정 또는 삭제함으로써 나타난다.
<br>

#### 3. Phantom Read

- 한 트랜잭션에서 같은 쿼리를 두 번 수행할 때, 첫 번째 쿼리에서 없던 레코드가 두 번째 쿼리에서 나타나는 현상
- 한 트랜잭션이 수행중일 때 다른 트랜잭션이 새로운 레코드가 삽입함으로써 나타난다.

<br><br>

출처  

이해가 안될때 다시 보자 => **https://nesoy.github.io/articles/2019-05/Database-Transaction-isolation**
https://doooyeon.github.io/2018/09/29/transaction-isolation-level.html <br>
https://devlog-wjdrbs96.tistory.com/422

