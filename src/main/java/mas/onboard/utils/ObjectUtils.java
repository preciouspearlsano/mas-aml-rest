package mas.onboard.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
	
	public static String nullSafe(Object obj) {
		return Objects.toString(obj, "");
	}
	
	public static <T> List<T> nullSafeList(Object object) {
		return new ArrayList<T>();
	}
}
