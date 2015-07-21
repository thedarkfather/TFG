package forms;

import java.util.List;

public class UserDetailsForm {
	private String username;
	//estadísticas
	private Integer sRPointsPercentaje;
	private Integer dRPointsPercentaje;
	private Integer sHRPointsPercentaje;
	private Integer dHRPointsPercentaje;
	private Integer hGPointsPercentaje;
	private Integer aGPointsPercentaje;
	private Integer hHGPointsPercentaje;
	private Integer hAGPointsPercentaje;
	private Integer mT25PointsPercentaje;
	private Integer rankingPosition;
	//seguidores y siguiendo
	private Integer followersNumber;
	private Integer followingNumber;
	//predicciones ordenadas por fecha
	private List<PredictionToListForm> predictions;
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public Integer getsRPointsPercentaje() {
		return sRPointsPercentaje;
	}
	
	public void setsRPointsPercentaje(Integer sRPointsPercentaje) {
		this.sRPointsPercentaje = sRPointsPercentaje;
	}
	
	public Integer getdRPointsPercentaje() {
		return dRPointsPercentaje;
	}
	
	public void setdRPointsPercentaje(Integer dRPointsPercentaje) {
		this.dRPointsPercentaje = dRPointsPercentaje;
	}
	
	public Integer getsHRPointsPercentaje() {
		return sHRPointsPercentaje;
	}
	
	public void setsHRPointsPercentaje(Integer sHRPointsPercentaje) {
		this.sHRPointsPercentaje = sHRPointsPercentaje;
	}
	
	public Integer getdHRPointsPercentaje() {
		return dHRPointsPercentaje;
	}
	
	public void setdHRPointsPercentaje(Integer dHRPointsPercentaje) {
		this.dHRPointsPercentaje = dHRPointsPercentaje;
	}
	
	public Integer gethGPointsPercentaje() {
		return hGPointsPercentaje;
	}
	
	public void sethGPointsPercentaje(Integer hGPointsPercentaje) {
		this.hGPointsPercentaje = hGPointsPercentaje;
	}
	
	public Integer getaGPointsPercentaje() {
		return aGPointsPercentaje;
	}
	
	public void setaGPointsPercentaje(Integer aGPointsPercentaje) {
		this.aGPointsPercentaje = aGPointsPercentaje;
	}
	
	public Integer gethHGPointsPercentaje() {
		return hHGPointsPercentaje;
	}
	
	public void sethHGPointsPercentaje(Integer hHGPointsPercentaje) {
		this.hHGPointsPercentaje = hHGPointsPercentaje;
	}
	
	public Integer gethAGPointsPercentaje() {
		return hAGPointsPercentaje;
	}
	
	public void sethAGPointsPercentaje(Integer hAGPointsPercentaje) {
		this.hAGPointsPercentaje = hAGPointsPercentaje;
	}
	
	public Integer getmT25PointsPercentaje() {
		return mT25PointsPercentaje;
	}
	
	public void setmT25PointsPercentaje(Integer mT25PointsPercentaje) {
		this.mT25PointsPercentaje = mT25PointsPercentaje;
	}
	
	public Integer getRankingPosition() {
		return rankingPosition;
	}
	
	public void setRankingPosition(Integer rankingPosition) {
		this.rankingPosition = rankingPosition;
	}
	
	public Integer getFollowersNumber() {
		return followersNumber;
	}
	
	public void setFollowersNumber(Integer followersNumber) {
		this.followersNumber = followersNumber;
	}
	
	public Integer getFollowingNumber() {
		return followingNumber;
	}
	
	public void setFollowingNumber(Integer followingNumber) {
		this.followingNumber = followingNumber;
	}
	
	public List<PredictionToListForm> getPredictions() {
		return predictions;
	}
	
	public void setPredictions(List<PredictionToListForm> predictions) {
		this.predictions = predictions;
	}	
}
