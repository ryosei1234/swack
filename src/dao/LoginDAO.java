package dao;

import static parameter.DAOParameters.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginDAO {

	public void sesRegist(String sessionid, String mailad, String password) {

		String sql = "INSERT INTO auth_login VALUES(?,?,?)";

		try {
			Class.forName(DRIVER_NAME);
			try (Connection conn = DriverManager.getConnection(CONNECT_STRING, USERID, PASSWARD);
					PreparedStatement pst = conn.prepareStatement(sql);) {
				pst.setString(1, sessionid);
				pst.setString(2, mailad);
				pst.setString(3, password);
				int cnt = pst.executeUpdate();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public boolean sesSerch(String sessionid) {

		String sql = "SELECT mailad , password FROM auth_login WHERE sessionid = ?";

		try {
			Class.forName(DRIVER_NAME);
			try (Connection conn = DriverManager.getConnection(CONNECT_STRING, USERID, PASSWARD); //parameterDAO
					PreparedStatement pst = conn.prepareStatement(sql);) {
				pst.setString(1, sessionid);
				ResultSet rs = pst.executeQuery();
				while (rs.next()) {
					return true; // DBに登録されていたらtrue
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false; // DBに登録されてなかったらfalse

	}

}
