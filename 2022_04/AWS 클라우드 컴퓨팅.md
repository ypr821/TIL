# AWS 클라우드 컴퓨팅

## 컴퓨팅 방식
1. On-premise 방식 - 자체적으로 서버를 구축 

2. Cloud 방식 - 가상 컴퓨팅 기술을 사용해서 컴퓨터의  물리적 자원을 필요에 때라 배분해서 사용할 수 있다.

​		IaaS : 하드웨어만 관리

​		Paas : 하드웨어 + 가상 서버 관리 (소프트웨어만 만들어서 올리면 됨)

​		Saas : 하드웨어 + 가상 서버 + 소프트웨어 까지 제공 (사용자들이 바로 온라인으로 사용할 수 있는 것)

<br>
https://aws.amazon.com/ko/types-of-cloud-computing/?WICC-N=tile&tile=types_of_cloud

<br><br>

## EC2 (Elastic Compute Cloud)
소프트웨어 시스템을 구축하고 호스팅하는 데 사용하는 크기 조정 가능한 컴퓨팅 용량(말 그대로 Amazon 데이터 센터의 서버)을 제공하는 웹 서비스

<br>

### VPC (Virtual Private Cloud)

- 사용자가 정의한 가상 네트워크

- AWS에서 기본 네트워크 수준은 [Amazon Virtual Private Cloud(VPC)](https://aws.amazon.com/vpc/)이다. VPC는 리소스를 정의 및 프로비저닝할 수 있는 논리적 네트워크이다.

( 프로비저닝(provisioning) : 사용자의 요구에 맞게 시스템 자원을 할당, 배치, 배포해 두었다가 필요 시 시스템을 즉시 사용할 수 있는 상태로 미리 준비해 두는 것을 말한다. )

<br>

### VPC를 구성하는 일부 구성 요소

- 서브넷 : VPC 내부의 IP 주소 범위 , 쉽게 말하면 부분 망을 의미
- 라우팅 테이블 : 트래픽이 이동하는 위치를 결정하는 규칙 집합
- 인터넷 게이트웨이 : VPC 내부의 리소스와 인터넷 사이에서 통신을 할 수 있게 해주는 구성 요소

<br>

### Internet Gateway

VPC와 인터넷 간에 통신할 수 있게 해준다.

<br>

### ELB (Elastic Load Balancer)

하나의 인터넷 서비스가 발생하는 트래픽이 많을 때 여러 대의 서버가 분산처리하여 서버의 로드율 증가, 부하량, 속도저하 등을 고려하여 적절히 분산처리하여 해결해주는 서비스.

<br>

### Region

AWS의 데이터 센터군이 설치되고 있는 지역. 물리적인 위치.

<br>

### AZ (Availability Zone - 가용영역)
AWS Region중에 복수의 가용영역이 있음. 가용영역은 물리적으로 격리된 장소이고, 네트워크 뿐만 아니라 공기조절이나 전원도 다른 환경에서 운용되고 있음.

<br>

### Public subnet

Internet Gateway 가 연결되어 있는 subnet 을 public subnet 이라 한다.

<br>

### Private subnet

Internet Gateway 가 연결되지 않는 subnet 을 private subnet 이라 한다.

가용영역안에 private subnet 을 추가로 생성하여 EC2 인스턴스 설치

인터넷에 연결되어야 하는 리소스에는 퍼블릭 서브넷을 사용하고, 인터넷에 연결되지 않는 리소스에는 프라이빗 서브넷을 사용 권장

<br>

![스크린샷 2022-04-12 오후 1 47 07](https://user-images.githubusercontent.com/56250078/162882765-fccb2e31-09a7-498c-aa77-8b448b4358cc.png)


<br>


참고. : CIDR은 결국 IP주소 할당 방법



