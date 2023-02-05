package mas.onboard.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import mas.onboard.dto.Onboard;
import mas.onboard.dto.StatusSearchRequest;

/**
 * @author Precious Pearl A. Sano <pr3_cious_15@yahoo.com>
 *
 * 
 */
@Mapper
public interface OnboardMapper {
	
	public List<Onboard> getExistingOnboard(@Param("search") StatusSearchRequest search);
}
