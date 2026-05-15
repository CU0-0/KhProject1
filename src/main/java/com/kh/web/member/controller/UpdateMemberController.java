package com.kh.web.member.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.web.member.model.dto.MemberDto;
import com.kh.web.member.model.service.MemberService;

@WebServlet("/update.me")
public class UpdateMemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UpdateMemberController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 1) GET? POST?
		// POST => 인코딩
		//request.setCharacterEncoding("UTF-8");
		// 앞으로 필터로 대체하겠음
		
		// 2) 요청시 전달값 뽑아서 가공하기
		String userName = request.getParameter("userName");
		String email = request.getParameter("email");
		
		/*
		 * UPDATE
		 * 		WEB_MEBER
		 * SET
		 * 		USER_NAME = #{userName}
		 * 		, EMAIL = #{email}
		 * WHERE
		 * 		USER_NO = #{userNo}
		 */
		// 2_2) 현재 요청보낸 사용자의 정보 뽑기
		HttpSession session = request.getSession();
		MemberDto member = (MemberDto)session.getAttribute("userInfo");
		Long userNo = member.getUserNo();
		//Long no = ((MemberDto)request.getSession().getAttribute("userInfo")).getUserNo();
		
		// 3) 가공 (DTO를 사용하지 않고)
		// Map
		/*
		Map<String, String> map = new HashMap();
		map.put("userName", userName);
		map.put("email", email);
		map.put("userNo", String.valueOf(userNo));
		
		Map.of() : K-V 10개까지 생성과 동시에 요소 초기화 가능 : 불변맵 반환
		*/
		Map<String, String> map = Map.of("userName", userName
										,"email", email
										,"userNo", String.valueOf(userNo));
		
		// 4) Service단 호출
		MemberDto userInfo = new MemberService().updateMember(map);
		
		// 5) 결과값에 따라서 응답화면 지정
		if(userInfo != null) {
			//member.setEmail(email);
			//member. setUserName(userName);
			session. setAttribute("userInfo", userInfo);
			
			//request.getRequestDispatcher("/WEB-INF/views/member/my_page.jsp").forward(request, response);
			
			// 클라이언트 에게
			// 너 mypage.do로 요청을 보내보지 않을래?
			response.sendRedirect("/kh/mypage.do");
			
		}else {
			request.setAttribute("message", "정보 수정에 실패했습니다 ... ");
			//request.getRequestDispatcher("/WEB-INF/views/common/fail_page.jsp").forward(request, response);
			response.sendRedirect("/kh/fail.do");
		}
		
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
