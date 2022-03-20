# Shallow copy 와 Deep copy

**Shallow copy** : 주소값을 복사, 참조하고 있는 실제값은 같습니다.

**Deep copy** : 실제값을 새로운 메모리에 복사, 실제값이 다릅니다.



<br><br>

## Shallow copy (얕은 복사)

주소값을 복사, 참조하고 있는 실제값은 같습니다.







<br><br>

## Deep copy (깊은 복사)

실제값을 새로운 메모리에 복사, 실제값이 다릅니다.

### 구현 방법

1. 복사 생성자 또는 복사 팩터리 사용
2. 직접 객체를 생성하여 복사
3. Cloneable을 구현하여 clone() 재정의



<br><br>

참고 자료

https://jackjeong.tistory.com/100

https://zzang9ha.tistory.com/372
