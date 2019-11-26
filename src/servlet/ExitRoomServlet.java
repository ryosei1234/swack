package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.RoomDAO;

/**
 * Servlet implementation class ExitRoomServlet
 */
@WebServlet("/ExitRoomServlet")
public class ExitRoomServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String roomname = request.getParameter("roomname");

		RoomDAO roomdao = new RoomDAO();
		roomdao.exitRoom(username, roomname);
		response.sendRedirect("ChatSarvlet");
	}

}
