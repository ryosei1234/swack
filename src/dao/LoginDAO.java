package dao;

import static parameter.DAOParameters.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * 自動ログインデータにアクセスするクラス
 */
public class LoginDAO {

	/**
	 * セッションIDとメールアドレスとパスワードをDBに新規に登録する
	 *
	 * @param sessionid セッションID
	 * @param mailad メールアドレス
	 * @param password パスワード
	 */
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

	/**
	 * 受け取ったセッションIDが存在するメールアドレスとパスワードを返却する
	 *
	 * @param sessionid セッションID
	 * @return value メールアドレスとパスワードを持ったクラスのインスタンス変数
	 */
	public Calculation sesSerch(String sessionid) {

		String sql = "SELECT mailad , password FROM auth_login WHERE sessionid = ?";

		try {
			Class.forName(DRIVER_NAME);
			try (Connection conn = DriverManager.getConnection(CONNECT_STRING, USERID, PASSWARD); //parameterDAO
					PreparedStatement pst = conn.prepareStatement(sql);) {
				pst.setString(1, sessionid);
				ResultSet rs = pst.executeQuery();
				while (rs.next()) {
					String mailad = rs.getString("mailad");
					String password = rs.getString("password");

					Calculation calc = new Calculation();
					Calculation value = calc.calcMethod(mailad, password);

					return value;

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

}
