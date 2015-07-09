package forms;

public class UserToRank {
	private int id;
	private String name;
	private Integer points;
	private Boolean following;
	private Integer position;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getPoints() {
		return points;
	}
	
	public void setPoints(Integer points) {
		this.points = points;
	}
	
	public Boolean getFollowing() {
		return following;
	}
	
	public void setFollowing(Boolean following) {
		this.following = following;
	}
	
	public Integer getPosition() {
		return position;
	}
	
	public void setPosition(Integer position) {
		this.position = position;
	}

}
