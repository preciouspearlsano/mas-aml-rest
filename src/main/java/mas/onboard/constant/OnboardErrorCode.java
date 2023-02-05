package mas.onboard.constant;

/**
 * @author Precious Pearl A. Sano <pr3_cious_15@yahoo.com>
 *
 * 
 */
public enum OnboardErrorCode {

	EXOB_NO_RECORD("No record found"), 
	EXOB_INV_OWNER("Now owner of account");
	
	private final String message;
	
	private OnboardErrorCode() {
		this.message = "";
	}
	
	private OnboardErrorCode(String message) {
		this.message = message;
	}
	
	@SuppressWarnings("unused")
	private String getMessage() {
		return message;
	}
}
