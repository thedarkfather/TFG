package forms;

import java.util.Collection;
import java.util.Date;


public class CommentToListForm {
	
	private Integer id;
	private String text;
	private Integer posPoints;
	private Integer negPoints;
	private Date date;	
	private Collection<CommentToListForm> children;
	private Boolean evaluated;	
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public Integer getPosPoints() {
		return posPoints;
	}
	
	public void setPosPoints(Integer posPoints) {
		this.posPoints = posPoints;
	}
	
	public Integer getNegPoints() {
		return negPoints;
	}
	
	public void setNegPoints(Integer negPoints) {
		this.negPoints = negPoints;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Collection<CommentToListForm> getChildren() {
		return children;
	}
	
	public void setChildren(Collection<CommentToListForm> children) {
		this.children = children;
	}

	public Boolean getEvaluated() {
		return evaluated;
	}

	public void setEvaluated(Boolean evaluated) {
		this.evaluated = evaluated;
	}	
	
}
