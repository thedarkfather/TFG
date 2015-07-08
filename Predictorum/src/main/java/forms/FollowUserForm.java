package forms;

import javax.validation.constraints.NotNull;

public class FollowUserForm {
	
	private Integer userId;

	@NotNull
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
}
