# @PathVariable, @MatrixVariable
- 작성일자 : 2022-02-09

<br><br>
## @PathVariable 
<br>
- **요청 URI 패턴의 일부를 핸들러 메소드 argument로 받는 어노테이션**

  > 참고  -  Parameter 와 Argument 구분
  >
  > | 단어      |                | 의미                                 |
  > | --------- | -------------- | ------------------------------------ |
  > | Parameter | 매개변수       | 함수와 메서드 입력 변수(Variable) 명 |
  > | Argument  | 전달인자, 인자 | 함수와 메서드의 입력 값(Value)       |

- **타입 변환 지원**

```java
  @Test
  public void getEventTest() throws Exception {
    mockMvc.perform(get("/events/1"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("id").value(1));
  }
```

입력값을 전달할때는 1이 문자열이다.

URI 에서 argument로 받을때는 Integer로 변환해주는 것을 확인 할 수 있다.

```java
  @GetMapping("/events/{id}")
  @ResponseBody
  public Event getEvent(@PathVariable Integer id) {
    //문자열을 Integer 값으로 자동 변환해준다.
    Event event = new Event();
    event.setId(id);
    event.setName(name);
    return event;
  }
```



- (기본)값이 반드시 있어야 한다.
- Optional 지원. 

```java
// null 값을 처리하기 위한 방법 1 
@GetMapping("/events/{id}")
  @ResponseBody
 public Event getEvent(@PathVariable Optional<Integer> id) {
    // Optional의 메소드를 사용해서 null 값 처리
    Event event = new Event();
    event.setId(id);
    event.setName(name);
    return event;
  }


 // null 값을 처리하기 위한 방법 2   
  @GetMapping(value = {"/events/{id}", "/events"})
  @ResponseBody
public Event getEvent(@PathVariable(required = false) Integer id) {
    //if 문으로 null 체크 후 처리
    Event event = new Event();
    event.setId(id);
    event.setName(name);
    return event;
  }
```

required = false는 null 이 들어왔을 때 기본값을 설정해준다.






<br><br>


## @MatrixVariable

- **요청 URI 패턴에서 키/값 쌍의 데이터를 메소드 아규먼트로 받는 어노테이션**

- **타입 변환 지원**

- (기본)값이 반드시 있어야 한다.

- Optional 지원.

- 설정 없이 실행해보니 에러가 발생하지 않음  (MatrixVariable을 찾지 못할 경우 아래의 설정 방법을 사용하자)


  
  이전에는 해당 기능은 기본적으로 비활성화 되어 있음. 활성화 하려면 다음과 같이 설정해야 함.

```java
@Configuration
public class WebConfig implements WebMvcConfigurer {
@Override
public void configurePathMatch(PathMatchConfigurer configurer) {
UrlPathHelper urlPathHelper = new UrlPathHelper();
urlPathHelper.setRemoveSemicolonContent(false);
configurer.setUrlPathHelper(urlPathHelper);
}
}
```

<br>

## @MatrixVariable 사용 방법 예시 코드

```java
//요청을 보내는 Test 코드

  @Test
  public void getEventTest() throws Exception {
    mockMvc.perform(get("/events/1;name=ypr"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("id").value(1));
  }
```



```java
//Controller 메소드에 @MatrixVariable 사용 

@GetMapping("/events/{id}")
  @ResponseBody
  public Event getEvent(@PathVariable Integer id, @MatrixVariable String name) {
    Event event = new Event();
    event.setId(id);       //1
    event.setName(name);   //ypr
    return event;
  }


```



그 외 공식 문서에서 제공하는 사용 방법



```java
// GET /pets/42;q=11;r=22

@GetMapping("/pets/{petId}")
public void findPet(@PathVariable String petId, @MatrixVariable int q) {

    // petId == 42
    // q == 11
}
```



```java
// GET /owners/42;q=11/pets/21;q=22

@GetMapping("/owners/{ownerId}/pets/{petId}")
public void findPet(
        @MatrixVariable(name="q", pathVar="ownerId") int q1,
        @MatrixVariable(name="q", pathVar="petId") int q2) {

    // q1 == 11
    // q2 == 22
}
```



```java
// GET /pets/42

@GetMapping("/pets/{petId}")
public void findPet(@MatrixVariable(required=false, defaultValue="1") int q) {

    // q == 1
}
```



Map 자료 구조에도 바로 적용 가능하다.

```java
// GET /owners/42;q=11;r=12/pets/21;q=22;s=23

@GetMapping("/owners/{ownerId}/pets/{petId}")
public void findPet(
        @MatrixVariable MultiValueMap<String, String> matrixVars,
        @MatrixVariable(pathVar="petId") MultiValueMap<String, String> petMatrixVars) {

    // matrixVars: ["q" : [11,22], "r" : 12, "s" : 23]
    // petMatrixVars: ["q" : 22, "s" : 23]
}
```









 참고:

https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc-ann-requestmapping-uri-templates

https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc-ann-matrix-variables

https://www.baeldung.com/spring-pathvariable
