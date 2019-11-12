<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ルーム招待</title>
<script src="js/jquery-3.2.0.min.js"></script>
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
			<div class=".col-md-6 .col-md-offset-4">
				<h2>
					#<%=roomname%>に招待
					</h2>
				<form action="InviteServlet" method="post">
					<div class="div-control">
						<select class="input-control" name="ユーザー一覧">
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
					<div class="div-control">
						<input type="submit" value="招待" class="btn-left">
					</div>
				</form>
			</div>
		</div>
	</div>

</body>
</html>