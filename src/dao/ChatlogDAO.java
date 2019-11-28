package dao;

import static parameter.DAOParameters.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import bean.ChatlogBean;

/**
 * ルームのチャットログデータにアクセスするクラス
 */
public class ChatlogDAO {

	/**
	 * 受け取ったルーム名のルームのチャットログを送信日時の昇順にして返却する
	 *
	 * @param roomname ルーム名
	 * @return chatloglist 受け取ったルーム名のルームのチャットログ
	 */

	public ArrayList<ChatlogBean> getChatloglist(String roomname) {

		ArrayList<ChatlogBean> chatloglist = new ArrayList<ChatlogBean>();
		String sql = "SELECT * FROM chatlog WHERE  roomname=? ORDER BY mdate";
		try {
			Class.forName(DRIVER_NAME);
			try (Connection conn = DriverManager.getConnection(CONNECT_STRING, USERID, PASSWARD);
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

	/**
	 * チャットログに新たにメッセージを追加するメソッド
	 *
	 * @param roomname ルーム名
	 * @param username ユーザ名
	 * @param message メッセージ
	 */

	public void saveChatlog(String roomname, String username, String message) {
		String sql = "INSERT INTO chatlog VALUES(chatseq.nextval,?,?,?,sysdate)";

		try {
			Class.forName(DRIVER_NAME);
			try (Connection conn = DriverManager.getConnection(CONNECT_STRING, USERID, PASSWARD);
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

}
