package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.RoomDAO;

/**
 * ルーム退室サーブレット<br>
 * ルーム退室をする際動作するサーブレット<br>
 * doPost: 画面から値を受けとったルームから退室する
 */
@WebServlet("/ExitRoomServlet")
public class ExitRoomServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String roomname = request.getParameter("roomname");

		if (!roomname.equals("everyone")) {
			RoomDAO roomdao = new RoomDAO();
			roomdao.exitRoom(username, roomname);
		}
		response.sendRedirect("ChatSarvlet");
	}

}
