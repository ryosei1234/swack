package dao;

public class Calculation {

	String mailad;
	String password;

	public Calculation calcMethod(String mailad, String password) {
		Calculation calc = new Calculation();
		calc.mailad = mailad;
		calc.password = password;

		return calc;
	}
}
