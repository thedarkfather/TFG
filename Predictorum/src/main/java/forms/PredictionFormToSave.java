package forms;

public class PredictionFormToSave {
	
	private Integer gameId;
	private String simpleResult;
	private String doubleResult;
	private Integer homeGoals;
	private Integer awayGoals;
	private Boolean moreThan25;
	
	public Integer getGameId() {
		return gameId;
	}
	
	public void setGameId(Integer gameId) {
		this.gameId = gameId;
	}
	
	public String getSimpleResult() {
		return simpleResult;
	}
	
	public void setSimpleResult(String simpleResult) {
		this.simpleResult = simpleResult;
	}
	
	public String getDoubleResult() {
		return doubleResult;
	}
	
	public void setDoubleResult(String doubleResult) {
		this.doubleResult = doubleResult;
	}
	
	public Integer getHomeGoals() {
		return homeGoals;
	}
	
	public void setHomeGoals(Integer homeGoals) {
		this.homeGoals = homeGoals;
	}
	
	public Integer getAwayGoals() {
		return awayGoals;
	}
	
	public void setAwayGoals(Integer awayGoals) {
		this.awayGoals = awayGoals;
	}
	
	public Boolean getMoreThan25() {
		return moreThan25;
	}
	
	public void setMoreThan25(Boolean moreThan25) {
		this.moreThan25 = moreThan25;
	}
	
}
