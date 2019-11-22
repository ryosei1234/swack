<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ルーム招待</title>
<script src="js/jquery-3.3.1.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/invite.css">
</head>
<body>
	<%
		String roomname = request.getParameter("roomname");
	%>
	<div class="container">
		<div class="row">
			<div class="col-md-10  col-md-offset-1">
				<h2>
					#<%=roomname%>に招待
					</h2>
					<p>ルームに招待したいユーザを選択してください。</p>
				<form action="InviteServlet" method="post">
					<div class="div-control">
						<select class="input-control" name="username">
							<%
								ArrayList<String> userlist = (ArrayList<String>) request.getAttribute("userlist");
								for (String list : userlist) {
							%>
							<option>
								<%=list%>
							</option>
							<%
								}
							%>
						</select>
					</div>
					<input type ="hidden" name="roomname" value="<%=roomname%>">
					<input type="submit" value="招待" class="btn">
				</form>
			</div>
		</div>
	</div>
</body>
</html>