package dao;

import static parameter.DAOParameters.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * ダイレクトチャットのルームデータにアクセスするクラス
 */
public class DRoomDAO {

	/**
	 * ログインしているユーザが参加しているダイレクトチャットのルームリストを返却する
	 *
	 * @param username ユーザ名
	 * @return droomlist ログインしているユーザが参加しているダイレクトチャットのルームリスト
	 */
	public ArrayList<String> getDRoomList(String username) {
		ArrayList<String> droomlist = new ArrayList<String>();
		String sql = "SELECT ROOMNAME FROM DROOM WHERE  USERNAME =?";

		try {
			Class.forName(DRIVER_NAME);
			try (Connection conn = DriverManager.getConnection(CONNECT_STRING, USERID, PASSWARD);
					PreparedStatement pst = conn.prepareStatement(sql);) {
				pst.setString(1, username);
				ResultSet rs = pst.executeQuery();
				while (rs.next()) {
					droomlist.add(rs.getString("roomname"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return droomlist;

	}

	/**
	 * ダイレクトチャットのルームを新規に登録する
	 *
	 * @param roomname ルーム名
	 * @param username1 ログイン中のユーザ名
	 * @param username2 ログイン中のユーザに指定されたユーザのユーザ名
	 */
	public void saveDRoom(String roomname, String username1, String username2) {
		String sql = "INSERT ALL INTO droom VALUES(?,?,'true') INTO droom VALUES(?,?,'true') SELECT * FROM DUAL";

		try {
			Class.forName(DRIVER_NAME);
			try (Connection conn = DriverManager.getConnection(CONNECT_STRING, USERID, PASSWARD);
					PreparedStatement pst = conn.prepareStatement(sql);) {
				pst.setString(1, roomname);
				pst.setString(2, username1);
				pst.setString(3, roomname);
				pst.setString(4, username2);
				int cnt = pst.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 受け取ったルーム名がDBに存在しているかどうかを返却する
	 *
	 * @param roomname ルーム名
	 * @return false DBに存在していた場合。 true DBに存在していなかった場合。
	 */
	public boolean checkDRoom(String roomname) {
		String sql = "SELECT roomname FROM droom WHERE roomname = ?";

		try {
			Class.forName(DRIVER_NAME);
			try (Connection conn = DriverManager.getConnection(CONNECT_STRING, USERID, PASSWARD);
					PreparedStatement pst = conn.prepareStatement(sql);) {
				pst.setString(1, roomname);
				ResultSet rs = pst.executeQuery();
				if (rs.next()) {
					return false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;

	}

}
