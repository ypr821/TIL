
# 트랜잭션이란? ACID란?

## 트랜잭션
- 데이터베이스의 상태를 변화시키기 해서 수행하는 작업의 단위입니다.

<br>

## ACID
- 원자성(Atomicity) : 트랜잭션과 관련된 작업들이 부분적으로 실행되다가 중단되지 않는 것을 보장합니다.
All or Noting의 개념입니다. 수행하고 있는 트랜잭션에 의해 변경된 내역을 유지하면서, 이전에 commit된 상태를 롤백 세그먼트(rollback segment)영역에 따로 저장함으로써 보장합니다.
- 일관성(Consistency) : 트랜잭션이 실행을 성공적으로 완료하면 언제나 일관성 있는 데이터베이스 상태로 유지하는 것을 의미합니다.
- 격리성(Isolation) : 트랜잭션을 수행 시 다른 트랜잭션의 연산 작업이 끼어들지 못하도록 보장하는 것을 의미합니다.
- 지속성(Durability) : 성공적으로 수행된 트랜잭션은 영원히 반영되어야 함을 의미합니다. 시스템 문제, DB 일관성 체크 등을 하더라도 유지되어야 합니다.
