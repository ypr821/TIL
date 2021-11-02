# Restful API

<br>

## API란 무엇일까요?

**애플리케이션 프로그래밍 인터페이스(Application Programming Interfaces, API)는 애플리케이션 소프트웨어 ,컴퓨터나 시스템 및 서비스가 서로 커뮤니케이션하여 상호작용 할 수 있도록 한다.**

컴퓨터나 시스템과 상호 작용하여 정보를 검색하거나 기능을 수행하고자 할 때 API는 사용자가 원하는 것을 시스템에 전달할 수 있게 지원하여 시스템이 이 요청을 이해하고 이행하도록 할 수 있다.

<br>


## Rest 란?

HTTP 통신에서 어떤 자원에 대한 CRUD 요청을 Resource와 Method로 표현하여 특정한 형태로 전달하는 방식

<br>

## Restful API 란 무엇일까요?

**REST란 어떤 자원에 대해 CRUD(Create, Read, Update, Delete) 연산을 수행하기 위해 URI(Resource)로 요청을 보내는 것으로, 
Get, Post 등의 방식(Method)을 사용하여 요청을 보내며, 요청을 위한 자원은 특정한 형태(Representation of Resource)로 표현된다.**

이때 특정한 형태란  REST(*REpresentational State Transfer*) 아키텍처 스타일의 디자인 원칙을 준수하는 것을 의미한다.



<br>


## 주요 메소드 5가지

GET  : 리소스 조회
POST  : 요청 데이터 처리, 주로 데이터 등록에 사용
PUT  : 리소스를 대체, 해당 리소스가 없으면 생성
PATCH  : 리소스를 **일부**만 변경
DELETE  : 리소스 삭제

<br>

## 기타 메소드 4가지

HEAD  : GET과 동일하지만 메시지 부분을 제외하고, 상태 줄과 헤더만 반환
OPTIONS  : 대상 리소스에 대한 통신 가능 옵션을 설명(주로 CORS에서 사용)
CONNECT  : 대상 자원으로 식별되는 서버에 대한 터널을 설정
TRACE  : 대상 리소스에 대한 경로를 따라 메시지 루프백 테스트를 수행

<br>

## Http 메소드 Put 과 Post 의 차이는 무엇일까요?

PUT : 자원의 전체 교체, 자원교체 시 모든 필드 필요
(만약 전체가 아닌 일부만 전달할 경우, 전달한 필드외 모두 null or 초기값 처리된다.!)

PATCH : 자원의 부분 교체, 자원교체시 일부 필드 필요

<br>

## Http 메소드 Head 는 무엇일까요?

GET과 동일하지만 메시지 부분을 제외하고, 상태 줄과 헤더만 반환한다.

<br><br>




출처

-  https://mangkyu.tistory.com/46 [MangKyu's Diary]

-  https://kyun2da.dev/CS/http-%EB%A9%94%EC%86%8C%EB%93%9C%EC%99%80-%EC%83%81%ED%83%9C%EC%BD%94%EB%93%9C/
