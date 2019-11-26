package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.RoomDAO;
import dao.UserDAO;

/**
 * Servlet implementation class InviteServlet
 */
@WebServlet("/InviteServlet")
public class InviteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String roomname = request.getParameter("roomname");
		String username = request.getParameter("username");
		String myusername = request.getParameter("myusername");

		if (username == null) {
			UserDAO userDAO = new UserDAO();
			ArrayList<String> userlist = userDAO.getUserList(myusername, roomname);
			request.setAttribute("roomname", roomname);
			request.setAttribute("userlist", userlist);
			request.getRequestDispatcher("WEB-INF/Invite.jsp").forward(request, response);
			return;
		} else {
			RoomDAO roomDAO = new RoomDAO();
			String checkbox = roomDAO.getCheckbox(roomname);
			roomDAO.saveRoom(roomname, username, checkbox);
			response.sendRedirect("ChatSarvlet");
		}
	}

}
