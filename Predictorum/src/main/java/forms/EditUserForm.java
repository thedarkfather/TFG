package forms;

import org.hibernate.validator.constraints.Email;

public class EditUserForm {

	private String email;
	private String password;
	private String repassword;
	
	@Email
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getRepassword() {
		return repassword;
	}
	
	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}
	
}
