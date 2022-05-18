# ConcurrentModificationException 

		for (MemberMgmtDto.CarInfo myCarInfo : myCarInfoList) { // i 로 수정!!
			if (myCarInfo.getCarEntity().getComCd() != null) {
				companyCarInfoList.add(myCarInfo);
				myCarInfoList.remove(myCarInfoList.indexOf(myCarInfo));
			}
		}
    
  at java.base/java.util.ArrayList$Itr.checkForComodification(ArrayList.java:1013)
	at java.base/java.util.ArrayList$Itr.next(ArrayList.java:967)
	at io.tuneit.admin.api.achakey.service.MemberMgmService.getMember(MemberMgmService.java:114)
	at io.tuneit.admin.api.achakey.service.MemberMgmService$$FastClassBySpringCGLIB$$4373bdd5.invoke(<generated>)
  \
  
  ## 원인 
  삭제 하려는 인덱스가 변해서.
  어떤 쓰레드가 Iterator가 반복중인 Collection을 수정하는 경우 발생한다.

  출처: https://imasoftwareengineer.tistory.com/85 [삐멜 소프트웨어 엔지니어]
  
  
  ## 해결방법
  분리해서 처리 
  
  		//회사차 리스트
		List<MemberMgmtDto.CarInfo> companyCarInfoList = new ArrayList<>();
		for (int i = 0; i < myCarInfoList.size(); i++) {
			String comCd = myCarInfoList.get(i).getCarEntity().getComCd();
			if (comCd != null && comCd.length() >= 1) {
				companyCarInfoList.add(myCarInfoList.get(i));
			}
		}

		for (int i = 0; i < guestCarInfoList.size(); i++) {
			String comCd = guestCarInfoList.get(i).getCarEntity().getComCd();
			if (comCd != null && comCd.length() >= 1) {
				companyCarInfoList.add(guestCarInfoList.get(i));
			}
		}

		for (MemberMgmtDto.CarInfo companyCarInfo : companyCarInfoList) {
			myCarInfoList.remove(companyCarInfo);
			guestCarInfoList.remove(companyCarInfo);
		}
