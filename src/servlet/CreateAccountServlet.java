package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.RegistDAO;

/**
 * Servlet implementation class CreateAccountServlet
 */
@WebServlet("/CreateAccountServlet")
public class CreateAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String mailad = request.getParameter("mailad");
		String password = request.getParameter("password");

		RegistDAO registdao = new RegistDAO();
		if (registdao.canRegist(username, password, mailad)) {//登録成功
			System.out.println("登録成功");
		} else {
			System.out.println("登録失敗");
		}
		request.getRequestDispatcher("login.html").forward(request, response);
	}

}
