<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
//addList 에서 받아온 no를 숫자로 바꿔준다
//int ContentNo = Integer.parseInt(request.getParameter("no"));

String no = request.getParameter("no");
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
				<td><input type="text" name="no" value="<%=no%>" readonly>
				<input type="hidden" name="action" value="delete">
				</td>
			</tr>

			<tr>
				<td>비밀번호 password</td>
				<td><input type="password" name="password"
					value=" " focus>
					<button type="submit">삭제 Del</button></td>
			</tr>
		</table>
	</form>
	<br>
	<br>
	<a href="/WEB-INF/addList.jsp"> 목록으로 돌아가기 (상대경로)</a>
	<br>


</body>
</html>