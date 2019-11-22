<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ルーム一覧</title>
<script src="js/jquery-3.3.1.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/join.css">
</head>
<body>
<%
		String username = request.getParameter("username");
	%>
	<div class="container">
		<div class="row">
			<div class="col-md-10 col-md-offset-1">
				<h2>ルーム一覧</h2>
				<p>参加するルームを選択してください。</p>
				<form action="JoinServlet" method="post">
					<div class="div-control">
						<select class="input-control" name="roomname">
							<%
								ArrayList<String> roomlist = (ArrayList<String>) request.getAttribute("roomlist");
								for (String list : roomlist) {
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
						<input type="hidden" name="username" value="<%=username%>">
						<input type="submit" value="参加" class="btn">
					</div>
				</form>
			</div>
		</div>
	</div>

</body>
</html>