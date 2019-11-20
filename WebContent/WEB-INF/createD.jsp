<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ダイレクトメッセージ</title>
<script src="js/jquery-3.2.0.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/invite.css">
</head>
<body>
<%
		String username1 = request.getParameter("username1");
	%>
	<div class="container">
		<div class="row">
			<div class="col-md-10 .col-md-offset-1">
				<h2>ダイレクトメッセージ</h2>
				<p>ダイレクトメッセージ開始したいユーザーを選択してください。</p>
				<form action="CreateDRoomServlet" method="post">
					<div class="div-control">
						<select class="input-control" name="username2">
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
					<input type ="hidden" name="username1" value="<%=username1%>">
						<input type="submit" value="招待" class="btn">
					</div>
				</form>
			</div>
		</div>
	</div>

</body>
</html>