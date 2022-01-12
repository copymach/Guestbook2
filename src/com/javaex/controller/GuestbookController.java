package com.javaex.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestbookDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.GuestbookVo;

//한글출력 문제 해결하기
//링크 http://localhost:8088/guestbook2/gbc?action=addList
//링크 동작하게 만들기 http://localhost:8088/guestbook2/gbc	

@WebServlet("/gbc") // GuestbookController
public class GuestbookController extends HttpServlet {
	private static final long serialVersionUID = 1L; // 식별자라고 함 생략가능

	public GuestbookController() {
		// 기본생성자 생략가능
	}
	
//	service 상위 호환 doGet doPost 한글깨짐 해결위해 서비스 사용
	protected void doGet (HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		// 1. 받을 때 : 한글깨짐 해결(post 방식)
		request.setCharacterEncoding("UTF-8");    
		// 2. 받을 때 : GET방식 처리
//		String filename = 
//		new  String(request.getParameter("parameter").getBytes("8859_1"),"KSC5601");   
		// 3. 보낼 때 한글처리
		response.setContentType("text/html; charset=UTF-8");   
		
		System.out.println("GuestbookController model2 시작");

		String act = request.getParameter("action");

//		목록으로
		if ("addList".equals(act)) {
			System.out.println("action = addList");

			GuestbookDao guestbookDao = new GuestbookDao();
			List<GuestbookVo> guestbookList = guestbookDao.getList();

//			Servlet간 공유하는 게스트북 객체
			request.setAttribute("gList", guestbookList);
			PrintWriter out = response.getWriter();
			out.println("<head>");

//			포워드
			WebUtil.forward(request, response, "/WEB-INF/addList.jsp");

			
//			게스트북 에러 확인 에러안남
//			System.out.println(guestbookList);

//		방명록글 써넣기
		} else if ("write".equals(act)) {
			System.out.println("action = write");

//			파라미터 3개를 꺼내온다
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String content = request.getParameter("content");

//			vo로 만든다
			GuestbookVo guestbookVo = new GuestbookVo(name, password, content);

//			쓰기 기능 에러체크 - 에러안남 
			System.out.println(guestbookVo);

//			dao 메모리 올린다
			GuestbookDao guestbookDao = new GuestbookDao();

			guestbookDao.ContentInsert(guestbookVo);

//			리다이렉트
			WebUtil.redirect(request, response, "/guestbook2/gbc");

//		삭제 폼 addList에서 글id 받아서 포워딩해줘야함	
		} else if ("deleteForm".equals(act)) {
			System.out.println("action = deleteForm");

//			포워드
			WebUtil.forward(request, response, "/WEB-INF/addList.jsp");

			
//		삭제	
		} else if ("delete".equals(act)) {
			System.out.println("action = delete");

			GuestbookDao guestbookDao = new GuestbookDao();

//			형변환 addList 에서 받아온 no를 숫자로 바꿔준다
			int ContentNo = Integer.parseInt(request.getParameter("no"));

//			deleteForm 에서 가져온 비번 
			String password = request.getParameter("password");

			guestbookDao.ContentDelete(ContentNo, password);

//			리다이렉트
			WebUtil.redirect(request, response, "/guestbook2/gbc");

			
		} else {
			System.out.println("파라미터 값 없음");
		}
		
		
	} // doGet 종료

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
//		post방식은 한글 데이터 주고받을때 무조건 깨진다
//		출력 페이지 한글 표시 문제 해결 (post방식용)
		request.setCharacterEncoding("UTF-8");

		doGet(request, response);

	} // doPost 종료

}