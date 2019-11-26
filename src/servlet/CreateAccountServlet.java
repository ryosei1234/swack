package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.RoomDAO;
import dao.UserDAO;
import validation.CheckParameter;

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

		UserDAO userdao = new UserDAO();
		boolean success = false;
		String message = "";

		if (CheckParameter.isNotEmpty(username)) {
			if (CheckParameter.isNotEmpty(mailad)) {
				if (CheckParameter.isNotEmpty(password)) {
					if (userdao.canRegist(username, password, mailad)) {//登録成功
						message = ("登録成功");
						RoomDAO roomDAO = new RoomDAO();
						roomDAO.saveRoom("everyone", username, null);
						success = true;
					} else {
						message = ("このユーザ名は使用されています");
					}
				} else {
					message = ("パスワードが未入力です。");
				}
			} else {
				message = ("メールアドレスが未入力です。");
			}
		} else {
			message = ("ユーザ名が未入力です。");
		}

		System.out.println(message);
		request.setAttribute("message", message);
		request.setAttribute("success", success);
		request.getRequestDispatcher("WEB-INF/access.jsp").forward(request, response);
	}

}
