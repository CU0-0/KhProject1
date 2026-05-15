package com.kh.web.board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.kh.web.board.model.dto.AttachmentDto;
import com.kh.web.board.model.dto.BoardDto;
import com.kh.web.board.model.service.BoardService;
import com.kh.web.common.MyRenamePolicy;
import com.kh.web.member.model.dto.MemberDto;
import com.oreilly.servlet.MultipartRequest;

@WebServlet("/update.bo")
public class BoardUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public BoardUpdateController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//String title = request.getParameter("boardTitle");
		//System.out.println(title);
		
		// 값뽑기전
		// multipart방식으로 요청이 잘 왔는지 확인
		if(ServletFileUpload.isMultipartContent(request)) {
			// 1. 전송파일 용량 제한
			int maxSize = 1024 * 1024 * 10;
			
			// 2. 실제 파일을 저장할 물리적인 경로
			String savePath = request.getServletContext()
									 .getRealPath("/resources/board_upfiles");
			
			// Multipart객체를 생성 => 파일 업로드
			MultipartRequest multiRequest =
					new MultipartRequest(request, savePath, maxSize, "UTF-8", new MyRenamePolicy());
			
			// case 1. 첨부파일이 없음				=> BOARD UPDATE + AT X
			// case 2. 기존 첨부파일 0, 새 첨부파일 0 	=> BOARD UPDATE + AT UPDATE
			// case 3. 기존 첨부파일 X, 새 첨부파일 0 	=> BOARD UPDATE + AT INSERT
			
			// DeadLine => 게시글 수정 기능 구현해야지 => 7시에 시작 9시까지는
			
			String boardTitle = multiRequest.getParameter("boardTitle");
			String boardContent = multiRequest.getParameter("boardContent");
			Long boardNo = Long.parseLong(multiRequest.getParameter("boardNo"));
			
			Long userNo = ((MemberDto)request.getSession().getAttribute("userInfo")).getUserNo();
			
			BoardDto board = new BoardDto();
			board.setBoardNo(boardNo);
			board.setBoardTitle(boardTitle);
			board.setBoardContent(boardContent);
			board.setUserNo(userNo);
			
			AttachmentDto at = null;
			
			if(multiRequest.getOriginalFileName("reUpfile") != null) {
				// 새 첨부파일이 있다면 Attachment생성
				at = new AttachmentDto();
				at.setOriginName(multiRequest.getOriginalFileName("reUpfile"));
				at.setChangeName(multiRequest.getFilesystemName("reUpfile"));
				at.setFilePath("resources/board_upfiles");
				at.setFileLevel(2);
				at.setBoardType("C");
				
				// INSERT / UPDATE
				// INSERT => 어떤게시글에 달리는 첨부파일인가 	=> REF_BNO
				// UPDATE => 원래 파일이 몇번째 행인가?		=> FILE_NO
				if(multiRequest.getParameter("fileNo") != null) {
					at.setFileNo(Long.parseLong(multiRequest.getParameter("fileNo")));
				}else {
					at.setRefBno(boardNo);
				}// 데이터 가공
				// 1. 기능 만들어야지 => 2. 요구사항분석 => SQL문을 생각 완성 => 4. 코드작성
				
			}
			int result = new BoardService().updateBoard(board, at);
			HttpSession session = request.getSession();
			String key = "";
			String value = "";
			String path = "";
			if(result > 0) {
				// http://localhost:8088/kh		/detail.bo?boardNo=번호
				//session.setAttribute("alertMsg", "게시글 수정 성공 ~~ ");
				//response.sendRedirect(request.getContextPath() + "/detail.bo?boardNo=" + boardNo);
				key = "alertMsg";
				value = "게시글수정성공";
				path = request.getContextPath() + "/detail.bo?boardNo=" + boardNo;
			}else {
				//session.setAttribute("message", "게시글 수정 실패...");
				//response.sendRedirect(request.getContextPath() + "/fail.do");
				key = "message";
				value = "게시글 수정 실패 ... ";
				path = request.getContextPath() + "/fail.do";
			}
			session.setAttribute(key, value);
			response. sendRedirect(path);
			
			
		}
		
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
