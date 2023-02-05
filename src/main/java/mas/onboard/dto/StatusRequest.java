package mas.onboard.dto;

import java.util.List;

import lombok.Data;

@Data
public class StatusRequest {
	private List<Onboard> onboards;
}
