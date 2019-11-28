package dao;

import static parameter.DAOParameters.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * ルームデータにアクセスするクラス
 */
public class RoomDAO {
	/**
	 * ログイン中のユーザが参加しているルームリストを返却する
	 *
	 * @param username ユーザ名
	 * @return roomlist ログイン中のユーザが参加しているルームリスト
	 */
	public ArrayList<String> getRoomList(String username) {
		ArrayList<String> roomlist = new ArrayList<String>();
		String sql = "SELECT ROOMNAME FROM ROOM WHERE  USERNAME =?";

		try {
			Class.forName(DRIVER_NAME);
			try (Connection conn = DriverManager.getConnection(CONNECT_STRING, USERID, PASSWARD);
					PreparedStatement pst = conn.prepareStatement(sql);) {
				pst.setString(1, username);
				ResultSet rs = pst.executeQuery();
				while (rs.next()) {
					roomlist.add(rs.getString("roomname"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return roomlist;

	}

	/**
	 * 新規にルームを作成する
	 *
	 * @param roomname ルーム名
	 * @param username ユーザ名
	 * @param checkbox プライベートルームかどうかを判別するチェックボックスの値
	 */
	public void saveRoom(String roomname, String username, String checkbox) {
		String sql = "INSERT INTO room VALUES(?,?,?)";

		try {
			Class.forName(DRIVER_NAME);
			try (Connection conn = DriverManager.getConnection(CONNECT_STRING, USERID, PASSWARD); //parameterDAO
					PreparedStatement pst = conn.prepareStatement(sql);) {
				pst.setString(1, roomname);
				pst.setString(2, username);
				pst.setString(3, checkbox);
				int cnt = pst.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 受け取ったルーム名がDBに存在するかどうかを返却する
	 *
	 * @param roomname ルーム名
	 * @return false DBに存在する場合。 true DBに存在しない場合。
	 */
	public boolean checkRoom(String roomname) {
		String sql = "SELECT roomname FROM room WHERE roomname = ?";

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

	/**
	 * プライベートルームかどうかを判別するチェックボックスを返却する
	 *
	 * @param roomname ルーム名
	 * @return checkbox ルームがDBに存在した場合。 null ルームがDBに存在しなかった場合。
	 */
	public String getCheckbox(String roomname) {
		String sql = "SELECT private FROM room WHERE roomname = ?";

		try {
			Class.forName(DRIVER_NAME);
			try (Connection conn = DriverManager.getConnection(CONNECT_STRING, USERID, PASSWARD);
					PreparedStatement pst = conn.prepareStatement(sql);) {
				pst.setString(1, roomname);
				ResultSet rs = pst.executeQuery();
				if (rs.next()) {
					String checkbox = rs.getString("private");
					return checkbox;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * パブリックルームとログイン中のユーザが既に参加しているルーム以外のルームリストを返却する
	 *
	 * @param username ユーザ名
	 * @return roomlist パブリックルームとログイン中のユーザが既に参加しているルーム以外のルームリスト
	 */
	public ArrayList<String> getPRoomList(String username) {
		ArrayList<String> roomlist = new ArrayList<String>();
		String sql = "SELECT DISTINCT ROOMNAME FROM ROOM WHERE  PRIVATE IS NULL AND roomname NOT IN(SELECT roomname FROM room WHERE username = ?)";

		try {
			Class.forName(DRIVER_NAME);
			try (Connection conn = DriverManager.getConnection(CONNECT_STRING, USERID, PASSWARD);
					PreparedStatement pst = conn.prepareStatement(sql);) {
				pst.setString(1, username);
				ResultSet rs = pst.executeQuery();
				while (rs.next()) {
					roomlist.add(rs.getString("roomname"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return roomlist;

	}

	/**
	 * ログイン中のユーザがルームを退出するメソッド
	 *
	 * @param username ユーザ名
	 * @param roomname ルーム名
	 */
	public void exitRoom(String username, String roomname) {

		String sql = "DELETE FROM room WHERE roomname = ? AND username = ?";

		try {
			Class.forName(DRIVER_NAME);
			try (Connection conn = DriverManager.getConnection(CONNECT_STRING, USERID, PASSWARD);
					PreparedStatement pst = conn.prepareStatement(sql);) {
				pst.setString(1, roomname);
				pst.setString(2, username);
				int cnt = pst.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
