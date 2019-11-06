package security;

public class SecurityUtil {
	public static String getESCEncodingString(String str) {
		str = str.replaceAll("&", "&amp");
		str = str.replaceAll("<", "&lt;");
		str = str.replaceAll(">", "&gt;");
		str = str.replaceAll("\"", "&quot");
		str = str.replaceAll("\'", "&apos");
		return str;
	}
}
