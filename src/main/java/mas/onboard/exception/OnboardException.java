package mas.onboard.exception;

import java.io.Serializable;
import java.util.Locale;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;
import mas.onboard.config.i18nUtil;

/**
 * @author Precious Pearl A. Sano <pr3_cious_15@yahoo.com>
 *
 * 
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ResponseStatus(code = HttpStatus.EXPECTATION_FAILED)
public class OnboardException extends RuntimeException implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HttpStatus error = HttpStatus.EXPECTATION_FAILED;
	private transient Object code;
	private transient Object payload;
	private transient PayloadMsg msg;

	/**
	 * 
	 */
	public OnboardException() {
		super();
	}

	public OnboardException(Throwable cause) {
		super(cause);
	}
	
	public <T> OnboardException(String key) {
		super(i18nUtil.getMessage(key,Locale.ENGLISH));
		this.msg = new PayloadMsg(key);
	}
	
	public <T> OnboardException(String key, Throwable cause) {
		super(i18nUtil.getMessage(key,Locale.ENGLISH),cause);
		this.msg = new PayloadMsg(key);
	}
	
	public <T> OnboardException(String key, Object[] args) {
		super(i18nUtil.getMessage(key,args,Locale.ENGLISH));
		this.msg = new PayloadMsg(key,args);
	}
}
