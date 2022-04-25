#  플러시

## 플러시 발생 
- 변경 감지
- 수정된 엔티티 쓰기 지연 SQL 저장소에 등록
- 쓰기 지연 SQL 저장소의 쿼리를 데이터베이스에 전송
  (등록, 수정, 삭제 쿼리)
  
  
## 영속성 컨텍스트를 플러시하는 방법
- em.flush()  :  직접호출
- 트랜잭션 커밋  :  플러시 자동 호출
- JPQL 쿼리 실행  :  플러시 자동 호출 
    왜 자동으로 호출 될까? 앞서 

```java 
            em.persist(memberA);
            em.persist(memberB);
            
            //중간에 JPQL 실행
            query = em.createQuery("select m from Member m",Member.class);
            List<Member> members = query.getResultList();
```



참고) 플러시해도 1차 캐시 유지됨.
영속성 컨텍스트를 비우지 않음
영속성 컨텍스트의 변경 내용을 데이터베이스에 동기화 
쓰지 지연 SQL 저장소에 있는 애들이 반영 된다.
트랜잭션이라는ㄴ 작업 단위가 중요 => 커밋 직전에만 동기화하면 됨.

플러시 모드 옵션 
기본은 Auto 그냥 기본 셋팅 오토로 써라

COMMIT 할때만 플러시 
