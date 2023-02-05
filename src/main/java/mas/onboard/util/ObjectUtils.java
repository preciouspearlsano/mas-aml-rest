package mas.onboard.util;

/**
 * @author Precious Pearl A. Sano <pr3_cious_15@yahoo.com>
 *
 * 
 */
public class ObjectUtils {
	
	public static String searchSafe(String value, String placeholder) {
		if (value == null || (value != null & value.trim().isEmpty())) {
			return placeholder;
		}
		return value;
	}
}
