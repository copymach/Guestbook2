<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="java.util.List, com.javaex.vo.GuestbookVo"%>

<%
//guestbook List 가져오기
//모델1에서는 dao에서 불러왔지만 모델2에서는 getAttribute 사용 
List<GuestbookVo> guestbookList = (List<GuestbookVo>) request.getAttribute("gList");

//테스트
System.out.println(guestbookList.toString());
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>방명록 GuestBook model2</title>
</head>
<body>
<h1>방명록 GuestBook model2</h1>
<p><a href="http://localhost:8088/guestbook2/gbc?action=addList">리스트 절대경로 (To the List)</a>
<br>
	<!-- 방명록 입력 시작 action value write을 넣어서 컨트롤러에 대응하기-->
	<form action="/guestbook2/gbc" method="get">
		<table border="1" width="500px">
			<tr>
				<td>이름(name)</td>
				<td><input type="text" name="name" value=""></td>
				<td>비밀번호(password)</td>
				<td><input type="password" name="password" value="">
				<input type="hidden" name="action" value="write"></td>
			</tr>
			<tr>
				<td colspan="4"><textarea name="content" rows="5" cols="60" 
						placeholder="여기에 인삿말을 적어주세요. Please Write down The Massage here."> </textarea></td>
			</tr>
			<tr>
			</tr>
			
			<tr>
				<td><button type="submit">저장 Post</button></td>
			</tr>
		</table>
	</form>
	<!-- 방명록 입력 종료 -->
	<br>
	
	<!-- 리스트영역 -->
	<%
	for (int i = 0; i < guestbookList.size(); i++) {
	%>
	<form action="/guestbook2/gbc" method=get>
	<table border="1" width="500px">
		<tr>
			<td><input type="text" name="no" value="<%=guestbookList.get(i).getNo()%>"></td>
			<td><%=guestbookList.get(i).getName()%></td>
			<td><%=guestbookList.get(i).getRegDate()%></td>
			
			<td><a href="/guestbook2/deleteForm.jsp?id=<%=guestbookList.get(i).getNo()%>">삭제 Del</a>
			<input type="hidden" name="action" value="deleteForm">
			</td>
		</tr>
		<tr>
			<td colspan="4"><%=guestbookList.get(i).getContent()%></td>
		</tr>
	</table>
	</form>
	<!-- 리스트영역 종료-->
	<br>
	<%
	}
	%>
	
	
</body>
</html>