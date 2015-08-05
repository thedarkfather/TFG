package forms;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

public class CommentForm {
	
	private Integer parentId;
	private String text;
	private Integer predictionId;	
	
	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	@NotBlank
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	@NotNull
	public Integer getPredictionId() {
		return predictionId;
	}
	
	public void setPredictionId(Integer predictionId) {
		this.predictionId = predictionId;
	}
	
	

}
