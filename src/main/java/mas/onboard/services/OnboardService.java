package mas.onboard.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mas.onboard.constant.OnboardErrorCode;
import mas.onboard.dto.OnboardSubmit;
import mas.onboard.dto.RequestSuccess;
import mas.onboard.dto.StatusRequest;
import mas.onboard.dto.StatusRequestView;
import mas.onboard.dto.StatusSearchRequest;
import mas.onboard.exception.OnboardException;
import mas.onboard.mapper.OnboardMapper;

/**
 * @author Precious Pearl A. Sano Ventura <pr3_cious_15@yahoo.com>
 *
 */
@Service
public class OnboardService {

	
	private final OnboardMapper mapper;
	
	
	@Autowired
	public OnboardService(OnboardMapper mapper) {
		this.mapper = mapper;
	}
	
	

	/**
	 * @param loginId
	 * @return StatusRequestView
	 */
	public StatusRequestView getOnboardList(String loginId) throws OnboardException {
		StatusSearchRequest search = new StatusSearchRequest();
		search.setOfficerAcctId(loginId);
		StatusRequestView result = new StatusRequestView();
		result.setOnboards(mapper.getExistingOnboard(search));
		return result;
	}

	/**
	 * @param submissionId
	 * @return StatusRequest
	 */
	public StatusRequest loadOnboard(String submissionId) throws OnboardException {
		StatusSearchRequest search = new StatusSearchRequest();
		search.setSubmissionId(submissionId);
		StatusRequest result = new StatusRequest();
		result.setOnboards(mapper.getExistingOnboard(search));
		if (result.getOnboards().size()==0) throw new OnboardException(OnboardErrorCode.EXOB_NO_RECORD.name());
		return new StatusRequest();
	}

	/**
	 * @param submissionId
	 * @return RequestSuccess
	 */
	public RequestSuccess cancelOnboard(String submissionId) throws OnboardException {
		// TODO Auto-generated method stub
		return new RequestSuccess();
	}

	/**
	 * @param request
	 * @return RequestSuccess
	 */
	public RequestSuccess onboardValidation(StatusRequest request) throws OnboardException {
		// TODO Auto-generated method stub
		return new RequestSuccess();
	}

	/**
	 * @param request
	 * @return OnboardSubmit
	 */
	public OnboardSubmit submitOnboard(StatusRequest request) throws OnboardException {
		// TODO Auto-generated method stub
		return new OnboardSubmit();
	}

	/**
	 * @param search
	 * @return StatusRequestView
	 */
	public StatusRequestView getOnboardStatus(StatusSearchRequest search) throws OnboardException {
		// TODO Auto-generated method stub
		return null;
	}


}
