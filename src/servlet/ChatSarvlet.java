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
import security.SecurityUtil;

/**
 * Servlet implementation class ChatSarvlet
 */
@WebServlet("/ChatSarvlet")
public class ChatSarvlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
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

		//TODO 初期部屋
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

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//画面から取得
		String str = request.getParameter("message");
		String message = SecurityUtil.getESCEncodingString(str);
		String roomname = request.getParameter("roomname");
		String username = request.getParameter("username");
		String direct = request.getParameter("direct");

		System.out.println("postのほうのchatservletのdirectは" + direct);
		HttpSession session = request.getSession();

		if (!message.equals("")) {
			if (direct.equals("false")) {
				ChatlogDAO chatlogDAO = new ChatlogDAO();
				chatlogDAO.saveChatlog(roomname, username, message);
			} else {
				//ここから
				DChatlogDAO dchatlogDAO = new DChatlogDAO();
				dchatlogDAO.saveDChatlog(roomname, username, message);
				//ここまで
			}
		}

		//GET処理にリダイレクトs
		response.sendRedirect("ChatSarvlet?roomname=" + roomname + "&direct=" + direct);
	}
}
