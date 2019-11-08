package dao;

import static parameter.DAOParameters.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class RoomDAO {
	public ArrayList<String> getRoomList(String username) {
		ArrayList<String> roomlist = new ArrayList<String>();
		String sql = "SELECT ROOMNAME FROM ROOM WHERE  USERNAME =?";

		try {
			Class.forName(DRIVER_NAME);
			try (Connection conn = DriverManager.getConnection(CONNECT_STRING, USERID, PASSWARD); //parameterDAO
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

	public void saveRoom(String roomname, String username) {
		String sql = "INSERT INTO room VALUES(?,?)";

		try {
			Class.forName(DRIVER_NAME);
			try (Connection conn = DriverManager.getConnection(CONNECT_STRING, USERID, PASSWARD); //parameterDAO
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
