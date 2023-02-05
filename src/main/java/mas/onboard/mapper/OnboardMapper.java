package mas.onboard.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import mas.onboard.dto.Doc;
import mas.onboard.dto.Onboard;
import mas.onboard.dto.StatusSearchRequest;
import mas.onboard.dto.TxnOnboard;

/**
 * @author Precious Pearl A. Sano <pr3_cious_15@yahoo.com>
 *
 * 
 */
@Mapper
public interface OnboardMapper {

	/**
	 * @param search
	 * @return List<Onboard>
	 */
	public List<Onboard> getExistingOnboard(StatusSearchRequest search);

	/**
	 * @param submissionId
	 * @
	 */
	public void cancelOnboard(StatusSearchRequest search);

	/**
	 * @param txnOb
	 */
	public void createOnboard(TxnOnboard txnOb);

	/**
	 * @param doc
	 */
	public void createOnboardDoc(Doc doc);

	/**
	 * @param search
	 * @return
	 */
	public List<Doc> getExistingOnboardDoc(StatusSearchRequest search);

	/**
	 * @param txnObUpdate
	 */
	public void updateOnboard(TxnOnboard txnObUpdate);

	/**
	 * @param doc
	 */
	public void deleteOnboardDoc(Doc doc);
}
