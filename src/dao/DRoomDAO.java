package dao;

import static parameter.DAOParameters.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DRoomDAO {

	public ArrayList<String> getDRoomList(String username) {
		ArrayList<String> droomlist = new ArrayList<String>();
		String sql = "SELECT ROOMNAME FROM DROOM WHERE  USERNAME =?";

		try {
			Class.forName(DRIVER_NAME);
			try (Connection conn = DriverManager.getConnection(CONNECT_STRING, USERID, PASSWARD); //parameterDAO
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

	public void saveDRoom(String roomname, String username1, String username2) {
		String sql = "INSERT ALL INTO droom VALUES(?,?) INTO droom VALUES(?,?) SELECT * FROM DUAL";

		try {
			Class.forName(DRIVER_NAME);
			try (Connection conn = DriverManager.getConnection(CONNECT_STRING, USERID, PASSWARD); //parameterDAO
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

	public boolean checkDRoom(String roomname) {
		String sql = "SELECT roomname FROM droom WHERE roomname = ?";

		try {
			Class.forName(DRIVER_NAME);
			try (Connection conn = DriverManager.getConnection(CONNECT_STRING, USERID, PASSWARD); //parameterDAO
					PreparedStatement pst = conn.prepareStatement(sql);) {
				pst.setString(1, roomname);
				ResultSet rs = pst.executeQuery();
				if (rs.next()) {
					return false; // DBに登録されていたらfalse
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true; // DBに登録されていなかったらtrue

	}

}
