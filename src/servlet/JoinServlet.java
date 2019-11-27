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
 * ルーム参加サーブレット<br>
 * ルーム参加するとき動作するサーブレット<br>
 * doPost: 画面から参加するルームを受け取り参加する
 */
@WebServlet("/JoinServlet")
public class JoinServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String roomname = request.getParameter("roomname");

		if (roomname == null) {
			RoomDAO roomdao = new RoomDAO();
			ArrayList<String> roomlist = roomdao.getPRoomList(username);//パブリックルーム取得
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
