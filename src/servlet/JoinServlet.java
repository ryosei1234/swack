package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.RoomDAO;

/**
 * Servlet implementation class JoinServlet
 */
@WebServlet("/JoinServlet")
public class JoinServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String roomname = request.getParameter("roomname");

		if (roomname == null) {
			RoomDAO roomdao = new RoomDAO();
			ArrayList<String> roomlist = roomdao.getPRoomList();
			request.setAttribute("username", username);
			request.setAttribute("roomlist", roomlist);
			request.getRequestDispatcher("WEB-INF/join.jsp").forward(request, response);
		} else {
			RoomDAO roomDAO = new RoomDAO();
			String checkbox = roomDAO.getCheckbox(roomname);
			roomDAO.saveRoom(roomname, username, checkbox);
			response.sendRedirect("ChatSarvlet");
		}

	}

}
