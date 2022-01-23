# Void

void를 나타내는 객체에 대한 참조를 유지하기 위해 인스턴스화할 수 없는 클래스의 자리 표시자 클래스이다.

```java

package java.lang;

/**
 * The {@code Void} class is an uninstantiable placeholder class to hold a
 * reference to the {@code Class} object representing the Java keyword
 * void.
 *
 * @author  unascribed
 * @since   1.1
 */
public final
class Void {

    /**
     * The {@code Class} object representing the pseudo-type corresponding to
     * the keyword {@code void}.
     */
    @SuppressWarnings("unchecked")
    public static final Class<Void> TYPE = (Class<Void>) Class.getPrimitiveClass("void");

    /*
     * The Void class cannot be instantiated.
     */
    private Void() {}
}

```



사용예시

Controller의 return 값에 일관성을 부여하기 위해 제네릭 타입의 필드를 가진 BasicResponse 클래스를 사용하였다.
Response body에 담아 보낼 값을 BasicResponse 클래스로 감싸서 return 한다.

Response body에 딱히 담아 보낼 객체가 없을때 Void를 제네릭 타입으로 담아서 사용했다.


```java
package com.flab.doorrush.global.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BasicResponse<T> {

  private T data;

}
```

```java
  @PostMapping("/{ownerSeq}/addRestaurant")
  public ResponseEntity<BasicResponse<Void>> addRestaurant(
      @Valid @RequestBody AddRestaurantRequest addRestaurantRequest) {
    restaurantService.addRestaurant(addRestaurantRequest);
    return ResponseEntity.status(HttpStatus.OK).build();
  }
```
