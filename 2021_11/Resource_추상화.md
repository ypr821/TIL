# Resource 추상화 

<br>

작성일자 : 2021-10-31

내용 : 백기선님의 스프링 프레임워크 핵심 기술 인프런 강의

<br>

### org.springframework.core.io.Resource 

### 특징

- java.net.URL을 추상화 한 것. 

  

- 스프링 **내부**에서 많이 사용하는 Resource 인터페이스.

  - 우리는 이미 많이 사용하고 있다.

    ```java
    //ApplicationContext사용할때 예시
    var ctx = new ClassPathXmlApplicationContext("***.xml");
    //클래스 패스 기준으로 설정파일을 찾는다.
    var ctx1 = new FileSystemXmlApplicationContext("***.xml");
    //파일 시스템 경로를 기준으로 설정파일을 찾는다.
    ```

   

<br><br>

### 추상화 한 이유

- 클래스패스 기준으로 리소스 읽어오는 기능 부재

  java.net.URL 클래스를 springframework.core.io.Resource 로 감싸서 low 레벨에있는 리소스에 접근할 수 있도록 함. 기존 java.net.URL가  클래스패스 기준으로 리소스를 가져오는 기능이 없었고 java.net.URL은 여러가지 prefix로 ftp, http, https등을 지원한다. 원래 클래스 패스를 가져올때는 resourceLoader.getResource("classpath:text.txt");를 사용해서 가져왔다. 클래스패스를 가져오는 것도 결국 리소스를 가져오는 것이므로 리소스를 가져오는 방법을 하나의 인터페이스 Resource 로 통일시킴

- ServletContext를 기준으로 상대 경로로 읽어오는 기능 부재

- 새로운 핸들러를 등록하여 특별한 URL 접미사를 만들어 사용할 수는 있지만 구현이 복잡하고 편의성 메소드가 부족하다. 

<br><br>

### [Resource 인터페이스 둘러보기]([Resource (Spring Framework 5.3.12 API)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/io/Resource.html))

- 상속 받은 인터페이스

- 주요 메소드

  - getInputStream() 

  - exitst() 

  - isOpen()

  - getDescription(): 전체 경로 포함한 파일 이름 또는 실제 URL 

    

  ```java
  package org.springframework.core.io;
  
  import java.io.File;
  import java.io.IOException;
  import java.net.URI;
  import java.net.URL;
  import java.nio.channels.Channels;
  import java.nio.channels.ReadableByteChannel;
  import org.springframework.lang.Nullable;
  
  public interface Resource extends InputStreamSource {
    boolean exists();
  
    default boolean isReadable() {
      return this.exists();
    }
  
    default boolean isOpen() {
      return false;
    }
  
    default boolean isFile() {
      return false;
    }
  
    URL getURL() throws IOException;
  
    URI getURI() throws IOException;
  
    File getFile() throws IOException; 
    //file로 가져오는 건 제한적이다
  
    default ReadableByteChannel readableChannel() throws IOException {
      return Channels.newChannel(this.getInputStream());
    }
  
    long contentLength() throws IOException;
  
    long lastModified() throws IOException;
  
    Resource createRelative(String relativePath) throws IOException;
  
    @Nullable
    String getFilename();
  
    String getDescription();
  }
  
  ```

  

<br><br>

### 구현체

- UrlResource: java.net.URL 참고, 기본으로 지원하는 프로토콜 http, https, ftp, file, jar.

- ClassPathResource: 지원하는 접두어 classpath:
  클래스 패스 리소스로 resolving 할 수 있다.

- FileSystemResource

- ServletContextResource: 웹 애플리케이션 루트에서 상대 경로로 리소스 찾는다. (가장 많이 씀.)

- ... 

  <br><br>

### 리소스 읽어오기

