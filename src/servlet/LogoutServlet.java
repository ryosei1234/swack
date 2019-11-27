package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * ログアウトサーブレット<br>
 * ユーザのログアウト要求によって動作するクラス <br>
 * doPost:ログアウト処理を実行する
 */
@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.invalidate();
		Cookie cooksessionid = new Cookie("sessionid", "");
		cooksessionid.setMaxAge(0);
		response.addCookie(cooksessionid);

		//ログイン画面へリダイレクト
		response.sendRedirect("login.jsp");
	}

}
