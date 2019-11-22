package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.Calculation;
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mailad = request.getParameter("mailad");
		String password = request.getParameter("password");
		String check = request.getParameter("checkbox");
		if (check == null) {
			check = "false";
		}

		System.out.println(mailad);
		System.out.println(password);

		//クッキーでログイン
		Cookie[] cookies = request.getCookies();
		String sessionid = "";
		if (cookies != null) {
			for (Cookie cook : cookies) {
				if (cook.getName().equals("sessionid")) {
					sessionid = cook.getValue();
					LoginDAO logindao = new LoginDAO();
					Calculation value = logindao.sesSerch(sessionid);
					mailad = value.mailad;
					password = value.password;
					Cookie recooksessionid = new Cookie("sessionid", sessionid);
					recooksessionid.setMaxAge(60 * 60 * 24);
					response.addCookie(recooksessionid);
					if (mailad == null) {
						Cookie cooksessionid = new Cookie("sessionid", "");
						cooksessionid.setMaxAge(0);
						response.addCookie(cooksessionid);
						System.out.println("クッキーログイン失敗");
						request.getRequestDispatcher("login.jsp").forward(request, response);
					}
				}
			}
		}

		LoginDAO logindao = new LoginDAO();
		HttpSession session = request.getSession();
		//ログインできるかチェック
		UserDAO userDAO = new UserDAO();
		if (userDAO.canLogin(mailad, password)) {
			System.out.println("ログイン成功");
			session.setAttribute("mailad", mailad);
			session.setAttribute("password", password);

			//クッキー発行//////////////////////////////////////↓
			System.out.println(check);
			if (check.equals("true")) {
				Cookie cooksessionid = new Cookie("sessionid", session.getId());
				cooksessionid.setMaxAge(60 * 3);
				response.addCookie(cooksessionid);
				logindao.sesRegist(session.getId(), mailad, password);
			}
			///////////////////////////////////////////////////↑
			//初期画面表示
			response.sendRedirect("ChatSarvlet");
		} else {
			//ログイン失敗
			System.out.println("ログイン失敗");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}

	}

}
