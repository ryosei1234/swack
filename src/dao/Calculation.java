package dao;

/**
 * 受け取った複数の値を複数の変数を持った1つのクラスにして返却する
 */

public class Calculation {

	public static String mailad;
	public static String password;

	/**
	 * 受け取った値をインスタンス変数に格納して返却する
	 *
	 * @param mailad メールアドレス
	 * @param password パスワード
	 * @return calc メールアドレスとパスワードが格納されたインスタンス変数
	 */
	public Calculation calcMethod(String mailad, String password) {
		Calculation calc = new Calculation();
		calc.mailad = mailad;
		calc.password = password;

		return calc;
	}
}
