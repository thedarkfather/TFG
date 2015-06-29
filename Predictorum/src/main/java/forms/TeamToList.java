package forms;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

public class TeamToList {
	
	private String leagueName;
	private Integer teamId;
	private String teamName;
	private Integer teamPosition;
	private Boolean isFollow;
	
	@NotBlank
	public String getLeagueName() {
		return leagueName;
	}
	
	public void setLeagueName(String leagueName) {
		this.leagueName = leagueName;
	}
	
	@NotNull
	public Integer getTeamId() {
		return teamId;
	}
	
	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}
	
	@NotBlank
	public String getTeamName() {
		return teamName;
	}
	
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	
	@NotNull
	public Integer getTeamPosition() {
		return teamPosition;
	}
	
	public void setTeamPosition(Integer teamPosition) {
		this.teamPosition = teamPosition;
	}
	
	@NotNull
	public Boolean getIsFollow() {
		return isFollow;
	}
	
	public void setIsFollow(Boolean isFollow) {
		this.isFollow = isFollow;
	}
	
	

}
