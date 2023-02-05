package mas.onboard.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mas.onboard.constant.OnboardConstant;
import mas.onboard.constant.OnboardErrorCode;
import mas.onboard.dto.Doc;
import mas.onboard.dto.Onboard;
import mas.onboard.dto.OnboardSubmit;
import mas.onboard.dto.RequestSuccess;
import mas.onboard.dto.StatusRequest;
import mas.onboard.dto.StatusRequestView;
import mas.onboard.dto.StatusSearchRequest;
import mas.onboard.dto.TxnOnboard;
import mas.onboard.exception.OnboardException;
import mas.onboard.mapper.OnboardMapper;
import mas.onboard.utils.ObjectUtils;
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
		StatusRequestView response = new StatusRequestView();
		response.setOnboards(mapper.getExistingOnboard(search));
		response.getOnboards().stream().forEach(onboard -> {
			search.setSubmissionId(onboard.getSubmissionId());
			onboard.setDocs(mapper.getExistingOnboardDoc(search));
		});
		
		return response;
	}

	/**
	 * @param submissionId
	 * @return StatusRequest
	 */
	public StatusRequest loadOnboard(StatusSearchRequest search) throws OnboardException {
		StatusRequest response = new StatusRequest();
		response.setOnboards(mapper.getExistingOnboard(search));
		if (response.getOnboards().size()==0) throw new OnboardException(OnboardErrorCode.EXOB_NO_RECORD.name());
		return response;
	}

	/**
	 * @param submissionId
	 * @return RequestSuccess
	 */
	public RequestSuccess cancelOnboard(StatusSearchRequest search) throws OnboardException {
		RequestSuccess success = new RequestSuccess();
		success.setSuccess(true);
		success.setRemarks(OnboardConstant.NO_REMARKS);
		mapper.cancelOnboard(search);
		List<Onboard> onboards = mapper.getExistingOnboard(search);
		if (Objects.isNull(onboards) || (Objects.nonNull(search) && onboards.size() == 0)) {
			success.setSuccess(false);
			success.setRemarks(OnboardConstant.NO_RECORD_FOUND);
		}
		return success;
	}

	/**
	 * @param request
	 * @return 
	 * @return RequestSuccess
	 */
	public RequestSuccess validationOnboard(StatusRequest request) throws OnboardException {
		RequestSuccess response = new RequestSuccess();
		response.setSuccess(false);
		request.getOnboards().stream().forEach(onboard -> {
			if (Objects.nonNull(onboard) && Objects.nonNull(onboard.getDocs())) {
				onboard.getDocs().stream().forEach(doc -> {
					if (doc.getName().contains("nbi") && 
						doc.getName().contains("sss") && 
						doc.getName().contains("philhealth") && 
						doc.getName().contains("pagibig") && 
						doc.getName().contains("bir") && 
						doc.getName().contains("sketchmap") && 
						doc.getName().contains("brgy") && 
						doc.getName().contains("medical") && 
						doc.getName().contains("drugtest") && 
						doc.getName().contains("coe") && 
						doc.getName().contains("tor")) {
						response.setSuccess(true);
					}
				});
			}
		});
		return response;
	}

	/**
	 * @param request
	 * @return OnboardSubmit
	 */
	@Transactional
	public OnboardSubmit submitOnboard(StatusRequest request) throws OnboardException {
		OnboardSubmit response = new OnboardSubmit();
		
		List<TxnOnboard> txnObs = new ArrayList<TxnOnboard>();
		request.getOnboards().stream().forEach(onboard -> {

			StatusSearchRequest search = new StatusSearchRequest();
			search.setSubmissionId(onboard.getSubmissionId());

			StatusRequestView result = new StatusRequestView();
			result.setOnboards(mapper.getExistingOnboard(search));
			
			validationOwner(onboard,result);
			
			RequestSuccess status = validationOnboard(request);
			
			if (Objects.isNull(onboard.getSubmissionId())) {
				TxnOnboard txnOb = buildOnboard(onboard);
				txnOb.setOfficerAcctId(request.getOfficerId());
				txnObs.add(txnOb);
				
				onboard.getDocs().stream().forEach(doc -> {
					doc.setObAcctId(txnOb.getId());
					Doc docSubmit = builOnboardDoc(doc);
					mapper.createOnboardDoc(docSubmit);
				});

				String submissionId = txnOb.getId();
				
				txnOb.setStatus(getStatus(status));
				mapper.createOnboard(txnOb);
				
				response.setSubmissionId(submissionId);
				response.setCreationDate(new Date());
			} else {
				result.getOnboards().stream().forEach(onboardUpdate -> {
					TxnOnboard txnObUpdate = buildOnboard(onboardUpdate);
					mapper.updateOnboard(txnObUpdate);
					txnObs.add(txnObUpdate);
					
					onboard.getDocs().stream().forEach(doc -> {
						doc.setObAcctId(txnObUpdate.getId());
						mapper.deleteOnboardDoc(doc);
						Doc docSubmit = builOnboardDoc(doc);
						mapper.createOnboardDoc(docSubmit);
					});
					

					response.setSubmissionId(txnObUpdate.getId());
					response.setCreationDate(new Date());
				});
				
				
			}
			
		});
		return response;
	}

	/**
	 * @param doc
	 * @return
	 */
	private Doc builOnboardDoc(Doc doc) {
		Doc tmp = new Doc();
		tmp.setName(doc.getName());
		tmp.setObAcctId(doc.getObAcctId());
		return tmp;
	}




	/**
	 * @param status
	 * @return
	 */
	private String getStatus(RequestSuccess status) {
		if (status.isSuccess()) {
			return OnboardConstant.COMPLETED;
		} else {
			return OnboardConstant.IN_COMPLETED;
		}
	}



	/**
	 * @param onboard
	 * @param 
	 */
	private void validationOwner(Onboard onboard, StatusRequestView db) throws OnboardException {
		db.getOnboards().stream().forEach(dbOb -> {
			if (!dbOb.getOfficerAcctId().equalsIgnoreCase(onboard.getOfficerAcctId())
					&& Objects.nonNull(onboard.getSubmissionId())) {
				throw new OnboardException(OnboardErrorCode.EXOB_INV_OWNER.name());
			}
		});
		
	}



	/**
	 * @param onboard
	 * @return
	 */
	private TxnOnboard buildOnboard(Onboard request) {
		TxnOnboard onboard = new TxnOnboard();
		onboard.setId(UUID.randomUUID().toString());
		if (!ObjectUtils.nullSafe(request.getSubmissionId()).isEmpty()) {
			onboard.setId(request.getSubmissionId());
		}
		onboard.setOfficerAcctId(request.getOfficerAcctId());
		onboard.setFirstName(request.getFirstName());
		onboard.setMiddleName(request.getMiddleName());
		onboard.setLastName(request.getLastName());
		onboard.setStatus(OnboardConstant.COMPLETED);
		return onboard;
	}



	/**
	 * @param search
	 * @return StatusRequestView
	 */
	public StatusRequestView getOnboardStatus(StatusSearchRequest request) throws OnboardException {
		StatusSearchRequest search = new StatusSearchRequest();
		search.setSubmissionId(request.getSubmissionId());
		StatusRequestView response = new StatusRequestView();
		response.setOnboards(mapper.getExistingOnboard(search));
		response.getOnboards().stream().forEach(onboard -> {
			search.setSubmissionId(onboard.getSubmissionId());
			onboard.setDocs(mapper.getExistingOnboardDoc(search));
		});
		
		return response;
	}


}
