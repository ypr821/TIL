# JPA 영속성 컨텍스트

4월 18일 월요일 , + 4월 25일 월요일

- 객체와 관계형 데이터베이스 매핑하기
(Object Relational Mapping)
- 영속성 컨텍스트 (내부적으로 어떻게 동작하는 지 정리 필요)

- 영속성 컨텍스트는 논리적인 개념
- 눈에 보이지 않는다
- 엔티티 매니저를 통해서 영속성 컨텍스트에 접근




- 고객이 요청이 오면 EntityManagerFactory 에서 `EntityManager` 를 생성하고
`EntityManager` 내부적으로 DB 커넥션을 사용해서 DB를 사용하게 된다.
- **엔티티의 생명주기**
    - 비영속 (new)
    영속성 컨텍스트와 전혀 관계가 없는 새로운 상태
        
        ```java
        Member member = new Member();
        member.setId("member1");
        member.setUsername("회원1");
        ```
        
    - 영속(managed)
    영속성 컨텍스트에 관리되는 상태 (persist 로 등록한 상태 or em.find 로 가져온 상태)
        
        ```java
        Member member = new Member();
        member.setId("member1");
        member.setUsername("회원1");
        
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
        //객체를 저장한 상태 (영속)
        em.persist(member);
        //아직 DB에 저장되지 않은 상태이다.
        ```
        
    - 준영속(detached)
    영속성 컨텍스트에 저장되었다가 분리된 상태
        
        ```java
        em.detach(member);
        ```
        
    - 삭제(removed)
    실제 DB에서 삭제된 상태
        
        ```java
        em.remove(member);
        ```
        
    <br><br>
    
- **영속성 컨텍스트의 이점**
    - 1차 캐시
        
        한 트랜잭션 내 저장되는 캐시. 큰효과는 없지만 쿼리가 정말 복잡할때 유용할때가 있다. 객체지향적으로 코드를 작성하는데 개념적인 도움을 준다.
        동일한 트랜잭션에서 한번 조회한 거 또 조회하면 쿼리 안날라  거.
        
        ```java
        /*  1차 캐시에서 조회 확인 */
                 //비영속
                 Member member = new Member();
                 member.setId(101L);
                 member.setName("HelloJPAJPA");
        
                 //영속 - 1차 캐시에 저장됨
                 System.out.println("=== BEFORE ===");
                 em.persist(member);
                 System.out.println("=== AFTER ===");
        
                 //1차 캐시에서 조회
                 Member findMember = em.find(Member.class, 101L);
        
                 System.out.println("findMember.id = " + findMember.getId());
                 System.out.println("findMember.name = " + findMember.getName());
        ```
        
        실행 결과 - 조회를 하는데 select 쿼리문을 사용하지 않은 걸 확인할 수 있다.
        
        ```java
        === BEFORE ===
        === AFTER ===
        [findMember.id](http://findmember.id/) = 101
        [findMember.name](http://findmember.name/) = HelloJPAJPA
        Hibernate:
        /* insert hellojpa.Member
        */ insert
        into
        MEMBER
        (name, id)
        values
        (?, ?)
        ```
        
        <br>
    
    - 영속 엔티티의 동일성 보장!!!
        
        1차 캐시로 반복 가능한 읽기(REPEATABLE READ) 등급의 트랜잭션 격리 수준을 데이터베이스가 아닌 애플리케이션 차원에서 제공
        
        ```java
        /*  영속 엔티티의 동일성 보장 */
        //이전에 101L 기본키로한 테이터를 저장해둠.
        
        Member findMember1 = em.find(Member.class, 101L);
        Member findMember2 = em.find(Member.class, 101L);
        
        System.out.println("is same result = " + (findMember1 == findMember2));
        ```
        
        실행 결과
        
        ```java
        Hibernate: 
            select
                member0_.id as id1_0_0_,
                member0_.name as name2_0_0_ 
            from
                MEMBER member0_ 
            where
                member0_.id=?
        is same result = true
        ```
        
        select 쿼리 1번 들어감.
        
        is same result = true 로 객체 주소값까지 완전 일치 확인 가능.
        마치 java collect에서 꺼내서 똑같은 referece가져오는 것처럼...
        1차 캐시에 있는 거 다시 주워옴
        
     
