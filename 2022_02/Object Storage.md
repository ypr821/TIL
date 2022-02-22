# **Object Storage** 저장소를 사용하는 이유

- **파일 스토리지** 는 많은 사용자에게 익숙한 스토리지 포맷입니다. 유저에게는 매우 편리한 방식이지만  특정 데이터에 대한 파일 경로는 매우 길어 관리에 비효율적일 수 있습니다. 

- 하지만 **Object Storage** 는 계층형 구조를 가진 기존 스토리지와는 달리 데이터가 커져도 복잡해지지 않습니다. 그리고 각 유닛에는 고유의 식별자 혹은 키가 있어서 분산된 시스템 내 어디에 저장되어 있든지 상관없이 데이터를 찾을 수 있습니다.  scale out 하여 저장소가 추가됐을 때도 식별자 나 키를 사용해서 데이터를 빠르게 찾을 수 있습니다.
  https://www.purestorage.com/kr/knowledge/what-is-object-storage.html

- 또한 REST 명령을 통해 오브젝트 스토리지 시스템의 데이터에 접근하고 관리할 수 있습니다.

- AWS(아마존 웹 서비스)에서 제공하는 VPC(virtual private cloud)와 VPN (virtual private network)커넥터를 제공합니다. 즉,  on premise 환경에서도 문제없이 사용할 수 있습니다. AWS Outposts 도 있습니다.

  https://aws.amazon.com/ko/s3/outposts/
  https://docs.aws.amazon.com/vpn/latest/clientvpn-admin/scenario-onprem.html
