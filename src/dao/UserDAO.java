package dao;

import static parameter.DAOParameters.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

	public boolean canLogin(String mailad, String password) {

		String sql = "SELECT * FROM users WHERE mailad = ? AND password= ?";
		try {
			Class.forName(DRIVER_NAME);
			try (Connection conn = DriverManager.getConnection(CONNECT_STRING, USERID, PASSWARD);
					PreparedStatement pst = conn.prepareStatement(sql);) {
				pst.setString(1, mailad);
				pst.setString(2, password);
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
}