package dao;

import static parameter.DAOParameters.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class RegistDAO {

	public boolean canRegist(String username, String password, String mailad) {

		String sql = "INSERT INTO users VALUES(?,?,?)";

		try {
			Class.forName(DRIVER_NAME);
			try (Connection conn = DriverManager.getConnection(CONNECT_STRING, USERID, PASSWARD);
					PreparedStatement pst = conn.prepareStatement(sql);) {
				pst.setString(1, username);
				pst.setString(2, password);
				pst.setString(3, mailad);
				int cnt = pst.executeUpdate();
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;

	}
}
