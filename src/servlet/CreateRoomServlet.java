package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.RoomDAO;

/**
 * グループチャットルーム作成サーブレット<br>
 * グループチャットルームを作成するサーブレット<br>
 * doPost: 画面から値を受け取りグループチャットルームを作成する
 */
@WebServlet("/CreateRoomServlet")
public class CreateRoomServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String roomname = request.getParameter("roomadd");
		String username = request.getParameter("username");
		String checkbox = request.getParameter("checkbox");

		RoomDAO roomdao = new RoomDAO();
		if (roomdao.checkRoom(roomname)) {
			roomdao.saveRoom(roomname, username, checkbox);
		} else {
			System.out.println("同じルーム名は作成できません");
		}
		response.sendRedirect("ChatSarvlet");
	}

}
