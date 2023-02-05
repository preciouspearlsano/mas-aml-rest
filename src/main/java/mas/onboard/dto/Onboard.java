package mas.onboard.dto;

import java.util.List;

import lombok.Data;

@Data
public class Onboard {
	private String submissionId;
	private String officerAcctId;
	private String accountNo;
	private String firstName;
	private String middleName;
	private String lastName;
	private String status;
	private List<Doc> docs;
}
