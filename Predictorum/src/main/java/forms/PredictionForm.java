package forms;

public class PredictionForm {
	
	private Integer id;
	private Integer homeTeamId;
	private Integer awayTeamId;
	private String homeName;
	private String awayName;
	//Atributos de prediction
	private String simpleResult;
	private Double pSimpleResult;
	private String doubleResult;
	private Double pDoubleResult;
	private Integer homeGoals;
	private Double pHomeGoals;
	private Integer awayGoals;
	private Double pAwayGoals;
	private Boolean moreThan25;
	private Double pmoreThan25;	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getHomeTeamId() {
		return homeTeamId;
	}
	
	public void setHomeTeamId(Integer homeTeamId) {
		this.homeTeamId = homeTeamId;
	}
	
	public Integer getAwayTeamId() {
		return awayTeamId;
	}
	
	public void setAwayTeamId(Integer awayTeamId) {
		this.awayTeamId = awayTeamId;
	}
	
	public String getHomeName() {
		return homeName;
	}
	
	public void setHomeName(String homeName) {
		this.homeName = homeName;
	}
	
	public String getAwayName() {
		return awayName;
	}
	
	public void setAwayName(String awayName) {
		this.awayName = awayName;
	}
	
	public String getSimpleResult() {
		return simpleResult;
	}
	
	public void setSimpleResult(String simpleResult) {
		this.simpleResult = simpleResult;
	}
	
	public Double getpSimpleResult() {
		return pSimpleResult;
	}
	
	public void setpSimpleResult(Double pSimpleResult) {
		this.pSimpleResult = pSimpleResult;
	}
	
	public String getDoubleResult() {
		return doubleResult;
	}
	
	public void setDoubleResult(String doubleResult) {
		this.doubleResult = doubleResult;
	}
	
	public Double getpDoubleResult() {
		return pDoubleResult;
	}
	
	public void setpDoubleResult(Double pDoubleResult) {
		this.pDoubleResult = pDoubleResult;
	}

	
	public Integer getHomeGoals() {
		return homeGoals;
	}
	
	public void setHomeGoals(Integer homeGoals) {
		this.homeGoals = homeGoals;
	}
	
	public Double getpHomeGoals() {
		return pHomeGoals;
	}
	
	public void setpHomeGoals(Double pHomeGoals) {
		this.pHomeGoals = pHomeGoals;
	}
	
	public Integer getAwayGoals() {
		return awayGoals;
	}
	
	public void setAwayGoals(Integer awayGoals) {
		this.awayGoals = awayGoals;
	}
	
	public Double getpAwayGoals() {
		return pAwayGoals;
	}
	
	public void setpAwayGoals(Double pAwayGoals) {
		this.pAwayGoals = pAwayGoals;
	}

	public Boolean getMoreThan25() {
		return moreThan25;
	}

	public void setMoreThan25(Boolean moreThan25) {
		this.moreThan25 = moreThan25;
	}

	public Double getPmoreThan25() {
		return pmoreThan25;
	}

	public void setPmoreThan25(Double pmoreThan25) {
		this.pmoreThan25 = pmoreThan25;
	}		
	
}
