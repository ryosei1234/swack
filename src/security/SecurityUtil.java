package security;

/**
 * セキュリティサーブレット<br>
 * クロスサイトスクリプティングへの対策を行うサーブレット<br>
 *
 * @param str 置換対象の文字列
 */
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
