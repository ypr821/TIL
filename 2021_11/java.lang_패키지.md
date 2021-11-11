

# java.lang 패키지

<br>

작성일자 : 2021-11-11

내용 : 자바의신.2  서적 공부  ( java.lang 패키지 ), 기억하고 싶은 내용 위주로 정리

<br><br>



자바의 패키지 중에 유일하게 java.lang 패키지는 import하지 않아도 사용할 수 있다.

실제로 자바 프로그램이 실행되면 JVM에서 static영역에 바로 메모리에 올리는 것이 java.lang 패키지이다.

https://docs.oracle.com/javase/7/docs/api/java/lang/package-summary.html

링크를 가보면 얼마나 많은 역할을 하는 지 알 수 있다.

<br><br>

-----

<br>

## 숫자를 처리하는 클래스를 감싼 Wrapper클래스

그 중 숫자를 처리하는 클래스를 살펴보면 자바에서 간단한 계산을 할때 대부분 기본자료형 Primitive Type을 사용한다.

기본자료형은 stack  영역에 저장되어 관리되기 때문에 빠른처리가 가능하다.

<br>

> 나의 질문 : 그렇다면 왜 **숫자를 처리하는 클래스를 감싼 Wrapper클래스**가 필요한 걸까?
>
> 
>
> - 매개변수를 참조 자료형으로만 받는 메소드를 처리할 수 있다.
> - 제네릭과 같이 기본자료형을 사용하지 않는 기능을 사용할 수 있다.
> - MIN_VALUE 최소값, MAX_VALUE 최대값 과 같이 클래스에 선언된 상수 값을 사용할 수 있다.
> - 문자열을 숫자로, 숫자를 문자열로 쉽게 변환하고, 2,8,10,16진수 변환을 쉽게 처리할 수 있다.
>
> 

<br>

**숫자를 처리하는 클래스를 감싼 Wrapper클래스**는 모두 Number라는 abstract클래스를 extends한다.

자바 컴파일러에서 자동으로 형변환을 해주기 때문에 참조자료형이지만 기본자료형처럼 사용할 수 있다.

<br>

-  Byte
-  Short
-  Integer
-  Long
-  Float
-  Double
-  Character
-  Boolean

<br>

Character 제외하고 **parse타입이름( ), valueOf( )라는 메소드를 공통적으로 제공**한다.

parse타입이름( ) 메소드는 기본자료형을 리턴한다.

valueOf( ) 메소드는 참조 자료형을 리턴한다.

```java
// 사용 예시 코드
package com.javastudy.javalangpackage;

public class Test {

  public static void main(String[] args) {
    String value1 = "3";
    String value2 = "5";
    byte byte1 = Byte.parseByte(value1);
    byte byte2 = Byte.parseByte(value2);
    System.out.println(byte1 + byte2);

    Integer refInt1 = Integer.valueOf(value1);
    Integer refInt2 = Integer.valueOf(value2);
    System.out.println(refInt1 + refInt2);

  }
}

/*
실행결과 
8
8
*/

```

<br><br>



## 각종 정보를 확인하기 위한 System 클래스



System 클래스는 생성자가 없고 err, in, out 3개의 static 변수가 선언되있다.

| 변수명 | 선언 및 리턴 타입    | 설명                               |
| ------ | -------------------- | ---------------------------------- |
| err    | static   PrintStream | 에러 및 오류를 출력할 때 사용한다. |
| in     | static   InputStream | 입력값을 처리할 때 사용한다.       |
| out    | static   PrintStream | 출력 값을 처리할때 사용한다.       |



### 이 중에서 **System.out을 **살펴보자

PrintStream클래스의 출력을 위한 주요 메소드 

- print( )
- println( )
- format( )
- printf( )
- write( )             =>     System.out 으로 많이 사용하진 않는다.



https://docs.oracle.com/javase/7/docs/api/java/io/PrintStream.html 을 살펴보면

print( )와 println( )메서드 파라미터에 어떤 타입이 들어갈수 있는지 확인 할 수 있다.



print(boolean b), print(char c), print(char[] s)

print(double d), print(float f), print(int i)

print(long l), print(Object obj), print(String s)

println(), println(boolean x), println(char x)



println(char[] x), println(double x), println(float x)

println(int x), println(long x), println(Object x)

println(String x)



byte, short 타입은 없는데 byte나 short타입을 print( ), println( ) 메서드에 넘겨주면 int 타입을 매개변수로 받는 메소드에서 알아서 처리해준다.



print(Object obj),println(Object x) 을 보면 Object 타입을 파라미터로 받을 수 있다.

obj 가 null인 경우를 살펴본다. 

<br><br>

## print( ),println( ) 메서드에 null을 파라미터로 담으면 어떻게 될까?

<br>

println이나 print메서드에서 Object를 파라미터로 받을때 

결국 String.valueOf(obj)를 호출하는 걸 알았다.

그래서 null을 출력할 수 있구나

<br>

참고) 

```java
package com.javastudy.javalangpackage;

public class Test2 {

  public static void main(String[] args) {
    Object obj = null;
    System.out.println(obj);
    System.out.println(obj + " is object's value");
  }
}
```

<br>

자바의 신책에서는 위 코드가 예외가 발생한다고 했는데 실제 실행해보니 예외가 발생하지 않아서 찾아봤다.

<br>

print(Object obj) 은 String.valueOf(obj)를 사용하는구나 알게됐다.

```java
    public void print(Object obj) {
        write(String.valueOf(obj));
    }
```

<br>

println(Object x)도 마찬가지다.

println메서드에서는 print(String s) 를 호출한다. 그리고 newLine();로 줄바꿈을 해준다. 오~

```java
public void println(Object x) {
        String s = String.valueOf(x);
        synchronized (this) {
            print(s);
            newLine();
        }
    }

```

<br>

```java
    public void print(String s) {
        write(String.valueOf(s));
    }
```

<br>





> **나의 질문 : 결과적으로 다 write 메서드를 호출하는데 write 메서드가 어떻게 출력을 하는건지 이해가 안간다.**



```java
private void write(String s) {
        try {
            synchronized (this) {
                ensureOpen();
                textOut.write(s);
                textOut.flushBuffer();
                charOut.flushBuffer();
                if (autoFlush && (s.indexOf('\n') >= 0))
                    out.flush();
            }
        }
        catch (InterruptedIOException x) {
            Thread.currentThread().interrupt();
        }
        catch (IOException x) {
            trouble = true;
        }
    }
```



> 
> textOut 요 변수가 **BufferedWriter** 타입이다.
>
> BufferedWriter은 간단하게 java에서 출력을 담당하는 클래스이다. 아하
>
> https://docs.oracle.com/javase/7/docs/api/java/io/BufferedWriter.html
>
> 



----



### toString( )보다는 valueOf( ) 메서드



```java
package com.javastudy.javalangpackage;

public class Test2 {

  public static void main(String[] args) {
    Object obj = null;
    System.out.println(String.valueOf(obj));
    System.out.println(obj.toString());
  }
}
```



를 실행해보면 String.valueOf(obj)는 에러가 발생하지않지만

obj.toString()는 java.lang.NullPointerException이 발생했다.



객체를 출력할때 toString( )보다는 valueOf( ) 메서드를 출력하는게 낫다.

