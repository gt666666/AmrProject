package cn.mldn.util;

public class ValidatorUtil {
	public static boolean isString(String str) {
		if (str == null || "".equals(str)) {	// 验证失败
			return false ;
		}
		return true ;
	}
	public static boolean isInt(String str) {
		if (ValidatorUtil.isString(str)) {	// 数据不为空
			return str.matches("\\d+") ;
		}
		return false ;
	}
	public static boolean isDouble(String str) {
		if (ValidatorUtil.isString(str)) {	// 数据不为空
			return str.matches("\\d+(\\.\\d+)?") ;
		}
		return false ;
	}
	public static boolean isDate(String str) {
		if (ValidatorUtil.isString(str)) {	// 数据不为空
			return str.matches("\\d{4}-\\d{2}-\\d{2}") ;
		}
		return false ;
	}
	public static boolean isDatetime(String str) {
		if (ValidatorUtil.isString(str)) {	// 数据不为空
			return str.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}") ;
		}
		return false ;
	} 
	public static boolean isMime(String mimeRules [] ,String mime) {
		if (ValidatorUtil.isString(mime)) {	// 如果数据不为空
			for (int x = 0 ; x < mimeRules.length ; x ++) {
				if (mime.equalsIgnoreCase(mimeRules[x])) {
					return true ;
				}
			}
		}
		return false ; 
	}
}