- Resource의 타입은 locaion 문자열과 ApplicationContext의 타입에 따라 결정 된다.

  - ClassPathXmlApplicationContext -> ClassPathResource 

  - FileSystemXmlApplicationContext -> FileSystemResource 

  - WebApplicationContext -> ServletContextResource 

    ApplicationContext 는 대부분의 경우에 WebApplicationContext를 사용하기 때문에 ServletContextResource 를 사용하게 했지만 리소스가 어디서 오는지 코드만 봐서는 알기가 어렵다. **classpath:**등 접두어를 사용하면 명시적이기때문에 접두어를 사용하는 걸 추천

 - ApplicationContext의 타입에 상관없이 리소스 타입을 강제하려면 java.net.URL 접두어(+ classpath:)중 하나를 사용할 수 있다.

   - **classpath:**me/whiteship/config.xml -> ClassPathResource
   - **file:**///some/resource/path/config.xml -> FileSystemResource



### 확인해보기

- ```java
  package com.inflearnb.resource;
  
  import java.nio.file.Files;
  import java.nio.file.Path;
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.boot.ApplicationArguments;
  import org.springframework.boot.ApplicationRunner;
  import org.springframework.context.ApplicationContext;
  import org.springframework.context.support.ClassPathXmlApplicationContext;
  import org.springframework.context.support.FileSystemXmlApplicationContext;
  import org.springframework.core.io.Resource;
  import org.springframework.core.io.ResourceLoader;
  import org.springframework.stereotype.Component;
  
  @Component
  public class AppRunner implements ApplicationRunner {
  
    @Autowired
    //ResourceLoader resourceLoader;
    ApplicationContext resourceLoader;
  
    @Override
    public void run(ApplicationArguments args) throws Exception {
      System.out.println(resourceLoader.getClass());
  
      Resource resource =resourceLoader.getResource("classpath:text.txt");
      System.out.println(resource.getClass());
      //클래스패스 기준으로 resource를 찾는다 왜냐하면 classpath 접두어를 뒀으니깐
      System.out.println(resource.exists()); //항상 확인
      System.out.println(resource.getDescription());
      System.out.println(Files.readString(Path.of(resource.getURI())));
      //readString 메서드 자바11버전부터 사용 가능
  
    }
  }
  
  
  ```

  

  > Resource resource =resourceLoader.getResource에서 **classpath:를 붙인** 실행결과
  >
  > - Resource resource =resourceLoader.getResource("classpath:text.txt");
  >
  > 
  >
  > class org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext
  > class org.springframework.core.io.**ClassPathResource**
  > true
  > class path resource [text.txt]
  > hello spring
  >
  > 

  

  > Resource resource =resourceLoader.getResource에서 **classpath:를 제외한** 실행결과
  >
  > - Resource resource =resourceLoader.getResource("text.txt");  
  > - ServletContextResource 니까 컨텍스트 (루트)패스 부터 찾게된다.
  >   스프링부트 기본적인 내장형 톰캣에는 컨텍스트 패스를 지정하지않음 따라서 리소스를 찾을 수 없다.
  >   Description을 읽을 수 있어도 파일을 읽을 순 없다. 에러 난다.
  > - AnnotationConfigServletWebServerApplicationContext => ServletWebServerApplicationContext => ConfigurableWebServerApplicationContext은 결국 WebServerApplicationContext 상속
  >
  > 
  >
  > class org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext
  > class org.springframework.web.context.support.**ServletContextResource**
  > false
  > ServletContext resource [/text.txt]
  > 2021-10-31 12:56:01.838  INFO 26996 --- [  restartedMain] ConditionEvaluationReportLoggingListener :
  >
  > Error starting ApplicationContext. To display the conditions report re-run your application with 'debug' enabled.
  > 2021-10-31 12:56:01.862 ERROR 26996 --- [  restartedMain] o.s.boot.SpringApplication               : Application run failed
  >
  > java.lang.IllegalStateException: Failed to execute ApplicationRunner
  >
  > 

  
