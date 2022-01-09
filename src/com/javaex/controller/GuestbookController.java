package com.javaex.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestbookDao;
import com.javaex.vo.GuestbookVo;

@WebServlet("/gbc") // GuestbookController
public class GuestbookController extends HttpServlet {
	private static final long serialVersionUID = 1L; //식별자라고 함 생략가능
       
    public GuestbookController() {
    	//기본생성자 생략가능
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		System.out.println("GuestbookController 시작");

		String act = request.getParameter("action");
		
		
//		목록으로
		if ("addList".equals(act)) {
			System.out.println("action = addList");
			
			GuestbookDao guestbookDao = new GuestbookDao();
			List<GuestbookVo> guestbookList = guestbookDao.getList();
			
//			Servlet간 공유하는 게스트북 객체
			request.setAttribute("gList", guestbookList);
			PrintWriter out = response.getWriter(); out.println("<head>");
			
//			포워드
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/addList.jsp");
			rd.forward(request, response);

//			게스트북 에러 확인
			System.out.println(guestbookList);

//		방명록글 써넣기
		} else if ("write".equals(act)) {
			System.out.println("action = write");
			
//			파라미터 4개를 꺼내온다
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String content = request.getParameter("content");
			//String regDate = request.getParameter("regDate");
			
//			vo로 만든다
			GuestbookVo guestbookVo = new GuestbookVo(name, password, content);
			System.out.println(guestbookVo);

//			dao 메모리 올린다
			GuestbookDao guestbookDao = new GuestbookDao();

			guestbookDao.ContentInsert(guestbookVo);

//			포워드 -> 서버 내 사이클 돌때
			response.sendRedirect("/phonebook2/pbc?action=addList");
			
			
//		삭제 폼	
		} else if ("deleteForm".equals(act)) {
			System.out.println("action = deleteForm");
		
			
//		삭제	
		} else if ("delete".equals(act)) {
			System.out.println("action = delete");
			
			
		} else {
			System.out.println("파라미터 값 없음");
		}
		
	} // doGet 종료

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//		출력 페이지 한글 표시 문제 해결
		request.setCharacterEncoding("UTF-8");
		
		doGet(request, response);
		
	} // doPost 종료

}
