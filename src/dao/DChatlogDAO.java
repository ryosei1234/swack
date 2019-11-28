package dao;

import static parameter.DAOParameters.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import bean.ChatlogBean;

/**
 * ダイレクトチャットのチャットログデータにアクセスするクラス
 */

public class DChatlogDAO {

	/**
	 * 受け取ったダイレクトチャットのルーム名のチャットログを返却する
	 *
	 * @param roomname ルーム名
	 * @return dchatloglist 受け取ったダイレクトチャットのルーム名のチャットログ
	 */

	public ArrayList<ChatlogBean> getDChatloglist(String roomname) {

		ArrayList<ChatlogBean> dchatloglist = new ArrayList<ChatlogBean>();
		String sql = "SELECT * FROM dchatlog WHERE  roomname=? ORDER BY mdate";
		try {
			Class.forName(DRIVER_NAME);
			try (Connection conn = DriverManager.getConnection(CONNECT_STRING, USERID, PASSWARD);
					PreparedStatement pst = conn.prepareStatement(sql);) {
				pst.setString(1, roomname);
				ResultSet rs = pst.executeQuery();
				while (rs.next()) {
					ChatlogBean chatlog = new ChatlogBean(rs.getInt("SEQ"), rs.getString("roomname"), rs.getString("username"), rs.getString("message"));
					dchatloglist.add(chatlog);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dchatloglist;
	}

	/**
	 * ダイレクトチャットのチャットログに新たにメッセージを追加する
	 *
	 * @param roomname ルーム名
	 * @param username ユーザ名
	 * @param message メッセージ
	 */

	public void saveDChatlog(String roomname, String username, String message) {
		String sql = "INSERT INTO dchatlog VALUES(chatseq.nextval,?,?,?,sysdate)";

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
