# ResourceLoader 

<br>

작성일자 : 2021-10-29

내용 : 백기선님의 스프링 프레임워크 핵심 기술 인프런 강의

<br><br>

### 리소스를 읽어오는 기능을 제공하는 인터페이스 

```java
package org.springframework.core.io;

import org.springframework.lang.Nullable;

public interface ResourceLoader {
  String CLASSPATH_URL_PREFIX = "classpath:";

  Resource getResource(String location);

  @Nullable
  ClassLoader getClassLoader();
}
```



<br><br>

### ApplicationContext extends ResourceLoader 

<br><br>

### 리소스 읽어오기

- 파일 시스템에서 읽어오기

- 클래스패스에서 읽어오기

- URL로 읽어오기

- 상대/절대 경로로 읽어오기 

  

  ```java
  package com.inflearnb.resourceloader;
  
  import java.nio.file.Files;
  import java.nio.file.Path;
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.boot.ApplicationArguments;
  import org.springframework.boot.ApplicationRunner;
  import org.springframework.core.io.Resource;
  import org.springframework.core.io.ResourceLoader;
  import org.springframework.stereotype.Component;
  
  @Component
  public class AppRunner implements ApplicationRunner {
  
    @Autowired
    ResourceLoader resourceLoader;
  
    @Override
    public void run(ApplicationArguments args) throws Exception {
      Resource resource =resourceLoader.getResource("classpath:text.txt");
      //classpath 기준으로 resource를 찾는다 왜냐하면 classpath 접두어를 뒀으니깐
      System.out.println(resource.exists());
      System.out.println(resource.getDescription());
      System.out.println(Files.readString(Path.of(resource.getURI())));
      //readString 메서드 자바 11 버전부터 사용 가능
    }
  }
  
  ```

  

  text.txt 생성

  ```java
  hello spring
  ```

  

  

  ```java
  //실행결과
  true
  class path resource [text.txt]
  hello spring
  
  ```

  

  <br><br>

### Resource getResource(java.lang.String location) 

  <br><br>

<img width="165" alt="20211030_004036" src="https://user-images.githubusercontent.com/56250078/139566299-32731e48-d6a4-40a1-a681-0a2cd0ff6218.png">

<img width="159" alt="20211030_005138" src="https://user-images.githubusercontent.com/56250078/139566302-fa5ccd7b-7eac-4df6-96da-6c94dae4f77b.png">
