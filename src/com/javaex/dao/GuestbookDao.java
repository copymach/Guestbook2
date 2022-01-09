package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.GuestbookVo;

public class GuestbookDao {

//	필드
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb";
	private String pw = "1234";

//생성자	
	public GuestbookDao() {
	}

//메소드gs

//메소드일반
	private void getConnection() {
		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName(driver);
			// 2. Connection 얻어오기
			conn = DriverManager.getConnection(url, id, pw);
		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	} // getConn 종료

	private void close() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	} // close 종료

	public List<GuestbookVo> getList() {
		List<GuestbookVo> guestbookList = new ArrayList<GuestbookVo>();
		getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " select	no ";
			query += " 			,name ";
			query += " 			,password ";
			query += " 			,content ";
			query += " 			,to_char(reg_date, 'yyyy-mm-dd hh:mi:ss') regDate ";
			query += " from guestbook ";
			query += " order by regDate desc ";

			System.out.println(query + " 리스트 작성중");

//			문자열 쿼리문으로 만들기
			pstmt = conn.prepareStatement(query);

//			바인딩 ? 표시가 없어서 할게 없음		

//			실행 rs = result select
			rs = pstmt.executeQuery();

			// 4.결과처리 next는 자체적으로 불린 값을 가지고 있다

			while (rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name");
				String password = rs.getString("password");
				String content = rs.getString("content");
				String regDate = rs.getString("regDate");
				content = content.replace(" ", "&nbsp;");
				content = content.replace("\n", "<br>");

				GuestbookVo guestbookVo = new GuestbookVo(no, name, password, content, regDate);
				guestbookList.add(guestbookVo);

			} // 한줄씩 꺼내서 변수에 담는다

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		// 5. 자원정리
		close();

		return guestbookList;
	} // getList 종료

	public void ContentInsert(GuestbookVo guestbookVo) {
		System.out.println(guestbookVo);
		getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
//		String query = "insert into"; 쿼리문 만들기 -> ?주의
			String query = "";

//		tip 쿼리 에러를 피하기 위해 ""사이를 띄워서 공백을 넣어준다
			query += " INSERT INTO guestbook ";

			query += " VALUES (seq_guestbook_id.nextval, ?, ?, ?, sysdate) ";

			System.out.println(query + " 방명록 추가중");

//		문자열 쿼리문으로 만들기
			pstmt = conn.prepareStatement(query);

//		바인딩		
			pstmt.setString(1, guestbookVo.getName());
			pstmt.setString(2, guestbookVo.getPassword());
			pstmt.setString(3, guestbookVo.getContent());

//		실행
			int count = pstmt.executeUpdate(); // 쿼리문 실행
//		4.결과처리
			System.out.println(count + "건이 저장되었습니다. \t (방명록 글) ");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		// 5. 자원정리
		close();

	} // contentInsert 종료

	// ContentDelete 방명록글 삭제
	public void ContentDelete(int no, String password) {
		System.out.println("ContentDelete");
		System.out.println(no);
		System.out.println(password);
		GuestbookVo ContentDelete = null;
		getConnection();

		try {
			/* DELETE FROM guestbook WHERE no = 1 ; */
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " DELETE FROM guestbook ";
			query += " WHERE no = ? ";
			query += " and password = ? ";

			System.out.println(query + "가 삭제 처리중");

//			문자열을 쿼리문으로 만들기
			pstmt = conn.prepareStatement(query);

//			바인딩
			pstmt.setInt(1, no);
			pstmt.setString(2, password);
			//ContentDelete = new GuestbookVo(no, password);
//			실행
			int count = pstmt.executeUpdate();

//			4.결과처리
			System.out.println(no + "번 방명록 글이" + count + "갯수 만큼 삭제되었습니다.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

//		5.자원 닫기
		close();
//		return ContentDelete;
		
	} // ContentDelete 종료

//	삭제용 getNo for delete.jsp
	public GuestbookVo getNo(int index) {

		GuestbookVo guestbookVo = null;

		this.getConnection();

		try {
			String query = "";
			query += " select	no ";
			query += " 			,name ";
			query += " 			,password ";
			query += " 			,content ";
			query += " 			,to_char(reg_date, 'yyyy-mm-dd hh:mi:ss') regDate ";
			query += " from guestbook ";
			query += " where no = ? ";

			pstmt = conn.prepareStatement(query);

			pstmt.setInt(1, index);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name");
				String password = rs.getString("password");
				String content = rs.getString("content");
				String regDate = rs.getString("regDate");

				guestbookVo = new GuestbookVo(no, name, password, content, regDate);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		this.close();

		return guestbookVo;
	} // getNo 종료

//	삭제용 getPassword for delete.jsp
	public GuestbookVo getPassword(String Password) {

		GuestbookVo guestbookVo = null;

		this.getConnection();

		try {
			String query = "";
			query += " select	no ";
			query += " 			,name ";
			query += " 			,password ";
			query += " 			,content ";
			query += " 			,to_char(reg_date, 'yyyy-mm-dd hh:mi:ss') regDate ";
			query += " from guestbook ";
			query += " where password = ? ";

			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, "password");

			rs = pstmt.executeQuery();

			while (rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name");
				String password = rs.getString("password");
				String content = rs.getString("content");
				String regDate = rs.getString("regDate");

				guestbookVo = new GuestbookVo(no, name, password, content, regDate);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		this.close();

		return guestbookVo;
	} // getPassword 종료


//	삭제용 checkPassword for delete.jsp
	public GuestbookVo checkPassword(int index) {

		GuestbookVo guestbookVo = null;

		this.getConnection();

		try {
			String query = "";
			query += " select	password ";
			query += " from guestbook ";
			query += " where no = ? ";

			pstmt = conn.prepareStatement(query);

			pstmt.setInt(1, index);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				int no = rs.getInt("no");				
				String password = rs.getString("password");

				guestbookVo = new GuestbookVo(no, password);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		this.close();

		return guestbookVo;
	} // checkPassword 종료

	
	

	
	
} // The end of GuestbookDao method.
