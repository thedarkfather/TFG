package forms;

import org.hibernate.validator.constraints.NotBlank;

public class JoinForm {
	
	private String username;
	private String password;
	private String rpassword;
	
	@NotBlank
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	@NotBlank
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	@NotBlank
	public String getRpassword() {
		return rpassword;
	}
	
	public void setRpassword(String rpassword) {
		this.rpassword = rpassword;
	}
	
	

}
