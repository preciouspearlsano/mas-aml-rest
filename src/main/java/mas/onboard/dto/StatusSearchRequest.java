package mas.onboard.dto;

import lombok.Data;

/**
 * @author Precious Pearl A. Sano <pr3_cious_15@yahoo.com>
 *
 * 
 */
@Data
public class StatusSearchRequest {
	private String submissionId;
	private String officerAcctId;
	private String firstName;
	private String middleName;
	private String lastName;
	private String status;
}
