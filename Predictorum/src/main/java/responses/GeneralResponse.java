package responses;

import java.util.Map;

public class GeneralResponse {
	
	private Boolean success;
	private Map<String, String> errors;
	
	public GeneralResponse(Boolean success, Map<String,String> errors){
		super();
		this.success = success;
		this.errors = errors;
	}	
	
	public Boolean getSuccess() {
		return success;
	}
	
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	
	public Map<String, String> getErrors() {
		return errors;
	}
	
	public void setErrors(Map<String, String> errors) {
		this.errors = errors;
	}
	
}
