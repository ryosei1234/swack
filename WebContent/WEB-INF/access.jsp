<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>確認画面</title>
<script src="js/jquery-3.2.0.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/access.css">
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-md-3"></div>
			<div class="col-md-6">
				<%
					if ((boolean) request.getAttribute("success")) {
				%>
				<h3>会員登録が完了しました。</h3>
				<form action="login.jsp">
					<input type="submit" value="完了" class="input-control btn-right">
				</form>
				<%
					} else {
				%>
				<h3>会員登録に失敗しました。</h3>
				<h5>既にこのメールアドレスは使用されています。</h5>
				<h3 class="right">新規会員登録</h3>
				<h5 class="right">氏名、メールアドレス、パスワードを入力してください。</h5>
				<form action="CreateAccountServlet" method="post">
					<div class="div-control-right">
						<input type="text" name="username" placeholder="氏名"
							class="input-control">
					</div>
					<div class="div-control-right">
						<input type="email" name="mailad" placeholder="メールアドレス"
							class="input-control">
					</div>
					<div class="div-control-right">
						<input type="password" name="password" placeholder="パスワード"
							class="input-control">
					</div>
					<div class="div-control-right">
						<input type="submit" value="登録する" class="input-control btn-right">
					</div>
				</form>
				<%
					}
				%>
			</div>
			<div class="col-md-3"></div>
		</div>
	</div>

</body>
</html>