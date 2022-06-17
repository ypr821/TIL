
# 1. @Valid와 @Validated

https://mangkyu.tistory.com/174


# 2. 다양한 제약조건 어노테이션
JSR 표준 스펙은 다양한 제약 조건 어노테이션을 제공하고 있는데, 대표적인 어노테이션으로는 다음과 같은 것들이 있다.

@NotNull: 해당 값이 null이 아닌지 검증함
@NotEmpty: 해당 값이 null이 아니고, 빈 스트링("") 아닌지 검증함(" "은 허용됨)
@NotBlank: 해당 값이 null이 아니고, 공백(""과 " " 모두 포함)이 아닌지 검증함
@AssertTrue: 해당 값이 true인지 검증함
@Size: 해당 값이 주어진 값 사이에 해당하는지 검증함(String, Collection, Map, Array에도 적용 가능)
@Min: 해당 값이 주어진 값보다 작지 않은지 검증함
@Max: 해당 값이 주어진 값보다 크지 않은지 검증함
@Pattern: 해당 값이 주어진 패턴과 일치하는지 검증함

출처: https://mangkyu.tistory.com/174 [MangKyu's Diary:티스토리]


# 3. 실제 프로젝트 적용
  DTO에서 @NotBlank 로 유효성 체크를 하도록하고 Controller 메소드 파라미터에 @Valid를 붙여서 불 충족시 BadRequestException이 발생하도록 하였다. 
  
# 4. 왜 사용했는가 
  하나하나 if문으로 null 체크, 공백 체크해주는 코드를 줄일 수 있어서 편하고 유용하다.