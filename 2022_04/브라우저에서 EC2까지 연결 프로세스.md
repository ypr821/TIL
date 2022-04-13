# 브라우저에서 EC2까지 연결 프로세스    

<br>

1. 브라우저에 Domain을 입력한다.


2. 입력하면 브라우저는 제일 먼저 로컬 PC 안에 host를 검색해서 입력한 Domain과 매핑된 IP가 있는지 찾는다. <br>
   ( 내부를 먼저 검색하고 DNS 서버로 찾으러 간다. ) <br>
    
    host에서 도메인과 IP를 매핑하는 경우 <br>
    도메인 베이스로 개발하는 경우 로컬(127.0.0.1)에서 띄워야 한다.
    == 입력한 Domain을 로컬로 연결하고 싶을 때 host를 수정해야 한다. 
    
    사용 예시) 구글 지도를 쓸 때 로컬 IP로 설정하면 동작하지 않고 Domain으로만 설정해야 한다...이처럼 어쩔 수 없이 로컬 IP를 Domain으로 설정해야 하는 경우
    
3. 연결된 공유기에 셋팅된 DNS 서버로 접근하고 입력한 Domain과 매핑된 IP를 찾는다.
    
    처리 과정을 AWS 기반으로 정리하면 Route 53 이 입력한 Domain과 연결된 IP를 확인하며 매핑된 서버를 찾아준다.
    
    Amazon Route 53 
    - Amazon Route 53는 가용성과 확장성이 뛰어난 DNS(도메인 이름 시스템) 웹 서비스입니다. <br>
    - Route 53을 사용하여 세 가지 주요 기능, 즉 도메인 등록, DNS 라우팅, 상태 확인을 조합하여 실행할 수 있습니다.<br>
    [https://docs.aws.amazon.com/ko_kr/ko_kr/Route53/latest/DeveloperGuide/route-53-concepts.html](https://docs.aws.amazon.com/ko_kr/ko_kr/Route53/latest/DeveloperGuide/Welcome.html)
    
4. IP로 찾아가는 과정에서 로드밸런서(ELB)가 EC2로 연결될때 중간에서 분기시켜준다. 

5. EC2로 연결한다.

<br>

정리 ) 

서비스를 띄우고 사용하려면 도메인이 제일 먼저 필요하다. 그리고 Route53으로 도메인 입력값이 들어오면 어디로 연결시킬지 셋팅해야한다. 서브 도메인을 여러 서버로 연결시킬 수도 있다.

새로 도메인을 생성하고 ELB 로드 밸런서로 연결해서 EC2로 연결시켜야한다.
