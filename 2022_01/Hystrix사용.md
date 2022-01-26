

# Hystrix  사용방법

1. 단계 - 의존성 추가
   implementation 'com.netflix.hystrix:hystrix-core:1.5.18'

2. 단계 - HystrixCommand을 상속한 클래스 구현
3. 단계 - Circuit breaker를 적용할 부분에 위 HystrixCommand을 상속한 클래스의 객체를 생성하고 execute메소드 호출한다.



<img width="600" alt="Hystrix  사용방법_1" src="https://user-images.githubusercontent.com/56250078/151114835-18130f93-7c8f-49d2-9c8b-75054b2ce5e6.png">

[View Source](https://github.com/Netflix/Hystrix/blob/master/hystrix-examples/src/main/java/com/netflix/hystrix/examples/basic/CommandHelloWorld.java)

This command could be used like this:

```
String s = new CommandHelloWorld("Bob").execute();
Future<String> s = new CommandHelloWorld("Bob").queue();
Observable<String> s = new CommandHelloWorld("Bob").observe();
```

<img width="600" alt="Hystrix  사용방법_2" src="https://user-images.githubusercontent.com/56250078/151114844-73b59a1a-8f53-4e73-9044-64afdf6446ff.png">

https://github.com/Netflix/Hystrix/wiki/Getting-Started



<br><br>

# Hystrix  프로젝트 적용



KakaoAddressApi의 getAddressBySpot메소드에서 HystrixCommand을 상속한 클래스의 객체를 생성하고 execute메소드 호출한다.



고민)  HystrixCommand클래스의 run메소드를 오버라이딩 할때 어떻게 버무려야할까?
