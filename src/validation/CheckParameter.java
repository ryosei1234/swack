package validation;

public class CheckParameter {
	/**
	 * CheckParameter<br>
	 * 値が入っているか確認するクラス。
	 *
	 * @param name
	 * @return true受け取った値が入っている。 false受け取った値が空。
	 */
	public static boolean isNotEmpty(String name) {

		if (name.isEmpty()) {
			return false;
		}

		return true;
	}

}
