<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>KH 501</title>
</head>
<body>
	<!-- 
		WEB환경에서의 CRUD
		
		실습 겸 숙제				==> 나만의 회원 서비스 만들기
		05/11 오늘의 숙제(수까지) 	==> 공지사항 목록 조회 구현 => 화면, 테이블, 페이징처리
		05/12 오늘의 숙제(수까지) 	==> 공지사항 작성(첨부파일×, 사용자검증), 상세조회, 삭제구현
		
		AI활용하기  
		
		1. 에러메시지 검색용 => 구글이나 네이버에 검색하지말고 이렇게 찾아보자
		=> 이거 왜이래 X, 이런 문제가 발생했는데 나랑 동일한 문제 발생한 URL을 검색해서 링크를 줘 O
		2. 모르는 문법/메소드 의미 물어보기(forEach태그 이거 뭐야? 어떻게 써 ?? )
		--------------------------------------------------------------
		AI활용 X
		
		1. 공지사항 목록 조회 구현해줘 X
		2. 본인이 이해 못하는 코드를 그대로 붙혀넣기 X
		
		-------------------------------------------------------------------
		
		실습 => 회원가입 흐름 그림판으로 그려보기
		
		
		
		* 회원서비스
		로그인/로그아웃, 회원가입, 내정보조회, 내정보변경, 비밀번호변경, 회원탈퇴
		
		* 일반게시판서비스
		게시글목록조회(페이징처리), 게시글작성(첨부파일 1개 업로드), 상세조회, 게시글수정, 게시글삭제,
		댓글서비스, 게시글검색
		
		* 사진게시판서비스
		사진게시글목록조회(썸네일), 상세조회, 게시글작성(다중파일업로드)
	 -->
	 
	 <jsp:include page="WEB-INF/views/include/header.jsp" />
	 <jsp:include page="WEB-INF/views/include/main.jsp" />
	 <jsp:include page="WEB-INF/views/include/footer.jsp" />
	 <!-- 
	 	Java, Oracle, JDBC, MyBatis
	 	
		+ Network(Web) -> 클라이언트, 서버, DB
		
		HTML, CSS, JS
	  -->
	  
	  <a href="insert.notice">노티스 인서트</a>
	  <hr>
	  <a href="select.notice">노티스 셀렉트</a>

</body>
</html>