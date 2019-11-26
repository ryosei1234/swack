package dao;

import static parameter.DAOParameters.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import security.PasswordUtil;

public class UserDAO {

	public boolean canLogin(String mailad, String password) {

		String hash = PasswordUtil.getSafetyPassword(password, mailad);

		String sql = "SELECT * FROM users WHERE hash = '" + hash + "'";
		try {
			Class.forName(DRIVER_NAME);
			try (Connection conn = DriverManager.getConnection(CONNECT_STRING, USERID, PASSWARD);
					PreparedStatement pst = conn.prepareStatement(sql);) {
				//pst.setString(1, mailad);
				//pst.setString(2, password);
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

	public String conversion(String mailad, String password) {

		String hash = PasswordUtil.getSafetyPassword(password, mailad);

		String sql = "SELECT username FROM users WHERE hash = '" + hash + "'";

		try {
			Class.forName(DRIVER_NAME);
			try (Connection conn = DriverManager.getConnection(CONNECT_STRING, USERID, PASSWARD);
					PreparedStatement pst = conn.prepareStatement(sql);) {
				//pst.setString(1, mailad);
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

	public boolean checkUser(String username) {
		String sql = "SELECT username FROM users WHERE username = ?";

		try {
			Class.forName(DRIVER_NAME);
			try (Connection conn = DriverManager.getConnection(CONNECT_STRING, USERID, PASSWARD); //parameterDAO
					PreparedStatement pst = conn.prepareStatement(sql);) {
				pst.setString(1, username);
				ResultSet rs = pst.executeQuery();
				if (rs.next()) {
					return false; // DBに登録されていたらfalse
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true; // DBに登録されてなかったらtrue

	}

	public ArrayList<String> getUserList(String username, String roomname) {
		ArrayList<String> userlist = new ArrayList<String>();
		String sql = "SELECT distinct u.USERNAME FROM USERS u , ROOM r WHERE u.username != ? AND u.username NOT IN (SELECT username FROM room WHERE roomname = ?)";

		try {
			Class.forName(DRIVER_NAME);
			try (Connection conn = DriverManager.getConnection(CONNECT_STRING, USERID, PASSWARD); //parameterDAO
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

	public ArrayList<String> getDUserList(String username1) {
		ArrayList<String> userlist = new ArrayList<String>();
		String sql = "SELECT distinct u.USERNAME FROM USERS u , DROOM d WHERE u.username NOT IN (SELECT username FROM droom WHERE roomname IN (SELECT distinct roomname FROM droom WHERE username = ?)) AND u.username != ?";

		try {
			Class.forName(DRIVER_NAME);
			try (Connection conn = DriverManager.getConnection(CONNECT_STRING, USERID, PASSWARD); //parameterDAO
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

	public boolean canRegist(String username, String password, String mailad) {

		String hash = PasswordUtil.getSafetyPassword(password, mailad);

		String sql = "INSERT INTO users VALUES(?,'" + hash + "')";

		try {
			Class.forName(DRIVER_NAME);
			try (Connection conn = DriverManager.getConnection(CONNECT_STRING, USERID, PASSWARD);
					PreparedStatement pst = conn.prepareStatement(sql);) {
				pst.setString(1, username);
				//pst.setString(2, password);
				//pst.setString(3, mailad);
				int cnt = pst.executeUpdate();

				//Statement statement=conn.createStatement();
				//sql="INSERT INTO room VALUES('everyone' , username)";
				//statement.executeUpdate(sql);

				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;

	}

}