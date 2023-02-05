package mas.onboard.config;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;

/**
 * @author Precious Pearl A. Sano <pr3_cious_15@yahoo.com>
 *
 * 
 */
@Component
public class i18nUtil {

	private static final Logger logger = LoggerFactory.getLogger(i18nUtil.class);
	
	private static ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
	
	
	/**
	 * 
	 */
	public i18nUtil() {
		messageSource.setBasenames(
				"messages",
				"classpath:messages",
				"classpath:/messages");
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setCacheMillis(0);
	}
	
	/**
	 * @param key
	 * @param english default locale
	 * @return
	 */
	
	public static String getMessage(String key, Locale local) {
		return getMessageFromSourceByKeys(key, null, local);
	}

	/**
	 * @param key
	 * @param object
	 * @param local
	 * @return
	 */
	private static String getMessageFromSourceByKeys(String key, Object[] args, Locale locale) {
		try {
			return messageSource.getMessage(key, args, locale);
		} catch (NoSuchMessageException ns) {
			logger.debug(ns.getMessage());
			return key;
		}
		
	}

	/**
	 * @param key
	 * @param args
	 * @param english default locale
	 * @return
	 */
	public static String getMessage(String key, Object[] args, Locale locale) {
		try {
			return messageSource.getMessage(key, args, locale);
		} catch (NoSuchMessageException ns) {
			logger.debug(ns.getMessage());
			return key;
		}
	}

}
