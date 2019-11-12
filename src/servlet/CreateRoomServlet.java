package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.RoomDAO;

/**
 * Servlet implementation class CreateRoomServlet
 */
@WebServlet("/CreateRoomServlet")
public class CreateRoomServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String roomname = request.getParameter("roomadd");
		String username = request.getParameter("username");

		RoomDAO roomdao = new RoomDAO();
		if (roomdao.checkRoom(roomname)) {
			roomdao.saveRoom(roomname, username);
		} else {
			System.out.println("同じルーム名は作成できません");
		}
		response.sendRedirect("ChatSarvlet");
	}

}
