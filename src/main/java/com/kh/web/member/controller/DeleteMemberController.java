package com.kh.web.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.web.member.model.dto.MemberDto;
import com.kh.web.member.model.service.MemberService;

@WebServlet("/delete.me")
public class DeleteMemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DeleteMemberController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 회원탈퇴
		// 값을 뽑고 -> 가공 -> 요청처리 -> 반환결과 응답화면지정

		// STATUS컬럼값을 Y로 변경

		String userPwd = request.getParameter("userPwd");

		// 식별값이 필요하다!

		HttpSession session = request.getSession();
		MemberDto userInfo = (MemberDto)session.getAttribute("userInfo");
		
		if(userInfo == null) {
			session.setAttribute("message", "로그인을 먼저 진행해주세요!");
			response.sendRedirect("/kh/fail.do");
			return;
		}
		Long userNo = userInfo.getUserNo();

		MemberDto member = new MemberDto();
		member.setUserNo(userNo);
		member.setUserPwd(userPwd);
		
		int result = new MemberService().deleteMember(member);

		if(result > 0) {
			session.removeAttribute("userInfo");
			response.sendRedirect("/kh");
		} else {
			session.setAttribute("message", "회원 탈퇴에 실패했습니다.");
			response.sendRedirect("/kh/fail.do");
		}
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
