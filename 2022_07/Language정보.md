Locale.getDefault().getLanguage() 로 사용해야 합니다.

request.getHeader(ACCEPT_LANGUAGE) 는 필수 값이 아니며, 없이 요청될수 있습니다.
있을때나, 없을시 인터셉터에서 기본 로케일 셋팅을 함으로, Locale을 참조해야 합니다.
