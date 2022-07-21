프로젝트에서 다국어 처리가 필요하여 언어정보를 가져와야한다.
처음에 request.getHeader(ACCEPT_LANGUAGE) 에서 언어 정보가 en인지 ko 인지 확인하였다.
문제는 요청시 Header에 ACCEPT_LANGUAGE 없이 요청할 수 있기때문에 NullpointException이 발생하였다.
이를 방지하기 위해 Locale에서 언어정보를 가져오도록 수정하였다.


Locale.getDefault().getLanguage() 로 사용해야 한다.

request.getHeader(ACCEPT_LANGUAGE) 는 필수 값이 아니며, 없이 요청될수 있다.
있을때나, 없을시 인터셉터에서 기본 로케일 셋팅을 하므로 Locale을 참조해야 한다.
