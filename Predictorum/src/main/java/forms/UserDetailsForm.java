package forms;

import java.util.Collection;

public class UserDetailsForm {
	
	private String username;
	//estadísticas
	private Integer sRPointsPercentaje;
	private Integer dRPointsPercentaje;
	private Integer hGPointsPercentaje;
	private Integer aGPointsPercentaje;
	private Integer mT25PointsPercentaje;
	private Integer rankingPosition;
	//seguidores y siguiendo
	private Integer followersNumber;
	private Integer followingNumber;
	//predicciones ordenadas por fecha
	private Collection<PredictionToListForm> predictions;
	
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
	
	public Collection<PredictionToListForm> getPredictions() {
		return predictions;
	}
	
	public void setPredictions(Collection<PredictionToListForm> predictions) {
		this.predictions = predictions;
	}	
}
