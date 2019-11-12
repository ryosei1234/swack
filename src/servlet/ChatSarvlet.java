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

		String username = "";
		if (session.getAttribute("mailad") == null) {
			//不正アクセス
			System.out.println("不正なアクセス");
			request.getRequestDispatcher("login.html").forward(request, response);
			return;
		} else {
			//正常 ユーザー名取得
			UserDAO userDAO = new UserDAO();
			String mailad = (String) session.getAttribute("mailad");
			username = userDAO.conversion(mailad);
		}

		if (roomname == null)
			roomname = "everyone";

		RoomDAO roomDAO = new RoomDAO();
		ArrayList<String> roomlist = roomDAO.getRoomList(username);//所属ルーム取得

		ChatlogDAO chatlogDAO = new ChatlogDAO();
		ArrayList<ChatlogBean> chatloglist = chatlogDAO.getChatloglist(roomname);//チャット取得

		//JSPに値を渡す
		request.setAttribute("roomlist", roomlist);
		request.setAttribute("chatloglist", chatloglist);
		request.setAttribute("username", username);
		request.setAttribute("roomname", roomname);

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

		HttpSession session = request.getSession();

		if (!message.equals("")) {
			ChatlogDAO chatlogDAO = new ChatlogDAO();
			chatlogDAO.saveChatlog(roomname, username, message);
		}

		//GET処理にリダイレクトs
		response.sendRedirect("ChatSarvlet?roomname=" + roomname);
	}
}
