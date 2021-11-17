# maven / gradle 차이

<br>

작성일자 : 2021-11-17

내용 : 프로젝트를 준비하면서 gradle를 사용하는 이유를 명확하게 하기 위한 공부

<br><br>

## maven 

<br>

- Build라는 동적인 요소를 **XML**로 정의하기에는 어려운 부분이 많다.
  **설정 내용이 길어지고 가독성 떨어진다.**
- 의존관계가 복잡한 프로젝트 설정하기에 부적절 하다.
  상속 구조를 이용한 멀티 모듈 구현한다.
  특정 설정을 소수의 모듈에서 공유하기 위해서는 부모 프로젝트를 생성하여 상속하게 해야 한다. ( 상속의 단점 생김 )

<br><br>

## Gradle

<br>

- Groovy를 사용하기 때문에, 동적인 빌드는 **Groovy 스크립트**로 플러그인을 호출하거나 직접 코드를 짜면 된다. **=>가독성 굳**

- **추가된 task 입력 및 출력으로 인해 다시 깨끗하게 실행할 필요가 없습니다.**
  incremental compilation은 소스와 클래스 간의 종속성을 분석하고 **변경 사항의 영향을 받는 항목만 다시 컴파일합니다. => 속도 업!!**

- Configuration Injection 방식을 사용해서 공통 모듈을 상속해서 사용하는 단점을 커버했다.
  설정 주입 시 프로젝트의 조건을 체크할 수 있어서 프로젝트별로 주입되는 설정을 다르게 할 수 있다.

<br><br>

Gradle 공식문서 살펴보기!!

the Gradle Daemon is a long-lived process that keeps build information “hot” in memory
incremental task inputs and outputs for various types of tasks makes it unnecessary to run clean ever again
incremental compilation analyzes the dependencies between your sources and classes and recompiles only those which are affected by changes
the build cache fetches results from a cache when switching branches or running a clean build and the same output has already been produced somewhere else in the organization.
Gradle’s smart classpath analyzer avoids unnecessary compilation when the binary interface of a library hasn’t changed
better modelling of dependencies using the Java Library plugin reduces the size of the compile classpath which has a large, positive impact on performance

<br>

**Gradle** Daemon은 빌드 정보를 메모리에 “hot” 상태로 유지하는 수명이 긴 프로세스입니다.
다양한 유형의 작업에 대한 **incremental task 입력 및 출력으로 인해 다시 깨끗하게 실행할 필요가 없습니다.**
incremental compilation은 소스와 클래스 간의 종속성을 분석하고 **변경 사항의 영향을 받는 항목만 다시 컴파일합니다. => 속도 업!!**
빌드 캐시는 분기를 전환하거나 클린 빌드를 실행할 때 캐시에서 결과를 가져오고 동일한 출력이 조직의 다른 곳에서 이미 생성되었습니다.
Gradle의 스마트 클래스 경로 분석기는 라이브러리의 바이너리 인터페이스가 변경되지 않은 경우 불필요한 컴파일을 방지합니다.
Java 라이브러리 플러그인을 사용한 더 나은 종속성 모델링은 성능에 크고 긍정적인 영향을 미치는 컴파일 클래스 경로의 크기를 줄입니다.



<br>

참고

> https://gradle.org/
>
> https://gradle.org/maven-vs-gradle/ -  maven과 gradle의 성능 비교를 확인 할 수 있다.
>
> https://bkim.tistory.com/13 [어쩌다, 블로그]
>
> https://velog.io/@hanblueblue/%EA%B8%B0%EC%B4%88-%EC%A7%80%EC%8B%9D-%EB%8B%A4%EC%A7%80%EA%B8%B0-2.-Gradle - gradle 상세 내용 
