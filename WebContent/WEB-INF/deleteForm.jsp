<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="com.javaex.vo.GuestbookVo"%>

<%

GuestbookVo guestbookVo = (GuestbookVo)request.getAttribute("gbVo");

//addList 에서 받아온 id를 숫자로 바꿔준다
int ContentId = Integer.parseInt(request.getParameter("id"));

int id = guestbookVo.getNo();
String password = guestbookVo.getPassword();
%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> 방명록 글 삭제폼 model2</title>
</head>
<body>

	<h2>글 삭제하려면 비밀번호를 입력하고 삭제버튼을 누르세요.</h2>
	<br>

	<form action="/guestbook2/gbc" method="get">
		<table>
			<tr>
				<td>방명록 글ID (type=hidden 처리예정)</td>
				<td><input type="text" name="no" value="<%=id%>" readonly>
				<input type="hidden" name="action" value="delete">
				</td>
			</tr>

			<tr>
				<td>비밀번호</td>
				<td><input type="password" name="password"
					value="<%=password%>">
					<button type="submit">삭제</button></td>
			</tr>
		</table>
	</form>
	<br>
	<br>
	<a href="/WEB-INF/addList.jsp"> 목록으로 돌아가기 (상대경로)</a>
	<br>


</body>
</html>