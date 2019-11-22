<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="bean.ChatlogBean"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Swack</title>
<script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/websocket.js"></script>
<script type="text/javascript" src="js/modal.js"></script>
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/modal.css">
</head>
<body>
	<jsp:useBean id="username" class="java.lang.String" scope="request" />
	<jsp:useBean id="roomname" class="java.lang.String" scope="request" />
	<jsp:useBean id="direct" class="java.lang.String" scope="request" />
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
				<h4 class="channel-top head">ルーム</h4>
				<!-- ルーム一覧 -->
				<form action="JoinServlet" method="post" class="channel-top">
					<input type="submit" value="ルーム一覧" class="btn">
				</form>
				<!-- ルーム招待 -->
				<form action="InviteServlet" method="post" class="channel-top">
					<input type="hidden" name="roomname" value="<%=roomname%>">
					<input type="submit" value="ルーム招待" class="btn">
				</form>
				<!-- ルーム作成 -->
				<dl class="channel-top">
					<dt>
						<button type="submit" id="modal-open" class="btn">ルーム作成</button>
					</dt>
					<dd class="modal">
						<h3>ルーム作成</h3>
						<form action="CreateRoomServlet" method="post">
							<input type="hidden" name="username" value=<%=username%>>
							<input type="text" name="roomadd" placeholder="ルーム名"> <input
								type="submit" value="追加" class="btn"><br> <input
								type="checkbox" name="checkbox" value="true">公開範囲【プライベート】
						</form>
					</dd>
				</dl>
				<%
					ArrayList<String> roomlist = (ArrayList<String>) request.getAttribute("roomlist");
					for (String list : roomlist) {
				%>
				<a class="roomlist-name" href="ChatSarvlet?roomname=<%=list%>">#
					<%=list%></a><br>
				<%
					}
				%>
				<h4 class="channel-top head">ダイレクトメッセージ</h4>
				<!-- DMユーザー選択 -->
				<form action="CreateDRoomServlet" method="post">
					<input type="hidden" name="username1" value=<%=username%>>
					<input type="submit" value="ユーザー選択" class="btn">
				</form>
				<%
					ArrayList<String> droomlist = (ArrayList<String>) request.getAttribute("droomlist");
					for (String list : droomlist) {
				%>
				<a class="roomlist-name"
					href="ChatSarvlet?roomname=<%=list%>&direct=true"> <%=list%></a><br>
				<%
					}
				%>

			</div>
			<div class="col-md-10 col-md-offset-2  right">
				<div class="row">
					<div class="col-md-12">
						<div id="messageArea">
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
		</div>
		<!-- row -->
	</div>
	<!-- container -->
	<footer>
		<input type="hidden" name="roomname" id="roomnameInput"
			value=<%=roomname%>> <input type="hidden" name="username"
			id="usernameInput" value=<%=username%>> <input type="hidden"
			name="direct" id="directInput" value=<%=direct%>> <input
			type="text" name="message" class="message" id="messageInput">
		<input type="submit" value="送信" class="btn">
	</footer>
</body>
</html>