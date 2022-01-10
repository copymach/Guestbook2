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

//			게스트북 에러 확인 에러안남
//			System.out.println(guestbookList);

//		방명록글 써넣기
		} else if ("write".equals(act)) {
			System.out.println("action = write");
			
//			파라미터 4개를 꺼내온다
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String content = request.getParameter("content");
//			String regDate = request.getParameter("regDate");
			
//			vo로 만든다
			GuestbookVo guestbookVo = new GuestbookVo(name, password, content);
			System.out.println(guestbookVo);

//			dao 메모리 올린다
			GuestbookDao guestbookDao = new GuestbookDao();

			guestbookDao.ContentInsert(guestbookVo);

//			리다이렉트
			response.sendRedirect("/guestbook2/gbc?action=addList");
			
			
//		삭제 폼 addList에서 글id 받아서 포워딩해줘야함	
		} else if ("deleteForm".equals(act)) {
			System.out.println("action = deleteForm");
			
//			id 형변환
			int no = Integer.parseInt(request.getParameter("no"));
			
//			숫자로 변경한 no로 대상 방명록글 식별
			GuestbookVo guestbookVo = new GuestbookDao().getNo(no); 
			
//			Action으로 넘어온 값을 변경시킨후 JSP 페이지로 넘겨주기
			request.setAttribute("gbVo", guestbookVo);
			
//			포워드 
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/deleteForm.jsp");
			rd.forward(request, response);
			
			
//		삭제	
		} else if ("delete".equals(act)) {
			System.out.println("action = delete");
			
			GuestbookDao guestbookDao = new GuestbookDao();

			//addList 에서 받아온 id를 숫자로 바꿔준다
			int ContentId = Integer.parseInt(request.getParameter("id"));

			GuestbookVo guestbookVo = guestbookDao.getNo(ContentId);

			int id = guestbookVo.getNo();
			String password = guestbookVo.getPassword();
				
			
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
