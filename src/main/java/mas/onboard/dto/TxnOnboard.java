package mas.onboard.dto;

import lombok.Data;

/**
 * @author Precious Pearl A. Sano <pr3_cious_15@yahoo.com>
 *
 * 
 */
@Data
public class TxnOnboard {

	private String id;
	private String officerAcctId;
	private String accountNo;
	private String firstName;
	private String middleName;
	private String lastName;
	private String status;
}
