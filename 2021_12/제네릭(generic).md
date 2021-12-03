# 제네릭 (generic)

<br>

작성일자 : 2021-12-03

내용 : 자바의 신 복습 중 제네릭



<br><br>

### 제네릭 (generic) 이란
타입 형 변환에서 발생할 수 있는 문제점을 "사전"에 없애기 위해서 만들어졌다.
즉, 실행시에 예외가 발생하도록 하는 것이 아니라 컴파일할 때 점검할 수 있도록 한다.
<>를 사용해서 꺽쇠안에 가상의 타입을 담는다.

<br><br>

### 제네릭 사용 전의 DTO

```java
package java_study.generic1;

import java.io.Serializable;

/*
* Serializable 인터페이스
* JVM에서 해당 객체를 저장하거나 다른 서버로 전송할 수 있게 된다.
* 직렬화랄 JVM의 메모리에 상주된 객체 데이터를 바이트 형태로 변환하는 기술과
* 직렬화된 바이트형태의 데이터를 객체러 변환해서 JVM으로 상주시카는 형태를 이야기한다.
* */

/*
* DTO 클래스는 private변수, getter, setter, Serializable 구현을 해야한다.
* */

public class CastingDTO implements Serializable {

  private Object object;

  public Object getObject() {
    return object;
  }

  public void setObject(Object object) {
    this.object = object;
  }
}
```

<br>



```java
package java_study.generic1;

public class GenericSample1 {

  public static void main(String[] args) {
    GenericSample1 sample = new GenericSample1();
    sample.checkCastingDTO();

  }

  private void checkCastingDTO() {
    CastingDTO dto1 = new CastingDTO();
    dto1.setObject(new String());

    CastingDTO dto2 = new CastingDTO();
    dto2.setObject(new StringBuffer());

    CastingDTO dto3 = new CastingDTO();
    dto3.setObject(new StringBuilder());

    /*
    * 각 객체의 getObject() 메서드를 호출했을 때 리턴값으로 넘어오는 타입은 Object 이다.
    * 따라서 각각 형변환을 해야만 한다.
    * */

    String tem1 = (String) dto1.getObject();
    StringBuffer tem2 = (StringBuffer) dto2.getObject();
    StringBuilder tem3 = (StringBuilder) dto3.getObject();

    /*
    * dto2의 인스턴스 변수의 타입이 StringBuilder 인지 StringBuffer인지 혼동될 경우에는
    * instanceof라는 예약어를 사용해야한다...
    * */
  }
}
```

제네릭을 사용하지 않기 때문에  형 변환을 해준다.



<br><br>

### 제네릭을 사용한 DTO

```java
package java_study.generic1;

import java.io.Serializable;


/*
* 제네릭 (generic)
* 타입 형 변환에서 발생할 수 있는 문제점을 "사전"에 없애기 위해서 만들어졌다.
* 즉, 실행시에 예외가 발생하도록 하는 것이 아니라 컴파일할 때 점검할 수 있도록 한다.
* <>를 사용해서 꺽쇠안에 가상의 타입을 담는다.
*
* */
public class CastingGenericDTO<T> implements Serializable {

  private T object;

  public void setObject(T object) {
    this.object = object;
  }

  public T getObject() {
    return object;
  }
}
```

<br>



```java
package java_study.generic1;

public class GenericSample2 {

  public static void main(String[] args) {
    GenericSample2 sample = new GenericSample2();
    sample.checkGenericDTO();

  }

  private void checkGenericDTO() {
    CastingGenericDTO<String> dto1 = new CastingGenericDTO<String>();
    dto1.setObject(new String());

    CastingGenericDTO<StringBuffer> dto2 = new CastingGenericDTO<StringBuffer>();
    dto2.setObject(new StringBuffer());

    CastingGenericDTO<StringBuilder> dto3 = new CastingGenericDTO<StringBuilder>();
    dto3.setObject(new StringBuilder());


    String tem1 = dto1.getObject();
    StringBuffer tem2 = dto2.getObject();
    StringBuilder tem3 = dto3.getObject();

    /*
    * 형변환을 할 필요성이 사라졌다.
    * 실행시 다른 타입으로 잘못 형 변환하여 예외가 발생하는 일은 없다.
    * 이처럼 명시적으로  타입을 지정할 때 사용하는 것이 제네릭이다.
    * */
  }
}
```



<br><br>



### 제네릭 와일드카드 사용하기

```java
package java_study.generic1;

public class WildcardGeneric<W> {

  W wildcard;

  public void setWildcard(W wildcard) {
    this.wildcard = wildcard;
  }

  public W getWildcard() {
    return wildcard;
  }
}
```

<br>

```java
package java_study.generic1;

public class WildcardSample {

  public static void main(String[] args) {
    WildcardSample sample = new WildcardSample();
    sample.callWildcardMethod();
  }

  private void callWildcardMethod() {
    WildcardGeneric<String> wildcard = new WildcardGeneric<String>();
    wildcard.setWildcard("A");
    wildcardStringMethod(wildcard);
  }

/*
 * 에러가 발생한다 컴파일 조차 안된다.
 * java: incompatible types: java.lang.String cannot be converted to capture#1 of ?
 * 와일드카드를 변수의 타입으로 사용하는 경우 ! 
  private void callWildcardMethod() {
    WildcardGeneric<?> wildcard = new WildcardGeneric<String>();
    wildcard.setWildcard("A");
    wildcardStringMethod(wildcard);
  }
*/

  /*
  * 현재는 wildcardStringMethod() 메서드의 매개변수 타입은  WildcardGeneric<String>으로
  * String 타입만 받을 수 있다.
  * 그러나 WildcardGeneric<?> ("?" 와일드카드)를 사용하면 다양한 타입을 받을 수 있다.
  *
  * 이 와일드카드는 매개변수로만 사용하는 것이 좋다.
  * */
  private void wildcardStringMethod(WildcardGeneric<String> c) {
    String value = c.getWildcard();
    System.out.println(value);
  }
}
```

<br>
