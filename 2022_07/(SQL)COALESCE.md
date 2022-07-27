# COALESCE

```java
SELECT A, B, COALESCE(A,B) FROM table_a;
```
## 설명 
A 값이 null 인 경우, B 값 출력
A 값이 null이 아닌 경우, A 값 출력

select
coalesce(sum(t.distance),?) 
from tb_trip t

해당 쿼리문에서 sum할 대상이 없는 경우 sum이 실행조차 되지않아서 결과값으로 null을 받았다.

coalesce(postgreSql), nullif(mySql)을 사용해서 null 체크를 할 수 있다.

querydsl에서 사용해봄
ExpressionUtils.as(JPAExpressions.select((entity.distance).sum().coalesce(0)).from(entity)
                                        .where(addressEntity.groupId.eq(entity.groupId)), "distance")
