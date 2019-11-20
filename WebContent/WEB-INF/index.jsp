<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="bean.ChatlogBean"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Swack</title>
<script src="js/jquery-3.2.0.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/style.css">
<!--<script>
	const timer = 10000
	window.addEventListener('load', function() {
		setInterval('location.reload()', timer);
	});
</script>-->
</head>
<body>
	<jsp:useBean id="username" class="java.lang.String" scope="request" />
	<jsp:useBean id="roomname" class="java.lang.String" scope="request" />
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-10 col-md-offset-2 header ">
				<h2>
					#<%=roomname%></h2>
			</div>
		</div>
		<!-- header -->
		<div class="row">
			<div class="col-md-2 left">
				<form action="LogoutServlet" method="post">
					<input type="submit" value="ログアウト" class="btn">
				</form>
				<h4>
					ユーザ名：<%=username%><br> ルーム名：<%=roomname%></h4>

				<br>

				<h4 class="channel-top">ルーム</h4>
				<form action="InviteServlet" method="post">
					<input type="hidden" name="roomname" value="<%=roomname%>">
					<input type="submit" value="ルーム招待" class="btn">
				</form>
				<%
					ArrayList<String> roomlist = (ArrayList<String>) request.getAttribute("roomlist");
					for (String list : roomlist) {
				%>
				<a class="roomlist-name" href="ChatSarvlet?roomname=<%=list%>">#
					<%=list%></a><br>
				<%
					}
				%>

				<form action="CreateRoomServlet" method="post">
					<input type="hidden" name="username" value=<%=username%>> <input
						type="text" name="roomadd" placeholder="ルーム名"> <input
						type="submit" value="追加" class="btn">
				</form>
				<br>
				<h4 class="channel-top">ダイレクトメッセージ</h4>
				<form action="CreateDRoomServlet" method="post">
					<input type="hidden" name="username1" value=<%=username%>>
					<input type="submit" value="ユーザー選択" class="btn">
				</form>
				<%
					ArrayList<String> droomlist = (ArrayList<String>) request.getAttribute("droomlist");
					for (String list : droomlist) {
				%>
				<a class="roomlist-name"
					href="ChatSarvlet?roomname=<%=list%> direct="true"> <%=list%></a><br>
				<%
					}
				%>

			</div>
			<div class="col-md-10 col-md-offset-2  right">
				<div class="row">
					<div class="col-md-12">
						<%
							ArrayList<ChatlogBean> chatloglist = (ArrayList<ChatlogBean>) request.getAttribute("chatloglist");
							for (ChatlogBean bean : chatloglist) {
						%>
						[<%=bean.getUsername()%>]
						<%=bean.getMessage()%>
						<br> <br>
						<%
							}
						%>
					</div>
				</div>
			</div>
		</div>
		<!-- row -->
	</div>
	<!-- container -->
	<footer>
		<form action="ChatSarvlet" method="post">
			<input type="hidden" name="roomname" value=<%=roomname%>>
			<input type="hidden" name="username" value=<%=username%>>
			<input type="text" name="message" placeholder="Hello World" class="message">
			<input type="submit" value="送信" class="btn">
		</form>
	</footer>
</body>
</html>