package forms;

import java.util.Date;

public class PredictionToListForm {	
	private String awayTeamName;
	private String homeTeamName;
	private Integer homeTeamGoals;
	private Integer awayTeamGoals;
	private Date finishDate;
	private Integer id;
	
	public String getAwayTeamName() {
		return awayTeamName;
	}
	
	public void setAwayTeamName(String awayTeamName) {
		this.awayTeamName = awayTeamName;
	}
	
	public String getHomeTeamName() {
		return homeTeamName;
	}
	
	public void setHomeTeamName(String homeTeamName) {
		this.homeTeamName = homeTeamName;
	}
	
	public Integer getHomeTeamGoals() {
		return homeTeamGoals;
	}
	
	public void setHomeTeamGoals(Integer homeTeamGoals) {
		this.homeTeamGoals = homeTeamGoals;
	}
	
	public Integer getAwayTeamGoals() {
		return awayTeamGoals;
	}
	
	public void setAwayTeamGoals(Integer awayTeamGoals) {
		this.awayTeamGoals = awayTeamGoals;
	}
	
	public Date getFinishDate() {
		return finishDate;
	}
	
	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}	

}
