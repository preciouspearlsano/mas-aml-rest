package mas.onboard.exception;

import java.util.Locale;

import lombok.Data;
import mas.onboard.config.i18nUtil;

/**
 * @author Precious Pearl A. Sano <pr3_cious_15@yahoo.com>
 *
 * 
 */
@Data
public class PayloadMsg {

	private String en;
	/**
	 * @param key
	 */
	public PayloadMsg(String key) {
		this.en = i18nUtil.getMessage(key, Locale.ENGLISH);
	}
	/**
	 * @param key
	 * @param args
	 */
	public PayloadMsg(String key, Object[] args) {
		this.en = i18nUtil.getMessage(key, args, Locale.ENGLISH);
	}

}