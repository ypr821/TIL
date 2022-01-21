

## 낙관적인 lock vs 비관적인 lock
- 낙관적인 lock : 수정할 때 내가 먼저 이 값을 수정했다고 명시하여 다른 사람이 동일한 조건으로 값을 수정할 수 없게 하는 것입니다.
DB에서 제공해주는 특징을 이용하는 것이 아닌 Application Level에서 잡아주는 Lock입니다. version 등의 구분 컬럼을 이용해서 충돌을 예방합니다.
- 비관적인 lock : Reeatable Read 또는 Serializable 정도의 격리성 수준에서 가능합니다.
트랜잭션이 시작될 때 Shared Lock 또는 Exclusive Lock을 걸고 시작하는 방법입니다. (선락 후처리)
