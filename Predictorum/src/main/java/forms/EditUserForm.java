package forms;

import org.hibernate.validator.constraints.Email;

public class EditUserForm {
	
	private String email;
	private byte[] image;
	
	@Email
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public byte[] getImage() {
		return image;
	}
	
	public void setImage(byte[] image) {
		this.image = image;
	}
	
	

}
