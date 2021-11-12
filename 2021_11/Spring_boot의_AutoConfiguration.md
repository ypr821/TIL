# Spring boot의 AutoConfiguration

<br>

h2 DB를 추가하기 위해서 의존성을 추가하고

application.properties에 h2 DB정보를 입력했다.

```
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.url=jdbc:h2:../test
spring.datasource.username=sa
spring.datasource.password=
```

입력하고서 실행해보니 잘 작동했다.

<br><br>

그런데.... Driver를 잘못입력해도 문제없이 잘 돌아갔다. 

너무 수상해서 왜 그런지 찾아보니 Spring boot의 **AutoConfiguration 기능** 덕분이 었다.

> 나의질문 : 그러면 애초에 application.properties 파일에 DB정보를 입력할 필요가 없는 건가?
>
> ??????????

<br>

<br>

**@SpringBootApplication** 에는 참 많은 것들이 들어있다.

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(excludeFilters = { @Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
  @Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class) })
public @interface SpringBootApplication {
    ```이하생략```
}

```

<br>

그중에 **@EnableAutoConfiguration**

그리고...

Gradle: org.springframework.boot:spring-boot-autoconfigure:2.5.6 이 곳도 뒤져봤다. 

뭐가 진짜 정말 정말 정말 많다 와우 그 중에 **h2**도 있었다.


<br>

<img width="360" alt="20211112_205039_2" src="https://user-images.githubusercontent.com/56250078/141465348-61129c24-831a-47d8-8796-ce2f942efa29.png">
<img width="360" alt="20211112_205039_3" src="https://user-images.githubusercontent.com/56250078/141465355-110e26b2-28c2-48a7-9b8d-4ff16ba4f607.png">
<img width="366" alt="20211112_205039_4" src="https://user-images.githubusercontent.com/56250078/141465363-aa2ee424-2b5e-45f0-a8ac-3e2e1606c781.png">
<br>
<br>
**@EnableAutoConfiguration**도 있네..

**@EnableAutoConfiguration**: 자동 설정의 핵심 어노테이션이다. 클래스 경로에 지정된 내용을 기반으로 설정 자동화를 수행한다.

<br>

> 나의질문 : H2ConsoleAutoConfiguration 이 클래스가 h2를 자동 설정해주는 로직을 가지고있고 @EnableAutoConfiguration을 통해 자동 등록되는건가..??

```java
/*
 * Copyright 2012-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.boot.autoconfigure.h2;

import java.sql.Connection;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.h2.server.web.WebServlet;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.boot.autoconfigure.h2.H2ConsoleProperties.Settings;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for H2's web console.
 *
 * @author Andy Wilkinson
 * @author Marten Deinum
 * @author Stephane Nicoll
 * @since 1.3.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication(type = Type.SERVLET)
@ConditionalOnClass(WebServlet.class)
@ConditionalOnProperty(prefix = "spring.h2.console", name = "enabled", havingValue = "true")
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
@EnableConfigurationProperties(H2ConsoleProperties.class)
public class H2ConsoleAutoConfiguration {

	private static final Log logger = LogFactory.getLog(H2ConsoleAutoConfiguration.class);

	@Bean
	public ServletRegistrationBean<WebServlet> h2Console(H2ConsoleProperties properties,
			ObjectProvider<DataSource> dataSource) {
		String path = properties.getPath();
		String urlMapping = path + (path.endsWith("/") ? "*" : "/*");
		ServletRegistrationBean<WebServlet> registration = new ServletRegistrationBean<>(new WebServlet(), urlMapping);
		configureH2ConsoleSettings(registration, properties.getSettings());
		dataSource.ifAvailable((available) -> {
			try (Connection connection = available.getConnection()) {
				logger.info("H2 console available at '" + path + "'. Database available at '"
						+ connection.getMetaData().getURL() + "'");
			}
			catch (Exception ex) {
				// Continue
			}
		});
		return registration;
	}

	private void configureH2ConsoleSettings(ServletRegistrationBean<WebServlet> registration, Settings settings) {
		if (settings.isTrace()) {
			registration.addInitParameter("trace", "");
		}
		if (settings.isWebAllowOthers()) {
			registration.addInitParameter("webAllowOthers", "");
		}
		if (settings.getWebAdminPassword() != null) {
			registration.addInitParameter("webAdminPassword", settings.getWebAdminPassword());
		}
	}

}

```

<br><br>

### 빈의 등록과 자동 설정에 필요한 파일

- META-INF/spring.factories
  - 자동 설정 타깃 클래스 목록
  - 이곳에 선언된 클래스들이 @EnableConfiguration 사용 시 자동 설정 타깃이 된다.
- META-INF/spring-configuration-matadata.json
  - 자동 설정에 사용할 프로퍼티 정의 파일
  - 미리 구현되어 있는 자동 설정에 프로퍼티만 주입시켜주면 된다.
  - 변경을 위해서는 [application.properties](http://application.properties/) 나 application.yml에 프로퍼티 값을 추가한다.
- org/springframework/boot/autoconfigure
  - 미리 구현해놓은 자동 설정 리스트
  - 모두 자바 설정 방식을 따른다.

<br>

아직 이해를 하기위해선 시간이 더 필요하다.!! 아래 블로그를 한번씩 다시보자!!

https://velog.io/@adam2/SpringBoot-%EC%9E%90%EB%8F%99-%ED%99%98%EA%B2%BD-%EC%84%A4%EC%A0%95AutoConfiguration

<br><br>
