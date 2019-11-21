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
<script type="text/javascript">
	var webSocket;
	window.onload = function() {
		var forRtoA = document.createElement('a');
		forRtoA.href = "loadMessage";
		webSocket = new WebSocket(forRtoA.href.replace("http://", "ws://").replace("https://", "wss://"));
		var messageArea = document.getElementById("messageArea");
		var appendMessage = function(value, color) {
			var messageElement = document.createElement("div");
			messageElement.style.color = color;
			messageElement.innerText = value;
			messageArea.insertBefore(messageElement, messageArea.lastChild);
		}
		webSocket.onopen = function() {
			appendMessage("Opened", "blue");
		}
		webSocket.onclose = function() {
			appendMessage("Closed", "red");
		}
		webSocket.onmessage = function(message) {
			var data = JSON.parse(message.data);
			if ("message" == data.command) {
				appendMessage(data.messages, "black");
			} else if ("error" == data.command) {
				appendMessage(data.text, "red");
			}
		}
		webSocket.onerror = function(message) {
			appendMessage(message, "red");
		}
		var messageInput = document.getElementById("messageInput");
		var roomnameInput= document.getElementById("roomnameInput");
		var usernameInput = document.getElementById("usernameInput");
		var directInput = document.getElementById("directInput")
		messageInput.onkeypress = function(e) {
			if (13 == e.keyCode) {
			var roomname=roomnameInput.value;
			var username = usernameInput.value;
			var message = messageInput.value;
			var direct = directInput.value;
			var Info=roomname +" "+username+" "+message+" "+direct;
				if (webSocket && "" != message) {
					webSocket.send(Info);
					messageInput.value = "";
				}

			}
		}
	}
</script>
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
			<input type="hidden" name="roomname" id="roomnameInput" value=<%=roomname%>>
			<input type="hidden" name="username" id="usernameInput" value=<%=username%>>
			<input type="hidden" name="direct" id="directInput" value=<%=direct%>>
			<input type="text" name="message" class="message" id="messageInput">
			<input type="submit" value="送信" class="btn">
	</footer>
</body>
</html>