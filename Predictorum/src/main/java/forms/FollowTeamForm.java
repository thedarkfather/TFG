package forms;

import javax.validation.constraints.NotNull;

public class FollowTeamForm {
	
	private Integer teamId;

	@NotNull
	public Integer getTeamId() {
		return teamId;
	}

	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}
	
	

}
