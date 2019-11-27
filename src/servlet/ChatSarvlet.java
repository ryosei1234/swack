package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ChatlogBean;
import dao.ChatlogDAO;
import dao.DChatlogDAO;
import dao.DRoomDAO;
import dao.RoomDAO;
import dao.UserDAO;

/**
 * チャットサーブレット<br>
 * チャット画面を表示するサーブレット<br>
 * doGet: 受け取った情報を元にチャット情報を取得する
 */
@WebServlet("/ChatSarvlet")
public class ChatSarvlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();

		//画面から取得
		String roomname = request.getParameter("roomname");
		String direct = request.getParameter("direct");
		if (direct == null) {
			direct = "false";
		}

		String username = "";
		if (session.getAttribute("mailad") == null) {
			//不正アクセス
			System.out.println("不正なアクセス");
			request.getRequestDispatcher("login.jsp").forward(request, response);
			return;
		} else {
			//正常 ユーザー名取得
			UserDAO userDAO = new UserDAO();
			String mailad = (String) session.getAttribute("mailad");
			String password = (String) session.getAttribute("password");
			username = userDAO.conversion(mailad, password);
		}

		if (roomname == null)
			roomname = "everyone";

		RoomDAO roomDAO = new RoomDAO();
		DRoomDAO droomDAO = new DRoomDAO();
		ArrayList<String> roomlist = roomDAO.getRoomList(username);//所属ルーム取得
		ArrayList<String> droomlist = droomDAO.getDRoomList(username);

		ChatlogDAO chatlogDAO = new ChatlogDAO();
		DChatlogDAO dchatlogDAO = new DChatlogDAO();
		System.out.println("chatservletのgetのほうのdirectは" + direct);
		if (direct.equals("false")) {
			ArrayList<ChatlogBean> chatloglist = chatlogDAO.getChatloglist(roomname);//チャット取得
			request.setAttribute("chatloglist", chatloglist);
		} else {
			ArrayList<ChatlogBean> chatloglist = dchatlogDAO.getDChatloglist(roomname);//ダイレクトチャット取得
			request.setAttribute("chatloglist", chatloglist);
		}

		//JSPに値を渡す
		request.setAttribute("roomlist", roomlist);
		request.setAttribute("droomlist", droomlist);
		request.setAttribute("username", username);
		request.setAttribute("roomname", roomname);
		request.setAttribute("direct", direct); //ここ

		//JSP呼び出し
		request.getRequestDispatcher("WEB-INF/index.jsp").forward(request, response);
	}
}
