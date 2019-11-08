<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="bean.ChatlogBean"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Swack</title>
<link rel="stylesheet" href="css/style.css">
</head>
<body>

	<jsp:useBean id="username" class="java.lang.String" scope="request" />
	<jsp:useBean id="roomname" class="java.lang.String" scope="request" />

	<div class="container">

		<div class="left">
			<form action="LogoutServlet" method="post">
				<input type="submit" value="ログアウト">
			</form>
			ユーザ名：<%=username%><br> ルーム名：<%=roomname%>
			<br> <br>
			<h3 class="channel-top">チャンネル</h3>
			<%
				ArrayList<String> roomlist = (ArrayList<String>) request.getAttribute("roomlist");
				for (String list : roomlist) {
			%>
			<a class="roomlist-name" href="ChatSarvlet?roomname=<%=list%>">#
				<%=list%></a><br>
			<%
				}
			%>
		</div>

		<div class="contents">

			<div class="send-form">
				<form action="ChatSarvlet" method="post">
					<input type="hidden" name="roomname" value=<%=roomname%>> <input
						type="hidden" name="username" value=<%=username%>> <input
						type="text" name="message"> <input type="submit"
						value="送信">
				</form>
			</div>

			<hr>

			<h2>チャット一覧</h2>
			<%
				ArrayList<ChatlogBean> chatloglist = (ArrayList<ChatlogBean>) request.getAttribute("chatloglist");

				for (ChatlogBean bean : chatloglist) {
			%>
			[<%=bean.getUsername()%>]
			<%=bean.getMessage()%>
			<br>
			<%
				}
			%>
		</div>

	</div>
	<!-- container -->
</body>
</html>