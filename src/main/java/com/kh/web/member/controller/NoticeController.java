package com.kh.web.member.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.web.board.model.dto.NoticeDto;
import com.kh.web.board.model.service.NoticeService;
import com.kh.web.common.model.dto.PageInfo;

@WebServlet("/notice.bo")
public class NoticeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public NoticeController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	        int listCount;    
	        int currentPage;  
	        int pageLimit;    
	        int boardLimit;   
	        int maxPage;     
	        int startPage;    
	        int endPage;     

	        NoticeService service = new NoticeService();

	        listCount = service.selectNoticeCount();

	        String pageParam = request.getParameter("page");
	        currentPage = (pageParam != null) ? Integer.parseInt(pageParam) : 1;

	        pageLimit  = 5;   
	        boardLimit = 10; 

	        maxPage = (int) Math.ceil((double) listCount / boardLimit);
	        if (maxPage == 0) maxPage = 1; 

	        if (currentPage < 1)        currentPage = 1;
	        if (currentPage > maxPage)  currentPage = maxPage;

	        startPage = (currentPage - 1) / pageLimit * pageLimit + 1;

	        endPage = startPage + pageLimit - 1;
	        if (endPage > maxPage) {
	            endPage = maxPage;
	        }

	        int offset = (currentPage - 1) * boardLimit;

	        PageInfo pi = new PageInfo(listCount, currentPage, pageLimit, boardLimit,
	                                   startPage, endPage, maxPage, offset);

	        List<NoticeDto> notices = service.selectNoticeList(pi);

	        request.setAttribute("pi", pi);
	        request.setAttribute("notices", notices);

	        request.getRequestDispatcher("/WEB-INF/views/board/notice.jsp")
	               .forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
