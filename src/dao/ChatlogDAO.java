package dao;

import static parameter.DAOParameters.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import bean.ChatlogBean;

public class ChatlogDAO {

	/* DB対応版 */
	public ArrayList<ChatlogBean> getChatloglist(String roomname) {

		ArrayList<ChatlogBean> chatloglist = new ArrayList<ChatlogBean>();
		String sql = "SELECT * FROM chatlog WHERE  roomname=? ORDER BY mdate";
		try {
			Class.forName(DRIVER_NAME);
			try (Connection conn = DriverManager.getConnection(CONNECT_STRING, USERID, PASSWARD); //parameterDAO
					PreparedStatement pst = conn.prepareStatement(sql);) {
				pst.setString(1, roomname);
				ResultSet rs = pst.executeQuery();
				while (rs.next()) {
					ChatlogBean chatlog = new ChatlogBean(rs.getInt("SEQ"), rs.getString("roomname"), rs.getString("username"), rs.getString("message"));
					chatloglist.add(chatlog);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return chatloglist;
	}
	//
	//	public ArrayList<ChatlogBean> getChatloglist(String roomname, HttpSession session) {
	//		//チャットログを取得する
	//		ArrayList<ChatlogBean> chatdata = (ArrayList<ChatlogBean>) session.getAttribute("chatdata");
	//
	//		if (chatdata == null) {
	//			chatdata = new ArrayList<ChatlogBean>();
	//			chatdata.add(new ChatlogBean(1, "everyone", "hoge", "hello"));
	//			chatdata.add(new ChatlogBean(2, "everyone", "ああ", "仮データ"));
	//			chatdata.add(new ChatlogBean(3, "random", "hoge", "こんにちわ"));
	//			chatdata.add(new ChatlogBean(4, "random", "ああ", "こんにちは"));
	//			chatdata.add(new ChatlogBean(5, "everyone", "さとう", "こんにちは！"));
	//			chatdata.add(new ChatlogBean(6, "everyone", "さとう", "こんにちは！"));
	//			chatdata.add(new ChatlogBean(7, "everyone", "さとう", "こんにちは！"));
	//			chatdata.add(new ChatlogBean(8, "news", "ああ", "ニュースnews"));
	//		}
	//		session.setAttribute("chatdata", chatdata);
	//
	//		ArrayList<ChatlogBean> chatloglist = new ArrayList<ChatlogBean>();
	//
	//		for (ChatlogBean bean : chatdata) {
	//			if (bean.getRoomname().equals(roomname)) {
	//				chatloglist.add(bean);
	//			}
	//		}
	//		return chatloglist;
	//	}

	/* DB対応版 */
	public void saveChatlog(String roomname, String username, String message) {
		String sql = "INSERT INTO chatlog VALUES(chatseq.nextval,?,?,?,sysdate)";

		try {
			Class.forName(DRIVER_NAME);
			try (Connection conn = DriverManager.getConnection(CONNECT_STRING, USERID, PASSWARD); //parameterDAO
					PreparedStatement pst = conn.prepareStatement(sql);) {
				pst.setString(1, roomname);
				pst.setString(2, username);
				pst.setString(3, message);
				int cnt = pst.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//	public void saveChatlog(HttpSession session, String roomname, String username, String message) {
	//		//チャットログを取得する
	//		ArrayList<ChatlogBean> chatdata = (ArrayList<ChatlogBean>) session.getAttribute("chatdata");
	//		//データを追加する
	//		chatdata.add(new ChatlogBean(chatdata.size() + 1, roomname, username, message));
	//		session.setAttribute("chatdata", chatdata);
	//	}
}
