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
 * ダイレクトチャットルーム作成サーブレット<br>
 * ダイレクトチャットルームを作成するサーブレット<br>
 * doPost: 画面から値を受け取りダイレクトチャットルームを作成する
 */
@WebServlet("/CreateDRoomServlet")
public class CreateDRoomServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username1 = request.getParameter("username1");
		String username2 = request.getParameter("username2");

		if (username2 == null) {
			UserDAO userDAO = new UserDAO();
			ArrayList<String> userlist = userDAO.getDUserList(username1);
			request.setAttribute("username1", username1);
			request.setAttribute("userlist", userlist);
			request.getRequestDispatcher("WEB-INF/createD.jsp").forward(request, response);
		} else {
			String roomname = username1 + "と" + username2;

			DRoomDAO droomdao = new DRoomDAO();
			droomdao.saveDRoom(roomname, username1, username2);
			response.sendRedirect("ChatSarvlet");
		}
	}

}
