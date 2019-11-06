package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		HttpSession session = request.getSession();

		//ログインできるかチェック
		UserDAO userDAO = new UserDAO();
		if (userDAO.canLogin(username, password)) {
			System.out.println("ログイン成功");
			session.setAttribute("username", username);
			//初期画面表示
			response.sendRedirect("ChatSarvlet");
		} else {
			//ログイン失敗
			System.out.println("ログイン失敗");
			request.getRequestDispatcher("login.html").forward(request, response);
		}

	}

}
