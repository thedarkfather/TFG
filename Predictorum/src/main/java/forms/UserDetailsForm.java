package forms;

import java.util.Collection;

public class UserDetailsForm {
	
	private Integer id;
	private String username;
	private String name;
	private String surname;
	private String email;
	private byte[] image;
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
	private Boolean following;
	//predicciones ordenadas por fecha
	private Collection<PredictionToListForm> predictions;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}
		
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
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
	
	public Boolean getFollowing() {
		return following;
	}

	public void setFollowing(Boolean following) {
		this.following = following;
	}

	public Collection<PredictionToListForm> getPredictions() {
		return predictions;
	}
	
	public void setPredictions(Collection<PredictionToListForm> predictions) {
		this.predictions = predictions;
	}	
}
