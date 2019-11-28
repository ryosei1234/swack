package dao;

import static parameter.DAOParameters.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import security.PasswordUtil;

/**
 * ユーザデータにアクセスするクラス
 */
public class UserDAO {

	/**
	 * ログイン可能かどうかを確認し、結果を返却する
	 *
	 * @param username ユーザ名
	 * @param password パスワード
	 * @return true ユーザが存在した場合。false ユーザが存在しない場合。
	 * @throws SQLException データベースサーバが停止していた場合。
	 */
	public boolean canLogin(String mailad, String password) {

		String hash = PasswordUtil.getSafetyPassword(password, mailad);

		String sql = "SELECT * FROM users WHERE hash = '" + hash + "'";
		try {
			Class.forName(DRIVER_NAME);
			try (Connection conn = DriverManager.getConnection(CONNECT_STRING, USERID, PASSWARD);
					PreparedStatement pst = conn.prepareStatement(sql);) {
				ResultSet rs = pst.executeQuery();

				if (rs.next()) {
					return true;
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * 受け取ったメールアドレスに該当するユーザ名を返却する
	 *
	 * @param mailad メールアドレス
	 * @param password パスワード
	 * @return username メールアドレスが存在した場合。 null メールアドレスが存在しない場合。
	 */
	public String conversion(String mailad, String password) {

		String hash = PasswordUtil.getSafetyPassword(password, mailad);

		String sql = "SELECT username FROM users WHERE hash = '" + hash + "'";

		try {
			Class.forName(DRIVER_NAME);
			try (Connection conn = DriverManager.getConnection(CONNECT_STRING, USERID, PASSWARD);
					PreparedStatement pst = conn.prepareStatement(sql);) {
				ResultSet rs = pst.executeQuery();
				if (rs.next()) {
					String username = rs.getString("username");
					return username;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	/**
	 * ユーザがDBに存在しているかどうかを返却する
	 *
	 * @param username ユーザ名
	 * @return false DBに存在していた場合。 true DBに存在しなかった場合。
	 */
	public boolean checkUser(String username) {
		String sql = "SELECT username FROM users WHERE username = ?";

		try {
			Class.forName(DRIVER_NAME);
			try (Connection conn = DriverManager.getConnection(CONNECT_STRING, USERID, PASSWARD);
					PreparedStatement pst = conn.prepareStatement(sql);) {
				pst.setString(1, username);
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
	 * ルーム招待において、ログイン中のユーザと受け取ったルーム名に既に参加しているユーザ以外のユーザリストを返却する
	 *
	 * @param username ユーザ名
	 * @param roomname ルーム名
	 * @return userlist ログイン中のユーザと受け取ったルーム名に既に参加しているユーザ以外のユーザリスト
	 */
	public ArrayList<String> getUserList(String username, String roomname) {
		ArrayList<String> userlist = new ArrayList<String>();
		String sql = "SELECT distinct u.USERNAME FROM USERS u , ROOM r WHERE u.username != ? AND u.username NOT IN (SELECT username FROM room WHERE roomname = ?)";

		try {
			Class.forName(DRIVER_NAME);
			try (Connection conn = DriverManager.getConnection(CONNECT_STRING, USERID, PASSWARD);
					PreparedStatement pst = conn.prepareStatement(sql);) {
				pst.setString(1, username);
				pst.setString(2, roomname);
				ResultSet rs = pst.executeQuery();
				while (rs.next()) {
					userlist.add(rs.getString("username"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userlist;

	}

	/**
	 * ダイレクトチャットにおいて、ログイン中のユーザと既にチャットを開始しているユーザ以外のユーザリストを返却する
	 *
	 * @param username1 ログイン中のユーザ名
	 * @return userlist ログイン中のユーザと既にチャットを開始しているユーザ以外のユーザリスト
	 */
	public ArrayList<String> getDUserList(String username1) {
		ArrayList<String> userlist = new ArrayList<String>();
		String sql = "SELECT distinct u.USERNAME FROM USERS u , DROOM d WHERE u.username NOT IN (SELECT username FROM droom WHERE roomname IN (SELECT distinct roomname FROM droom WHERE username = ?)) AND u.username != ?";

		try {
			Class.forName(DRIVER_NAME);
			try (Connection conn = DriverManager.getConnection(CONNECT_STRING, USERID, PASSWARD);
					PreparedStatement pst = conn.prepareStatement(sql);) {
				pst.setString(1, username1);
				pst.setString(2, username1);
				ResultSet rs = pst.executeQuery();
				while (rs.next()) {
					userlist.add(rs.getString("username"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userlist;

	}

	/**
	 * ユーザを新規に登録できるかどうかを返却する
	 *
	 * @param username ユーザ名
	 * @param password パスワード
	 * @param mailad メールアドレス
	 * @return true 登録できた場合。 false 登録できなかった場合。
	 */
	public boolean canRegist(String username, String password, String mailad) {

		String hash = PasswordUtil.getSafetyPassword(password, mailad);

		String sql = "INSERT INTO users VALUES(?,'" + hash + "')";

		try {
			Class.forName(DRIVER_NAME);
			try (Connection conn = DriverManager.getConnection(CONNECT_STRING, USERID, PASSWARD);
					PreparedStatement pst = conn.prepareStatement(sql);) {
				pst.setString(1, username);
				int cnt = pst.executeUpdate();

				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;

	}

}