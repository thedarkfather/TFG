package forms;

import javax.validation.constraints.NotNull;

public class EvaluationForm {
	
	private Integer commentId;
	private Boolean type;
	
	@NotNull
	public Integer getCommentId() {
		return commentId;
	}
	
	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}

	public Boolean getType() {
		return type;
	}

	public void setType(Boolean type) {
		this.type = type;
	}	

}
