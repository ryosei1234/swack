package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DRoomDAO;
import dao.UserDAO;

/**
 * Servlet implementation class CreateDRoomServlet
 */
@WebServlet("/CreateDRoomServlet")
public class CreateDRoomServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username1 = request.getParameter("username1");
		String username2 = request.getParameter("username2");

		if (username2 == null) {
			UserDAO userDAO = new UserDAO();
			ArrayList<String> userlist = userDAO.getUserList();
			request.setAttribute("roomname1", username1);
			request.setAttribute("userlist", userlist);
			request.getRequestDispatcher("WEB-INF/aaaaaaaaa.jsp").forward(request, response);
		} else {
			String roomname = username1 + "&" + username2;

			DRoomDAO droomdao = new DRoomDAO();
			droomdao.saveDRoom(roomname, username1, username2);
			response.sendRedirect("ChatSarvlet");
		}
	}

}
