package mas.onboard.dto;

import java.util.List;

import lombok.Data;

@Data
public class StatusRequest {
	private String officerId;
	private List<Onboard> onboards;
}
