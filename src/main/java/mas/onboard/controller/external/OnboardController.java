package mas.onboard.controller.external;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import mas.onboard.dto.OnboardSubmit;
import mas.onboard.dto.RequestSuccess;
import mas.onboard.dto.StatusRequest;
import mas.onboard.dto.StatusRequestView;
import mas.onboard.dto.StatusSearchRequest;
import mas.onboard.exception.OnboardException;
import mas.onboard.services.OnboardService;
import mas.onboard.utils.ObjectUtils;

/**
 * @author Precious Pearl A. Sano Ventura <pr3_cious_15@yahoo.com>
 *
 */
@RestController
@RequestMapping("/external/")
@Transactional
@Tag(name = "Onboard API Sample")
public class OnboardController  {
	
	private final OnboardService service;
	
	/**
	 * 
	 */
	@Autowired
	public OnboardController(OnboardService service) throws OnboardException {
		this.service = service;
	}
	
	@GetMapping("/onboard-list")
	@Operation(summary = "ONBOARD-LIST", description = "Get onboard list by officer id")
	public ResponseEntity<StatusRequestView> getOnboardList(
			@RequestHeader(name = "x-api-login-id", required=false) String loginId) throws OnboardException {
		return ResponseEntity.ok(service.getOnboardList(loginId));
	}
	
	@GetMapping("/onboard-load/{submissionId}")
	@Operation(summary = "ONBOARD-LOAD", description = "Get onboard information")
	public ResponseEntity<StatusRequest> loadOnboard(
			@RequestHeader(name = "x-api-login-id", required=false) String loginId,
			@PathVariable(name = "submissionId", required=false) String submissionId) throws OnboardException {
		StatusSearchRequest request = new StatusSearchRequest();
		request.setOfficerAcctId(loginId);
		request.setSubmissionId(submissionId);
		return ResponseEntity.ok(service.loadOnboard(request));
	}
	
	@PutMapping("/onboard-load/{submissionId}/cancel")
	@Operation(summary = "ONBOARD-LOAD-CANCEL", description = "Cancel onboard")
	@Transactional
	public ResponseEntity<RequestSuccess> cancelOnboard(
			@RequestHeader(name = "x-api-login-id", required=false) String loginId,
			@PathVariable(name = "submissionId", required=true) String submissionId) throws OnboardException {
		StatusSearchRequest request = new StatusSearchRequest();
		request.setOfficerAcctId(loginId);
		request.setSubmissionId(submissionId);
		return ResponseEntity.ok(service.cancelOnboard(request));
	}
	
	@GetMapping("/onboard-load/onboard-validation")
	@Operation(summary = "ONBOARD-VALIDATION", description = "Onboard validation return complete or incomplete")
	public ResponseEntity<RequestSuccess> onboardValidation(
			@RequestBody StatusRequest request) throws OnboardException {
		return ResponseEntity.ok(service.validationOnboard(request));
	}
	
	@PostMapping("/onboard-load/submission")
	@Operation(summary = "ONBOARD-SUBMISSION", description = "Onboard submit either incomplete or complete")
	@Transactional
	public ResponseEntity<OnboardSubmit> submitOnboard(
			@RequestHeader(name = "x-api-login-id", required=false) String loginId,
			@RequestBody StatusRequest request) throws OnboardException {
		request.setOfficerId(loginId);
		return ResponseEntity.ok(service.submitOnboard(request));
	}
			
	@PutMapping("/onboard-load/{submissionId}/submission")
	@Operation(summary = "ONBOARD-SUBMISSION-PUT", description = "Onboard submit either incomplete or complete existing submissionId")
	@Transactional
	public ResponseEntity<OnboardSubmit> submitOnboard(
			@RequestHeader(name = "x-api-login-id", required=false) String loginId,
			@PathVariable(name = "submissionId", required=false) String submissionId,
			@RequestBody StatusRequest request) throws OnboardException {
		return ResponseEntity.ok(service.submitOnboard(request));
	}
	
	@GetMapping("/onboard-load/status")
	@Operation(summary = "ONBOARD-STATUS", description = "Onboard status search")
	public ResponseEntity<StatusRequestView> getOnboardStatus(
		@RequestHeader(name = "x-api-login-id", required=false) String loginId,
		@RequestParam(name = "firstName", required=false) String firstName,
		@RequestParam(name = "middleName", required=false) String middleName,
		@RequestParam(name = "lastName", required=false) String lastName,
		@RequestParam(name = "submissionId", required=false) String submissionId,
		@RequestParam(name = "status", required=false) String status) throws OnboardException {
		StatusSearchRequest search = new StatusSearchRequest();
		search.setSubmissionId(ObjectUtils.searchSafe(submissionId,"%"));
		search.setFirstName(ObjectUtils.searchSafe(firstName,"%"));
		search.setMiddleName(ObjectUtils.searchSafe(middleName,"%"));
		search.setLastName(ObjectUtils.searchSafe(lastName,"%"));
		search.setStatus(ObjectUtils.searchSafe(status,"%"));
		return ResponseEntity.ok(service.getOnboardStatus(search));
	}
	
}
