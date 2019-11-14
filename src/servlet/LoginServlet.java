package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.LoginDAO;
import dao.UserDAO;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mailad = request.getParameter("mailad");
		String password = request.getParameter("password");

		//クッキー取得
		//		Cookie[] cookies = request.getCookies();
		//		if (cookies != null) {
		//			for (Cookie cook : cookies) {
		//				if (cook.getName().equals("mailad")) {
		//					mailad = cook.getValue();
		//				}
		//				if (cook.getName().equals("password")) {
		//					password = cook.getValue();
		//				}
		//			}
		//		}

		HttpSession session = request.getSession();

		//ログインできるかチェック
		UserDAO userDAO = new UserDAO();
		if (userDAO.canLogin(mailad, password)) {
			System.out.println("ログイン成功");
			session.setAttribute("mailad", mailad);
			//クッキー発行////////////////////////////////////////
			if (request.getParameter("checkbox") == "true") {
				Cookie cooksessionid = new Cookie("sessionid", session.getId());
				cooksessionid.setMaxAge(10);
				response.addCookie(cooksessionid);
				LoginDAO logindao = new LoginDAO();
				logindao.sesRegist(session.getId(), mailad, password);
			}
			/////////////////////////////////////////////////////
			//初期画面表示
			response.sendRedirect("ChatSarvlet");
		} else {
			//ログイン失敗
			System.out.println("ログイン失敗");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}

	}

}
