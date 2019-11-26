package validation;

public class CheckParameter {

	public static boolean isNotEmpty(String name) {

		if (name.isEmpty()) {
			return false;
		}

		return true;
	}

}
